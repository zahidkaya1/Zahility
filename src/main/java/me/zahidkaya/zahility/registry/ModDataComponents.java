package me.zahidkaya.zahility.registry;

import com.mojang.serialization.Codec;
import me.zahidkaya.zahility.Zahility;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModDataComponents {

    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Zahility.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> LEVELING_HEIGHT =
            DATA_COMPONENTS.registerComponentType(
                    "leveling_height",
                    builder -> builder.persistent(Codec.INT)
            );

    private ModDataComponents() {
    }
}