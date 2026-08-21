package me.zahidkaya.zahility.client.workbench;

import me.zahidkaya.zahility.registry.ModItems;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.function.Supplier;

public final class ZahilityRecipeBookEnums {

    private static final String BOOK_TYPE_NAME =
            "ZAHILITY_WORKBENCH";

    private static final String SEARCH_CATEGORY_NAME =
            "ZAHILITY_WORKBENCH_SEARCH";

    private static final String MATERIALS_CATEGORY_NAME =
            "ZAHILITY_WORKBENCH_MATERIALS";

    private static final String INITIAL_CATEGORY_NAME =
            "ZAHILITY_WORKBENCH_INITIAL";

    private static final String DUPLICATION_CATEGORY_NAME =
            "ZAHILITY_WORKBENCH_DUPLICATION";

    private static final String INFINITE_CATEGORY_NAME =
            "ZAHILITY_WORKBENCH_INFINITE";

    private ZahilityRecipeBookEnums() {
    }

    public static RecipeBookType workbenchType() {
        return RecipeBookType.valueOf(
                BOOK_TYPE_NAME
        );
    }

    public static RecipeBookCategories searchCategory() {
        return RecipeBookCategories.valueOf(
                SEARCH_CATEGORY_NAME
        );
    }

    public static RecipeBookCategories materialsCategory() {
        return RecipeBookCategories.valueOf(
                MATERIALS_CATEGORY_NAME
        );
    }

    public static RecipeBookCategories initialCategory() {
        return RecipeBookCategories.valueOf(
                INITIAL_CATEGORY_NAME
        );
    }

    public static RecipeBookCategories duplicationCategory() {
        return RecipeBookCategories.valueOf(
                DUPLICATION_CATEGORY_NAME
        );
    }

    public static RecipeBookCategories infiniteCategory() {
        return RecipeBookCategories.valueOf(
                INFINITE_CATEGORY_NAME
        );
    }

    /*
     * enumextensions.json tarafından kullanılan
     * tarif kitabı kategori ikonları.
     */

    public static Object searchIcon(
            int parameterIndex,
            Class<?> parameterType
    ) {
        Supplier<List<ItemStack>> icons =
                () -> List.of(
                        new ItemStack(Items.COMPASS)
                );

        return parameterType.cast(icons);
    }

    public static Object materialsIcon(
            int parameterIndex,
            Class<?> parameterType
    ) {
        Supplier<List<ItemStack>> icons =
                () -> List.of(
                        new ItemStack(
                                ModItems.SPECIAL_SNOWBALL_TEMPLATE.get()
                        ),
                        new ItemStack(
                                ModItems.INFINITY_CORE.get()
                        )
                );

        return parameterType.cast(icons);
    }

    public static Object initialIcon(
            int parameterIndex,
            Class<?> parameterType
    ) {
        Supplier<List<ItemStack>> icons =
                () -> List.of(
                        new ItemStack(
                                ModItems.TERRAFORM_SNOWBALL.get()
                        )
                );

        return parameterType.cast(icons);
    }

    public static Object duplicationIcon(
            int parameterIndex,
            Class<?> parameterType
    ) {
        Supplier<List<ItemStack>> icons =
                () -> List.of(
                        new ItemStack(Items.SNOWBALL),
                        new ItemStack(
                                ModItems.TERRAFORM_SNOWBALL.get()
                        )
                );

        return parameterType.cast(icons);
    }

    public static Object infiniteIcon(
            int parameterIndex,
            Class<?> parameterType
    ) {
        Supplier<List<ItemStack>> icons =
                () -> List.of(
                        new ItemStack(
                                ModItems.CREATIVE_TERRAFORM_SNOWBALL.get()
                        )
                );

        return parameterType.cast(icons);
    }
}