package net.keb4.kims_artifacts.item.artifacts;

import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.container.PotionBagMenu;
import net.keb4.kims_artifacts.item.ArtifactItem;
import net.keb4.kims_artifacts.item.CurioArtifactItem;
import net.keb4.kims_artifacts.util.NBTHelper;
import net.minecraft.nbt.CompoundTag;
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
import top.theillusivec4.curios.api.SlotContext;

import javax.annotation.Nonnull;
import java.util.UUID;

public class PotionBagItem extends CurioArtifactItem {





    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {
            // Get or create the ItemStackHandler for this item stack
            ItemStackHandler itemHandler = getOrCreateItemHandler(itemStack);

            // Open the custom menu. The lambda provides the menu constructor with necessary data.
            NetworkHooks.openScreen(((ServerPlayer) pPlayer),new SimpleMenuProvider(
                    (windowId, playerInventory, player) -> new PotionBagMenu(windowId, playerInventory, itemStack),
                    Component.translatable("container." + Main.MODID + ".potion_bag") // Title for the screen
            ), (buf ->
            {
                buf.writeItem(itemStack);
            }));
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }


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
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pStack.getOrCreateTag().getCompound("Inventory") == null)
        {
            pStack.getOrCreateTag().getCompound("Inventory");
            pStack.getOrCreateTag().putInt("Progress", -1);
            pStack.getOrCreateTag().putUUID("uuid", UUID.randomUUID());
        }
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        // Ensure the NBT exists and potentially set up default inventory
        super.onCraftedBy(pStack, pLevel, pPlayer);
    }
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {

    }
}
