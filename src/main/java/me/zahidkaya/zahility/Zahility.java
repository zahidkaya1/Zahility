package me.zahidkaya.zahility;

import me.zahidkaya.zahility.feature.leveling.LevelingImpactHandler;
import me.zahidkaya.zahility.feature.freezing.FreezingImpactHandler;
import me.zahidkaya.zahility.feature.sponge.SpongeImpactHandler;
import me.zahidkaya.zahility.feature.terraform.TerraformImpactHandler;
import me.zahidkaya.zahility.registry.ModCreativeTabs;
import me.zahidkaya.zahility.registry.ModDataComponents;
import me.zahidkaya.zahility.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Zahility.MOD_ID)
public final class Zahility {
    public static final String MOD_ID = "zahility";

    public Zahility(IEventBus modEventBus) {
        ModDataComponents.DATA_COMPONENTS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.addListener(TerraformImpactHandler::onProjectileImpact);
        NeoForge.EVENT_BUS.addListener(LevelingImpactHandler::onProjectileImpact);
        NeoForge.EVENT_BUS.addListener(SpongeImpactHandler::onProjectileImpact);
        NeoForge.EVENT_BUS.addListener(FreezingImpactHandler::onProjectileImpact);
    }
}