package net.keb4.kims_artifacts.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.LingeringPotionItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class lingeringConcoction extends LingeringPotionItem {
    public lingeringConcoction(){
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).defaultDurability(4));
    }


}
