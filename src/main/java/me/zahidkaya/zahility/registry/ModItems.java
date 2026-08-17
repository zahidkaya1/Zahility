package me.zahidkaya.zahility.registry;

import me.zahidkaya.zahility.Zahility;
import me.zahidkaya.zahility.feature.leveling.LevelingSnowballItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SnowballItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Zahility.MOD_ID);

    // Survival/craftable line.
    public static final DeferredItem<SnowballItem> TERRAFORM_SNOWBALL = ITEMS.register(
            "terraform_snowball",
            () -> new SnowballItem(
                    new Item.Properties()
                            .stacksTo(16)
            )
    );

    // Creative/admin line. It intentionally has no recipe.
    public static final DeferredItem<SnowballItem> CREATIVE_TERRAFORM_SNOWBALL = ITEMS.register(
            "creative_terraform_snowball",
            () -> new SnowballItem(
                    new Item.Properties()
                            .stacksTo(64)
                            .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
            )
    );

    public static final DeferredItem<LevelingSnowballItem> LEVELING_SNOWBALL = ITEMS.register(
            "leveling_snowball",
            () -> new LevelingSnowballItem(
                    new Item.Properties()
                            .stacksTo(16)
            )
    );

    public static final DeferredItem<LevelingSnowballItem> CREATIVE_LEVELING_SNOWBALL = ITEMS.register(
        "creative_leveling_snowball",
            () -> new LevelingSnowballItem(
                    new Item.Properties()
                            .stacksTo(64)
                            .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
            )
    );

    private ModItems() {
    }
}