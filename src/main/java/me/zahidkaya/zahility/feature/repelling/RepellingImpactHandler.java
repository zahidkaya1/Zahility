package me.zahidkaya.zahility.feature.repelling;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import me.zahidkaya.zahility.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

import java.util.Comparator;
import java.util.List;

public final class RepellingImpactHandler {

    /*
     * Normal Kovucu Kar Topu:
     *
     * 4 blok yarıçap içinde en fazla 5 sıradan düşmanı etkiler.
     */
    private static final int NORMAL_RADIUS = 4;
    private static final int NORMAL_MAX_TARGETS = 5;

    /*
     * Yaratıcı Kovucu Kar Topu:
     *
     * 16 blok yarıçap içindeki bütün uygun düşmanları siler.
     */
    private static final int CREATIVE_RADIUS = 16;

    /*
     * Normal sürümün bekleme süresi:
     *
     * 20 tick = 1 saniye
     * 100 tick = 5 saniye
     */
    private static final int NORMAL_COOLDOWN_TICKS = 100;

    /*
     * Normal sürümün verdiği etkilerin süresi:
     *
     * 160 tick = 8 saniye
     */
    private static final int EFFECT_DURATION_TICKS = 160;

    /*
     * Savurma gücü.
     */
    private static final double KNOCKBACK_STRENGTH = 1.6;
    private static final double KNOCKBACK_UPWARD = 0.45;

    private RepellingImpactHandler() {
    }

    public static void onProjectileImpact(ProjectileImpactEvent event) {

        /*
         * Yalnızca Snowball entity'leriyle ilgileniyoruz.
         */
        if (!(event.getProjectile() instanceof Snowball snowball)) {
            return;
        }

        /*
         * Entity değişikliklerini yalnızca server tarafında yapıyoruz.
         */
        if (!(snowball.level() instanceof ServerLevel level)) {
            return;
        }

        ItemStack projectileStack = snowball.getItem();

        boolean creative;
        int radius;

        /*
         * Normal Kovucu Kar Topu.
         */
        if (projectileStack.is(ModItems.REPELLING_SNOWBALL.get())) {

            creative = false;
            radius = NORMAL_RADIUS;

        /*
         * Creative Kovucu Kar Topu.
         */
        } else if (projectileStack.is(
                ModItems.CREATIVE_REPELLING_SNOWBALL.get()
        )) {

            creative = true;
            radius = CREATIVE_RADIUS;

        /*
         * Vanilla kar topu veya başka bir projectile ise dokunma.
         */
        } else {
            return;
        }

        BlockPos center = snowball.blockPosition();

        /*
         * Etki alanındaki uygun düşmanları bul.
         */
        List<LivingEntity> targets = findTargets(
                level,
                center,
                radius
        );



        int affectedTargets;

        if (creative) {

            /*
             * Creative sürüm bütün uygun hedefleri dünyadan kaldırır.
             */
            affectedTargets = removeTargets(targets);

        } else {

            /*
             * En yakın hedefler önce etkilensin.
             */
            targets.sort(
                    Comparator.comparingDouble(
                            target -> target.distanceToSqr(
                                    center.getX() + 0.5,
                                    center.getY() + 0.5,
                                    center.getZ() + 0.5
                            )
                    )
            );

            /*
             * Normal sürüm en fazla 5 hedefi etkiler.
             */
            if (targets.size() > NORMAL_MAX_TARGETS) {

                targets = targets.subList(
                        0,
                        NORMAL_MAX_TARGETS
                );
            }

            affectedTargets = repelTargets(
                    targets,
                    center
            );


            /*
             * Normal sürüme 5 saniyelik bekleme süresi ekle.
             */
            if (snowball.getOwner() instanceof Player player) {

                player.getCooldowns().addCooldown(
                        ModItems.REPELLING_SNOWBALL.get(),
                        NORMAL_COOLDOWN_TICKS
                );
            }
        }

        /*
         * Gerçekten bir düşman etkilendiyse particle ve ses oynat.
         */
        if (affectedTargets > 0) {

            playRepellingEffect(
                    level,
                    center,
                    radius,
                    creative
            );
        }

        /*
         * Sonucu oyuncunun action bar bölümünde göster.
         */
        if (snowball.getOwner() instanceof Player player) {

            if (affectedTargets > 0) {

                String messageKey =
                        creative
                                ? "message.zahility.repelling.creative_success"
                                : "message.zahility.repelling.normal_success";

                player.displayClientMessage(
                        Component.translatable(
                                messageKey,
                                affectedTargets
                        ),
                        true
                );

            } else {

                player.displayClientMessage(
                        Component.translatable(
                                "message.zahility.repelling.empty"
                        ),
                        true
                );
            }
        }
    }

    /*
     * Belirlenen yarıçap içindeki uygun düşmanları bulur.
     */
    private static List<LivingEntity> findTargets(
            ServerLevel level,
            BlockPos center,
            int radius
    ) {

        Vec3 centerPosition =
                Vec3.atCenterOf(center);

        AABB searchArea =
                new AABB(center).inflate(radius);

        double radiusSquared =
                (double) radius * radius;

        return level.getEntitiesOfClass(
                LivingEntity.class,
                searchArea,
                target -> target instanceof Enemy
                        && !isProtectedTarget(target)
                        && target.distanceToSqr(centerPosition)
                        <= radiusSquared
        );
    }

    /*
     * Korunması gereken özel hedefleri belirler.
     */
    private static boolean isProtectedTarget(
            LivingEntity target
    ) {

        /*
         * İsim etiketi verilmiş yaratıkları koru.
         */
        if (target.hasCustomName()) {
            return true;
        }

        /*
         * Raid yaratıklarını koru.
         */
        if (target instanceof Raider) {
            return true;
        }

        /*
         * Warden ve bossları koru.
         */
        return target instanceof Warden
                || target instanceof WitherBoss
                || target instanceof EnderDragon;
    }

    /*
     * Normal sürüm:
     *
     * Hedefleri merkezden uzağa savurur,
     * Yavaşlık II ve Zayıflık I verir.
     */
    private static int repelTargets(
            List<LivingEntity> targets,
            BlockPos center
    ) {

        Vec3 centerPosition =
                Vec3.atCenterOf(center);

        int affectedTargets = 0;

        for (LivingEntity target : targets) {

            double deltaX =
                    target.getX() - centerPosition.x;

            double deltaZ =
                    target.getZ() - centerPosition.z;

            double horizontalDistance =
                    Math.sqrt(
                            deltaX * deltaX
                                    + deltaZ * deltaZ
                    );

            /*
             * Hedef tam merkezdeyse sıfıra bölmeyi önle
             * ve sabit bir yön kullan.
             */
            if (horizontalDistance < 0.001) {

                deltaX = 1.0;
                deltaZ = 0.0;
                horizontalDistance = 1.0;
            }

            target.push(
                    deltaX / horizontalDistance
                            * KNOCKBACK_STRENGTH,
                    KNOCKBACK_UPWARD,
                    deltaZ / horizontalDistance
                            * KNOCKBACK_STRENGTH
            );

            /*
             * Yavaşlık II:
             *
             * amplifier 1 = seviye II
             */
            target.addEffect(
                    new MobEffectInstance(
                            MobEffects.MOVEMENT_SLOWDOWN,
                            EFFECT_DURATION_TICKS,
                            1
                    )
            );

            /*
             * Zayıflık I:
             *
             * amplifier 0 = seviye I
             */
            target.addEffect(
                    new MobEffectInstance(
                            MobEffects.WEAKNESS,
                            EFFECT_DURATION_TICKS,
                            0
                    )
            );

            affectedTargets++;
        }

        return affectedTargets;
    }

    /*
     * Creative sürüm:
     *
     * Bütün uygun hedefleri eşya ve XP bırakmadan
     * doğrudan dünyadan kaldırır.
     */
    private static int removeTargets(
            List<LivingEntity> targets
    ) {

        int removedTargets = 0;

        for (LivingEntity target : targets) {

            target.discard();
            removedTargets++;
        }

        return removedTargets;
    }


    /*
     * Başarılı kovma işleminin görsel ve ses geri bildirimi.
     */
    private static void playRepellingEffect(
            ServerLevel level,
            BlockPos center,
            int radius,
            boolean creative
    ) {

        if (creative) {

            /*
             * Creative sürümde düşmanların dünyadan
             * silinmesini temsil eden portal parçacıkları.
             */
            level.sendParticles(
                    ParticleTypes.REVERSE_PORTAL,
                    center.getX() + 0.5,
                    center.getY() + 0.5,
                    center.getZ() + 0.5,
                    180,
                    radius * 0.45,
                    radius * 0.35,
                    radius * 0.45,
                    0.12
            );

            level.playSound(
                    null,
                    center,
                    SoundEvents.ENDERMAN_TELEPORT,
                    SoundSource.HOSTILE,
                    1.3F,
                    0.75F
            );

        } else {

            /*
             * Normal sürümde dışarı doğru yayılan
             * savurma darbesini temsil eden bulut parçacıkları.
             */
            level.sendParticles(
                    ParticleTypes.POOF,
                    center.getX() + 0.5,
                    center.getY() + 0.5,
                    center.getZ() + 0.5,
                    55,
                    radius * 0.55,
                    radius * 0.30,
                    radius * 0.55,
                    0.10
            );

            level.playSound(
                    null,
                    center,
                    SoundEvents.WIND_CHARGE_BURST.value(),
                    SoundSource.HOSTILE,
                    1.0F,
                    0.90F
            );
        }
    }


}