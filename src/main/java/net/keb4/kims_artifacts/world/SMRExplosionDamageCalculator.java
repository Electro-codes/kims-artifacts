package net.keb4.kims_artifacts.world;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import java.util.Optional;

public class SMRExplosionDamageCalculator extends ExplosionDamageCalculator {

    @Override
    public Optional<Float> getBlockExplosionResistance(Explosion pExplosion, BlockGetter pReader, BlockPos pPos, BlockState pState, FluidState pFluid) {

        Optional<Float> original = super.getBlockExplosionResistance(pExplosion, pReader, pPos, pState, pFluid);
        if (original.isPresent())
        {
            Float a = original.get() - 6;
            a = (a < 0 ? 0 : a);
            original = Optional.of(a);
        }
        return original;
    }
}
