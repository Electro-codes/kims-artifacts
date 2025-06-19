package net.keb4.kims_artifacts.entity.damage;

import net.keb4.kims_artifacts.Main;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class DamageTypes {

    public static final DeferredRegister<DamageType> DAMAGE_TYPES = DeferredRegister.create(Registries.DAMAGE_TYPE, Main.MODID);

//    public static final RegistryObject<DamageType> ARTIFACT = DAMAGE_TYPES.register("artifact",
//            () -> new DamageType("death.attack." + Main.MODID + ".artifact",
//                    0f));

    public static void register(IEventBus bus)
    {
        //DAMAGE_TYPES.register(bus);
    }
}
