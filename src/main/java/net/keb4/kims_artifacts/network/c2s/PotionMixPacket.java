package net.keb4.kims_artifacts.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PotionMixPacket {

    public final ItemStack stack;

    public PotionMixPacket(ItemStack stack) {
        this.stack = stack;
    }

    public PotionMixPacket(FriendlyByteBuf buf) {
        this.stack = buf.readItem();
    }

    public static void encode(PotionMixPacket msg, FriendlyByteBuf buf) {
        buf.writeItem(msg.stack);
    }

    public static PotionMixPacket decode(FriendlyByteBuf buf) {
        return new PotionMixPacket(buf);
    }

    // --- Handler: What happens when the packet is received on the server ---
    public static void handle(PotionMixPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPacketHandlers.handlePotionMixPacket(message,contextSupplier);
        });
        context.setPacketHandled(true); // Mark the packet as handled
    }



}
