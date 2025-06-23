package net.keb4.kims_artifacts.system.artifact.potion;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PotionMixUtils {

    /**
     * @param target The mob effect list to be added to.
     * @param addent The mob effect list which will be sacrificed.
     * **/
    public static List<MobEffectInstance> mix(List<MobEffectInstance> target, List<MobEffectInstance> addent)
    {
        List<MobEffectInstance> out = new ArrayList<>();
        List<MobEffectInstance> unmixed = new ArrayList<>();
        for (MobEffectInstance product : target)
        {
            for (MobEffectInstance additive : addent)
            {
                if (product.getEffect() == additive.getEffect())
                {
                    out.add(new MobEffectInstance(product.getEffect(), product.getDuration() + additive.getDuration(), product.getAmplifier() + additive.getAmplifier()));
                } else
                {
                    out.add(new MobEffectInstance(product));
                    unmixed.add(additive);
                }
            }
        }
        out.addAll(unmixed);
        return out;
    }

}
