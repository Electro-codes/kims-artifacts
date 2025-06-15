package net.keb4.kimsartifacts.item.smr;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

//This Class Represents the SMR cannon item, which is a curio item.
public class smrItem extends Item implements ICurioItem {
    // Constructor for the Super "Item" which basicly defines its basic item properties, not as a curio item but as a normal item (IE when holding it in hand)
    public smrItem(){
        super(new Item.Properties().stacksTo(1).fireResistant().defaultDurability(0));
    }

    @Override
    public void curioTick(SlotContext slotContext,ItemStack stack){
        
    }
}
