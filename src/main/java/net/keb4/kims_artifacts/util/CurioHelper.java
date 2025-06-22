package net.keb4.kims_artifacts.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import java.util.Optional;

public class CurioHelper {

    public static boolean wearingArtifactItem(Player player, Item item)
    {
        return (getArtifactCurio(player).getItem() == item);
    }

    public static ItemStack getArtifactCurio(LivingEntity entity)
    {
        Optional<ICuriosItemHandler> curiosHandler = CuriosApi.getCuriosInventory(entity).resolve();
        ItemStack stack = ItemStack.EMPTY;
        if (curiosHandler.isPresent())
        {
            Optional<ICurioStacksHandler> handler = curiosHandler.get().getStacksHandler("artifacts");
            if (!handler.isPresent()) {handler = curiosHandler.get().getStacksHandler("artifact");}
            if (handler.isPresent())
            {
                stack = handler.get().getStacks().getStackInSlot(0);
            }
        }
        return stack;
    }


}
