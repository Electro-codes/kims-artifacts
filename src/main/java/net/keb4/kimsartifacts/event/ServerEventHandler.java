package net.keb4.kimsartifacts.event;


import net.keb4.kimsartifacts.item.ItemRegistry;
import net.keb4.kimsartifacts.Config;
import net.keb4.kimsartifacts.world.ArtifactGenData;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventHandler {

    @SubscribeEvent
    public static void onServerStart(ServerStartingEvent event)
    {
        //inserting all items that should be included in the special generation method (debug fix)
        if (!Config.artifacts.contains(ItemRegistry.TEST_ITEM.get())) Config.artifacts.add(ItemRegistry.TEST_ITEM.get());

        ArtifactGenData artifactData = ArtifactGenData.get(event.getServer().getLevel(Level.OVERWORLD));
        artifactData.populateItemMap();
    }

}
