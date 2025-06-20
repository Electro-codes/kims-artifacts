package net.keb4.kims_artifacts.network.s2c.effects;

import net.keb4.kims_artifacts.network.s2c.ClientPacketHandlers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SMRWeakExplosionCallbackPacket {
    public final int senderID;

    public SMRWeakExplosionCallbackPacket(FriendlyByteBuf buf) {
        this.senderID = buf.readInt();
    }

    public SMRWeakExplosionCallbackPacket(int senderID) {
        this.senderID = senderID;
    }

    public static void encode(SMRWeakExplosionCallbackPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.senderID);
    }

    public static SMRWeakExplosionCallbackPacket decode(FriendlyByteBuf buf) {
        return new SMRWeakExplosionCallbackPacket(buf);
    }

    // --- Handler: What happens when the packet is received on the server ---
    public static void handle(SMRWeakExplosionCallbackPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ClientPacketHandlers.handleSMRWeakExplosionPacketCB(message, contextSupplier);
        });
        context.setPacketHandled(true); // Mark the packet as handled
    }
}
