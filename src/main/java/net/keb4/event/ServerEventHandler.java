package net.keb4.event;


import net.keb4.item.ItemRegistry;
import net.keb4.kimsartifacts.Config;
import net.keb4.world.ArtifactGenData;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

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
