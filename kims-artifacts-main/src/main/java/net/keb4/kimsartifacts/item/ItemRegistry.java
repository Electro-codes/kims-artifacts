package net.keb4.kimsartifacts.item;

import net.keb4.kimsartifacts.Main;
import net.keb4.kimsartifacts.item.smr.smrItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);


    // The reason why inside the () in smrItem is nothing, is because its already defined in the smrItem class, by calling its super constructor.
    // Have a look into it, since im calling the super constructor there once i call the constructor of smrItem it automatically does what it used to do here!
    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test", () -> new Item(new Item.Properties().stacksTo(1).fireResistant().defaultDurability(0)));
    public static final RegistryObject<Item> SMR_ITEM = ITEMS.register("smr", () -> new smrItem());


    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }

}
