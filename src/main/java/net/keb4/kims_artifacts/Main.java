package net.keb4.kims_artifacts;

import com.mojang.logging.LogUtils;

import net.keb4.kims_artifacts.client.model.artifact.AlchemistBagModel;
import net.keb4.kims_artifacts.client.model.artifact.SMRModel;
import net.keb4.kims_artifacts.client.particle.ParticleTypes;
import net.keb4.kims_artifacts.client.renderer.artifact.AlchemistBagRenderer;
import net.keb4.kims_artifacts.client.renderer.artifact.SMRRenderer;
import net.keb4.kims_artifacts.config.CommonConfig;
import net.keb4.kims_artifacts.container.MenuRegistry;
import net.keb4.kims_artifacts.item.ItemRegistry;
import net.keb4.kims_artifacts.item.enchantment.EnchantmentRegistry;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.keb4.kims_artifacts.sound.SoundRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
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
import org.slf4j.Logger;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import net.keb4.kims_artifacts.entity.capability.CapRegistry; //keep this here

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
        CommonConfig.register(context);


        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerLayerDefinitions);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        //All registration types
        ItemRegistry.register(modEventBus);
        PacketNetwork.register();
        ParticleTypes.register(modEventBus);
        EnchantmentRegistry.register(modEventBus);
        SoundRegistry.register(modEventBus);
        MenuRegistry.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::finalSetup);

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

    private void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        // This is where you tell Minecraft about the structure of your model
        // Make sure smrModel.createMesh() returns a LayerDefinition
        event.registerLayerDefinition(SMRModel.LAYER_LOCATION, SMRModel::createBodyLayer);
        event.registerLayerDefinition(AlchemistBagModel.LAYER_LOCATION, AlchemistBagModel::createBodyLayer);
        LOGGER.info("Registered model layer definition for {}", SMRModel.LAYER_LOCATION);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            CuriosRendererRegistry.register( (Item)(ItemRegistry.SMR_ITEM.get()), SMRRenderer::new);
            CuriosRendererRegistry.register((Item)(ItemRegistry.POTION_BAG_ITEM.get()), AlchemistBagRenderer::new);

            // Log a message to indicate that the client setup is complete
            LOGGER.info("Client setup complete for {}", MODID);
        }
    }
}
