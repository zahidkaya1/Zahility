package me.zahidkaya.zahility.registry;

import me.zahidkaya.zahility.Zahility;
import me.zahidkaya.zahility.feature.workbench.ZahilityWorkbenchRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(
                    Registries.RECIPE_TYPE,
                    Zahility.MOD_ID
            );

    public static final DeferredRegister<RecipeSerializer<?>>
            RECIPE_SERIALIZERS = DeferredRegister.create(
                    Registries.RECIPE_SERIALIZER,
                    Zahility.MOD_ID
            );

    /*
     * Zahility Tezgâhı tariflerinin ayrı tarif türü.
     *
     * Normal Crafting Table bu tarifleri görmez.
     */
    public static final DeferredHolder<
            RecipeType<?>,
            RecipeType<ZahilityWorkbenchRecipe>
    > WORKBENCH_CRAFTING = RECIPE_TYPES.register(
            "workbench_crafting",
            () -> RecipeType.simple(
                    ResourceLocation.fromNamespaceAndPath(
                            Zahility.MOD_ID,
                            "workbench_crafting"
                    )
            )
    );

    /*
     * Şekilli Zahility Tezgâhı tariflerinin
     * JSON serializer'ı.
     */
    public static final DeferredHolder<
            RecipeSerializer<?>,
            RecipeSerializer<ZahilityWorkbenchRecipe>
    > WORKBENCH_SHAPED_SERIALIZER =
            RECIPE_SERIALIZERS.register(
                    "workbench_shaped",
                    ZahilityWorkbenchRecipe.Serializer::new
            );

    private ModRecipeTypes() {
    }
}