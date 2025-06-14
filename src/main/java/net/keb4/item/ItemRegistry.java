package net.keb4.item;

import net.keb4.kimsartifacts.Main;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test", FireItem::new);


    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }

}
