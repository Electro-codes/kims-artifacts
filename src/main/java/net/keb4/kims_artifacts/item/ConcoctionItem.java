package net.keb4.kims_artifacts.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.alchemy.PotionUtils;

public class ConcoctionItem extends PotionItem {
    public ConcoctionItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).defaultDurability(4));
    }

}
