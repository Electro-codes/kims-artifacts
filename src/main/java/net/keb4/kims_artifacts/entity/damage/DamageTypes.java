package net.keb4.kims_artifacts.entity.damage;

import net.keb4.kims_artifacts.Main;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
/**
 * List of custom {@link DamageType DamageTypes}.
 * @apiNote Requires a .json file in data/kims_artifacts/damage_type.
 * **/
public enum DamageTypes {

    ARTIFACT(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Main.MODID, "artifact")));

    DamageTypes(ResourceKey<DamageType> type)
    {
        this.type = type;
    }

    /**
     * @return Returns a usable {@link DamageSource} instance for hurting entities.
     * @param lvl Where the damage is occurring.
     * @param cause Entity responsible for damage. Can be null.
     * **/
    public DamageSource getSource(Level lvl, @Nullable Entity cause)
    {
        if (lvl == null)
        {
            Main.LOGGER.error("'getSource' of custom damagetype failed! Level is null!");
            return null;
        }

        RegistryAccess registry = lvl.registryAccess();

        DamageType dmg = registry.registry(Registries.DAMAGE_TYPE).get().get(this.type);

        if (cause instanceof LivingEntity live)
        {
            return new DamageSource(Holder.direct(dmg), live);
        } else {
            return new DamageSource(Holder.direct(dmg), cause);
        }

    }

    public final ResourceKey<DamageType> type;
}
