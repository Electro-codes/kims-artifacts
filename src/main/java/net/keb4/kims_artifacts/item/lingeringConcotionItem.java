package net.keb4.kims_artifacts.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.LingeringPotionItem;
import net.minecraft.world.item.Rarity;

import net.keb4.kims_artifacts.entity.EntityRegistry;
import net.keb4.kims_artifacts.entity.ThrownLingeringConcoction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;

public class lingeringConcotionItem extends LingeringPotionItem {

    public lingeringConcotionItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).durability(4));
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return this.getDescriptionId();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.LINGERING_POTION_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            ThrownPotion entity = new ThrownLingeringConcoction(level, player);
            entity.setItem(stack.copyWithCount(1));
            entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.5F, 1.0F);
            level.addFreshEntity(entity);

            if (!player.getAbilities().instabuild) {
                stack.hurtAndBreak(1, player, (p) -> {});
            }
        }
        player.swing(hand);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

}


