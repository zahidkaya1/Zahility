package me.zahidkaya.zahility.feature.terraform;

import me.zahidkaya.zahility.registry.ModBlockTags;
import me.zahidkaya.zahility.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

public final class TerraformImpactHandler {
    // Balance values are centralized so we can tune them later without touching effect logic.
    private static final int SURVIVAL_RADIUS = 3;
    private static final int CREATIVE_RADIUS = 8;

    private TerraformImpactHandler() {
    }

    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof Snowball snowball)) {
            return;
        }

        if (!(snowball.level() instanceof ServerLevel level)) {
            return;
        }

        ItemStack projectileItem = snowball.getItem();
        int radius;
        boolean creativeVariant;

        if (projectileItem.is(ModItems.TERRAFORM_SNOWBALL.get())) {
            radius = SURVIVAL_RADIUS;
            creativeVariant = false;
        } else if (projectileItem.is(ModItems.CREATIVE_TERRAFORM_SNOWBALL.get())) {
            radius = CREATIVE_RADIUS;
            creativeVariant = true;
        } else {
            // Vanilla player snowballs, Snow Golem snowballs and snowballs from other mods are ignored.
            return;
        }

        Vec3 impactLocation = event.getRayTraceResult().getLocation();
        BlockPos center = BlockPos.containing(impactLocation);
        int changedBlocks = terraformNaturalBlocks(level, center, radius);

        // Do not play feedback when the snowball hits an area with no terraformable blocks.
        if (changedBlocks > 0) {
            playImpactFeedback(level, impactLocation, radius, changedBlocks, creativeVariant);
        }
    }

    private static int terraformNaturalBlocks(ServerLevel level, BlockPos center, int radius) {
        int radiusSquared = radius * radius;
        int changedBlocks = 0;

        for (BlockPos pos : BlockPos.betweenClosed(
                center.offset(-radius, -radius, -radius),
                center.offset(radius, radius, radius))) {

            int dx = pos.getX() - center.getX();
            int dy = pos.getY() - center.getY();
            int dz = pos.getZ() - center.getZ();

            if ((dx * dx) + (dy * dy) + (dz * dz) > radiusSquared) {
                continue;
            }

            BlockState state = level.getBlockState(pos);
            if (state.is(ModBlockTags.TERRAFORMABLE_BLOCKS)) {
                level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
                changedBlocks++;
            }
        }

        return changedBlocks;
    }

    private static void playImpactFeedback(
            ServerLevel level,
            Vec3 impactLocation,
            int radius,
            int changedBlocks,
            boolean creativeVariant) {

        int dirtParticleCount = Math.min(creativeVariant ? 120 : 45, 12 + (changedBlocks / 2));
        int greenParticleCount = Math.min(creativeVariant ? 45 : 18, 4 + (changedBlocks / 8));
        double spread = radius * 0.32D;

        BlockParticleOption dirtParticle = new BlockParticleOption(
                ParticleTypes.BLOCK,
                Blocks.DIRT.defaultBlockState()
        );

        level.sendParticles(
                dirtParticle,
                impactLocation.x,
                impactLocation.y,
                impactLocation.z,
                dirtParticleCount,
                spread,
                Math.max(0.35D, spread * 0.65D),
                spread,
                0.12D
        );

        level.sendParticles(
                ParticleTypes.HAPPY_VILLAGER,
                impactLocation.x,
                impactLocation.y + 0.15D,
                impactLocation.z,
                greenParticleCount,
                spread * 0.8D,
                Math.max(0.25D, spread * 0.45D),
                spread * 0.8D,
                0.04D
        );

        level.playSound(
                null,
                impactLocation.x,
                impactLocation.y,
                impactLocation.z,
                SoundEvents.GRASS_PLACE,
                SoundSource.BLOCKS,
                creativeVariant ? 1.15F : 0.85F,
                creativeVariant ? 0.82F : 1.02F
        );
    }
}