package net.keb4.kims_artifacts.client.renderer.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.client.keybind.Keybinds;
import net.keb4.kims_artifacts.item.artifacts.SMRItem;
import net.keb4.kims_artifacts.util.CurioHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SMROverlayRenderer {
    boolean renderFlag = true;

    private static final ResourceLocation HOTBAR = ResourceLocation.fromNamespaceAndPath(Main.MODID, "textures/gui/smr_gui.png");
    private static boolean activeState = false;
    private static boolean alreadyCached = false;

    public static final IGuiOverlay SMR_HOTBAR = ((gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return; // Don't render if player is null
        }

        if (Minecraft.getInstance().player.tickCount % 5 == 0)
        {
            alreadyCached = false;
        }

        if (!alreadyCached)
        {
            activeState = CurioHelper.getArtifactCurio(minecraft.player).getItem() instanceof SMRItem;
            alreadyCached = true;
        }

        //Optimizing SMR check to run every second instead of every GUI call.
        if (!activeState) return;

        // --- Calculate position for your mini-hotbar ---
        // Let's place it above the regular hotbar, centered.
        float guiX = screenWidth*7 / 8; // Center x for a 182-pixel wide hotbar (91 is half of 182)
        float guiY = screenHeight*3/4; // 22 is default hotbar height, 25 for spacing above it

        // --- Render your mini-hotbar background (if you have one) ---
        // Set up render states for texture
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F); // White tint, full opacity
        RenderSystem.setShaderTexture(0, HOTBAR);

        // Draw the background texture (example: a 182x22 pixel texture)
        // You'll need to adjust U/V if your texture is part of a larger atlas
        guiGraphics.blit(HOTBAR, (int)guiX, (int)guiY, 22, 0, 22, 22, 64, 64);
        guiGraphics.blit(HOTBAR, (int)guiX+22, (int)guiY, 22, 0, 22, 22, 64, 64);

        guiGraphics.blit(HOTBAR, (int)guiX, (int)guiY, 0, 22, 22, 22, 64, 64);
        guiGraphics.blit(HOTBAR, (int)guiX+22, (int)guiY, 22, 22, 22, 22, 64, 64);



        //controller
        guiX += Keybinds.SMR_strongSelected ? 22 : 0;
        guiGraphics.blit(HOTBAR, (int)guiX, (int)guiY, 0, 0, 22, 22, 64, 64);

        RenderSystem.disableBlend();
    });


}
