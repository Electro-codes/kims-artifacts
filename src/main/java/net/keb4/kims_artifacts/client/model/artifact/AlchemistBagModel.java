// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
package net.keb4.kims_artifacts.client.model.artifact;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.client.animation.AlchemistBagAnimation;
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
import net.minecraft.world.phys.Vec3;

public class AlchemistBagModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Main.MODID, "alchemistbag"), "main");
	private final ModelPart Root;
	private final ModelPart Flask;
	private final ModelPart Flask2;
	private final ModelPart Flask3;
	private final ModelPart Flask4;
	private final AnimationState idleAnimation = new AnimationState();

   	private Vec3 lastRenderedEntityPos = Vec3.ZERO; // Posição da entidade no último render
    private Vec3 currentVisualPos = Vec3.ZERO; // Posição interpolada do modelo para o lag behind
    private boolean lagInitialized = false; // Flag para inicializar o lag behind

    // Constantes para o lag behind
    private static final double SMOOTHING_FACTOR_LAG = 0.15; // Menor = mais lag; Maior = menos lag
    private static final double MAX_LAG_OFFSET_MAGNITUDE = 0.5;

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
		// Initialize smoothed position on first tick
		this.root().getAllParts().forEach(ModelPart::resetPose);
		// Continue with your idle animation
		this.animate(this.idleAnimation, AlchemistBagAnimation.Idle, ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		
		return this.Root;
	}
	 public void renderCurioWithLag(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float partialTicks, float red, float green, float blue, float alpha,Entity entity) {
        
        // Posição alvo (onde o curio deveria estar *sem* lag)
        // Usamos a posição interpolada da entidade que o usa.
        double interpolatedX = Mth.lerp(partialTicks, entity.xOld, entity.getX());
        double interpolatedY = Mth.lerp(partialTicks, entity.yOld, entity.getY());
        double interpolatedZ = Mth.lerp(partialTicks, entity.zOld, entity.getZ());
        Vec3 currentTargetPos = new Vec3(interpolatedX, interpolatedY, interpolatedZ);

        // Inicializa currentVisualPos na primeira chamada
        if (!lagInitialized) {
            this.currentVisualPos = currentTargetPos;
            lagInitialized = true;
        }

        // 1. Calcular a diferença (delta) entre a posição alvo e a posição visual atual
        Vec3 delta = currentTargetPos.subtract(this.currentVisualPos);

        // 2. Limitar o delta para evitar saltos muito grandes
        if (delta.length() > MAX_LAG_OFFSET_MAGNITUDE) {
            delta = delta.normalize().scale(MAX_LAG_OFFSET_MAGNITUDE);
        }

        // 3. Interpolar para a nova posição visual suave
        // Multiplica pelo partialTicks para suavizar ainda mais entre os frames
        Vec3 newVisualPos = this.currentVisualPos.add(delta.scale(SMOOTHING_FACTOR_LAG));

        // 4. Atualizar a 'currentVisualPos' para o próximo frame
        this.currentVisualPos = newVisualPos;

        // 5. Calcular o offset a aplicar na PoseStack para criar o "lag"
        // Este é o movimento que a PoseStack precisa fazer para que o modelo pareça estar atrasado.
        Vec3 visualLagOffset = newVisualPos.subtract(currentTargetPos);

        // --- Aplicar o offset na PoseStack antes de renderizar o modelo ---
        poseStack.pushPose(); // Guarda o estado atual da PoseStack

        // Aplica o deslocamento do lag behind.
        // O `translate` moverá o modelo inteiro.
        poseStack.translate(visualLagOffset.x, visualLagOffset.y, visualLagOffset.z);

        // Chama o método `renderToBuffer` original para desenhar o modelo.
        // As animações internas (keyframes) já foram aplicadas no `setupAnim`.
        this.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);

        poseStack.popPose(); // Restaura o estado anterior da PoseStack
    }

}