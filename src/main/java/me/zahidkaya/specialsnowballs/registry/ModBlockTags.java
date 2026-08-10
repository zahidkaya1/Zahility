package me.zahidkaya.specialsnowballs.registry;

import me.zahidkaya.specialsnowballs.SpecialSnowballs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class ModBlockTags {
    public static final TagKey<Block> TERRAFORMABLE_BLOCKS = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(SpecialSnowballs.MOD_ID, "terraformable_blocks")
    );

    private ModBlockTags() {
    }
}
