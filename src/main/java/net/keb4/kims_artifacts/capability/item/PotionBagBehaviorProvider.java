package net.keb4.kims_artifacts.capability.item;

import net.keb4.kims_artifacts.capability.CapRegistry;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class PotionBagBehaviorProvider implements ICapabilitySerializable<CompoundTag> {
    private final PotionBagBehavior potionBagBehavior = new PotionBagBehavior();
    private final ItemStackHandler itemHandler = new ItemStackHandler(4);

    private final LazyOptional<IPotionBagBehavior> potionBagOptional = LazyOptional.of(() -> potionBagBehavior);
    private final LazyOptional<IItemHandler> itemHandlerOptional = LazyOptional.of(() -> itemHandler);



    @Nonnull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction direction) {

        if (capability == CapRegistry.POTION_BAG_BEHAVIOR_CAP)
        {
            return CapRegistry.POTION_BAG_BEHAVIOR_CAP.orEmpty(capability, potionBagOptional);
        }
        if (capability == ForgeCapabilities.ITEM_HANDLER) {
            return ForgeCapabilities.ITEM_HANDLER.orEmpty(capability, itemHandlerOptional);
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("Inventory", itemHandler.serializeNBT());
        tag.put("PotionBagBehavior", potionBagBehavior.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("PotionBagBehavior", Tag.TAG_COMPOUND))
            potionBagBehavior.deserializeNBT(tag.getCompound("PotionBagBehavior"));
        if (tag.contains("Inventory", Tag.TAG_COMPOUND))
            itemHandler.deserializeNBT(tag.getCompound("Inventory"));
    }
}
