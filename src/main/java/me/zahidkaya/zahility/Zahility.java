package me.zahidkaya.zahility;

import me.zahidkaya.zahility.feature.freezing.FreezingImpactHandler;
import me.zahidkaya.zahility.feature.growth.GrowthImpactHandler;
import me.zahidkaya.zahility.feature.leveling.LevelingImpactHandler;
import me.zahidkaya.zahility.feature.repelling.RepellingImpactHandler;
import me.zahidkaya.zahility.feature.sponge.SpongeImpactHandler;
import me.zahidkaya.zahility.feature.terraform.TerraformImpactHandler;
import me.zahidkaya.zahility.registry.ModBlocks;
import me.zahidkaya.zahility.registry.ModCreativeTabs;
import me.zahidkaya.zahility.registry.ModDataComponents;
import me.zahidkaya.zahility.registry.ModItems;
import me.zahidkaya.zahility.registry.ModMenus;
import me.zahidkaya.zahility.registry.ModRecipeTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Zahility.MOD_ID)
public final class Zahility {

    public static final String MOD_ID = "zahility";

    public Zahility(
            IEventBus modEventBus
    ) {
        /*
         * ========================================
         * MOD KAYITLARI
         * ========================================
         *
         * Bağımlılık sırasına göre kaydedilir:
         *
         * 1. Data component'ler
         * 2. Bloklar
         * 3. Item'lar
         * 4. Tarif türleri ve serializer'lar
         * 5. Menüler
         * 6. Creative sekmesi
         */

        ModDataComponents.DATA_COMPONENTS.register(
                modEventBus
        );

        ModBlocks.BLOCKS.register(
                modEventBus
        );

        ModItems.ITEMS.register(
                modEventBus
        );

        ModRecipeTypes.RECIPE_TYPES.register(
                modEventBus
        );

        ModRecipeTypes.RECIPE_SERIALIZERS.register(
                modEventBus
        );

        ModMenus.MENUS.register(
                modEventBus
        );

        ModCreativeTabs.CREATIVE_TABS.register(
                modEventBus
        );

        /*
         * ========================================
         * OYUN EVENT LISTENER'LARI
         * ========================================
         */

        NeoForge.EVENT_BUS.addListener(
                TerraformImpactHandler::onProjectileImpact
        );

        NeoForge.EVENT_BUS.addListener(
                LevelingImpactHandler::onProjectileImpact
        );

        NeoForge.EVENT_BUS.addListener(
                SpongeImpactHandler::onProjectileImpact
        );

        NeoForge.EVENT_BUS.addListener(
                FreezingImpactHandler::onProjectileImpact
        );

        NeoForge.EVENT_BUS.addListener(
                GrowthImpactHandler::onProjectileImpact
        );

        NeoForge.EVENT_BUS.addListener(
                RepellingImpactHandler::onProjectileImpact
        );
    }
}