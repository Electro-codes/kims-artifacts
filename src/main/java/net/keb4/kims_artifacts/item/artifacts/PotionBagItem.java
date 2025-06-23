package net.keb4.kims_artifacts.item.artifacts;

import net.keb4.kims_artifacts.capability.CapRegistry;
import net.keb4.kims_artifacts.capability.item.IPotionBagBehavior;
import net.keb4.kims_artifacts.capability.item.PotionBagBehavior;
import net.keb4.kims_artifacts.capability.item.PotionBagBehaviorProvider;
import net.keb4.kims_artifacts.item.CurioArtifactItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class PotionBagItem extends CurioArtifactItem {


    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        PotionBagBehaviorProvider provider = new PotionBagBehaviorProvider();
        if (nbt != null)
        {
            provider.deserializeNBT(nbt);
        }
        return provider;
    }
}
