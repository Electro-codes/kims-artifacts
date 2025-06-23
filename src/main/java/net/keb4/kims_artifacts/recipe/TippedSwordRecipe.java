package net.keb4.kims_artifacts.recipe;

import com.google.gson.JsonObject;
import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.config.CommonConfig;
import net.keb4.kims_artifacts.item.ConcoctionItem;
import net.keb4.kims_artifacts.util.NBTHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.Nullable;

public class TippedSwordRecipe implements CraftingRecipe {
    private final ResourceLocation id;

    public TippedSwordRecipe(ResourceLocation id)
    {
        this.id = id;
    }

    @Override
    public boolean matches(CraftingContainer craftingContainer, Level level) {
        boolean foundSword = false;
        boolean foundConcoction = false;
        int notEmpty = 0;
        for (ItemStack item : craftingContainer.getItems())
        {
            if (foundSword && foundConcoction) break;
            if (!item.isEmpty()) {
                notEmpty++;
                if (item.getItem() instanceof SwordItem) {
                    foundSword = true;
                }
                if (item.getItem() instanceof ConcoctionItem) {
                    foundConcoction = true;
                }
            }
        }
        return foundSword && foundConcoction && notEmpty == 2;
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingContainer, RegistryAccess registryAccess) {
        ItemStack weapon = ItemStack.EMPTY;
        ItemStack concoction = ItemStack.EMPTY;

        for (ItemStack stack : craftingContainer.getItems())
        {
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof SwordItem) {
                    weapon = stack.copy();
                }
                if (stack.getItem() instanceof ConcoctionItem) {
                    concoction = stack.copy();
                }
            }
        }

        if (!weapon.isEmpty() && !concoction.isEmpty()) {
            ItemStack out = weapon.copy();
            CompoundTag t = out.getOrCreateTag();
            CompoundTag l1 = new CompoundTag();
            ListTag l = concoction.getOrCreateTag().getList("CustomPotionEffects", Tag.TAG_COMPOUND);
            l1.put("CustomPotionEffects", l);
            l1.putInt("Uses", CommonConfig.tippedWeaponUses);
            t.put(NBTHelper.TIPPED_WEAPON_LOC, l1);
            return out;
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return i * i1 >= 2;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return new ItemStack(Items.DIAMOND_SWORD);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.TIPPED_SWORD_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.EQUIPMENT;
    }


    public static class Serializer implements RecipeSerializer<TippedSwordRecipe>
    {

        @Override
        public TippedSwordRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return new TippedSwordRecipe(resourceLocation);
        }

        @Override
        public @Nullable TippedSwordRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            return new TippedSwordRecipe(resourceLocation);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, TippedSwordRecipe tippedSwordRecipe) {

        }
    }
}
