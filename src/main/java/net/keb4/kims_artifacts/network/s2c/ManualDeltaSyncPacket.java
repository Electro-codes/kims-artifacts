package net.keb4.kims_artifacts.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**Manually syncs delta movement in ways vanilla cannot.**/
public class ManualDeltaSyncPacket {

    public final Vec3 delta;

    public ManualDeltaSyncPacket(FriendlyByteBuf buf) {
        delta = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public ManualDeltaSyncPacket(Vec3 delta) {
        this.delta = delta;
    }

    public static void encode(ManualDeltaSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.delta.x);
        buf.writeDouble(msg.delta.y);
        buf.writeDouble(msg.delta.z);
    }

    public static ManualDeltaSyncPacket decode(FriendlyByteBuf buf) {
        return new ManualDeltaSyncPacket(buf);
    }

    // --- Handler: What happens when the packet is received on the server ---
    public static void handle(ManualDeltaSyncPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> ClientPacketHandlers.handleManualDeltaSyncPacket(message,contextSupplier));
        context.setPacketHandled(true); // Mark the packet as handled
    }
    
}
