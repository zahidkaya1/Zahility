package me.zahidkaya.specialsnowballs;

import me.zahidkaya.specialsnowballs.gameplay.TerraformImpactHandler;
import me.zahidkaya.specialsnowballs.registry.ModCreativeTabs;
import me.zahidkaya.specialsnowballs.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(SpecialSnowballs.MOD_ID)
public final class SpecialSnowballs {
    public static final String MOD_ID = "specialsnowballs";

    public SpecialSnowballs(IEventBus modEventBus) {
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);
        NeoForge.EVENT_BUS.addListener(TerraformImpactHandler::onProjectileImpact);
    }
}
