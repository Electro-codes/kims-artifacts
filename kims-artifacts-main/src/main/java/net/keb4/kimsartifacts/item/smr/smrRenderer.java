package net.keb4.kimsartifacts.item.smr;

import com.mojang.blaze3d.vertex.PoseStack;


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

public class smrRenderer implements ICurioRenderer {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("kimsartifacts", "textures/item/curiochestplateartifact.png");
    @SuppressWarnings("rawtypes")
    private final smrModel model;

     @SuppressWarnings("rawtypes")
    public smrRenderer() {
      // Correct way to get your model from the registered layer definition
      this.model = new smrModel(Minecraft.getInstance().getEntityModels().bakeLayer(smrModel.LAYER_LOCATION));
   }

     @SuppressWarnings("unchecked")
    @Override
  public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,
                                                                        SlotContext slotContext,
                                                                        PoseStack matrixStack,
                                                                        RenderLayerParent<T, M> renderLayerParent,
                                                                        MultiBufferSource renderTypeBuffer,
                                                                        int light, float limbSwing,
                                                                        float limbSwingAmount,
                                                                        float partialTicks,
                                                                        float ageInTicks,
                                                                        float netHeadYaw,
                                                                        float headPitch) {
    LivingEntity entity = slotContext.entity();
    ICurioRenderer.translateIfSneaking(matrixStack, entity);
    ICurioRenderer.rotateIfSneaking(matrixStack, entity);
    this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    this.model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(this.model.renderType(TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                                                                        

  }
}
