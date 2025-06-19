package net.keb4.kims_artifacts.network;

import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.network.c2s.SMRWeakExplosionPacket;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
public class PacketNetwork {

    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }


    public static void register()
    {
        INSTANCE = NetworkRegistry.ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(Main.MODID, "main_channel"))
                .serverAcceptedVersions((v) -> true) // Accept all client versions
                .clientAcceptedVersions((v) -> true) // Accept all server versions
                .networkProtocolVersion(() -> "1") // Define your protocol version
                .simpleChannel();

        INSTANCE.messageBuilder(SMRWeakExplosionPacket.class, id())
                .encoder(SMRWeakExplosionPacket::encode) // Method to write packet data
                .decoder(SMRWeakExplosionPacket::decode) // Method to read packet data
                .consumerMainThread(SMRWeakExplosionPacket::handle) // Method to process packet on the main thread
                .add();
    }


    /**
     * @apiNote Sends a packet to the server from the client. Call on the client only.
     * **/
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), message);
    }

    /**
     * @apiNote Sends a packet to a player from the server. Call on server only.
     * **/
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }


}
