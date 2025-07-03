package net.keb4.kims_artifacts.entity;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ThrownLingeringConcoction extends ThrownPotion {
    public ThrownLingeringConcoction(EntityType<? extends ThrownPotion> type, Level level) {
        super(type, level);
    }

    public ThrownLingeringConcoction(Level level, LivingEntity thrower) {
        super(level, thrower);
    }
    @Override
    protected void onHit(HitResult pResult) {
      super.onHit(pResult);
      if (!this.level().isClientSide) {
         ItemStack itemstack = this.getItem();
         Potion potion = PotionUtils.getPotion(itemstack);
         List<MobEffectInstance> list = PotionUtils.getMobEffects(itemstack);
         boolean flag = potion == Potions.WATER && list.isEmpty();
         if (flag) {
         this.applyWater();
         } else if (!list.isEmpty()) {
        //All this effort to change 1 fucking line of code, i hate it, but allas, mixins could be worse and run into
        // stupid compatibility issues, so i guess this is the best way to do it
         this.makeAreaOfEffectCloud(itemstack, potion);

         

         int i = potion.hasInstantEffects() ? 2007 : 2002;
         //Hack fix here too because of course the color works for the cloud, but not the fucking initial splash, but ofc
         this.level().levelEvent(i, this.blockPosition(), 0x3F76E4);
         this.discard();
         }
      }
   }
   // INSANELY RETARDED, i had to copy this shit cus the visibility for it had to be aids for no reason,
   // Hacky as fuck, but it works ig
   private void makeAreaOfEffectCloud(ItemStack pStack, Potion pPotion) {
      AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
      Entity entity = this.getOwner();
      if (entity instanceof LivingEntity) {
         areaeffectcloud.setOwner((LivingEntity)entity);
      }

      areaeffectcloud.setRadius(6.0F);
      areaeffectcloud.setRadiusOnUse(-0.5F);
      areaeffectcloud.setWaitTime(10);
      areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());
      areaeffectcloud.setPotion(pPotion);

      for(MobEffectInstance mobeffectinstance : PotionUtils.getCustomEffects(pStack)) {
         areaeffectcloud.addEffect(new MobEffectInstance(mobeffectinstance));
      }

      CompoundTag compoundtag = pStack.getTag();
      if (compoundtag != null && compoundtag.contains("CustomPotionColor", 99)) {
         areaeffectcloud.setFixedColor(compoundtag.getInt("CustomPotionColor"));
      }

      this.level().addFreshEntity(areaeffectcloud);
   }
   private void applyWater() {
      AABB aabb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);

      for(LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, aabb, WATER_SENSITIVE_OR_ON_FIRE)) {
         double d0 = this.distanceToSqr(livingentity);
         if (d0 < 16.0D) {
            if (livingentity.isSensitiveToWater()) {
               livingentity.hurt(this.damageSources().indirectMagic(this, this.getOwner()), 1.0F);
            }

            if (livingentity.isOnFire() && livingentity.isAlive()) {
               livingentity.extinguishFire();
            }
         }
      }

      for(Axolotl axolotl : this.level().getEntitiesOfClass(Axolotl.class, aabb)) {
         axolotl.rehydrate();
      }

   }
}
