package net.keb4.kims_artifacts.capability.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPotionBagBehavior extends INBTSerializable<CompoundTag> {

    void setProgress(int progress);
    int getProgress();
}
