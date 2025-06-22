package net.keb4.kims_artifacts.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.container.PotionBagMenu;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.keb4.kims_artifacts.network.c2s.PotionMixPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PotionBagScreen extends AbstractContainerScreen<PotionBagMenu> {
    public PotionBagScreen(PotionBagMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageHeight = 166;
        this.imageWidth = 176;

        this.addRenderableWidget(Button.builder(Component.literal("Clear Slot 0"), (button) -> {
            // When button is pressed, send a packet to the server
            // We'll hardcode clearing slot 0 for demonstration.
            // In a real mod, you'd send more context (e.g., slot to modify, action type).
            PacketNetwork.sendToServer(new PotionMixPacket());
        })
                .bounds(0,0,20,20)
                .build());
    }


    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Main.MODID, "textures/gui/potion_bag_menu.png");

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = 8; // Position for the inventory title
        this.inventoryLabelX = 8; // Position for the player inventory title
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
