package me.zahidkaya.zahility.feature.leveling;

import me.zahidkaya.zahility.registry.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.context.UseOnContext;

public class LevelingSnowballItem extends SnowballItem {

    public LevelingSnowballItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        if (player == null) {
            return InteractionResult.PASS;
        }

        if (!player.isShiftKeyDown()) {
            return super.useOn(context);
        }

        int selectedY = context.getClickedPos().getY();

        if (!context.getLevel().isClientSide()) {
            context.getItemInHand().set(
                    ModDataComponents.LEVELING_HEIGHT.value(),
                    selectedY
            );

            player.displayClientMessage(
                    Component.translatable(
                            "message.zahility.leveling.selected",
                            selectedY
                    ),
                    true
            );
        }

        return InteractionResult.SUCCESS;
    }
}