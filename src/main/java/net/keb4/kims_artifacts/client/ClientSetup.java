package net.keb4.kims_artifacts.client;


import net.keb4.kims_artifacts.client.particle.FlamepoofParticle;
import net.keb4.kims_artifacts.client.particle.ParticleTypes;
import net.keb4.kims_artifacts.client.renderer.artifact.SMRRenderer;
import net.keb4.kims_artifacts.client.renderer.overlay.SMROverlayRenderer;
import net.keb4.kims_artifacts.client.screen.PotionBagScreen;
import net.keb4.kims_artifacts.container.MenuRegistry;
import net.keb4.kims_artifacts.item.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
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
        event.enqueueWork(() -> {
            CuriosRendererRegistry.register( (Item)(ItemRegistry.SMR_ITEM.get()), SMRRenderer::new);
            MenuScreens.register(MenuRegistry.POTION_BAG_MENU.get(), PotionBagScreen::new);


            Minecraft.getInstance().getItemColors().register((itemStack, i) ->
            {
             if (i == 1)
             {
                 CompoundTag tag = itemStack.getTag();
                 if (tag != null && tag.contains("CustomPotionEffects", Tag.TAG_LIST)) {
                     return PotionUtils.getColor(PotionUtils.getCustomEffects(itemStack.getTag()));
                 }
                 return 0x55AAFF;
             }
             return 0xFFFFFF;
            }, ItemRegistry.CONCOCTION_ITEM.get());




        });

    }

    @SubscribeEvent
    public static void registerGUIOverlays(RegisterGuiOverlaysEvent event)
    {
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "smr_gui_overlay", SMROverlayRenderer.SMR_HOTBAR);
    }




}
