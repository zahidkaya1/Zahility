package me.zahidkaya.zahility.feature.workbench;

import me.zahidkaya.zahility.registry.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.EventHooks;

public final class ZahilityWorkbenchResultSlot
        extends Slot {

    private final CraftingContainer craftingContainer;
    private final Player player;

    private int removeCount;

    public ZahilityWorkbenchResultSlot(
            Player player,
            CraftingContainer craftingContainer,
            ResultContainer resultContainer,
            int slotIndex,
            int x,
            int y
    ) {
        super(
                resultContainer,
                slotIndex,
                x,
                y
        );

        this.player = player;
        this.craftingContainer = craftingContainer;
    }

    @Override
    public boolean mayPlace(
            ItemStack itemStack
    ) {
        return false;
    }

    @Override
    public ItemStack remove(
            int amount
    ) {
        if (hasItem()) {

            removeCount += Math.min(
                    amount,
                    getItem().getCount()
            );
        }

        return super.remove(amount);
    }

    @Override
    protected void onQuickCraft(
            ItemStack itemStack,
            int amount
    ) {
        removeCount += amount;

        checkTakeAchievements(
                itemStack
        );
    }

    @Override
    protected void onSwapCraft(
            int amount
    ) {
        removeCount += amount;
    }

    @Override
    protected void checkTakeAchievements(
            ItemStack itemStack
    ) {
        if (removeCount > 0) {

            itemStack.onCraftedBy(
                    player.level(),
                    player,
                    removeCount
            );

            EventHooks.firePlayerCraftingEvent(
                    player,
                    itemStack,
                    craftingContainer
            );
        }

        if (container
                instanceof RecipeCraftingHolder recipeHolder) {

            recipeHolder.awardUsedRecipes(
                    player,
                    craftingContainer.getItems()
            );
        }

        removeCount = 0;
    }

    @Override
    public void onTake(
            Player player,
            ItemStack resultStack
    ) {
        checkTakeAchievements(
                resultStack
        );

        CraftingInput.Positioned positionedInput =
                craftingContainer.asPositionedCraftInput();

        CraftingInput input =
                positionedInput.input();

        int left =
                positionedInput.left();

        int top =
                positionedInput.top();

        /*
         * Kalan kap veya crafting remainder bilgilerini
         * yalnızca Zahility tarif türünden alır.
         */
        CommonHooks.setCraftingPlayer(player);

        NonNullList<ItemStack> remainingItems =
                player.level()
                        .getRecipeManager()
                        .getRemainingItemsFor(
                                ModRecipeTypes
                                        .WORKBENCH_CRAFTING
                                        .get(),
                                input,
                                player.level()
                        );

        CommonHooks.setCraftingPlayer(null);

        for (int row = 0;
             row < input.height();
             row++) {

            for (int column = 0;
                 column < input.width();
                 column++) {

                int craftingSlotIndex =
                        column
                                + left
                                + (row + top)
                                * craftingContainer.getWidth();

                ItemStack currentStack =
                        craftingContainer.getItem(
                                craftingSlotIndex
                        );

                ItemStack remainingStack =
                        remainingItems.get(
                                column
                                        + row
                                        * input.width()
                        );

                /*
                 * Kullanılan her dolu üretim slotundan
                 * bir eşya tüketilir.
                 */
                if (!currentStack.isEmpty()) {

                    craftingContainer.removeItem(
                            craftingSlotIndex,
                            1
                    );

                    currentStack =
                            craftingContainer.getItem(
                                    craftingSlotIndex
                            );
                }

                /*
                 * Kova, şişe gibi geride kalan bir eşya
                 * varsa uygun yere geri yerleştir.
                 */
                if (!remainingStack.isEmpty()) {

                    if (currentStack.isEmpty()) {

                        craftingContainer.setItem(
                                craftingSlotIndex,
                                remainingStack
                        );

                    } else if (ItemStack
                            .isSameItemSameComponents(
                                    currentStack,
                                    remainingStack
                            )) {

                        remainingStack.grow(
                                currentStack.getCount()
                        );

                        craftingContainer.setItem(
                                craftingSlotIndex,
                                remainingStack
                        );

                    } else if (!player
                            .getInventory()
                            .add(remainingStack)) {

                        player.drop(
                                remainingStack,
                                false
                        );
                    }
                }
            }
        }
    }

    @Override
    public boolean isFake() {
        return true;
    }
}