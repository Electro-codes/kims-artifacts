package net.keb4.kims_artifacts.item.enchantment;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AegisEnchantment extends Enchantment {
    protected AegisEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getDamageProtection(int pLevel, DamageSource pSource) {
        return 0;
    }
}
