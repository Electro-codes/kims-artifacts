package net.keb4.kims_artifacts.network.s2c;

import net.keb4.kims_artifacts.item.ItemRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.function.Supplier;

public class ResonanceSyncPacket {

    public final HashMap<ResourceLocation, Float> resonanceValues;
    public final boolean initialized;
    private final int length;

    public ResonanceSyncPacket(FriendlyByteBuf buf) {
        this.initialized = buf.readBoolean();
        this.length = buf.readInt();
        this.resonanceValues = new HashMap<>();
        for (int i = 0; i < length; i++)
        {
            this.resonanceValues.put(ResourceLocation.parse(buf.readUtf()), buf.readFloat());
        }

    }

    public ResonanceSyncPacket(HashMap<ResourceLocation, Float> resval, boolean initialized) {
        this.resonanceValues = resval;
        this.initialized = initialized;
        this.length = resval.size();
    }

    public static void encode(ResonanceSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.initialized);
        buf.writeInt(msg.length);
        for (RegistryObject<Item> a : ItemRegistry.getArtifacts())
        {
            buf.writeUtf(a.getId().toString());
            buf.writeFloat(msg.resonanceValues.get(a.getId()));
        }
    }

    public static ResonanceSyncPacket decode(FriendlyByteBuf buf) {
        return new ResonanceSyncPacket(buf);
    }

    // --- Handler: What happens when the packet is received on the server ---
    public static void handle(ResonanceSyncPacket message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> ClientPacketHandlers.handleResonanceSyncPacket(message,contextSupplier));
        context.setPacketHandled(true); // Mark the packet as handled
    }
    
    
}
