package net.keb4.kims_artifacts.system.artifact.potion.application;

import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.system.artifact.potion.PotionUtil;
import net.keb4.kims_artifacts.util.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
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
                if (item.getOrCreateTag().getCompound(NBTHelper.TIPPED_WEAPON_LOC).getInt("Uses") <= 0)
                {
                    item.getOrCreateTag().remove(NBTHelper.TIPPED_WEAPON_LOC);
                    return;
                }
                List<MobEffectInstance> efx = PotionUtils.getCustomEffects(item.getOrCreateTag().getCompound(NBTHelper.TIPPED_WEAPON_LOC));
                for (MobEffectInstance e: efx)
                {
                    event.getEntity().addEffect(e);
                }

                item.getOrCreateTag().getCompound(NBTHelper.TIPPED_WEAPON_LOC).putInt("Uses",item.getOrCreateTag().getCompound(NBTHelper.TIPPED_WEAPON_LOC).getInt("Uses") - 1);
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
                PotionUtil.appendTooltipWithoutApplied(instances, toBeAdded, 1);
                event.getToolTip().addAll(2, toBeAdded);
            }
        }
    }



}
