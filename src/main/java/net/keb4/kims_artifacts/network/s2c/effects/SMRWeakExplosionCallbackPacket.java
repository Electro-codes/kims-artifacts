package net.keb4.kims_artifacts.network.s2c.effects;

import net.keb4.kims_artifacts.network.c2s.SMRWeakExplosionPacket;
import net.keb4.kims_artifacts.network.c2s.ServerPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @apiNote Example of a callback or response packet from the server to a client or clients.
 * **/
public class SMRWeakExplosionCallbackPacket {
    public SMRWeakExplosionCallbackPacket() {


    }

    public static void encode(SMRWeakExplosionCallbackPacket msg, FriendlyByteBuf buf) {

    }

    public static SMRWeakExplosionCallbackPacket decode(FriendlyByteBuf buf) {
        return new SMRWeakExplosionCallbackPacket();
    }

    // --- Handler: What happens when the packet is received on the server ---
    public static void handle(SMRWeakExplosionCallbackPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {

        });
        context.setPacketHandled(true); // Mark the packet as handled
    }


}
