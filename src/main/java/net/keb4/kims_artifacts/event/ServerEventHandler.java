package net.keb4.kims_artifacts.event;


import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.item.ArtifactItem;
import net.keb4.kims_artifacts.world.ArtifactGenData;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventHandler {

    @SubscribeEvent
    public static void onServerStart(ServerStartingEvent event)
    {
        ArtifactGenData artifactData = ArtifactGenData.get(event.getServer().getLevel(Level.OVERWORLD));
        artifactData.populateItemMap();
    }

    @SubscribeEvent
    public static void onItemStackDespawn(ItemExpireEvent event)
    {
        if (event.getEntity().getItem().getItem() instanceof ArtifactItem && event.getExtraLife() < 6000)
        {
            event.setCanceled(true);
        }
    }



}
