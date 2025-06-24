package net.keb4.kims_artifacts.recipe;

import net.keb4.kims_artifacts.Main;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeRegistry {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Main.MODID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Main.MODID);

    public static final RegistryObject<RecipeSerializer<TippedSwordRecipe>> TIPPED_SWORD_SERIALIZER =
            SERIALIZERS.register("tipped_sword", TippedSwordRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<TippedSwordRecipe>> TIPPED_SWORD_RECIPE_TYPE =
            TYPES.register("tipped_sword", () -> new RecipeType<TippedSwordRecipe>() {
                public String toString() {
                    return Main.MODID + ":" + "tipped_sword";
                }
            });

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
        TYPES.register(bus);
    }
}
