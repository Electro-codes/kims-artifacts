package net.keb4.kims_artifacts.network.c2s;

import net.keb4.kims_artifacts.entity.damage.DamageTypes;
import net.keb4.kims_artifacts.item.artifacts.SMRItem;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.keb4.kims_artifacts.network.s2c.effects.SMRWeakExplosionCallbackPacket;
import net.keb4.kims_artifacts.util.CurioHelper;
import net.keb4.kims_artifacts.util.RayUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @apiNote All methods inside this class are assumed to be run inside {@link NetworkEvent.Context#enqueueWork(Runnable)}
 * **/
public class ServerPacketHandlers {
    /**
     * Dictates the range from the sender where other players can receive callback packets.
     * **/
    public static final int defaultResponseRange = 16*4; //~4 chunks


    public static void handleSMRSmallExplosionPacket(SMRWeakExplosionPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ServerPlayer player = player(ctx);
            HitResult hit = RayUtils.simpleEntityBlockRay(player, SMRItem.RAYCAST_RANGE, true);
            if (hit.getType() == HitResult.Type.MISS) {
                return;
            }

            Vec3 pos = hit.getLocation();

            ServerLevel server = player.serverLevel();

            server.explode(player, DamageTypes.ARTIFACT.getSource(server, player), new EntityBasedExplosionDamageCalculator(player), pos.x, pos.y, pos.z, 5f, false, Level.ExplosionInteraction.TNT);

            for (Player p : player.level().players()) {
                if (p.position().distanceToSqr(player.position()) <= defaultResponseRange * defaultResponseRange) {
                    PacketNetwork.sendToPlayer(new SMRWeakExplosionCallbackPacket(player.getId()), (ServerPlayer) p);
                }
            }
    }


    public static ServerPlayer player(Supplier<NetworkEvent.Context> ctx) {return ctx.get().getSender();}
}
