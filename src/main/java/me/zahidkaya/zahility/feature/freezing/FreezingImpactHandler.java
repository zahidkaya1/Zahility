package me.zahidkaya.zahility.feature.freezing;

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

public final class FreezingImpactHandler {

    /*
     * Normal Dondurucu Kar Topu:
     *
     * radius = 2
     * 2 + 1 + 2 = 5
     *
     * Sonuç: 5x5x5 alan.
     */
    private static final int NORMAL_RADIUS = 2;

    /*
     * Yaratıcı Dondurucu Kar Topu:
     *
     * radius = 5
     * 5 + 1 + 5 = 11
     *
     * Sonuç: 11x11x11 alan.
     */
    private static final int CREATIVE_RADIUS = 5;

    private FreezingImpactHandler() {
    }

    public static void onProjectileImpact(ProjectileImpactEvent event) {

        /*
         * Yalnızca Snowball entity'leriyle ilgileniyoruz.
         */
        if (!(event.getProjectile() instanceof Snowball snowball)) {
            return;
        }

        /*
         * Blok değişikliklerini yalnızca server tarafında yapıyoruz.
         */
        if (!(snowball.level() instanceof ServerLevel level)) {
            return;
        }

        ItemStack projectileStack = snowball.getItem();

        int radius;

        /*
         * Normal Dondurucu Kar Topu.
         */
        if (projectileStack.is(ModItems.FREEZING_SNOWBALL.get())) {

            radius = NORMAL_RADIUS;

        /*
         * Creative Dondurucu Kar Topu.
         */
        } else if (projectileStack.is(
                ModItems.CREATIVE_FREEZING_SNOWBALL.get()
        )) {

            radius = CREATIVE_RADIUS;

        /*
         * Vanilla kar topu veya başka bir projectile ise dokunma.
         */
        } else {
            return;
        }

        BlockPos center = snowball.blockPosition();

        /*
         * Belirlenen alan içindeki suyu buza çevir.
         */
        int frozenWater = freezeWater(
                level,
                center,
                radius
        );

        /*
         * Gerçekten su donduysa particle ve ses oynat.
         */
        if (frozenWater > 0) {

            playFreezeEffect(
                    level,
                    center,
                    radius
            );
        }

        /*
         * Sonucu oyuncunun action bar bölümünde göster.
         */
        if (snowball.getOwner() instanceof Player player) {

            if (frozenWater > 0) {

                player.displayClientMessage(
                        Component.translatable(
                                "message.zahility.freezing.success",
                                frozenWater
                        ),
                        true
                );

            } else {

                player.displayClientMessage(
                        Component.translatable(
                                "message.zahility.freezing.empty"
                        ),
                        true
                );
            }
        }
    }

    /*
     * Belirlenen küp içindeki normal ve akan suyu Ice yapar.
     *
     * Normal:
     * 5x5x5
     *
     * Creative:
     * 11x11x11
     */
    private static int freezeWater(
            ServerLevel level,
            BlockPos center,
            int radius
    ) {

        int frozenWater = 0;

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
                     * Su içermeyen bloklara dokunma.
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
                     * Waterlogged bloklara hiçbir şekilde dokunma.
                     *
                     * Merdiven, slab, fence ve trapdoor gibi bloklar
                     * hem kendisini hem içindeki suyu korur.
                     */
                    if (state.hasProperty(
                            BlockStateProperties.WATERLOGGED
                    ) && state.getValue(
                            BlockStateProperties.WATERLOGGED
                    )) {

                        continue;
                    }

                    /*
                     * Normal veya akan suyu Ice'a çevir.
                     *
                     * Su içinde bulunan seagrass ve kelp gibi bloklar da
                     * su hücresiyle birlikte Ice'a dönüşebilir.
                     */
                    if (level.setBlock(
                            pos,
                            Blocks.ICE.defaultBlockState(),
                            3
                    )) {

                        frozenWater++;
                    }
                }
            }
        }

        return frozenWater;
    }

    /*
     * Başarılı dondurma işleminin görsel ve ses geri bildirimi.
     */
    private static void playFreezeEffect(
            ServerLevel level,
            BlockPos center,
            int radius
    ) {

        boolean creative =
                radius == CREATIVE_RADIUS;

        /*
         * Donma hissi veren kar tanesi parçacıkları.
         */
        level.sendParticles(
                ParticleTypes.SNOWFLAKE,
                center.getX() + 0.5,
                center.getY() + 0.5,
                center.getZ() + 0.5,
                creative ? 120 : 40,
                radius * 0.55,
                radius * 0.45,
                radius * 0.55,
                creative ? 0.05 : 0.03
        );

        /*
         * Buzun oluşmasını temsil eden cam yerleştirme sesi.
         *
         * Creative sürüm biraz daha güçlü ve kalın duyulur.
         */
        level.playSound(
                null,
                center,
                SoundEvents.GLASS_PLACE,
                SoundSource.BLOCKS,
                creative ? 1.3F : 1.0F,
                creative ? 0.75F : 0.90F
        );
    }
}