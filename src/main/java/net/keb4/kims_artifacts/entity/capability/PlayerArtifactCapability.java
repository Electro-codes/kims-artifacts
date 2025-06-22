package net.keb4.kims_artifacts.entity.capability;

import com.google.common.collect.ImmutableMap;
import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.item.ArtifactItem;
import net.keb4.kims_artifacts.item.ItemRegistry;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Random;

public class PlayerArtifactCapability implements IArtifactPlayerCap{

    private static Random r = new Random();

    public PlayerArtifactCapability()
    {

    }


    @Override
    public void copyFrom(IArtifactPlayerCap source) {

    }

    @Override
    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
