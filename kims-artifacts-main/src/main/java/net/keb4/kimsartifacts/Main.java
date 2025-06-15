package net.keb4.kimsartifacts;

import com.mojang.logging.LogUtils;
import net.keb4.kimsartifacts.item.ItemRegistry;

import net.keb4.kimsartifacts.item.smr.smrModel;
import net.keb4.kimsartifacts.item.smr.smrRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MODID)
public class Main
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "kims_artifacts";


    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Main(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerLayerDefinitions);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        //All registration types
        ItemRegistry.register(modEventBus);


        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::finalSetup);

    }
    private void clientSetup(final FMLClientSetupEvent event)
    {
        // Register the smrRenderer to the Curios Renderer Registry

            // Register the smrRenderer for the SMR item
        CuriosRendererRegistry.register( (Item)(ItemRegistry.SMR_ITEM.get()),smrRenderer::new);

        // Log a message to indicate that the client setup is complete
        LOGGER.info("Client setup complete for {}", MODID);
    }

     private void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        // This is where you tell Minecraft about the structure of your model
        // Make sure smrModel.createMesh() returns a LayerDefinition
        event.registerLayerDefinition(smrModel.LAYER_LOCATION, smrModel::createBodyLayer);
        LOGGER.info("Registered model layer definition for {}", smrModel.LAYER_LOCATION);
    }
    


    private void commonSetup(final FMLCommonSetupEvent event)
    {
        
    }

    private void finalSetup(final FMLLoadCompleteEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }


}
