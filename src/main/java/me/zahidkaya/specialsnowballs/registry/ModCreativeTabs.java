package me.zahidkaya.specialsnowballs.registry;

import me.zahidkaya.specialsnowballs.SpecialSnowballs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SpecialSnowballs.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SPECIAL_SNOWBALLS_TAB =
            CREATIVE_TABS.register("special_snowballs", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.specialsnowballs"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.TERRAFORM_SNOWBALL.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.TERRAFORM_SNOWBALL.get());
                        output.accept(ModItems.CREATIVE_TERRAFORM_ORB.get());
                    })
                    .build());

    private ModCreativeTabs() {
    }
}
