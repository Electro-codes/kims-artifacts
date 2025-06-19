package net.keb4.kims_artifacts.client.model.artifact;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.keb4.kims_artifacts.Main;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SMRModel <T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Main.MODID, "smrmodel"), "main");
    public final ModelPart arm;
    public final ModelPart yawrotator;
    public final ModelPart pitchrotator;

    public SMRModel(ModelPart root) {
        this.arm = root.getChild("arm");
        this.yawrotator = root.getChild("yawrotator");
        this.pitchrotator = root.getChild("pitchrotator");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition arm = partdefinition.addOrReplaceChild("arm", CubeListBuilder.create().texOffs(17, 0).addBox(-1.25F, -0.5F, -1.15F, 2.5F, 1.0F, 2.5F, new CubeDeformation(0.0F)), PartPose.offset(-9.0462F, -1.1331F, 2.0355F));
        arm.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(15, 27).addBox(-0.5F, -0.5F, -4.5228F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.3445F, 2.9806F, -0.7854F, 0.0F, 0.0F));
        arm.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 26).addBox(-0.6989F, -0.5F, -3.7807F, 1.0F, 1.0F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.75F, 3.2887F, 2.9641F, 0.0F, 0.2618F, 1.5708F));
        arm.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(11, 26).addBox(-0.875F, -1.0F, -0.9749F, 1.5F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.875F, 3.3107F, 2.9822F, -2.3562F, 0.0F, 0.0F));
        arm.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(14, 26).addBox(-0.5178F, -0.5178F, -5.125F, 1.0F, 1.0F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.875F, 3.3107F, 2.9822F, 0.0F, -1.5708F, -3.1416F));
        arm.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(11, 26).addBox(-0.75F, -0.9772F, -1.0F, 1.5F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.3445F, 2.9806F, -2.3562F, 0.0F, 0.0F));
        PartDefinition yawrotator = partdefinition.addOrReplaceChild("yawrotator", CubeListBuilder.create().texOffs(0, 18).addBox(-0.75F, 1.0937F, -0.75F, 1.5F, 3.5F, 1.5F, new CubeDeformation(0.0F)).texOffs(0, 27).addBox(-1.75F, 2.5937F, -1.75F, 3.5F, 1.0F, 3.5F, new CubeDeformation(0.0F)), PartPose.offset(-9.0462F, -6.2268F, 2.1355F));
        yawrotator.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(22, 12).addBox(2.5F, 4.0F, -1.5F, 2.5F, 1.0F, 2.5F, new CubeDeformation(0.0F)).texOffs(22, 12).addBox(2.5F, -0.5F, -1.5F, 2.5F, 1.0F, 2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, -0.9063F, 0.25F, 0.0F, 0.0F, -1.5708F));
        yawrotator.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(10, 11).addBox(-0.5F, -0.5F, -0.75F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2).addBox(-0.5F, 0.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 11).addBox(-0.5F, 4.0F, -0.75F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, 2.0937F, 0.25F, 0.0F, 0.0F, -1.5708F));
        yawrotator.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.25F, 2.0937F, 0.25F, 0.0F, 0.0F, 1.5708F));
        PartDefinition pitchrotator = partdefinition.addOrReplaceChild("pitchrotator", CubeListBuilder.create(), PartPose.offset(-10.2962F, -10.8731F, 2.1355F));
        pitchrotator.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(6, 17).addBox(-1.25F, 0.75F, -0.75F, 3.0F, 1.0F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5687F, 2.1322F, 0.0F, 1.1781F, -1.5708F));
        pitchrotator.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(26, 22).addBox(-3.0F, -1.5F, -0.75F, 1.5F, 3.0F, 1.5F, new CubeDeformation(0.0F)).texOffs(0, 10).addBox(-5.0F, -1.75F, -1.75F, 2.0F, 3.5F, 3.5F, new CubeDeformation(0.0F)).texOffs(23, 16).addBox(-1.5F, -0.75F, -0.75F, 3.0F, 1.5F, 1.5F, new CubeDeformation(0.0F)).texOffs(7, 3).addBox(3.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(11, 5).addBox(2.75F, -1.5F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(16, 7).addBox(2.75F, 0.5F, -0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(18, 9).addBox(3.75F, -0.5F, -1.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 8).addBox(3.75F, -0.5F, 0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(6, 20).addBox(1.5F, -1.75F, -0.75F, 2.5F, 3.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -0.01F, 0.0F, 0.0F, 1.5708F, -1.5708F));
        pitchrotator.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(6, 17).addBox(-6.25F, 0.5F, -2.5F, 3.0F, 1.0F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, -0.2921F, -3.1569F, -3.1416F, 1.1781F, 1.5708F));
        pitchrotator.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(8, 0).addBox(0.75F, -0.75F, -0.85F, 2.0F, 1.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -0.5215F, 4.7107F, 0.0F, 0.7854F, -1.5708F));
        pitchrotator.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(15, 15).addBox(-4.25F, 0.0F, -0.25F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, -3.0072F, -1.1919F, 0.0F, 1.3526F, -1.5708F));
        pitchrotator.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(20, 23).addBox(1.375F, -0.5F, 1.45F, 1.5F, 1.5F, 1.5F, new CubeDeformation(0.0F)).texOffs(27, 0).addBox(1.625F, -0.25F, -0.05F, 1.0F, 1.0F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.7189F, 1.8158F, 0.0F, 1.5708F, -1.5708F));
        pitchrotator.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(8, 6).addBox(-0.5F, -1.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(8, 6).addBox(-0.5F, 0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -0.01F, 0.0F, 0.0F, 0.7854F, -1.5708F));
        pitchrotator.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(16, 18).addBox(-1.0F, -1.0F, -0.75F, 2.0F, 1.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 0.4144F, -3.1131F, -3.1416F, 1.1781F, 1.5708F));
        pitchrotator.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(14, 21).addBox(1.125F, -0.75F, -1.0F, 2.0F, 1.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, 0.1478F, -1.0542F, 0.0F, 1.1781F, -1.5708F));
        pitchrotator.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(26, 3).addBox(-0.5F, -4.0F, -0.75F, 1.5F, 1.0F, 1.5F, new CubeDeformation(0.0F)).texOffs(26, 3).addBox(-0.5F, -2.0F, -0.75F, 1.5F, 1.0F, 1.5F, new CubeDeformation(0.0F)).texOffs(26, 3).addBox(-0.5F, 0.0F, -0.75F, 1.5F, 1.0F, 1.5F, new CubeDeformation(0.0F)).texOffs(26, 27).addBox(-0.25F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.7678F, -0.01F, -4.9748F, 1.5708F, 0.0F, 0.0F));
        pitchrotator.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(26, 3).addBox(-0.5F, -4.0F, -0.75F, 1.5F, 1.0F, 1.5F, new CubeDeformation(0.0F)).texOffs(26, 3).addBox(-0.5F, -2.0F, -0.75F, 1.5F, 1.0F, 1.5F, new CubeDeformation(0.0F)).texOffs(26, 3).addBox(-0.5F, 0.0F, -0.75F, 1.5F, 1.0F, 1.5F, new CubeDeformation(0.0F)).texOffs(26, 27).addBox(-0.25F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.7678F, -0.01F, -4.9748F, 1.5708F, 0.0F, 0.0F));
        pitchrotator.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(23, 19).addBox(-0.75F, -1.5F, -0.75F, 1.0F, 2.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.2803F, -0.26F, -3.091F, 1.5708F, -0.7854F, 0.0F));
        pitchrotator.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(23, 19).addBox(-0.25F, -1.5F, -0.5F, 1.0F, 2.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7803F, -0.01F, -3.091F, 1.5708F, 0.7854F, 0.0F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }


    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.pitchrotator.yRot = netHeadYaw / 57.295776F;
        this.pitchrotator.xRot = headPitch / 57.295776F;
        this.yawrotator.yRot = netHeadYaw / 57.295776F;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.yawrotator.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.pitchrotator.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
