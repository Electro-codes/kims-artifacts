package net.keb4.kims_artifacts.network.s2c;

import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.client.renderer.ScreenShakeRenderer;
import net.keb4.kims_artifacts.client.util.ParticleHelper;
import net.keb4.kims_artifacts.entity.capability.CapRegistry;
import net.keb4.kims_artifacts.entity.capability.IArtifactPlayerCap;
import net.keb4.kims_artifacts.entity.capability.PlayerArtifactCapability;
import net.keb4.kims_artifacts.item.artifacts.SMRItem;
import net.keb4.kims_artifacts.network.s2c.effects.SMRStrongExplosionCallbackPacket;
import net.keb4.kims_artifacts.network.s2c.effects.SMRWeakExplosionCallbackPacket;
import net.keb4.kims_artifacts.util.RayUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientPacketHandlers {

    public static void handleManualDeltaSyncPacket(ManualDeltaSyncPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        Minecraft.getInstance().player.setDeltaMovement(msg.delta);
    }

    public static void handleSMRStrongExplosionPacketCB(SMRStrongExplosionCallbackPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        Entity sender = Minecraft.getInstance().level.getEntity(msg.senderID);
        if (sender == null) return;
        if (sender instanceof Player player) {
            double headYaw = Math.toRadians(player.getYHeadRot());
            double headPitch = Math.toRadians(player.getXRot());
            HitResult r = RayUtils.simpleEntityBlockRay(player, SMRItem.RAYCAST_RANGE*2, true);
            ParticleHelper.explosion(player.getEyePosition().add((-Math.cos(headYaw))*0.60,-0.35+Math.cos(-headPitch),(-Math.sin(headYaw))*0.60),1.0D,50.0D,10D, ParticleTypes.ELECTRIC_SPARK);
            ParticleHelper.line(player.getEyePosition().add((-Math.cos(headYaw))*0.60,-0.35+Math.cos(-headPitch),(-Math.sin(headYaw))*0.60), r.getLocation(), 0.3D,1, ParticleTypes.POOF);
            ParticleHelper.explosion(r.getLocation(), 3.0D,50.0D,1.0D, ParticleTypes.SPIT);
            ParticleHelper.sphere(r.getLocation(), 4.0D,10.0D, ParticleTypes.POOF);
        }
    }

    public static void handleSMRWeakExplosionPacketCB(SMRWeakExplosionCallbackPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        Entity sender = Minecraft.getInstance().level.getEntity(msg.senderID);
        if (sender == null) return;
        if (sender instanceof Player player) {
            double headYaw = Math.toRadians(player.getYHeadRot());
            double headPitch = Math.toRadians(player.getXRot());
            HitResult r = RayUtils.simpleEntityBlockRay(player, SMRItem.RAYCAST_RANGE, true);
            ParticleHelper.explosion(player.getEyePosition().add((-Math.cos(headYaw))*0.60,-0.35+Math.cos(-headPitch),(-Math.sin(headYaw))*0.60),1.0D,50.0D,10D, ParticleTypes.ELECTRIC_SPARK);
            ParticleHelper.line(player.getEyePosition().add((-Math.cos(headYaw))*0.60,-0.35+Math.cos(-headPitch),(-Math.sin(headYaw))*0.60), r.getLocation(), 0.3D,1, ParticleTypes.POOF);
            ParticleHelper.explosion(r.getLocation(), 1.0D,50.0D,2.5D, ParticleTypes.POOF);
            ParticleHelper.sphere(r.getLocation(), 2.0D,10.0D, ParticleTypes.POOF);
        }
    }

    public static void handleScreenShakePacket(ScreenShakePacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ScreenShakeRenderer.shakeScreen(msg.duration, msg.strength, msg.type);
    }

    public static void handleResonanceSyncPacket(ResonanceSyncPacket message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        IArtifactPlayerCap cap = Minecraft.getInstance().player.getCapability(CapRegistry.PLAYER_ARTIFACT_CAP).resolve().get();
        PlayerArtifactCapability newCap = new PlayerArtifactCapability(message.resonanceValues, message.initialized);
        cap.copyFrom(newCap);
    }
}
