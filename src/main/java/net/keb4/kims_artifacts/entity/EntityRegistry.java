package net.keb4.kims_artifacts.entity;

import net.keb4.kims_artifacts.Main;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Main.MODID);

    public static final RegistryObject<EntityType<ThrownLingeringConcoction>> THROWN_LINGERING_CONCOCTION =
        ENTITIES.register("thrown_lingering_concoction",
            () -> EntityType.Builder.<ThrownLingeringConcoction>of(ThrownLingeringConcoction::new, MobCategory.MISC)
                .sized(0.25F, 0.25F) // same as vanilla potion
                .clientTrackingRange(4)
                .updateInterval(10)
                .build("thrown_lingering_concoction"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
