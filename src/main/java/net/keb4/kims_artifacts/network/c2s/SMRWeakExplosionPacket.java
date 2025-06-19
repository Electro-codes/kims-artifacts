package net.keb4.kims_artifacts.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @apiNote Example of a client to server packet. No data needs to be written because the server knows what the correct values are (e.x, explosion strength)
 * **/
public class SMRWeakExplosionPacket {

    public SMRWeakExplosionPacket() {


    }

    public static void encode(SMRWeakExplosionPacket msg, FriendlyByteBuf buf) {

    }

    public static SMRWeakExplosionPacket decode(FriendlyByteBuf buf) {
        return new SMRWeakExplosionPacket();
    }

    // --- Handler: What happens when the packet is received on the server ---
    public static void handle(SMRWeakExplosionPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPacketHandlers.handleSMRSmallExplosionPacket(message,contextSupplier);
        });
        context.setPacketHandled(true); // Mark the packet as handled
    }


}
