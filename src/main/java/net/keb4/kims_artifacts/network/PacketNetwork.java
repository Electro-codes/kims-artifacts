package net.keb4.kims_artifacts.network;

import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.network.c2s.OpenPotionBagPacket;
import net.keb4.kims_artifacts.network.c2s.PotionMixPacket;
import net.keb4.kims_artifacts.network.c2s.SMRStrongExplosionPacket;
import net.keb4.kims_artifacts.network.c2s.SMRWeakExplosionPacket;
import net.keb4.kims_artifacts.network.s2c.ArtifactSlotSyncPacket;
import net.keb4.kims_artifacts.network.s2c.ManualDeltaSyncPacket;
import net.keb4.kims_artifacts.network.s2c.PotionBagProgressSyncPacket;
import net.keb4.kims_artifacts.network.s2c.ScreenShakePacket;
import net.keb4.kims_artifacts.network.s2c.effects.SMRStrongExplosionCallbackPacket;
import net.keb4.kims_artifacts.network.s2c.effects.SMRWeakExplosionCallbackPacket;
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

        INSTANCE.messageBuilder(SMRStrongExplosionPacket.class, id())
                .encoder(SMRStrongExplosionPacket::encode) // Method to write packet data
                .decoder(SMRStrongExplosionPacket::decode) // Method to read packet data
                .consumerMainThread(SMRStrongExplosionPacket::handle) // Method to process packet on the main thread
                .add();

        INSTANCE.messageBuilder(SMRStrongExplosionCallbackPacket.class, id())
                .encoder(SMRStrongExplosionCallbackPacket::encode) // Method to write packet data
                .decoder(SMRStrongExplosionCallbackPacket::decode) // Method to read packet data
                .consumerMainThread(SMRStrongExplosionCallbackPacket::handle) // Method to process packet on the main thread
                .add();

        INSTANCE.messageBuilder(SMRWeakExplosionPacket.class, id())
                .encoder(SMRWeakExplosionPacket::encode) // Method to write packet data
                .decoder(SMRWeakExplosionPacket::decode) // Method to read packet data
                .consumerMainThread(SMRWeakExplosionPacket::handle) // Method to process packet on the main thread
                .add();

        INSTANCE.messageBuilder(SMRWeakExplosionCallbackPacket.class, id())
                .encoder(SMRWeakExplosionCallbackPacket::encode) // Method to write packet data
                .decoder(SMRWeakExplosionCallbackPacket::decode) // Method to read packet data
                .consumerMainThread(SMRWeakExplosionCallbackPacket::handle) // Method to process packet on the main thread
                .add();

        INSTANCE.messageBuilder(ManualDeltaSyncPacket.class, id())
                .encoder(ManualDeltaSyncPacket::encode) // Method to write packet data
                .decoder(ManualDeltaSyncPacket::decode) // Method to read packet data
                .consumerMainThread(ManualDeltaSyncPacket::handle) // Method to process packet on the main thread
                .add();
        INSTANCE.messageBuilder(ScreenShakePacket.class, id())
                .encoder(ScreenShakePacket::encode) // Method to write packet data
                .decoder(ScreenShakePacket::decode) // Method to read packet data
                .consumerMainThread(ScreenShakePacket::handle) // Method to process packet on the main thread
                .add();
        INSTANCE.messageBuilder(PotionMixPacket.class, id())
                .encoder(PotionMixPacket::encode) // Method to write packet data
                .decoder(PotionMixPacket::decode) // Method to read packet data
                .consumerMainThread(PotionMixPacket::handle) // Method to process packet on the main thread
                .add();
        INSTANCE.messageBuilder(PotionBagProgressSyncPacket.class, id())
                .encoder(PotionBagProgressSyncPacket::encode) // Method to write packet data
                .decoder(PotionBagProgressSyncPacket::decode) // Method to read packet data
                .consumerMainThread(PotionBagProgressSyncPacket::handle) // Method to process packet on the main thread
                .add();
        INSTANCE.messageBuilder(OpenPotionBagPacket.class, id())
                .encoder(OpenPotionBagPacket::encode) // Method to write packet data
                .decoder(OpenPotionBagPacket::decode) // Method to read packet data
                .consumerMainThread(OpenPotionBagPacket::handle) // Method to process packet on the main thread
                .add();
        INSTANCE.messageBuilder(ArtifactSlotSyncPacket.class, id())
                .encoder(ArtifactSlotSyncPacket::encode) // Method to write packet data
                .decoder(ArtifactSlotSyncPacket::decode) // Method to read packet data
                .consumerMainThread(ArtifactSlotSyncPacket::handle) // Method to process packet on the main thread
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
