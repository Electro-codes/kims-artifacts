package net.keb4.kims_artifacts.container;

import net.keb4.kims_artifacts.item.artifacts.PotionBagItem;
import net.keb4.kims_artifacts.util.CurioHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class PotionBagMenu extends AbstractContainerMenu {
    private final ContainerData data;

    public PotionBagMenu(int pContainerId, Inventory playerInv) {
        super(MenuRegistry.POTION_BAG_MENU.get(), pContainerId);
        this.playerInv = playerInv;


        this.data = new ContainerData()
        {
            private int progress = 0;
            @Override
            public int get(int i) {
                return progress;
            }

            @Override
            public void set(int i, int i1) {
                progress = i1;
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
        this.addDataSlots(data);

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


    public int getRawProgress()
    {
        return this.data.get(0);
    }
    private int progress;


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
}
