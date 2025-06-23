package net.keb4.kims_artifacts.capability.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import org.checkerframework.checker.units.qual.C;

public class PotionBagBehavior implements IPotionBagBehavior, INBTSerializable<CompoundTag> {
    private int progress = 0;

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public int getProgress() {
        return this.progress;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag root = new CompoundTag();
        root.putInt("Progress", this.progress);
        return root;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.progress = tag.getInt("Progress");
    }
}
