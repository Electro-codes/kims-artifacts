package net.keb4.kims_artifacts.item;

import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.item.artifacts.PotionBagItem;
import net.keb4.kims_artifacts.item.artifacts.SMRItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ItemRegistry {


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
    private static final HashSet<RegistryObject<Item>> ARTIFACTS = new HashSet<>();

    //Artifacts will go below here

    //example method of registering an artifact
    public static final RegistryObject<Item> TEST_ITEM = registerArtifact("test", ArtifactItem::new);
    public static final RegistryObject<Item> SMR_ITEM = registerArtifact("smr", SMRItem::new);
    public static final RegistryObject<Item> POTION_BAG_ITEM = registerArtifact("potion_bag", PotionBagItem::new);

    public static final RegistryObject<Item> CONCOCTION_ITEM = ITEMS.register("concoction", ConcoctionItem::new);
    public static final RegistryObject<Item> LINGERING_CONCOCTION_ITEM = ITEMS.register("lingering_concoction", lingeringConcotionItem::new);

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }

    /**
     * Functionally identically to ITEMS.register, while also adding to the {@link ItemRegistry#ARTIFACTS} pools.
     * @param name Name of the artifact to be registered.
     * @param supplier Supplies the artifact to be registered.
     * **/
    private static <T extends Item> RegistryObject<T> registerArtifact(String name, Supplier<T> supplier)
    {
        RegistryObject<T> item = ITEMS.register(name, supplier);
        ARTIFACTS.add((RegistryObject<Item>) item);
        return item;
    }

    /**
     * @return an unmodifiable copy of the {@link ItemRegistry#ARTIFACTS} pool.
     * **/
    public static Set<RegistryObject<Item>> getArtifacts() {
        return Collections.unmodifiableSet(ARTIFACTS);
    }



}
