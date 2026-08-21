package me.zahidkaya.zahility.registry;

import me.zahidkaya.zahility.Zahility;
import me.zahidkaya.zahility.feature.workbench.ZahilityWorkbenchBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Zahility.MOD_ID);

    public static final DeferredBlock<ZahilityWorkbenchBlock>
            ZAHILITY_WORKBENCH = BLOCKS.register(
                    "zahility_workbench",
                    () -> new ZahilityWorkbenchBlock(
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.WOOD)
                                    .strength(2.5F)
                                    .sound(SoundType.WOOD)
                    )
            );

    private ModBlocks() {
    }
}