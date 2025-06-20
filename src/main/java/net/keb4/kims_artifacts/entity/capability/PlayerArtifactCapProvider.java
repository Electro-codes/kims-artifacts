package net.keb4.kims_artifacts.entity.capability;

import net.keb4.kims_artifacts.Main;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerArtifactCapProvider implements ICapabilitySerializable<CompoundTag> {

    private final PlayerArtifactCapability capability = new PlayerArtifactCapability();
    private final LazyOptional<IArtifactPlayerCap> optional = LazyOptional.of(() -> capability); // LazyOptional wrapper
    public static final String REGISTRY_NAME = "player_artifact_capability";

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == CapRegistry.PLAYER_ARTIFACT_CAP) {
            return optional.cast(); // Cast our LazyOptional to the correct type
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return capability.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        capability.deserializeNBT(tag);
    }



}
