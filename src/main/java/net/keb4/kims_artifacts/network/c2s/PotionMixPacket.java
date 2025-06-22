package net.keb4.kims_artifacts.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PotionMixPacket {


    public PotionMixPacket() {


    }

    public static void encode(PotionMixPacket msg, FriendlyByteBuf buf) {

    }

    public static PotionMixPacket decode(FriendlyByteBuf buf) {
        return new PotionMixPacket();
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
