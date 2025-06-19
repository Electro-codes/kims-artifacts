package net.keb4.kims_artifacts.item.artifacts;

import net.keb4.kims_artifacts.item.CurioArtifactItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;


/**
 * @author Kimbestiga
 * **/
public class SMRItem extends CurioArtifactItem {

    public static final double RAYCAST_RANGE = 20.0D;


    @Override
    public @NotNull Component getDescription() {
        MutableComponent component = MutableComponent.create(ComponentContents.EMPTY);
        component.append("Pew pew").withStyle(ChatFormatting.AQUA);
        return component;
    }
}
