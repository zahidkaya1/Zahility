package me.zahidkaya.zahility.registry;

import me.zahidkaya.zahility.Zahility;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SnowballItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Zahility.MOD_ID);

    // Survival/craftable line. Recipe intentionally comes later, after gameplay tuning.
    public static final DeferredItem<SnowballItem> TERRAFORM_SNOWBALL = ITEMS.register(
            "terraform_snowball",
            () -> new SnowballItem(new Item.Properties().stacksTo(16))
    );

    // Creative/admin line. It intentionally has no recipe.
    public static final DeferredItem<SnowballItem> CREATIVE_TERRAFORM_SNOWBALL = ITEMS.register(
            "creative_terraform_snowball",
            () -> new SnowballItem(new Item.Properties().stacksTo(64))
    );

    private ModItems() {
    }
}
