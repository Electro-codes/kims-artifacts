package net.keb4.kims_artifacts.client.renderer.artifact;

import com.mojang.blaze3d.vertex.PoseStack;

import cpw.mods.modlauncher.api.ITransformationService.Resource;
import net.keb4.kims_artifacts.client.model.artifact.AlchemistBagModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class AlchemistBagRenderer implements ICurioRenderer {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("kims_artifacts", "textures/model/alchemist_bag.png");
    private final AlchemistBagModel model;

    public AlchemistBagRenderer() {
        this.model = new AlchemistBagModel(Minecraft.getInstance().getEntityModels().bakeLayer(AlchemistBagModel.LAYER_LOCATION));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
            PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
            int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
            float headPitch) {
        LivingEntity entity = slotContext.entity();
        this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(this.model.renderType(TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

}
