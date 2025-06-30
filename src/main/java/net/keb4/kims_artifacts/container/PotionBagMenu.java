package net.keb4.kims_artifacts.container;

import net.keb4.kims_artifacts.capability.CapRegistry;
import net.keb4.kims_artifacts.item.artifacts.PotionBagItem;
import net.keb4.kims_artifacts.util.CurioHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class PotionBagMenu extends AbstractContainerMenu {

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return progress;
        }

        @Override
        public void set(int index, int value) {
            progress = value;
        }

        @Override
        public int getCount() {
            return 1; // only syncing 1 int (progress)
        }
    };

    private ItemStack bag;
    public PotionBagMenu(int pContainerId, Inventory playerInv, ItemStack potionBag) {
        super(MenuRegistry.POTION_BAG_MENU.get(), pContainerId);
        this.playerInv = playerInv;
        this.bag = potionBag;

        buildSlots(this.bag.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(new ItemStackHandler(6)));
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

        this.addDataSlots(data);
    }

    private int progress = 200;

    
    private final Inventory playerInv;

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
    }

    public void updateProgress() {
        bag.getCapability(CapRegistry.POTION_BAG_BEHAVIOR_CAP).ifPresent(cap -> {
            this.progress = cap.getProgress(); // or however you retrieve it
        });
    }

    public void buildSlots(IItemHandler itemhandler)
    {
        this.addSlot(new SlotItemHandler(itemhandler, 0, 8,20)
        {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof PotionItem;
            }
        });
        this.addSlot(new SlotItemHandler(itemhandler, 1, 31,13){
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof PotionItem;
            }
        });
        this.addSlot(new SlotItemHandler(itemhandler, 2, 54,20){
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() instanceof PotionItem;
            }
        });
        this.addSlot(new SlotItemHandler(itemhandler, 3, 31,54));
        this.addSlot(new SlotItemHandler(itemhandler, 4, 85,20));
        this.addSlot(new SlotItemHandler(itemhandler, 5, 85,54));
    }

    public int getProgress() {
        return this.data.get(0);
    }

    public void setProgress(int progress)
    {
        this.data.set(0,progress);
    }
}
