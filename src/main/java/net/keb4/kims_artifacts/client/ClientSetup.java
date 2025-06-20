package net.keb4.kims_artifacts.client;


import net.keb4.kims_artifacts.client.particle.FlamepoofParticle;
import net.keb4.kims_artifacts.client.particle.ParticleTypes;
import net.keb4.kims_artifacts.client.renderer.artifact.SMRRenderer;
import net.keb4.kims_artifacts.client.renderer.overlay.SMROverlayRenderer;
import net.keb4.kims_artifacts.item.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleTypes.FLAMEPOOF.get().getType(), FlamepoofParticle.Provider::new);
    }

    @SubscribeEvent
    public static void fmlClientSetup(FMLClientSetupEvent event)
    {
        CuriosRendererRegistry.register( (Item)(ItemRegistry.SMR_ITEM.get()), SMRRenderer::new);
    }

    @SubscribeEvent
    public static void registerGUIOverlays(RegisterGuiOverlaysEvent event)
    {
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "smr_gui_overlay", SMROverlayRenderer.SMR_HOTBAR);
    }




}
