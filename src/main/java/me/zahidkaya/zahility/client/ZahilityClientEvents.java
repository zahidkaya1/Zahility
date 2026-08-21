package me.zahidkaya.zahility.client;

import me.zahidkaya.zahility.Zahility;
import me.zahidkaya.zahility.client.workbench.ZahilityRecipeBookEnums;
import me.zahidkaya.zahility.client.workbench.ZahilityWorkbenchScreen;
import me.zahidkaya.zahility.feature.workbench.ZahilityWorkbenchRecipe;
import me.zahidkaya.zahility.registry.ModMenus;
import me.zahidkaya.zahility.registry.ModRecipeTypes;
import net.minecraft.client.RecipeBookCategories;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;

import java.util.List;

@EventBusSubscriber(
        modid = Zahility.MOD_ID,
        value = Dist.CLIENT
)
public final class ZahilityClientEvents {

    private ZahilityClientEvents() {
    }

    /*
     * Zahility Tezgâhı ekranını özel menüye bağlar.
     */
    @SubscribeEvent
    public static void registerMenuScreens(
            RegisterMenuScreensEvent event
    ) {
        event.register(
                ModMenus.ZAHILITY_WORKBENCH.get(),
                ZahilityWorkbenchScreen::new
        );
    }

    /*
     * Zahility Tezgâhı'nın vanilla tarif kitabı
     * kategorilerini kaydeder.
     */
    @SubscribeEvent
    public static void registerRecipeBookCategories(
            RegisterRecipeBookCategoriesEvent event
    ) {
        RecipeBookCategories search =
                ZahilityRecipeBookEnums.searchCategory();

        RecipeBookCategories materials =
                ZahilityRecipeBookEnums.materialsCategory();

        RecipeBookCategories initial =
                ZahilityRecipeBookEnums.initialCategory();

        RecipeBookCategories duplication =
                ZahilityRecipeBookEnums.duplicationCategory();

        RecipeBookCategories infinite =
                ZahilityRecipeBookEnums.infiniteCategory();

        /*
         * Tarif kitabının sol tarafında görünecek
         * kategori sekmeleri.
         */
        event.registerBookCategories(
                ZahilityRecipeBookEnums.workbenchType(),
                List.of(
                        search,
                        materials,
                        initial,
                        duplication,
                        infinite
                )
        );

        /*
         * Arama kategorisi diğer bütün Zahility
         * kategorilerinin birleşimidir.
         */
        event.registerAggregateCategory(
                search,
                List.of(
                        materials,
                        initial,
                        duplication,
                        infinite
                )
        );

        /*
         * JSON tariflerindeki category alanını
         * ilgili tarif kitabı sekmesine dönüştürür.
         */
        event.registerRecipeCategoryFinder(
                ModRecipeTypes.WORKBENCH_CRAFTING.get(),
                recipeHolder -> {
                    if (!(recipeHolder.value()
                            instanceof ZahilityWorkbenchRecipe recipe)) {

                        return materials;
                    }

                    return switch (
                            recipe.getWorkbenchCategory()
                    ) {
                        case "workbench_material" ->
                                materials;

                        case "snowball_initial" ->
                                initial;

                        case "snowball_duplication" ->
                                duplication;

                        case "snowball_infinite" ->
                                infinite;

                        default ->
                                materials;
                    };
                }
        );
    }
}