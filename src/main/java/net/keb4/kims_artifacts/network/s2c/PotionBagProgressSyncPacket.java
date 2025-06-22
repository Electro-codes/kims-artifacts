package net.keb4.kims_artifacts.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class PotionBagProgressSyncPacket {

    public int progress;
    private UUID itemStackUUID;

    public PotionBagProgressSyncPacket(FriendlyByteBuf buf) {
        this.progress = buf.readInt();
        this.itemStackUUID = buf.readUUID();
    }

    public PotionBagProgressSyncPacket(int progress, UUID uuid) {
        this.progress = progress;
        this.itemStackUUID = uuid;
    }

    public static void encode(PotionBagProgressSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.progress);
        buf.writeUUID(msg.itemStackUUID);
    }

    public static PotionBagProgressSyncPacket decode(FriendlyByteBuf buf) {
        return new PotionBagProgressSyncPacket(buf);
    }

    // --- Handler: What happens when the packet is received on the server ---
    public static void handle(PotionBagProgressSyncPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> ClientPacketHandlers.handlePotionBagProgressSyncPacket(message,contextSupplier));
        context.setPacketHandled(true); // Mark the packet as handled
    }

}
