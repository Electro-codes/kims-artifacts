package net.keb4.kims_artifacts.network.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**Lets the server know a potion bag was opened clientside**/
public class OpenPotionBagPacket {

    public OpenPotionBagPacket() {


    }

    public static void encode(OpenPotionBagPacket msg, FriendlyByteBuf buf) {

    }

    public static OpenPotionBagPacket decode(FriendlyByteBuf buf) {
        return new OpenPotionBagPacket();
    }

    // --- Handler: What happens when the packet is received on the server ---
    public static void handle(OpenPotionBagPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPacketHandlers.handleOpenPotionBagPacket(message,contextSupplier);
        });
        context.setPacketHandled(true); // Mark the packet as handled
    }
}
