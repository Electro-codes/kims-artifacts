package net.keb4.kims_artifacts.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**Updates the artifact slot on the client with the itemstack passed in.**/
public class ArtifactSlotSyncPacket {

    public final ItemStack stack;


    public ArtifactSlotSyncPacket(FriendlyByteBuf buf) {
        this.stack = buf.readItem();
    }

    public ArtifactSlotSyncPacket(ItemStack val) {
        this.stack = val;
    }

    public static void encode(ArtifactSlotSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeItem(msg.stack);
    }

    public static ArtifactSlotSyncPacket decode(FriendlyByteBuf buf) {
        return new ArtifactSlotSyncPacket(buf);
    }

    // --- Handler: What happens when the packet is received on the server ---
    public static void handle(ArtifactSlotSyncPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> ClientPacketHandlers.handleArtifactSlotSyncPacket(message,contextSupplier));
        context.setPacketHandled(true); // Mark the packet as handled
    }
    
    
}
