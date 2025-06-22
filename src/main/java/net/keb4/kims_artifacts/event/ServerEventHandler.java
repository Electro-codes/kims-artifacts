package net.keb4.kims_artifacts.event;


import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.entity.capability.CapRegistry;
import net.keb4.kims_artifacts.entity.capability.IArtifactPlayerCap;
import net.keb4.kims_artifacts.entity.capability.PlayerArtifactCapProvider;
import net.keb4.kims_artifacts.entity.capability.PlayerArtifactCapability;
import net.keb4.kims_artifacts.entity.damage.DamageTypes;
import net.keb4.kims_artifacts.item.ArtifactItem;
import net.keb4.kims_artifacts.item.IArtifact;
import net.keb4.kims_artifacts.item.ResonanceForkItem;
import net.keb4.kims_artifacts.item.artifacts.PotionBagItem;
import net.keb4.kims_artifacts.item.enchantment.EnchantmentRegistry;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.keb4.kims_artifacts.network.c2s.ServerPacketHandlers;
import net.keb4.kims_artifacts.network.s2c.PotionBagProgressSyncPacket;
import net.keb4.kims_artifacts.util.DamageFuncs;
import net.keb4.kims_artifacts.world.ArtifactGenData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    //supply brand new resonance values to new players only
    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getEntity().level().isClientSide) return;
        LazyOptional<IArtifactPlayerCap> capLazyOptional = event.getEntity().getCapability(CapRegistry.PLAYER_ARTIFACT_CAP);
        if (capLazyOptional.isPresent())
        {
            IArtifactPlayerCap cap = capLazyOptional.resolve().get();

            if (!cap.isInitializedAlready()) {
                cap.setInitalized();
                PlayerArtifactCapability.randomizeResonance((ServerPlayer) event.getEntity(), cap, 1);
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

    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END)
        {
            List<UUID> replace = new ArrayList<>();
            ServerPacketHandlers.itemBrewStates.replaceAll(((uuid, aLong) ->
            {
                aLong -= 1;
                if (aLong < 0)
                {
                    replace.add(uuid);
                    return 0;
                }
                return aLong;
            }));
            for (UUID u : replace)
            {
                ServerPacketHandlers.itemBrewStates.remove(u);
            }
        }
    }
}
