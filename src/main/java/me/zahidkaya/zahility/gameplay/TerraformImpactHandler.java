package me.zahidkaya.zahility.gameplay;

import me.zahidkaya.zahility.registry.ModBlockTags;
import me.zahidkaya.zahility.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

public final class TerraformImpactHandler {
    // Balance values are centralized so we can tune them later without touching the effect logic.
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

        if (projectileItem.is(ModItems.TERRAFORM_SNOWBALL.get())) {
            radius = SURVIVAL_RADIUS;
        } else if (projectileItem.is(ModItems.CREATIVE_TERRAFORM_SNOWBALL.get())) {
            radius = CREATIVE_RADIUS;
        } else {
            // Vanilla player snowballs, Snow Golem snowballs and snowballs from other mods are ignored.
            return;
        }

        BlockPos center = BlockPos.containing(event.getRayTraceResult().getLocation());
        terraformNaturalBlocks(level, center, radius);
    }

    private static void terraformNaturalBlocks(ServerLevel level, BlockPos center, int radius) {
        int radiusSquared = radius * radius;

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
            }
        }
    }
}
