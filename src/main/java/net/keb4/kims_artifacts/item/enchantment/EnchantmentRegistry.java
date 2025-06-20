package net.keb4.kims_artifacts.item.enchantment;

import net.keb4.kims_artifacts.Main;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnchantmentRegistry {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Main.MODID);

    public static final RegistryObject<Enchantment> AEGIS = ENCHANTMENTS.register("aegis", AegisEnchantment::new);

    public static void register(IEventBus iEventBus)
    {
        ENCHANTMENTS.register(iEventBus);
    }
}
