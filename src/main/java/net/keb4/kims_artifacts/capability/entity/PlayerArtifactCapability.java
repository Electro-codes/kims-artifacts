package net.keb4.kims_artifacts.capability.entity;

import net.minecraft.nbt.CompoundTag;

import java.util.Random;

public class PlayerArtifactCapability implements IArtifactPlayerCap{

    private static Random r = new Random();

    public PlayerArtifactCapability()
    {

    }


    @Override
    public void copyFrom(IArtifactPlayerCap source) {

    }

    @Override
    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
