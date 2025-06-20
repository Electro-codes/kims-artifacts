package net.keb4.kims_artifacts.network.s2c;

import ca.weblite.objc.Client;
import net.keb4.kims_artifacts.client.renderer.ScreenShakeRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import javax.naming.Context;
import java.util.function.Supplier;

public class ScreenShakePacket {

    public final float strength;
    public final int duration;
    public final ScreenShakeRenderer.Types type;


    public ScreenShakePacket(float strength, int duration, String type)
    {
        this.strength = strength;
        this.duration = duration;
        this.type = ScreenShakeRenderer.Types.valueOf(type);
    }

    public ScreenShakePacket(FriendlyByteBuf buf)
    {
        this.strength = buf.readFloat();
        this.duration = buf.readInt();
        this.type = ScreenShakeRenderer.Types.values()[buf.readInt()];
    }

    public static void encode(ScreenShakePacket msg, FriendlyByteBuf buf) {
        buf.writeFloat(msg.strength);
        buf.writeInt(msg.duration);
        buf.writeInt(msg.type.ordinal());
    }

    public static ScreenShakePacket decode(FriendlyByteBuf buf)
    {
        return new ScreenShakePacket(buf);
    }

    public static void handle(ScreenShakePacket msg, Supplier<NetworkEvent.Context> contextSupplier)
    {
        contextSupplier.get().enqueueWork(() ->
        {
            ClientPacketHandlers.handleScreenShakePacket(msg, contextSupplier);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
