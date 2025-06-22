package net.keb4.kims_artifacts.container;

import net.keb4.kims_artifacts.Main;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegistry {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MODID);

    // Menu Type Registration
    public static final RegistryObject<MenuType<PotionBagMenu>> POTION_BAG_MENU = MENU_TYPES.register("potion_bag_menu",
            () -> IForgeMenuType.create((windowId, inventory, data) -> {
                // Read the ItemStack from the packet buffer
                ItemStack stack = data.readItem();
                return new PotionBagMenu(windowId, inventory, stack);
            }));

    public static void register(IEventBus bus)
    {
        MENU_TYPES.register(bus);
    }

}
