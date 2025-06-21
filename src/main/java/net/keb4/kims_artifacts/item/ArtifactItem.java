package net.keb4.kims_artifacts.item;

import net.keb4.kims_artifacts.client.renderer.ItemVibrationRenderer;
import net.keb4.kims_artifacts.util.MythicalRarity;
import net.keb4.kims_artifacts.util.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.checkerframework.checker.units.qual.C;

import java.util.function.Consumer;

/**
 * @author Electro
 * @apiNote Generic Artifact item. Use {@link CurioArtifactItem} for Curio Artifact Items
 * **/
public class ArtifactItem extends Item implements IArtifact {
    public ArtifactItem() {
        super(new Item.Properties().stacksTo(1).rarity(MythicalRarity.MYTHICAL).fireResistant());
    }

    public static final String IS_VIBRATING_LOCATION = "Vibrating";


    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        initializeArtifactItem(stack);
        return stack;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pStack.getOrCreateTag().get(NBTHelper.ROOT_LOC_ARTIFACT) == null)
        {
            initializeArtifactItem(pStack);
        }
    }

    private static void initializeArtifactItem(ItemStack stack)
    {
        stack.getOrCreateTag().put(NBTHelper.ROOT_LOC_ARTIFACT, new CompoundTag());
        stack.getOrCreateTag().getCompound(NBTHelper.ROOT_LOC_ARTIFACT).putBoolean(IS_VIBRATING_LOCATION, true);
    }

    @Override
    public float getDefaultResonance() {
        return 0.5f;
    }

    public static void setVibrating(ItemStack stack, boolean b)
    {
        stack.getOrCreateTag().getCompound(NBTHelper.ROOT_LOC_ARTIFACT).putBoolean(IS_VIBRATING_LOCATION, b);
    }
}
