package net.keb4.kims_artifacts.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.capability.CapRegistry;
import net.keb4.kims_artifacts.config.CommonConfig;
import net.keb4.kims_artifacts.container.PotionBagMenu;
import net.keb4.kims_artifacts.item.ItemRegistry;
import net.keb4.kims_artifacts.item.artifacts.PotionBagItem;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.keb4.kims_artifacts.network.c2s.PotionMixPacket;
import net.keb4.kims_artifacts.util.CurioHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.io.ObjectInputFilter;

public class PotionBagScreen extends AbstractContainerScreen<PotionBagMenu> {


    public PotionBagScreen(PotionBagMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        //gui size
        this.imageHeight = 166;
        this.imageWidth = 176;
        // button
        this.addRenderableWidget(Button.builder(Component.literal("Brew"), (button) -> {
            if (CurioHelper.wearingArtifactItem(Minecraft.getInstance().player, ItemRegistry.POTION_BAG_ITEM.get())) {
                PacketNetwork.sendToServer(new PotionMixPacket(CurioHelper.getArtifactCurio(Minecraft.getInstance().player)));
            }
        })
                .bounds(0,0,60,20)
                .build());
    }

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Main.MODID, "textures/gui/potion_bag_menu.png");
    private int progress = 0;
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        this.progress = CommonConfig.potionBagBrewTime - this.menu.getProgress();
        //basic render
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int yLevelProgress = (int)((this.progress / (float)CommonConfig.potionBagBrewTime) * 13);
        guiGraphics.blit(TEXTURE,x + 17, y+43, 176,0, 28, yLevelProgress);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = 8; // Position for the inventory title
        this.titleLabelY = 2;
        this.inventoryLabelX = 8; // Position for the player inventory title
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }


}
