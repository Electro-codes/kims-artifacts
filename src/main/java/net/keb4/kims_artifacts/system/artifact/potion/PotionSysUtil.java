package net.keb4.kims_artifacts.system.artifact.potion;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.keb4.kims_artifacts.config.CommonConfig;
import net.keb4.kims_artifacts.container.PotionBagMenu;
import net.keb4.kims_artifacts.util.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PotionSysUtil {


    public static void appendTooltipWithoutApplied(List<MobEffectInstance> pEffects, List<Component> pTooltips, float pDurationFactor)
    {
        List<Pair<Attribute, AttributeModifier>> $$3 = Lists.newArrayList();
        if (pEffects.isEmpty()) {

        } else {
            for(MobEffectInstance $$4 : pEffects) {
                MutableComponent $$5 = Component.translatable($$4.getDescriptionId());
                MobEffect $$6 = $$4.getEffect();
                Map<Attribute, AttributeModifier> $$7 = $$6.getAttributeModifiers();
                if (!$$7.isEmpty()) {
                    for(Map.Entry<Attribute, AttributeModifier> $$8 : $$7.entrySet()) {
                        AttributeModifier $$9 = (AttributeModifier)$$8.getValue();
                        AttributeModifier $$10 = new AttributeModifier($$9.getName(), $$6.getAttributeModifierValue($$4.getAmplifier(), $$9), $$9.getOperation());
                        $$3.add(new Pair((Attribute)$$8.getKey(), $$10));
                    }
                }

                if ($$4.getAmplifier() > 0) {
                    $$5 = Component.translatable("potion.withAmplifier", new Object[]{$$5, Component.translatable("potion.potency." + $$4.getAmplifier())});
                }

                if (!$$4.endsWithin(20)) {
                    $$5 = Component.translatable("potion.withDuration", new Object[]{$$5, MobEffectUtil.formatDuration($$4, pDurationFactor)});
                }

                pTooltips.add($$5.withStyle($$6.getCategory().getTooltipFormatting()));
            }
        }
    }


    /**
     * Subclass dealing with mixing logic.
     * **/
    public static class Mix
    {
        /**
         * @param target The mob effect list to be added to.
         * @param addent The mob effect list which will be sacrificed.
         * @apiNote Kim you can add your own potion mixing logic in here, mine kinda sucks I didn't spend too much time on it
         * **/
        public static List<MobEffectInstance> mix(List<MobEffectInstance> target, List<MobEffectInstance> addent) {
            List<MobEffectInstance> out = new ArrayList<>();
            for (MobEffectInstance t : target)
            {
                for (MobEffectInstance e : addent)
                {
                    if (e.getEffect() == t.getEffect())
                    {
                        out.add(new MobEffectInstance(e.getEffect(), e.getDuration() + t.getDuration(), t.getAmplifier()));
                    } else
                    {
                        out.add(new MobEffectInstance(t));
                    }
                }
            }
            return out;
        }
    }

    /**
     * Subclass dealing with crafting logic.
     * **/
    public static class Craft
    {
        //these two methods should be functionally identical

        /**
         * @param handler The bag handler to be inserted.
         * @apiNote Used to determine if the input slots of the bag are considered "valid" for a recipe. Similar to vanilla recipe validation.
         * **/
        public static boolean isValid(IItemHandler handler)
        {
            boolean stack1 = false, stack2 = false, stack3 = false;
            stack1 = handler.getStackInSlot(0).getItem() instanceof PotionItem;
            stack2 = handler.getStackInSlot(1).getItem() instanceof PotionItem;
            stack3 = handler.getStackInSlot(2).getItem() instanceof PotionItem;
            return stack1 && stack2 && stack3;
        }

        /**
         * @param menu The bag menu to be inserted.
         * @apiNote Basically identical to {@link PotionSysUtil.Craft#isValid(IItemHandler) isValid()} but in a menu context.
         * **/
        public static boolean isValid(@Nullable PotionBagMenu menu)
        {
            if (menu == null) return false;
            boolean stack1 = false, stack2 = false, stack3 = false;
            stack1 = menu.getSlot(0).getItem().getItem() instanceof PotionItem;
            stack2 = menu.getSlot(1).getItem().getItem() instanceof PotionItem;
            stack3 = menu.getSlot(2).getItem().getItem() instanceof PotionItem;
            return stack1 && stack2 && stack3;
        }

        /**
         * Used exclusively for tipped melee weapons
         * **/
        public static boolean isTipped(ItemStack stack)
        {
            //uses stack.getTag() so it doesn't create a tag on items which aren't tipped
            return stack.getTag() != null && stack.getTag().contains(NBTHelper.TIPPED_WEAPON_LOC);
        }

        //tips a weapon with an amount of concoctions
        public static void tipWeapon(ItemStack stack, ItemStack... concoction)
        {
            if (!(stack.getItem() instanceof SwordItem)) return;
            CompoundTag t = stack.getOrCreateTag();
            CompoundTag root = new CompoundTag();
            List<ItemStack> concoctions = Arrays.stream(concoction).toList();
            ListTag allEfx = new ListTag();

            for (ItemStack c : concoctions)
            {
                ListTag tx = c.getOrCreateTag().getList(PotionUtils.TAG_CUSTOM_POTION_EFFECTS, Tag.TAG_COMPOUND);
                allEfx.addAll(tx);
            }
            root.put(PotionUtils.TAG_CUSTOM_POTION_EFFECTS, allEfx);
            root.putInt("Uses", CommonConfig.tippedWeaponUses);
            t.put(NBTHelper.TIPPED_WEAPON_LOC, root);
        }
    }


}
