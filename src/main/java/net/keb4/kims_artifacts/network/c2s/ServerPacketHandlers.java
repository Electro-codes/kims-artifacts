package net.keb4.kims_artifacts.network.c2s;

import net.keb4.kims_artifacts.entity.damage.DamageTypes;
import net.keb4.kims_artifacts.util.RayUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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



    public static void handleSMRSmallExplosionPacket(SMRWeakExplosionPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ServerPlayer player = player(ctx);

        HitResult hit = RayUtils.simpleBlockRay(player, 20D, true);
        if (hit.getType() == HitResult.Type.MISS) {return;}

        Vec3 pos = hit.getLocation();

        ServerLevel server = player.serverLevel();

        server.explode(player, DamageTypes.ARTIFACT.getSource(server, player), new EntityBasedExplosionDamageCalculator(player), pos.x, pos.y, pos.z, 5f, false, Level.ExplosionInteraction.TNT);

    }


    public static ServerPlayer player(Supplier<NetworkEvent.Context> ctx) {return ctx.get().getSender();}
}
