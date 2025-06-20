package net.keb4.kims_artifacts.item.artifacts;

import net.keb4.kims_artifacts.item.CurioArtifactItem;
import net.keb4.kims_artifacts.sound.SoundRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;


/**
 * @author Kimbestiga
 * **/
public class SMRItem extends CurioArtifactItem {

    public static final double RAYCAST_RANGE = 100.0D;


    @Override
    public @NotNull Component getDescription() {
        MutableComponent component = MutableComponent.create(ComponentContents.EMPTY);
        component.append("Pew pew").withStyle(ChatFormatting.AQUA);
        return component;
    }


    @Override
    public @NotNull ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundRegistry.SMR_EQUIP.get(), 1, 1);
    }
}
