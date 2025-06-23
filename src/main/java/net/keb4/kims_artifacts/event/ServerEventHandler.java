package net.keb4.kims_artifacts.event;


import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.capability.CapRegistry;
import net.keb4.kims_artifacts.capability.entity.PlayerArtifactCapProvider;
import net.keb4.kims_artifacts.entity.damage.DamageTypes;
import net.keb4.kims_artifacts.item.ArtifactItem;
import net.keb4.kims_artifacts.item.enchantment.EnchantmentRegistry;
import net.keb4.kims_artifacts.util.DamageFuncs;
import net.keb4.kims_artifacts.world.ArtifactGenData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventHandler {

    //attach capabilities to new entities
    @SubscribeEvent
    public static void attachCapsEvent(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof Player player)
        {
            if (!event.getObject().getCapability(CapRegistry.PLAYER_ARTIFACT_CAP).isPresent())
            {
                event.addCapability(ResourceLocation.fromNamespaceAndPath(Main.MODID, PlayerArtifactCapProvider.REGISTRY_NAME), new PlayerArtifactCapProvider());
            }
        }
    }

    //handles persistence of capability through player death
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        if (!event.getOriginal().level().isClientSide()) {
            event.getEntity().getCapability(CapRegistry.PLAYER_ARTIFACT_CAP).ifPresent(neW -> {
                event.getOriginal().reviveCaps();
                event.getOriginal().getCapability(CapRegistry.PLAYER_ARTIFACT_CAP).ifPresent(old -> {
                    Main.LOGGER.info("cloning!");
                    neW.copyFrom(old);
                });
            });
        }
    }

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
            event.setExtraLife(Integer.MAX_VALUE);
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingHurtEvent event)
    {
        if (event.getEntity().level().isClientSide) return;
        if (event.getEntity() instanceof Player player)
        {
            int lvl = EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistry.AEGIS.get(), player);
            if (lvl > 0 && event.getSource() == DamageTypes.ARTIFACT.getSource(player.level(),null))
            {
                float reduction = DamageFuncs.Point.funcDefault(lvl);
                Main.LOGGER.info("artifact damage reduction: {}%", (1-reduction)*100);
                event.setAmount(event.getAmount() * reduction);
            }
        }
    }
}
