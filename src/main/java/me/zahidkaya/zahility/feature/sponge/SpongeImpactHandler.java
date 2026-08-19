package me.zahidkaya.zahility.feature.sponge;

import me.zahidkaya.zahility.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

public final class SpongeImpactHandler {

    /*
     * Normal Sünger Kar Topu:
     *
     * radius = 2
     * 2 + 1 + 2 = 5
     *
     * Sonuç: 5x5x5 alan.
     */
    private static final int NORMAL_RADIUS = 2;

    /*
     * Yaratıcı Sünger Kar Topu:
     *
     * radius = 5
     * 5 + 1 + 5 = 11
     *
     * Sonuç: 11x11x11 alan.
     */
    private static final int CREATIVE_RADIUS = 5;

    private SpongeImpactHandler() {
    }

    public static void onProjectileImpact(ProjectileImpactEvent event) {

        /*
         * Yalnızca Snowball entity'leriyle ilgileniyoruz.
         */
        if (!(event.getProjectile() instanceof Snowball snowball)) {
            return;
        }

        /*
         * Dünya değişikliklerini yalnızca server tarafında yapıyoruz.
         */
        if (!(snowball.level() instanceof ServerLevel level)) {
            return;
        }

        ItemStack projectileStack = snowball.getItem();

        int radius;

        /*
         * Normal Sünger Kar Topu.
         */
        if (projectileStack.is(ModItems.SPONGE_SNOWBALL.get())) {

            radius = NORMAL_RADIUS;

        /*
         * Creative Sünger Kar Topu.
         */
        } else if (projectileStack.is(
                ModItems.CREATIVE_SPONGE_SNOWBALL.get()
        )) {

            radius = CREATIVE_RADIUS;

        /*
         * Vanilla Snowball veya başka bir projectile ise hiçbir şey yapma.
         */
        } else {
            return;
        }

        BlockPos center = snowball.blockPosition();

        /*
         * Su temizleme işlemini gerçekleştir.
         *
         * Dönen sayı kaç su hücresinin temizlendiğini gösterir.
         */
        int absorbedWater = absorbWater(
                level,
                center,
                radius
        );

        /*
         * Gerçekten su bulunduysa efekt ve ses oynat.
         *
         * Boş araziye atılırsa boş yere efekt çıkmaz.
         */
        if (absorbedWater > 0) {

            playAbsorbEffect(
                    level,
                    center,
                    radius
            );
        }

        /*
         * Atan oyuncuya kısa bilgi mesajı.
         */
        if (snowball.getOwner() instanceof Player player) {

            if (absorbedWater > 0) {

                player.displayClientMessage(
                        Component.literal(
                                "Emilen su hücresi: "
                                        + absorbedWater
                        ),
                        false
                );

            } else {

                player.displayClientMessage(
                        Component.literal(
                                "Emilecek su bulunamadı."
                        ),
                        false
                );
            }
        }
    }

    /*
     * Belirlenen küp içindeki suyu temizler.
     *
     * Normal:
     * 5x5x5
     *
     * Creative:
     * 11x11x11
     */
    private static int absorbWater(
            ServerLevel level,
            BlockPos center,
            int radius
    ) {

        int absorbedWater = 0;

        int minBuildY = level.getMinBuildHeight();
        int maxBuildY = level.getMaxBuildHeight() - 1;

        BlockPos.MutableBlockPos pos =
                new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {

            for (int dy = -radius; dy <= radius; dy++) {

                for (int dz = -radius; dz <= radius; dz++) {

                    int x = center.getX() + dx;
                    int y = center.getY() + dy;
                    int z = center.getZ() + dz;

                    /*
                     * Dünya yükseklik sınırlarının dışına çıkma.
                     */
                    if (y < minBuildY || y > maxBuildY) {
                        continue;
                    }

                    pos.set(x, y, z);

                    BlockState state =
                            level.getBlockState(pos);

                    /*
                     * Bu blok/hücre su içermiyorsa dokunma.
                     *
                     * Bu kontrol sayesinde:
                     *
                     * - Lava etkilenmez.
                     * - Ice etkilenmez.
                     * - Packed Ice etkilenmez.
                     * - Blue Ice etkilenmez.
                     */
                    if (!state.getFluidState().is(FluidTags.WATER)) {
                        continue;
                    }

                    /*
                     * ====================================
                     * WATERLOGGED BLOKLAR
                     * ====================================
                     *
                     * Örneğin:
                     *
                     * - merdiven
                     * - slab
                     * - fence
                     * - trapdoor
                     *
                     * gibi bir blok su içeriyorsa bloğu yok etmiyoruz.
                     *
                     * Sadece:
                     *
                     * waterlogged = false
                     *
                     * yapıyoruz.
                     */
                    if (state.hasProperty(
                            BlockStateProperties.WATERLOGGED
                    ) && state.getValue(
                            BlockStateProperties.WATERLOGGED
                    )) {

                        BlockState dryState =
                                state.setValue(
                                        BlockStateProperties.WATERLOGGED,
                                        false
                                );

                        if (level.setBlock(
                                pos,
                                dryState,
                                3
                        )) {

                            absorbedWater++;
                        }

                        continue;
                    }

                    /*
                     * ====================================
                     * NORMAL / AKAN SU
                     * ====================================
                     *
                     * Hücre su içeriyor fakat waterlogged bir blok değilse
                     * suyu tamamen kaldırıyoruz.
                     *
                     * Seagrass, kelp vb. doğrudan suyun içinde yaşayan
                     * bloklar da bu işlem sırasında kaldırılabilir.
                     */
                    if (level.setBlock(
                            pos,
                            Blocks.AIR.defaultBlockState(),
                            3
                    )) {

                        absorbedWater++;
                    }
                }
            }
        }

        return absorbedWater;
    }

    /*
     * Başarılı emme işleminin görsel ve ses geri bildirimi.
     */
    private static void playAbsorbEffect(
            ServerLevel level,
            BlockPos center,
            int radius
    ) {

        boolean creative =
                radius == CREATIVE_RADIUS;

        /*
         * Su sıçrama parçacıkları.
         */
        level.sendParticles(
                ParticleTypes.SPLASH,
                center.getX() + 0.5,
                center.getY() + 0.5,
                center.getZ() + 0.5,
                creative ? 90 : 35,
                radius * 0.55,
                radius * 0.45,
                radius * 0.55,
                0.08
        );

        /*
         * Birkaç bubble particle emilme hissini güçlendirir.
         */
        level.sendParticles(
                ParticleTypes.BUBBLE,
                center.getX() + 0.5,
                center.getY() + 0.5,
                center.getZ() + 0.5,
                creative ? 35 : 15,
                radius * 0.40,
                radius * 0.35,
                radius * 0.40,
                0.03
        );

        /*
         * Vanilla Sponge emme sesi.
         */
        level.playSound(
                null,
                center,
                SoundEvents.SPONGE_ABSORB,
                SoundSource.BLOCKS,
                creative ? 1.2F : 1.0F,
                creative ? 0.90F : 1.0F
        );
    }
}