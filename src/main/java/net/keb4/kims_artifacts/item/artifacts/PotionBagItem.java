package net.keb4.kims_artifacts.item.artifacts;

import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.container.PotionBagMenu;
import net.keb4.kims_artifacts.item.ArtifactItem;
import net.keb4.kims_artifacts.item.CurioArtifactItem;
import net.keb4.kims_artifacts.util.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.UUID;

public class PotionBagItem extends CurioArtifactItem {


    public static ItemStackHandler getOrCreateItemHandler(@Nonnull ItemStack stack) {
        // Ensure the item has NBT data
        CompoundTag compoundTag = stack.getOrCreateTag();
        // Get the handler, or create a new one if it doesn't exist
        ItemStackHandler itemHandler = new ItemStackHandler(4);

        // Load existing inventory data if available
        if (compoundTag.contains("Inventory")) {
            itemHandler.deserializeNBT(compoundTag.getCompound("Inventory"));

        }
        return itemHandler;
    }

    public static void saveItemHandler(@Nonnull ItemStack stack, @Nonnull ItemStackHandler handler) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        // Serialize the handler's data and put it into the "Inventory" tag
        compoundTag.put("Inventory", handler.serializeNBT());
    }


    @Override
    public void inventoryTick(ItemStack stack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(stack, pLevel, pEntity, pSlotId, pIsSelected);
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains("Inventory", Tag.TAG_COMPOUND)) {
            tag.put("Inventory", new CompoundTag());
        }
        if (!tag.contains("Progress", Tag.TAG_INT)) {
            tag.putInt("Progress", -1);
        }
        if (!tag.contains("UUID", Tag.TAG_INT_ARRAY)) {
            tag.putUUID("UUID", UUID.randomUUID());
        }
    }
}
