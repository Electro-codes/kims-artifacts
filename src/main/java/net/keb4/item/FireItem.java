package net.keb4.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.NoteBlockEvent;

public class FireItem extends Item {

    //Item properties define some misc things like if its damageable, durability, food state, rarity, etc. It's a builder so you can stack them onto each other (like so)
    public FireItem() {
        super(new Item.Properties().fireResistant().rarity(Rarity.COMMON).stacksTo(10));
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected); //ticks everything of the parent class

        if (pLevel.isClientSide) return; //We want our specific changes to happen on the server, which will be auto synced to client. Running it on both might cause desyncs.


        if (pEntity.tickCount % 200 == 0) //Every 200 ticks the entity is alive (10 seconds), execute the following:
        {
            pEntity.setSecondsOnFire(200); //light the entity on fire for 200 ticks (10 sec)
            if (pEntity instanceof Player player) //if the entity is a player, cast it to its Player version (because its a player, we checked)
            {
                player.sendSystemMessage(Component.empty().append("This hurts.")); //Send a message to the player
            }
        }
    }

    @Override
    public Component getName(ItemStack pStack) {
        Component name = super.getName(pStack); //get the original name
        MutableComponent out = name.copy(); //copy it (so you can change it)
        out.append("... of doom!"); //add custom text afterwards

        return out; //return the modified version
    }
}
