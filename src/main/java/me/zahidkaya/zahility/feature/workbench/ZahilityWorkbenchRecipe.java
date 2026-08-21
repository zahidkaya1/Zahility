package me.zahidkaya.zahility.feature.workbench;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.zahidkaya.zahility.registry.ModBlocks;
import me.zahidkaya.zahility.registry.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;

public final class ZahilityWorkbenchRecipe
        implements Recipe<CraftingInput> {

    private final String group;
    private final String category;
    private final ShapedRecipePattern pattern;
    private final ItemStack result;

    public ZahilityWorkbenchRecipe(
            String group,
            String category,
            ShapedRecipePattern pattern,
            ItemStack result
    ) {
        this.group = group;
        this.category = category;
        this.pattern = pattern;
        this.result = result;
    }

    @Override
    public boolean matches(
            CraftingInput input,
            Level level
    ) {
        return pattern.matches(input);
    }

    @Override
    public ItemStack assemble(
            CraftingInput input,
            HolderLookup.Provider registries
    ) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(
            int width,
            int height
    ) {
        return width >= pattern.width()
                && height >= pattern.height();
    }

    @Override
    public ItemStack getResultItem(
            HolderLookup.Provider registries
    ) {
        return result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return pattern.ingredients();
    }

    @Override
    public String getGroup() {
        return group;
    }

    /*
     * Tarif kitabının özel kategori bilgisidir.
     *
     * Örnekler:
     *
     * snowball_initial
     * snowball_duplication
     * snowball_infinite
     * qol
     * building
     * farming
     */
    public String getWorkbenchCategory() {
        return category;
    }

    public ShapedRecipePattern getPattern() {
        return pattern;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(
                ModBlocks.ZAHILITY_WORKBENCH.get()
        );
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.WORKBENCH_SHAPED_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.WORKBENCH_CRAFTING.get();
    }

    /*
     * Zahility Tezgâhı şekilli tariflerinin
     * JSON ve ağ serializer'ı.
     */
    public static final class Serializer
            implements RecipeSerializer<ZahilityWorkbenchRecipe> {

        public static final MapCodec<ZahilityWorkbenchRecipe> CODEC =
                RecordCodecBuilder.mapCodec(
                        instance -> instance.group(
                                        Codec.STRING
                                                .optionalFieldOf(
                                                        "group",
                                                        ""
                                                )
                                                .forGetter(
                                                        recipe ->
                                                                recipe.group
                                                ),

                                        Codec.STRING
                                                .optionalFieldOf(
                                                        "category",
                                                        "misc"
                                                )
                                                .forGetter(
                                                        recipe ->
                                                                recipe.category
                                                ),

                                        ShapedRecipePattern.MAP_CODEC
                                                .forGetter(
                                                        recipe ->
                                                                recipe.pattern
                                                ),

                                        ItemStack.STRICT_CODEC
                                                .fieldOf("result")
                                                .forGetter(
                                                        recipe ->
                                                                recipe.result
                                                )
                                )
                                .apply(
                                        instance,
                                        ZahilityWorkbenchRecipe::new
                                )
                );

        public static final StreamCodec<
                RegistryFriendlyByteBuf,
                ZahilityWorkbenchRecipe
        > STREAM_CODEC = StreamCodec.of(
                Serializer::encode,
                Serializer::decode
        );

        @Override
        public MapCodec<ZahilityWorkbenchRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<
                RegistryFriendlyByteBuf,
                ZahilityWorkbenchRecipe
        > streamCodec() {
            return STREAM_CODEC;
        }

        private static void encode(
                RegistryFriendlyByteBuf buffer,
                ZahilityWorkbenchRecipe recipe
        ) {
            buffer.writeUtf(recipe.group);
            buffer.writeUtf(recipe.category);

            ShapedRecipePattern.STREAM_CODEC.encode(
                    buffer,
                    recipe.pattern
            );

            ItemStack.STREAM_CODEC.encode(
                    buffer,
                    recipe.result
            );
        }

        private static ZahilityWorkbenchRecipe decode(
                RegistryFriendlyByteBuf buffer
        ) {
            String group =
                    buffer.readUtf();

            String category =
                    buffer.readUtf();

            ShapedRecipePattern pattern =
                    ShapedRecipePattern.STREAM_CODEC.decode(
                            buffer
                    );

            ItemStack result =
                    ItemStack.STREAM_CODEC.decode(
                            buffer
                    );

            return new ZahilityWorkbenchRecipe(
                    group,
                    category,
                    pattern,
                    result
            );
        }
    }
}