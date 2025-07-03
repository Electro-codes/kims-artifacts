package net.keb4.kims_artifacts.system.artifact.potion.application;

import net.keb4.kims_artifacts.system.artifact.potion.PotionSysUtil;
import net.keb4.kims_artifacts.util.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**Handles all tipped weapon events**/
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TippedWeaponEventHandler {

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event)
    {
        //null + clientside check
        if (event.getEntity().level().isClientSide) return;
        if (event.getSource().getEntity() == null) return;


        if (event.getSource().getEntity() instanceof Player player)
        {
            ItemStack item = player.getItemBySlot(EquipmentSlot.MAINHAND);
            if (item.getOrCreateTag().contains(NBTHelper.TIPPED_WEAPON_LOC))
            {
                List<MobEffectInstance> efx = PotionUtils.getCustomEffects(item.getOrCreateTag().getCompound(NBTHelper.TIPPED_WEAPON_LOC));
                for (MobEffectInstance e: efx)
                {
                    event.getEntity().addEffect(e);
                }

                CompoundTag tipped = item.getOrCreateTag().getCompound(NBTHelper.TIPPED_WEAPON_LOC);
                int uses = tipped.getInt("Uses") - 1;
                if (uses <= 0) {
                    // Remove the tipped tag immediately when out of uses
                    item.getTag().remove(NBTHelper.TIPPED_WEAPON_LOC);
                } else {
                    tipped.putInt("Uses", uses);
                    item.getTag().put(NBTHelper.TIPPED_WEAPON_LOC, tipped);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event)
    {
        CompoundTag t = event.getItemStack().getTag();
        if (t != null)
        {
            if (t.contains(NBTHelper.TIPPED_WEAPON_LOC))
            {
                List<MobEffectInstance> instances = PotionUtils.getCustomEffects(t.getCompound(NBTHelper.TIPPED_WEAPON_LOC));
                List<Component> toBeAdded = new ArrayList<>();
                toBeAdded.add(Component.literal("Tipped Weapon").withStyle(ChatFormatting.AQUA));
                toBeAdded.add(Component.literal("Uses: " + t.getCompound(NBTHelper.TIPPED_WEAPON_LOC).getInt("Uses")));
                PotionSysUtil.appendTooltipWithoutApplied(instances, toBeAdded, 1);
                event.getToolTip().addAll(2, toBeAdded);
            }
        }
    }



}
