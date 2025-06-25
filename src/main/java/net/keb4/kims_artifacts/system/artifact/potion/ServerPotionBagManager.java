package net.keb4.kims_artifacts.system.artifact.potion;


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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerPotionBagManager {

    //format is player:item, remaining
    private static HashMap<UUID, Integer> cooldownList = new HashMap<>();

    //get max cooldown from config
    public static final int maxCooldown = CommonConfig.potionBagBrewTime;


    //add a mix to the cooldown map
    public static void scheduleNewMix(ServerPlayer sender, ItemStack stack)
    {
        //basically a pair of player and random UUIDs (I might deimplement this since theres only gonna be one artifact, but we'll see)
        
        cooldownList.put(sender.getUUID(), maxCooldown);
    }

    //runs every tick on server
    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent tickEvent)
    {
        //if statements:
        //ticks have two phases so you have to make sure you're only hooking into one, or itll run twice.
        //every x ticks run this list
        if (tickEvent.phase == TickEvent.Phase.END && tickEvent.getServer().getTickCount() % CommonConfig.potionSyncPeriod == 0) {
            //for each entry logged in list
            for (UUID uuid : cooldownList.keySet()) {
                //get player
                ServerPlayer p = tickEvent.getServer().getPlayerList().getPlayer(uuid);
                //get amount remaining
                int amt = cooldownList.getOrDefault(uuid, 0);
                //(serverside) decrement an unsynced copy of the curio item
                CurioHelper.getArtifactCurio(p).getCapability(CapRegistry.POTION_BAG_BEHAVIOR_CAP).ifPresent(iPotionBagBehavior -> iPotionBagBehavior.setProgress(amt));

                //sync changes
                PacketNetwork.sendToPlayer(new PotionBagProgressSyncPacket(amt, uuid), tickEvent.getServer().getPlayerList().getPlayer(uuid));
                //if the container is open sync changes there too
                if (p.containerMenu instanceof PotionBagMenu menu)
                {
                    menu.updateProgress();
                }

                //if an entry cooldown is completed,
                if (cooldownList.get(uuid) <= 0) {
                    //remove it from cooldown
                    cooldownList.remove(uuid);
                    //add any on-completion code here
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
        //get curio
        ItemStack bag = CurioHelper.getArtifactCurio(p);

        //if the bag has an inventory/is present (it definitely should)
        bag.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent((handler ->
        {
            //filter allowed items to only items sp
            if (!PotionSysUtil.Craft.isValid(handler)) return;

            //get mobeffects for each of the two potion slots (third is WIP)
            List<MobEffectInstance> i1 = PotionUtils.getMobEffects(handler.getStackInSlot(0));
            List<MobEffectInstance> i2 = PotionUtils.getMobEffects(handler.getStackInSlot(1));
            List<MobEffectInstance> i3 = PotionUtils.getMobEffects(handler.getStackInSlot(2));


            //new concoction instance
            ItemStack out = ItemStack.EMPTY;
            if (handler.getStackInSlot(3).isEmpty()) {
                out = new ItemStack(ItemRegistry.CONCOCTION_ITEM.get());
                PotionUtils.setCustomEffects(out, PotionSysUtil.Mix.mix(PotionSysUtil.Mix.mix(i1, i2), i3));
                out.getOrCreateTag().put(NBTHelper.CONCOCTION_METADATA, new CompoundTag());
            } else
            {
                out = handler.getStackInSlot(3);
                PotionSysUtil.Craft.tipWeapon(out, new ItemStack[]{handler.getStackInSlot(0), handler.getStackInSlot(1), handler.getStackInSlot(2)});
            }

            //add or subtract items
            handler.insertItem(3, out, false);
            handler.extractItem(0, 1, false);
            handler.extractItem(1, 1, false);
            handler.extractItem(2, 1, false);
            handler.insertItem(0, Items.GLASS_BOTTLE.getDefaultInstance(), false);
            handler.insertItem(1, Items.GLASS_BOTTLE.getDefaultInstance(), false);
            handler.insertItem(2, Items.GLASS_BOTTLE.getDefaultInstance(), false);

            //sync menu/itemhandler changes to menu if its open
            if (p.containerMenu instanceof PotionBagMenu menu)
            {
                menu.broadcastChanges();
                menu.setProgress(CommonConfig.potionBagBrewTime);
            }
        }));
    }
}
