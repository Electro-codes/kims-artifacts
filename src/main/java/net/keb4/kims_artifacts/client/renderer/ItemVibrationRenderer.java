package net.keb4.kims_artifacts.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.keb4.kims_artifacts.item.ArtifactItem;
import net.keb4.kims_artifacts.util.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ItemVibrationRenderer extends BlockEntityWithoutLevelRenderer {
    public ItemVibrationRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pDisplayContext, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        float time = Minecraft.getInstance().player.tickCount + Minecraft.getInstance().getPartialTick();

        if (pStack.getOrCreateTag().getCompound(NBTHelper.ROOT_LOC_ARTIFACT).getBoolean(ArtifactItem.IS_VIBRATING_LOCATION)) {// Example time source
            float offsetX = (float) Math.sin(time * 0.2) * 0.5F;
            float offsetY = (float) Math.cos(time * 0.1) * 0.5F;
            float offsetZ = (float) Math.cos(time * 0.2) * 0.5F;
            poseStack.pushPose();
            poseStack.translate(offsetX, offsetY, offsetZ);
        }
        BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(pStack, null, null, 0);
        Minecraft.getInstance().getItemRenderer().render(pStack, pDisplayContext, false, poseStack, pBuffer, pPackedLight, pPackedOverlay, model);

        if (pStack.getOrCreateTag().getCompound(NBTHelper.ROOT_LOC_ARTIFACT).getBoolean(ArtifactItem.IS_VIBRATING_LOCATION)) poseStack.popPose();
    }
}
