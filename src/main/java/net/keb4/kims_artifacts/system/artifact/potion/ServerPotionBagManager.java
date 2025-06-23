package net.keb4.kims_artifacts.system.artifact.potion;

import javafx.util.Pair;
import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.config.CommonConfig;
import net.keb4.kims_artifacts.item.ConcoctionItem;
import net.keb4.kims_artifacts.item.CurioArtifactItem;
import net.keb4.kims_artifacts.item.ItemRegistry;
import net.keb4.kims_artifacts.item.artifacts.PotionBagItem;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.keb4.kims_artifacts.network.s2c.ArtifactSlotSyncPacket;
import net.keb4.kims_artifacts.network.s2c.PotionBagProgressSyncPacket;
import net.keb4.kims_artifacts.util.CurioHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemStackHandler;

import java.util.*;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerPotionBagManager {

    //format is player:item, remaining
    private static HashMap<Pair<UUID, UUID>, Integer> cooldownList = new HashMap<>();

    public static final int maxCooldown = CommonConfig.potionBagBrewTime;


    public static void scheduleNewMix(ServerPlayer sender, ItemStack stack)
    {
        Pair<UUID, UUID> p = new Pair<>(sender.getUUID(), stack.getOrCreateTag().getUUID("UUID"));
        cooldownList.put(p, maxCooldown);
    }

    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent tickEvent)
    {
        //might make this run every syncperiod instead but eh we'll see
        if (tickEvent.phase == TickEvent.Phase.END) {


        }
    }
}
