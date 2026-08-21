package me.zahidkaya.zahility.feature.workbench;

import me.zahidkaya.zahility.registry.ModBlocks;
import me.zahidkaya.zahility.registry.ModMenus;
import me.zahidkaya.zahility.registry.ModRecipeTypes;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public final class ZahilityWorkbenchMenu
        extends RecipeBookMenu<
                CraftingInput,
                ZahilityWorkbenchRecipe
        > {

    /*
     * Menü slot numaraları:
     *
     * 0      → sonuç
     * 1-9    → 3×3 üretim alanı
     * 10-36  → oyuncu ana envanteri
     * 37-45  → hotbar
     */
    private static final int RESULT_SLOT_INDEX = 0;
    private static final int CRAFT_SLOT_START = 1;
    private static final int CRAFT_SLOT_END = 10;
    private static final int PLAYER_SLOT_START = 10;
    private static final int PLAYER_SLOT_END = 46;

    private final CraftingContainer craftingContainer;
    private final ResultContainer resultContainer;
    private final ContainerLevelAccess access;
    private final Player player;

    /*
     * Tarif kitabı malzemeleri yerleştirirken slotların
     * her değişiminde sonucu tekrar hesaplamayı engeller.
     */
    private boolean placingRecipe;

    /*
     * Client constructor.
     */
    public ZahilityWorkbenchMenu(
            int containerId,
            Inventory playerInventory
    ) {
        this(
                containerId,
                playerInventory,
                ContainerLevelAccess.NULL
        );
    }

    /*
     * Server constructor.
     */
    public ZahilityWorkbenchMenu(
            int containerId,
            Inventory playerInventory,
            ContainerLevelAccess access
    ) {
        super(
                ModMenus.ZAHILITY_WORKBENCH.get(),
                containerId
        );

        this.access = access;
        this.player = playerInventory.player;

        this.craftingContainer =
                new TransientCraftingContainer(
                        this,
                        3,
                        3
                );

        this.resultContainer =
                new ResultContainer();

        /*
         * Sonuç slotu.
         */
        addSlot(
                new ZahilityWorkbenchResultSlot(
                        playerInventory.player,
                        craftingContainer,
                        resultContainer,
                        0,
                        124,
                        35
                )
        );

        /*
         * 3×3 üretim alanı.
         */
        for (int row = 0; row < 3; row++) {

            for (int column = 0;
                 column < 3;
                 column++) {

                addSlot(
                        new Slot(
                                craftingContainer,
                                column + row * 3,
                                30 + column * 18,
                                17 + row * 18
                        )
                );
            }
        }

        /*
         * Oyuncu ana envanteri.
         */
        for (int row = 0; row < 3; row++) {

            for (int column = 0;
                 column < 9;
                 column++) {

                addSlot(
                        new Slot(
                                playerInventory,
                                column + row * 9 + 9,
                                8 + column * 18,
                                84 + row * 18
                        )
                );
            }
        }

        /*
         * Hotbar.
         */
        for (int column = 0;
             column < 9;
             column++) {

            addSlot(
                    new Slot(
                            playerInventory,
                            column,
                            8 + column * 18,
                            142
                    )
            );
        }

        /*
         * Zahility Tezgâhı açıldığında bütün özel
         * tezgâh tariflerini oyuncunun tarif kitabına ekler.
         *
         * Böylece ayrı advancement dosyaları olmadan
         * bütün Zahility tarifleri kitapta görünür.
         */
        if (playerInventory.player
                instanceof ServerPlayer serverPlayer) {

        Collection<RecipeHolder<?>>
                workbenchRecipes =
                new ArrayList<>();

        workbenchRecipes.addAll(
                serverPlayer.serverLevel()
                        .getRecipeManager()
                        .getAllRecipesFor(
                                ModRecipeTypes
                                        .WORKBENCH_CRAFTING
                                        .get()
                        )
        );

        serverPlayer.awardRecipes(
                workbenchRecipes
        );
        }
    }

    /*
     * Giriş alanı değiştiğinde yalnızca Zahility
     * Tezgâhı tarif türünde sonuç arar.
     */
    @Override
    public void slotsChanged(
            Container container
    ) {
        if (!placingRecipe) {
            access.execute(
                    (level, blockPos) ->
                            updateCraftingResult(
                                    this,
                                    level,
                                    player,
                                    craftingContainer,
                                    resultContainer,
                                    null
                            )
            );
        }
    }

    private static void updateCraftingResult(
            ZahilityWorkbenchMenu menu,
            Level level,
            Player player,
            CraftingContainer craftingContainer,
            ResultContainer resultContainer,
            @Nullable RecipeHolder<ZahilityWorkbenchRecipe>
                    preferredRecipe
    ) {
        if (level.isClientSide) {
            return;
        }

        CraftingInput input =
                craftingContainer.asCraftInput();

        ServerPlayer serverPlayer =
                (ServerPlayer) player;

        ItemStack resultStack =
                ItemStack.EMPTY;

        Optional<
                RecipeHolder<ZahilityWorkbenchRecipe>
        > matchingRecipe = level
                .getServer()
                .getRecipeManager()
                .getRecipeFor(
                        ModRecipeTypes
                                .WORKBENCH_CRAFTING
                                .get(),
                        input,
                        level,
                        preferredRecipe
                );

        if (matchingRecipe.isPresent()) {

            RecipeHolder<ZahilityWorkbenchRecipe>
                    recipeHolder =
                    matchingRecipe.get();

            if (resultContainer.setRecipeUsed(
                    level,
                    serverPlayer,
                    recipeHolder
            )) {
                ItemStack assembledResult =
                        recipeHolder.value().assemble(
                                input,
                                level.registryAccess()
                        );

                if (assembledResult.isItemEnabled(
                        level.enabledFeatures()
                )) {
                    resultStack =
                            assembledResult;
                }
            }
        }

        resultContainer.setItem(
                0,
                resultStack
        );

        menu.setRemoteSlot(
                RESULT_SLOT_INDEX,
                resultStack
        );

        serverPlayer.connection.send(
                new ClientboundContainerSetSlotPacket(
                        menu.containerId,
                        menu.incrementStateId(),
                        RESULT_SLOT_INDEX,
                        resultStack
                )
        );
    }

    /*
     * Vanilla tarif yerleştirme işlemi başlamadan önce
     * geçici sonuç güncellemelerini durdurur.
     */
    @Override
    protected void beginPlacingRecipe() {
        placingRecipe = true;
    }

    /*
     * Tarif yerleştirme tamamlandığında sonucu
     * seçilen tarif üzerinden bir kez hesaplar.
     */
    @Override
    protected void finishPlacingRecipe(
            RecipeHolder<ZahilityWorkbenchRecipe> recipe
    ) {
        placingRecipe = false;

        access.execute(
                (level, blockPos) ->
                        updateCraftingResult(
                                this,
                                level,
                                player,
                                craftingContainer,
                                resultContainer,
                                recipe
                        )
        );
    }

    /*
     * Tarif kitabının oyuncu envanterindeki ve
     * üretim alanındaki malzemeleri saymasını sağlar.
     */
    @Override
    public void fillCraftSlotsStackedContents(
            StackedContents stackedContents
    ) {
        craftingContainer.fillStackedContents(
                stackedContents
        );
    }

    /*
     * Tarif kitabı yeni tarif yerleştirmeden önce
     * üretim alanını temizler.
     */
    @Override
    public void clearCraftingContent() {
        craftingContainer.clearContent();
        resultContainer.clearContent();
    }

    /*
     * Seçilen tarifin mevcut 3×3 içerikle
     * eşleşip eşleşmediğini kontrol eder.
     */
    @Override
    public boolean recipeMatches(
            RecipeHolder<ZahilityWorkbenchRecipe> recipe
    ) {
        return recipe.value().matches(
                craftingContainer.asCraftInput(),
                player.level()
        );
    }

    @Override
    public boolean stillValid(
            Player player
    ) {
        return stillValid(
                access,
                player,
                ModBlocks.ZAHILITY_WORKBENCH.get()
        );
    }

    /*
     * Vanilla Crafting Table ile aynı güvenli
     * Shift+tıklama düzeni.
     */
    @Override
    public ItemStack quickMoveStack(
            Player player,
            int slotIndex
    ) {
        ItemStack originalStack =
                ItemStack.EMPTY;

        Slot slot =
                slots.get(slotIndex);

        if (slot == null
                || !slot.hasItem()) {

            return ItemStack.EMPTY;
        }

        ItemStack slotStack =
                slot.getItem();

        originalStack =
                slotStack.copy();

        /*
         * Sonuç slotu.
         */
        if (slotIndex == RESULT_SLOT_INDEX) {

            access.execute(
                    (level, blockPos) ->
                            slotStack.getItem()
                                    .onCraftedBy(
                                            slotStack,
                                            level,
                                            player
                                    )
            );

            if (!moveItemStackTo(
                    slotStack,
                    PLAYER_SLOT_START,
                    PLAYER_SLOT_END,
                    true
            )) {
                return ItemStack.EMPTY;
            }

            slot.onQuickCraft(
                    slotStack,
                    originalStack
            );

        /*
         * Oyuncu envanterinden 3×3 alana.
         */
        } else if (slotIndex >= PLAYER_SLOT_START
                && slotIndex < PLAYER_SLOT_END) {

            if (!moveItemStackTo(
                    slotStack,
                    CRAFT_SLOT_START,
                    CRAFT_SLOT_END,
                    false
            )) {
                return ItemStack.EMPTY;
            }

        /*
         * 3×3 alandan oyuncu envanterine.
         */
        } else if (!moveItemStackTo(
                slotStack,
                PLAYER_SLOT_START,
                PLAYER_SLOT_END,
                false
        )) {
            return ItemStack.EMPTY;
        }

        if (slotStack.isEmpty()) {

            slot.setByPlayer(
                    ItemStack.EMPTY
            );

        } else {

            slot.setChanged();
        }

        if (slotStack.getCount()
                == originalStack.getCount()) {

            return ItemStack.EMPTY;
        }

        slot.onTake(
                player,
                slotStack
        );

        if (slotIndex == RESULT_SLOT_INDEX) {

            player.drop(
                    slotStack,
                    false
            );
        }

        return originalStack;
    }

    @Override
    public boolean canTakeItemForPickAll(
            ItemStack itemStack,
            Slot slot
    ) {
        return slot.container != resultContainer
                && super.canTakeItemForPickAll(
                        itemStack,
                        slot
                );
    }

    /*
     * Menü kapanınca üretim alanındaki
     * malzemeleri oyuncuya geri verir.
     */
    @Override
    public void removed(
            Player player
    ) {
        super.removed(player);

        access.execute(
                (level, blockPos) ->
                        clearContainer(
                                player,
                                craftingContainer
                        )
        );
    }

    /*
     * Tarif kitabının sonuç slotu.
     */
    @Override
    public int getResultSlotIndex() {
        return RESULT_SLOT_INDEX;
    }

    /*
     * Tarif kitabının 3×3 genişliği.
     */
    @Override
    public int getGridWidth() {
        return craftingContainer.getWidth();
    }

    /*
     * Tarif kitabının 3×3 yüksekliği.
     */
    @Override
    public int getGridHeight() {
        return craftingContainer.getHeight();
    }

    /*
     * Sonuç slotu dahil tezgâh slotlarının sayısı.
     */
    @Override
    public int getSize() {
        return CRAFT_SLOT_END;
    }

    /*
     * enumextensions.json ile eklenen özel
     * Zahility tarif kitabı türü.
     */
    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.valueOf(
                "ZAHILITY_WORKBENCH"
        );
    }

    /*
     * Tarif yerleştirilirken sonuç dışındaki
     * üretim slotları envantere geri taşınabilir.
     */
    @Override
    public boolean shouldMoveToInventory(
            int slotIndex
    ) {
        return slotIndex != RESULT_SLOT_INDEX;
    }

    public CraftingContainer getCraftingContainer() {
        return craftingContainer;
    }

    public ResultContainer getResultContainer() {
        return resultContainer;
    }
}