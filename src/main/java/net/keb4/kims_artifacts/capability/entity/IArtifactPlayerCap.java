package net.keb4.kims_artifacts.capability.entity;

import net.minecraft.nbt.CompoundTag;

public interface IArtifactPlayerCap {



    // Methods for persistence (saving/loading/cloning)
    void copyFrom(IArtifactPlayerCap source);
    CompoundTag serializeNBT();
    void deserializeNBT(CompoundTag nbt);
}
