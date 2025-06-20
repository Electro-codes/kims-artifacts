package net.keb4.kims_artifacts.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @apiNote Example of a client to server packet. No data needs to be written because the server knows what the correct values are (e.x, explosion strength)
 * **/
public class SMRStrongExplosionPacket {

    public SMRStrongExplosionPacket() {


    }

    public static void encode(SMRStrongExplosionPacket msg, FriendlyByteBuf buf) {

    }

    public static SMRStrongExplosionPacket decode(FriendlyByteBuf buf) {
        return new SMRStrongExplosionPacket();
    }

    // --- Handler: What happens when the packet is received on the server ---
    public static void handle(SMRStrongExplosionPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPacketHandlers.handleSMRStrongExplosionPacket(message,contextSupplier);
        });
        context.setPacketHandled(true); // Mark the packet as handled
    }


}
