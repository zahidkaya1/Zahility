package me.zahidkaya.zahility.feature.growth;

import me.zahidkaya.zahility.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

public final class GrowthImpactHandler {

    /*
     * Normal Yeşertici Kar Topu:
     *
     * radius = 2
     * 2 + 1 + 2 = 5
     *
     * Sonuç: 5x5x5 alan.
     */
    private static final int NORMAL_RADIUS = 2;

    /*
     * Yaratıcı Yeşertici Kar Topu:
     *
     * radius = 5
     * 5 + 1 + 5 = 11
     *
     * Sonuç: 11x11x11 alan.
     */
    private static final int CREATIVE_RADIUS = 5;

    /*
     * Normal sürüm her uygun blok üzerinde
     * bir kemik tozu büyütme denemesi yapar.
     */
    private static final int NORMAL_GROWTH_ATTEMPTS = 1;

    /*
     * Creative sürüm her uygun blok üzerinde
     * üç kemik tozu büyütme denemesi yapar.
     */
    private static final int CREATIVE_GROWTH_ATTEMPTS = 3;

    private GrowthImpactHandler() {
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
        int growthAttempts;

        /*
         * Normal Yeşertici Kar Topu.
         */
        if (projectileStack.is(ModItems.GROWTH_SNOWBALL.get())) {

            radius = NORMAL_RADIUS;
            growthAttempts = NORMAL_GROWTH_ATTEMPTS;

        /*
         * Creative Yeşertici Kar Topu.
         */
        } else if (projectileStack.is(
                ModItems.CREATIVE_GROWTH_SNOWBALL.get()
        )) {

            radius = CREATIVE_RADIUS;
            growthAttempts = CREATIVE_GROWTH_ATTEMPTS;

        /*
         * Vanilla kar topu veya başka bir projectile ise dokunma.
         */
        } else {
            return;
        }

        BlockPos center = snowball.blockPosition();

        /*
         * Belirlenen alan içindeki uygun bitkileri büyüt.
         */
        int grownPlants = growPlants(
                level,
                center,
                radius,
                growthAttempts
        );

        /*
         * Gerçekten büyütme gerçekleştiyse particle ve ses oynat.
         */
        if (grownPlants > 0) {

            playGrowthEffect(
                    level,
                    center,
                    radius
            );
        }

        /*
         * Atan oyuncuya işlem sonucu hakkında bilgi ver.
         */
        if (snowball.getOwner() instanceof Player player) {

            if (grownPlants > 0) {

                player.displayClientMessage(
                        Component.literal(
                                "Gerçekleşen büyütme işlemi: "
                                        + grownPlants
                        ),
                        false
                );

            } else {

                player.displayClientMessage(
                        Component.literal(
                                "Büyütülebilecek bitki bulunamadı."
                        ),
                        false
                );
            }
        }
    }

    /*
     * Belirlenen küp içindeki kemik tozuyla büyüyebilen
     * blokları bulur ve büyütme denemesi yapar.
     */
    private static int growPlants(
            ServerLevel level,
            BlockPos center,
            int radius,
            int growthAttempts
    ) {

        int grownPlants = 0;

        int minBuildY = level.getMinBuildHeight();
        int maxBuildY = level.getMaxBuildHeight() - 1;

        RandomSource random = level.getRandom();

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

                    /*
                     * Normal sürüm bir kez, Creative sürüm
                     * üç kez büyütme girişimi yapar.
                     */
                    for (int attempt = 0;
                         attempt < growthAttempts;
                         attempt++) {

                        /*
                         * Önceki büyütme girişimi bloğu değiştirmiş
                         * olabileceği için state'i her defasında yenile.
                         */
                        BlockState state =
                                level.getBlockState(pos);

                        /*
                         * Yalnızca vanilla kemik tozu sistemini
                         * destekleyen bloklarla ilgilen.
                         */
                        if (!(state.getBlock()
                                instanceof BonemealableBlock bonemealable)) {

                            break;
                        }

                        /*
                         * Blok şu anda büyütülebilecek durumda değilse:
                         *
                         * - tamamen büyümüş ekin,
                         * - uygun olmayan fidan,
                         * - geçersiz konumdaki bitki
                         *
                         * gibi durumlarda işlem yapma.
                         */
                        if (!bonemealable.isValidBonemealTarget(
                                level,
                                pos,
                                state
                        )) {

                            break;
                        }

                        /*
                         * Vanilla kemik tozunun başarı ihtimalini koru.
                         */
                        if (!bonemealable.isBonemealSuccess(
                                level,
                                random,
                                pos,
                                state
                        )) {

                            continue;
                        }

                        /*
                         * Vanilla kemik tozu büyütme davranışını uygula.
                         */
                        bonemealable.performBonemeal(
                                level,
                                random,
                                pos,
                                state
                        );

                        grownPlants++;
                    }
                }
            }
        }

        return grownPlants;
    }

    /*
     * Başarılı büyütme işleminin görsel ve ses geri bildirimi.
     */
    private static void playGrowthEffect(
            ServerLevel level,
            BlockPos center,
            int radius
    ) {

        boolean creative =
                radius == CREATIVE_RADIUS;

        /*
         * Vanilla kemik tozuna benzeyen yeşil parçacıklar.
         */
        level.sendParticles(
                ParticleTypes.HAPPY_VILLAGER,
                center.getX() + 0.5,
                center.getY() + 0.5,
                center.getZ() + 0.5,
                creative ? 120 : 40,
                radius * 0.55,
                radius * 0.45,
                radius * 0.55,
                creative ? 0.08 : 0.05
        );

        /*
         * Vanilla kemik tozu kullanım sesi.
         */
        level.playSound(
                null,
                center,
                SoundEvents.BONE_MEAL_USE,
                SoundSource.BLOCKS,
                creative ? 1.2F : 1.0F,
                creative ? 0.85F : 1.0F
        );
    }
}