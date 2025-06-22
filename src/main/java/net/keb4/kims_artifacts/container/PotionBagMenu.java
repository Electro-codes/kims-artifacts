package net.keb4.kims_artifacts.container;

import net.keb4.kims_artifacts.item.ItemRegistry;
import net.keb4.kims_artifacts.item.artifacts.PotionBagItem;
import net.keb4.kims_artifacts.util.CurioHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class PotionBagMenu extends AbstractContainerMenu {
    public PotionBagMenu(int pContainerId, Inventory playerInv, ItemStack associatedStack) {
        super(MenuRegistry.POTION_BAG_MENU.get(), pContainerId);
        this.playerInv = playerInv;
        this.associatedStack = associatedStack;

        this.potionBagInvHandler = PotionBagItem.getOrCreateItemHandler(this.associatedStack);
        this.progress = associatedStack.getOrCreateTag().getInt("Progress");
        this.addent1 = this.addSlot(new SlotItemHandler(this.potionBagInvHandler, 0, 13,26));
        this.addent2 = this.addSlot(new SlotItemHandler(this.potionBagInvHandler, 1, 33,26));
        this.product = this.addSlot(new SlotItemHandler(this.potionBagInvHandler, 2, 23,45));
        this.tbd = this.addSlot(new SlotItemHandler(this.potionBagInvHandler, 3, 142,56));


        // Add player inventory slots
        // Player inventory (main 3 rows)
        //not my code ai got this for me :D
        for(int $$3 = 0; $$3 < 3; ++$$3) {
            for(int $$4 = 0; $$4 < 9; ++$$4) {
                this.addSlot(new Slot(playerInv, $$4 + $$3 * 9 + 9, 8 + $$4 * 18, 84 + $$3 * 18));
            }
        }

        for(int $$5 = 0; $$5 < 9; ++$$5) {
            this.addSlot(new Slot(playerInv, $$5, 8 + $$5 * 18, 142));
        }


    }

    private Slot addent1;
    private Slot addent2;
    private Slot product;
    private Slot tbd;
    private final ItemStack associatedStack;
    private int progress;

    private final ItemStackHandler potionBagInvHandler;

    private final Inventory playerInv;

    public ItemStack getAssociatedStack() {
        return this.associatedStack;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return CurioHelper.wearingArtifactItem(player, ItemRegistry.POTION_BAG_ITEM.get());
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        // Only save on the server side to ensure persistence
        if (!pPlayer.level().isClientSide) {
            PotionBagItem.saveItemHandler(this.associatedStack, this.potionBagInvHandler);
        }
    }
}
