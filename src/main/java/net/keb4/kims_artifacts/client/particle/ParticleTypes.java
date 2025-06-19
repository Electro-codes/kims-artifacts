package net.keb4.kims_artifacts.client.particle;

import net.keb4.kims_artifacts.Main;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Main.MODID);

    public static final RegistryObject<SimpleParticleType> FLAMEPOOF =
            PARTICLE_TYPES.register("flamepoof", () -> new SimpleParticleType(true));

    public static void register(IEventBus bus)
    {
        PARTICLE_TYPES.register(bus);
    }
}
