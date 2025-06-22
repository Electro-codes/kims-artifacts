package net.keb4.kims_artifacts.entity.capability;

import com.google.common.collect.ImmutableMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public interface IArtifactPlayerCap {



    // Methods for persistence (saving/loading/cloning)
    void copyFrom(IArtifactPlayerCap source);
    CompoundTag serializeNBT();
    void deserializeNBT(CompoundTag nbt);
}
