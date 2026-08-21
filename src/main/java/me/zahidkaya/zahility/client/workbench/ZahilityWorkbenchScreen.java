package me.zahidkaya.zahility.client.workbench;

import me.zahidkaya.zahility.feature.workbench.ZahilityWorkbenchMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

public final class ZahilityWorkbenchScreen
        extends AbstractContainerScreen<ZahilityWorkbenchMenu>
        implements RecipeUpdateListener {

    /*
     * Minecraft'ın gerçek Crafting Table arayüzü.
     */
    private static final ResourceLocation TEXTURE =
            ResourceLocation.withDefaultNamespace(
                    "textures/gui/container/crafting_table.png"
            );

    /*
     * Vanilla tarif kitabı bileşeni.
     *
     * Panel, arama, kategoriler, tarif düğmeleri,
     * hayalet tarifler ve otomatik yerleştirme
     * bu sınıf tarafından yönetilir.
     */
    private final RecipeBookComponent recipeBookComponent =
            new RecipeBookComponent();

    /*
     * Dar ekranlarda tarif kitabının tezgâhın
     * üzerine açılıp açılmayacağını belirler.
     */
    private boolean widthTooNarrow;

    public ZahilityWorkbenchScreen(
            ZahilityWorkbenchMenu menu,
            Inventory playerInventory,
            Component title
    ) {
        super(
                menu,
                playerInventory,
                title
        );

        this.imageWidth = 176;
        this.imageHeight = 166;

        this.titleLabelX = 29;
        this.titleLabelY = 6;

        this.inventoryLabelX = 8;
        this.inventoryLabelY = 72;
    }

    /*
     * Ekran açılırken vanilla tarif kitabını,
     * kitabın düğmesini ve ekran konumunu hazırlar.
     */
    @Override
    protected void init() {
        super.init();

        this.widthTooNarrow =
                this.width < 379;

        this.recipeBookComponent.init(
                this.width,
                this.height,
                this.minecraft,
                this.widthTooNarrow,
                this.menu
        );

        this.leftPos =
                this.recipeBookComponent
                        .updateScreenPosition(
                                this.width,
                                this.imageWidth
                        );

        /*
         * Vanilla Crafting Table ile aynı
         * yeşil tarif kitabı düğmesi.
         */
        addRenderableWidget(
                new ImageButton(
                        this.leftPos + 5,
                        this.height / 2 - 49,
                        20,
                        18,
                        RecipeBookComponent
                                .RECIPE_BUTTON_SPRITES,
                        button -> {
                            this.recipeBookComponent
                                    .toggleVisibility();

                            this.leftPos =
                                    this.recipeBookComponent
                                            .updateScreenPosition(
                                                    this.width,
                                                    this.imageWidth
                                            );

                            button.setPosition(
                                    this.leftPos + 5,
                                    this.height / 2 - 49
                            );
                        }
                )
        );

        /*
         * Tarif kitabını ekranın widget sistemine ekler.
         */
        addWidget(
                this.recipeBookComponent
        );
    }

    /*
     * Envanter ve tariflerin üretilebilirlik
     * durumunu her oyun tick'inde günceller.
     */
    @Override
    public void containerTick() {
        super.containerTick();

        this.recipeBookComponent.tick();
    }

    @Override
    public void render(
            GuiGraphics guiGraphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        /*
         * Dar ekranda açık tarif kitabı tezgâhın
         * yerine ön planda gösterilir.
         */
        if (this.recipeBookComponent.isVisible()
                && this.widthTooNarrow) {

            renderBackground(
                    guiGraphics,
                    mouseX,
                    mouseY,
                    partialTick
            );

            this.recipeBookComponent.render(
                    guiGraphics,
                    mouseX,
                    mouseY,
                    partialTick
            );

        } else {

            /*
             * Normal tezgâh ekranı.
             */
            super.render(
                    guiGraphics,
                    mouseX,
                    mouseY,
                    partialTick
            );

            /*
             * Tarif kitabı açıksa tezgâhın solunda çizilir.
             */
            this.recipeBookComponent.render(
                    guiGraphics,
                    mouseX,
                    mouseY,
                    partialTick
            );

            /*
             * Eksik malzemeli tarifin hayalet
             * malzemelerini 3×3 alanda gösterir.
             */
            this.recipeBookComponent.renderGhostRecipe(
                    guiGraphics,
                    this.leftPos,
                    this.topPos,
                    true,
                    partialTick
            );
        }

        renderTooltip(
                guiGraphics,
                mouseX,
                mouseY
        );

        this.recipeBookComponent.renderTooltip(
                guiGraphics,
                this.leftPos,
                this.topPos,
                mouseX,
                mouseY
        );
    }

    @Override
    protected void renderBg(
            GuiGraphics guiGraphics,
            float partialTick,
            int mouseX,
            int mouseY
    ) {
        guiGraphics.blit(
                TEXTURE,
                this.leftPos,
                this.topPos,
                0,
                0,
                this.imageWidth,
                this.imageHeight
        );
    }

    /*
     * Arama kutusu açıkken klavye girişlerini
     * tarif kitabına yönlendirir.
     */
    @Override
    public boolean keyPressed(
            int keyCode,
            int scanCode,
            int modifiers
    ) {
        if (this.recipeBookComponent.keyPressed(
                keyCode,
                scanCode,
                modifiers
        )) {
            return true;
        }

        return super.keyPressed(
                keyCode,
                scanCode,
                modifiers
        );
    }

    @Override
    public boolean charTyped(
            char character,
            int modifiers
    ) {
        if (this.recipeBookComponent.charTyped(
                character,
                modifiers
        )) {
            return true;
        }

        return super.charTyped(
                character,
                modifiers
        );
    }

    /*
     * Dar ekranda kitap açıkken arkasındaki
     * tezgâh slotlarına yanlışlıkla tıklanmasını engeller.
     */
    @Override
    protected boolean isHovering(
            int x,
            int y,
            int width,
            int height,
            double mouseX,
            double mouseY
    ) {
        return (!this.widthTooNarrow
                || !this.recipeBookComponent.isVisible())
                && super.isHovering(
                        x,
                        y,
                        width,
                        height,
                        mouseX,
                        mouseY
                );
    }

    @Override
    public boolean mouseClicked(
            double mouseX,
            double mouseY,
            int button
    ) {
        if (this.recipeBookComponent.mouseClicked(
                mouseX,
                mouseY,
                button
        )) {
            setFocused(
                    this.recipeBookComponent
            );

            return true;
        }

        if (this.widthTooNarrow
                && this.recipeBookComponent.isVisible()) {

            return true;
        }

        return super.mouseClicked(
                mouseX,
                mouseY,
                button
        );
    }

    /*
     * Tarif kitabına yapılan tıklamaların ekranın
     * dışına tıklama sayılmasını engeller.
     */
    @Override
    protected boolean hasClickedOutside(
            double mouseX,
            double mouseY,
            int left,
            int top,
            int mouseButton
    ) {
        boolean outsideWorkbench =
                mouseX < left
                        || mouseY < top
                        || mouseX >= left + imageWidth
                        || mouseY >= top + imageHeight;

        return this.recipeBookComponent
                .hasClickedOutside(
                        mouseX,
                        mouseY,
                        this.leftPos,
                        this.topPos,
                        this.imageWidth,
                        this.imageHeight,
                        mouseButton
                )
                && outsideWorkbench;
    }

    /*
     * Slot değişikliklerini tarif kitabına bildirir.
     */
    @Override
    protected void slotClicked(
            Slot slot,
            int slotId,
            int mouseButton,
            ClickType clickType
    ) {
        super.slotClicked(
                slot,
                slotId,
                mouseButton,
                clickType
        );

        this.recipeBookComponent.slotClicked(
                slot
        );
    }

    /*
     * Sunucudan yeni tarif bilgisi geldiğinde
     * tarif kitabını yeniler.
     */
    @Override
    public void recipesUpdated() {
        this.recipeBookComponent
                .recipesUpdated();
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return this.recipeBookComponent;
    }
}