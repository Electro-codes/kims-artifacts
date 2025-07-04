package net.keb4.kims_artifacts.sound;


import net.keb4.kims_artifacts.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MODID);


    // Register your sound effect using the ID from sounds.json
    public static final RegistryObject<SoundEvent> SMR_EQUIP = registerSoundEvent("smr_equip");
    public static final RegistryObject<SoundEvent> SMR_WEAK_SHOOT = registerSoundEvent("smr_weakshot");
    public static final RegistryObject<SoundEvent> SMR_STRONG_SHOOT = registerSoundEvent("smr_strongshot");
    public static final RegistryObject<SoundEvent> SMR_SWITCHTO_WEAK = registerSoundEvent("smr_switch_weak");
    public static final RegistryObject<SoundEvent> SMR_SWITCHTO_STRONG = registerSoundEvent("smr_switch_strong");

    public static final RegistryObject<SoundEvent> VIBRATION_VERY_LOW = registerSoundEvent("vibration_very_low");
    public static final RegistryObject<SoundEvent> VIBRATION_LOW = registerSoundEvent("vibration_low");
    public static final RegistryObject<SoundEvent> VIBRATION_MEDIUM = registerSoundEvent("vibration_medium");
    public static final RegistryObject<SoundEvent> VIBRATION_HIGH = registerSoundEvent("vibration_high");
    public static final RegistryObject<SoundEvent> VIBRATION_VERY_HIGH = registerSoundEvent("vibration_very_high");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        // createVariableRangeEvent uses the attenuation_distance from sounds.json
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Main.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
