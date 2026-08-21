package me.zahidkaya.zahility.feature.leveling;

import me.zahidkaya.zahility.item.InfiniteSnowballItem;
import me.zahidkaya.zahility.registry.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class InfiniteLevelingSnowballItem
        extends InfiniteSnowballItem {

    public InfiniteLevelingSnowballItem(
            Item.Properties properties
    ) {

        super(properties);
    }

    /*
     * Shift + sağ tık ile hedef düzleme yüksekliğini
     * sonsuz itemin kendi Data Component verisine kaydeder.
     */
    @Override
    public InteractionResult useOn(
            UseOnContext context
    ) {

        Player player =
                context.getPlayer();

        if (player == null) {
            return InteractionResult.PASS;
        }

        /*
         * Shift basılı değilse normal sağ tık davranışına devam et.
         */
        if (!player.isShiftKeyDown()) {
            return super.useOn(context);
        }

        int selectedY =
                context.getClickedPos().getY();

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