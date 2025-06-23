package net.keb4.kims_artifacts.system.artifact.potion;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PotionUtil {


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


    public static class Mix
    {
        /**
         * @param target The mob effect list to be added to.
         * @param addent The mob effect list which will be sacrificed.
         * @apiNote Kim you can add your own potion mixing logic in here, mine kinda sucks I didn't spend too much time on it
         * **/
        public static List<MobEffectInstance> mix(List<MobEffectInstance> target, List<MobEffectInstance> addent) {
            List<MobEffectInstance> out = new ArrayList<>();
            List<MobEffectInstance> unmixed = new ArrayList<>();
            for (MobEffectInstance product : target) {
            }
            out.addAll(target);
            out.addAll(addent);
            return out;
        }
    }


}
