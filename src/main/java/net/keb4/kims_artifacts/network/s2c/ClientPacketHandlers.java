package net.keb4.kims_artifacts.network.s2c;

import net.keb4.kims_artifacts.client.util.ParticleHelper;
import net.keb4.kims_artifacts.item.artifacts.SMRItem;
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

    public static void handleSMRWeakExplosionPacketCB(SMRWeakExplosionCallbackPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        Entity sender = Minecraft.getInstance().level.getEntity(msg.senderID);
        if (sender == null) return;
        if (sender instanceof Player player) {
            HitResult r = RayUtils.simpleEntityBlockRay(player, SMRItem.RAYCAST_RANGE, true);
            ParticleHelper.explosion(r.getLocation(), 2.0D,50.0D,5.0D, ParticleTypes.POOF);
            ParticleHelper.sphere(r.getLocation(), 2.0D,5.0D, ParticleTypes.POOF);
            player.playSound(SoundEvents.AMETHYST_BLOCK_RESONATE, 3f, 0.2f);
        }
    }







}
