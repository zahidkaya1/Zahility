package me.zahidkaya.zahility.registry;

import me.zahidkaya.zahility.Zahility;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Zahility.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ZAHILITY_TAB =
            CREATIVE_TABS.register("special_snowballs", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.zahility"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.TERRAFORM_SNOWBALL.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {

                        output.accept(ModItems.TERRAFORM_SNOWBALL.get());
                        output.accept(ModItems.CREATIVE_TERRAFORM_SNOWBALL.get());

                        output.accept(ModItems.LEVELING_SNOWBALL.get());
                        output.accept(ModItems.CREATIVE_LEVELING_SNOWBALL.get());

                        output.accept(ModItems.SPONGE_SNOWBALL.get());
                        output.accept(ModItems.CREATIVE_SPONGE_SNOWBALL.get());
                    })
                    .build());

    private ModCreativeTabs() {
    }
}
