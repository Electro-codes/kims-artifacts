package net.keb4.kims_artifacts.system.artifact.potion;

import javafx.util.Pair;
import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.config.CommonConfig;
import net.keb4.kims_artifacts.item.artifacts.PotionBagItem;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.keb4.kims_artifacts.network.s2c.PotionBagProgressSyncPacket;
import net.keb4.kims_artifacts.util.CurioHelper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
    public static HashMap<Pair<UUID, UUID>, Integer> cooldownList = new HashMap<>();

    public static final int maxCooldown = CommonConfig.potionBagBrewTime;


    public static void scheduleNewMix(ServerPlayer sender, ItemStack stack)
    {
        Pair<UUID, UUID> p = new Pair<>(sender.getUUID(), stack.getOrCreateTag().getUUID("UUID"));
        cooldownList.put(p, maxCooldown);
        Main.LOGGER.info("Scheduled new mix!");
    }

    public static void removeInvalid(MinecraftServer server)
    {
        List<Pair<UUID,UUID>> removeQueue = new ArrayList<>();

        for (Map.Entry<Pair<UUID, UUID>, Integer> i : cooldownList.entrySet())
        {
            if (i.getValue() <= 0)
            {
                removeQueue.add(i.getKey());
                Main.LOGGER.info("Finished mixing!");
            }
        }
        for (Pair<UUID, UUID> i : removeQueue)
        {
            finishMix(server);
            cooldownList.remove(i);
        }
    }

    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent tickEvent)
    {
        //might make this run every syncperiod instead but eh we'll see
        if (tickEvent.phase == TickEvent.Phase.END) {

            cooldownList.replaceAll(((uuiduuidPair, integer) ->
            {
                return integer - 1;
            }));

            for (Map.Entry<Pair<UUID, UUID>, Integer> t : cooldownList.entrySet())
            {
                if (t.getValue() % CommonConfig.syncPeriod == 0)
                {
                    findAndUpdateStacks(tickEvent.getServer());
                    removeInvalid(tickEvent.getServer());
                    PacketNetwork.sendToPlayer(new PotionBagProgressSyncPacket(t.getValue(), t.getKey().getValue()), tickEvent.getServer().getPlayerList().getPlayer(t.getKey().getKey()));
                }
            }


        }
    }

    public static void findAndUpdateStacks(MinecraftServer server)
    {
        List<ServerPlayer> players = new ArrayList<>();

        for (ServerPlayer p : server.getPlayerList().getPlayers())
        {
            for (Pair<UUID, UUID> e : cooldownList.keySet())
            {
                if (e.getKey() == p.getUUID()) players.add(p); break;
            }
        }

        for (ServerPlayer p : players)
        {
            //first check the curio slot
            ItemStack possibleCurio = CurioHelper.getArtifactCurio(p);
            if (possibleCurio.getItem() instanceof PotionBagItem)
            {
                UUID id = possibleCurio.getOrCreateTag().getUUID("UUID");
                possibleCurio.getOrCreateTag().putInt("Progress", cooldownList.get(new Pair<>(p.getUUID(), id)));
                Main.LOGGER.info("successfully updated potionbag on server side! Used quick method!");
                continue;
            }

            //if not there, then scan the player inventory
            for (int i = 0; i < p.getInventory().items.size(); i++)
            {
                ItemStack item = p.getInventory().getItem(i);
                if (item.getItem() instanceof PotionBagItem)
                {
                    UUID id = item.getOrCreateTag().getUUID("UUID");
                    item.getOrCreateTag().putInt("Progress", cooldownList.get(new Pair<>(p.getUUID(), id)));
                    Main.LOGGER.info("successfully updated potionbag on server side!");
                    continue;
                }
            }
        }
    }


    public static void finishMix(MinecraftServer server)
    {
        List<ServerPlayer> players = new ArrayList<>();

        for (ServerPlayer p : server.getPlayerList().getPlayers())
        {
            for (Pair<UUID, UUID> e : cooldownList.keySet())
            {
                if (e.getKey() == p.getUUID()) players.add(p); break;
            }
        }

        for (ServerPlayer p : players)
        {
            ItemStack possibleCurio = CurioHelper.getArtifactCurio(p);
            if (possibleCurio.getItem() instanceof PotionBagItem)
            {
                UUID id = possibleCurio.getOrCreateTag().getUUID("UUID");
                if (cooldownList.get(new Pair<>(p.getUUID(), id)) != null)
                {
                    ItemStackHandler handler = PotionBagItem.getOrCreateItemHandler(possibleCurio);
                    continue;
                }
            }

            for (int i = 0; i < p.getInventory().items.size(); i++)
            {
                ItemStack item = p.getInventory().getItem(i);
                if (item.getItem() instanceof PotionBagItem)
                {
                    UUID id = item.getOrCreateTag().getUUID("UUID");
                    if (cooldownList.get(new Pair<>(p.getUUID(), id)) != null)
                    {
                        ItemStackHandler handler = PotionBagItem.getOrCreateItemHandler(item);

                    }
                }
            }
        }


    }

}
