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
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.UUID;

public class PotionBagItem extends CurioArtifactItem {

}
