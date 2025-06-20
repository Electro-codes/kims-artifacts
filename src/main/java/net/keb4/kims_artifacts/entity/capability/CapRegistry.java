package net.keb4.kims_artifacts.entity.capability;

import net.keb4.kims_artifacts.Main;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapRegistry {

    public static Capability<IArtifactPlayerCap> PLAYER_ARTIFACT_CAP = CapabilityManager.get(new CapabilityToken<IArtifactPlayerCap>() {});

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IArtifactPlayerCap.class); // Register the interface of your capability
    }
}
