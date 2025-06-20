package net.keb4.kims_artifacts.network.s2c.effects;


import net.keb4.kims_artifacts.network.s2c.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @apiNote Example of a callback or response packet from the server to a client or clients.
 * **/
public class SMRStrongExplosionCallbackPacket {

    public final int senderID;

    public SMRStrongExplosionCallbackPacket(FriendlyByteBuf buf) {
        this.senderID = buf.readInt();
    }

    public SMRStrongExplosionCallbackPacket(int senderID) {
        this.senderID = senderID;
    }

    public static void encode(SMRStrongExplosionCallbackPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.senderID);
    }

    public static SMRStrongExplosionCallbackPacket decode(FriendlyByteBuf buf) {
        return new SMRStrongExplosionCallbackPacket(buf);
    }

    // --- Handler: What happens when the packet is received on the server ---
    public static void handle(SMRStrongExplosionCallbackPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ClientPacketHandlers.handleSMRStrongExplosionPacketCB(message, contextSupplier);
        });
        context.setPacketHandled(true); // Mark the packet as handled
    }


}
