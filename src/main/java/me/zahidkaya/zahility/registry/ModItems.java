package me.zahidkaya.zahility.registry;

import me.zahidkaya.zahility.Zahility;
import me.zahidkaya.zahility.feature.leveling.LevelingSnowballItem;
import me.zahidkaya.zahility.feature.leveling.InfiniteLevelingSnowballItem;
import me.zahidkaya.zahility.item.InfiniteSnowballItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SnowballItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public final class ModItems {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Zahility.MOD_ID);

    // Terraform Snowball - Survival
    public static final DeferredItem<SnowballItem> TERRAFORM_SNOWBALL = ITEMS.register(
            "terraform_snowball",
            () -> new SnowballItem(
                    new Item.Properties()
                            .stacksTo(16)
            )
    );

    // Terraform Snowball - Infinite
    public static final DeferredItem<InfiniteSnowballItem> CREATIVE_TERRAFORM_SNOWBALL = ITEMS.register(
            "creative_terraform_snowball",
            () -> new InfiniteSnowballItem(
                    new Item.Properties()
                            .stacksTo(1)
                            .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
            )
    );

    // Leveling Snowball - Survival
    public static final DeferredItem<LevelingSnowballItem> LEVELING_SNOWBALL = ITEMS.register(
            "leveling_snowball",
            () -> new LevelingSnowballItem(
                    new Item.Properties()
                            .stacksTo(16)
            )
    );

    // Leveling Snowball - Infinite
    public static final DeferredItem<InfiniteLevelingSnowballItem> CREATIVE_LEVELING_SNOWBALL = ITEMS.register(
            "creative_leveling_snowball",
            () -> new InfiniteLevelingSnowballItem(
                    new Item.Properties()
                            .stacksTo(1)
                            .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
            )
    );

    // Sponge Snowball - Survival
    public static final DeferredItem<SnowballItem> SPONGE_SNOWBALL = ITEMS.register(
            "sponge_snowball",
            () -> new SnowballItem(
                    new Item.Properties()
                            .stacksTo(16)
            )
    );

    // Sponge Snowball - Infinite
    public static final DeferredItem<InfiniteSnowballItem> CREATIVE_SPONGE_SNOWBALL = ITEMS.register(
            "creative_sponge_snowball",
            () -> new InfiniteSnowballItem(
                    new Item.Properties()
                            .stacksTo(1)
                            .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
            )
    );

    // Freezing Snowball - Survival
    public static final DeferredItem<SnowballItem> FREEZING_SNOWBALL = ITEMS.register(
            "freezing_snowball",
            () -> new SnowballItem(
                    new Item.Properties()
                            .stacksTo(16)
            )
    );

    // Freezing Snowball - Infinite
    public static final DeferredItem<InfiniteSnowballItem> CREATIVE_FREEZING_SNOWBALL = ITEMS.register(
            "creative_freezing_snowball",
            () -> new InfiniteSnowballItem(
                    new Item.Properties()
                            .stacksTo(1)
                            .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
            )
    );

    // Growth Snowball - Survival
    public static final DeferredItem<SnowballItem> GROWTH_SNOWBALL = ITEMS.register(
            "growth_snowball",
            () -> new SnowballItem(
                    new Item.Properties()
                            .stacksTo(16)
            )
    );

    // Growth Snowball - Infinite
    public static final DeferredItem<InfiniteSnowballItem> CREATIVE_GROWTH_SNOWBALL = ITEMS.register(
            "creative_growth_snowball",
            () -> new InfiniteSnowballItem(
                    new Item.Properties()
                            .stacksTo(1)
                            .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
            )
    );

    // Repelling Snowball - Survival
    public static final DeferredItem<SnowballItem> REPELLING_SNOWBALL = ITEMS.register(
            "repelling_snowball",
            () -> new SnowballItem(
                    new Item.Properties()
                            .stacksTo(16)
            )
    );

    // Repelling Snowball - Infinite
    public static final DeferredItem<InfiniteSnowballItem> CREATIVE_REPELLING_SNOWBALL = ITEMS.register(
            "creative_repelling_snowball",
            () -> new InfiniteSnowballItem(
                    new Item.Properties()
                            .stacksTo(1)
                            .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
            )
    );

    private ModItems() {
    }
}