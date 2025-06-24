package net.keb4.kims_artifacts.item;

import net.keb4.kims_artifacts.Main;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ConcoctionItem extends PotionItem {
    public ConcoctionItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).defaultDurability(4));
    }



    //mostly overrides of behavior of the potionitem class
    @Override
    public int getUseDuration(ItemStack pStack) {
        return 32;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        Player $$3 = pEntityLiving instanceof Player ? (Player)pEntityLiving : null;
        if ($$3 instanceof ServerPlayer player) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)$$3, pStack);
        }

        if (!pLevel.isClientSide) {
            for(MobEffectInstance $$5 : PotionUtils.getMobEffects(pStack)) {
                if ($$5.getEffect().isInstantenous()) {
                    $$5.getEffect().applyInstantenousEffect($$3, $$3, pEntityLiving, $$5.getAmplifier(), (double)1.0F);
                } else {
                    pEntityLiving.addEffect(new MobEffectInstance($$5));
                }
            }
        }

        if ($$3 != null) {
            $$3.awardStat(Stats.ITEM_USED.get(this));
            if (!$$3.getAbilities().instabuild && $$3 instanceof ServerPlayer player) {
                pStack.hurtAndBreak(1, player,(serverPlayer) ->
                {});

            }
        }

        pEntityLiving.gameEvent(GameEvent.DRINK);
        return pStack;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level $$1 = pContext.getLevel();
        BlockPos $$2 = pContext.getClickedPos();
        Player $$3 = pContext.getPlayer();
        ItemStack $$4 = pContext.getItemInHand();
        BlockState $$5 = $$1.getBlockState($$2);
        if (pContext.getClickedFace() != Direction.DOWN && $$5.is(BlockTags.CONVERTABLE_TO_MUD) && PotionUtils.getPotion($$4) == Potions.WATER) {
            $$1.playSound((Player)null, $$2, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0F);
            $$3.setItemInHand(pContext.getHand(), ItemUtils.createFilledResult($$4, $$3, new ItemStack(Items.GLASS_BOTTLE)));
            $$3.awardStat(Stats.ITEM_USED.get($$4.getItem()));
            if (!$$1.isClientSide) {
                ServerLevel $$6 = (ServerLevel)$$1;

                for(int $$7 = 0; $$7 < 5; ++$$7) {
                    $$6.sendParticles(ParticleTypes.SPLASH, (double)$$2.getX() + $$1.random.nextDouble(), (double)($$2.getY() + 1), (double)$$2.getZ() + $$1.random.nextDouble(), 1, (double)0.0F, (double)0.0F, (double)0.0F, (double)1.0F);
                }
            }

            $$1.playSound((Player)null, $$2, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            $$1.gameEvent((Entity)null, GameEvent.FLUID_PLACE, $$2);
            $$1.setBlockAndUpdate($$2, Blocks.MUD.defaultBlockState());
            return InteractionResult.sidedSuccess($$1.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }

    @Override
    public String getDescriptionId(ItemStack pStack) {
        return this.getDescriptionId();
    }

}
