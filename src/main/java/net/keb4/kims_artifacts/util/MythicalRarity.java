package net.keb4.kims_artifacts.util;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

/**
 * @apiNote Just used for Artifact items.
 * **/
public class MythicalRarity {

    public static final Rarity MYTHICAL;

    static {
        MYTHICAL = Rarity.create("mythical", ChatFormatting.RED);
    }



}
