package me.zahidkaya.zahility.registry;

import me.zahidkaya.zahility.Zahility;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class ModBlockTags {

    // Topraklaştırıcı Kar Topu'nun dönüştürebileceği doğal bloklar.
    public static final TagKey<Block> TERRAFORMABLE_BLOCKS = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(
                    Zahility.MOD_ID,
                    "terraformable_blocks"
            )
    );

    // Düzleştirici Kar Topu'nun doğal bitki örtüsü olarak kabul ettiği bloklar.
    public static final TagKey<Block> LEVELING_VEGETATION = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(
                    Zahility.MOD_ID,
                    "leveling_vegetation"
            )
    );

    private ModBlockTags() {
    }
}