package net.keb4.kims_artifacts.system.artifact.potion;

import javafx.util.Pair;
import net.keb4.kims_artifacts.capability.CapRegistry;
import net.keb4.kims_artifacts.config.CommonConfig;
import net.keb4.kims_artifacts.container.PotionBagMenu;
import net.keb4.kims_artifacts.item.ItemRegistry;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.keb4.kims_artifacts.network.s2c.PotionBagProgressSyncPacket;
import net.keb4.kims_artifacts.util.CurioHelper;
import net.keb4.kims_artifacts.util.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerPotionBagManager {

    //format is player:item, remaining
    private static HashMap<Pair<UUID, UUID>, Integer> cooldownList = new HashMap<>();

    public static final int maxCooldown = CommonConfig.potionBagBrewTime;


    public static void scheduleNewMix(ServerPlayer sender, ItemStack stack)
    {
        Pair<UUID, UUID> p = new Pair<>(sender.getUUID(), UUID.randomUUID());
        cooldownList.put(p, maxCooldown);
    }

    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent tickEvent)
    {
        if (tickEvent.phase == TickEvent.Phase.END && tickEvent.getServer().getTickCount() % CommonConfig.potionSyncPeriod == 0) {
            //for each entry logged in list
            for (Pair<UUID,UUID> pair : cooldownList.keySet()) {
                //get player
                ServerPlayer p = tickEvent.getServer().getPlayerList().getPlayer(pair.getKey());
                //get amount remaining
                int amt = cooldownList.getOrDefault(pair, 0);
                //(serverside) decrement an unsynced copy of the curio item
                CurioHelper.getArtifactCurio(p).getCapability(CapRegistry.POTION_BAG_BEHAVIOR_CAP).ifPresent(iPotionBagBehavior -> iPotionBagBehavior.setProgress(amt));

                //sync changes
                PacketNetwork.sendToPlayer(new PotionBagProgressSyncPacket(amt, pair.getValue()), tickEvent.getServer().getPlayerList().getPlayer(pair.getKey()));
                //if the container is open sync changes there too
                if (p.containerMenu instanceof PotionBagMenu menu)
                {
                    menu.updateProgress();
                }

                //if an entry cooldown is completed,
                if (cooldownList.get(pair) <= 0) {
                    //remove it from cooldown
                    cooldownList.remove(pair);
                    //add any final code here
                    finishBrew(p, tickEvent);
                }
            }
            //end of all, decrement values of server cooldown list
            decrementValues();
        }
    }


    private static void decrementValues()
    {
        cooldownList.replaceAll(((uuiduuidPair, integer) ->
        {
            int i = integer - CommonConfig.potionSyncPeriod;
            if (i < 0) i = 0;
            return i;
        }));
    }

    private static void finishBrew(ServerPlayer p, TickEvent.ServerTickEvent event)
    {
        ItemStack bag = CurioHelper.getArtifactCurio(p);
        bag.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((handler ->
        {
            //mixing only works with potions
            if (!(handler.getStackInSlot(0).getItem() instanceof PotionItem && handler.getStackInSlot(1).getItem() instanceof PotionItem)) return;
            List<MobEffectInstance> i1 = PotionUtils.getMobEffects(handler.getStackInSlot(0));
            List<MobEffectInstance> i2 = PotionUtils.getMobEffects(handler.getStackInSlot(1));


            ItemStack out = new ItemStack(ItemRegistry.CONCOCTION_ITEM.get());
            PotionUtils.setCustomEffects(out, PotionUtil.Mix.mix(i1, i2));
            out.getOrCreateTag().put(NBTHelper.CONCOCTION_METADATA, new CompoundTag());
            handler.insertItem(2, out, false);
            handler.extractItem(0, 1, false);
            handler.extractItem(1, 1, false);

            if (p.containerMenu instanceof PotionBagMenu menu)
            {
                menu.broadcastChanges();
                menu.setProgress(CommonConfig.potionBagBrewTime);
            }
        }));
    }
}
