package me.zahidkaya.zahility.registry;

import me.zahidkaya.zahility.Zahility;
import me.zahidkaya.zahility.feature.workbench.ZahilityWorkbenchMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(
                    Registries.MENU,
                    Zahility.MOD_ID
            );

    public static final DeferredHolder<
            MenuType<?>,
            MenuType<ZahilityWorkbenchMenu>
    > ZAHILITY_WORKBENCH = MENUS.register(
            "zahility_workbench",
            () -> new MenuType<>(
                    ZahilityWorkbenchMenu::new,
                    FeatureFlags.DEFAULT_FLAGS
            )
    );

    private ModMenus() {
    }
}