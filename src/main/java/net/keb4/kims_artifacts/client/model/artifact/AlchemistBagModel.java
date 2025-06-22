// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
package net.keb4.kims_artifacts.client.model.artifact;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.keb4.kims_artifacts.Main;

import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.schedule.Keyframe;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class AlchemistBagModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Main.MODID, "alchemistbag"), "main");
	private final ModelPart Root;
	private final ModelPart Flask;
	private final ModelPart Flask2;
	private final ModelPart Flask3;
	private final ModelPart Flask4;
	private float posx;
	private float posy;
	private float posz;
	private Vector3f lerpVec;



	public AlchemistBagModel(ModelPart root) {
		this.Root = root.getChild("Root");
		this.Flask = this.Root.getChild("Flask");
		this.Flask2 = this.Root.getChild("Flask2");
		this.Flask3 = this.Root.getChild("Flask3");
		this.Flask4 = this.Root.getChild("Flask4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Root = partdefinition.addOrReplaceChild("Root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition Flask = Root.addOrReplaceChild("Flask", CubeListBuilder.create().texOffs(21, 10).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -17.375F, -4.0F, -0.1309F, 0.0F, 0.2618F));

		PartDefinition Bottle_r1 = Flask.addOrReplaceChild("Bottle_r1", CubeListBuilder.create().texOffs(0, 17).addBox(-1.0F, -0.625F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition Flask2 = Root.addOrReplaceChild("Flask2", CubeListBuilder.create(), PartPose.offsetAndRotation(10.0F, -31.375F, 2.0F, -2.8377F, -1.0965F, 2.436F));

		PartDefinition Cap_r1 = Flask2.addOrReplaceChild("Cap_r1", CubeListBuilder.create().texOffs(21, 10).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition Bottle_r2 = Flask2.addOrReplaceChild("Bottle_r2", CubeListBuilder.create().texOffs(9, 17).addBox(-1.0F, -0.625F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0436F, 0.0F, 0.2618F));

		PartDefinition Flask3 = Root.addOrReplaceChild("Flask3", CubeListBuilder.create(), PartPose.offsetAndRotation(-10.0F, -29.375F, -5.0F, 1.7177F, 0.3971F, 2.4494F));

		PartDefinition Cap_r2 = Flask3.addOrReplaceChild("Cap_r2", CubeListBuilder.create().texOffs(21, 10).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition Bottle_r3 = Flask3.addOrReplaceChild("Bottle_r3", CubeListBuilder.create().texOffs(18, 17).addBox(-1.0F, -0.625F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0436F, 0.0F, 0.2618F));

		PartDefinition Flask4 = Root.addOrReplaceChild("Flask4", CubeListBuilder.create(), PartPose.offsetAndRotation(-11.0F, -11.375F, 5.0F, -0.4969F, -0.3367F, 1.8411F));

		PartDefinition Cap_r3 = Flask4.addOrReplaceChild("Cap_r3", CubeListBuilder.create().texOffs(21, 10).addBox(-0.5F, -1.375F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition Bottle_r4 = Flask4.addOrReplaceChild("Bottle_r4", CubeListBuilder.create().texOffs(18, 23).addBox(-1.0F, -0.625F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0436F, 0.0F, 0.2618F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.Root.resetPose();
		this.Flask.resetPose();
		this.Flask2.resetPose();
		this.Flask3.resetPose();
		this.Flask4.resetPose();

		this.Flask.y = Mth.cos(ageInTicks * 0.09F) * 0.10F; // Sway up and down
		this.Flask2.y = Mth.cos(ageInTicks * 0.08F) * 0.07F; // Sway up and down
		this.Flask3.y = Mth.cos(ageInTicks * 0.07F) * 0.06F; // Sway up and down
		this.Flask4.y = Mth.cos(ageInTicks * 0.1F) * 0.08F; // Sway up
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

}