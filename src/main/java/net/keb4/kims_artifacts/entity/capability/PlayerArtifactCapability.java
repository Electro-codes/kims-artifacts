package net.keb4.kims_artifacts.entity.capability;

import com.google.common.collect.ImmutableMap;
import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.item.ArtifactItem;
import net.keb4.kims_artifacts.item.ItemRegistry;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.keb4.kims_artifacts.network.s2c.ResonanceSyncPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Random;

public class PlayerArtifactCapability implements IArtifactPlayerCap{

    private HashMap<ResourceLocation, Float> resonanceValues = new HashMap<>();
    private boolean initalizedAlready = false;
    public static final String RESONANCE_VALUES_KEY = "Resonance_Values";
    public static final String FIRST_INITALIZE_FLAG = "InitFlag";
    private static Random r = new Random();

    public static void randomizeResonance(ServerPlayer player, IArtifactPlayerCap capability, float randCoeff, long seed)
    {
        r.setSeed(seed);
        randomizeResonance(player, capability, randCoeff);
    }
    public static void randomizeResonance(ServerPlayer player, IArtifactPlayerCap capability, float randCoeff)
    {
        capability.getResonanceValues().clear();
        for (RegistryObject<Item> item : ItemRegistry.getArtifacts()) {
            Main.LOGGER.info("ran randomize iteration");
            ArtifactItem artifact = (ArtifactItem) item.get();
            capability.getResonanceValues().put(item.getId(), artifact.getDefaultResonance() + (r.nextBoolean() ? (r.nextFloat() * randCoeff) : -(r.nextFloat() * randCoeff)));
        }
        PacketNetwork.sendToPlayer(new ResonanceSyncPacket(capability.getResonanceValues(), capability.isInitializedAlready()), player);
    }

    public PlayerArtifactCapability()
    {

    }
    public PlayerArtifactCapability(HashMap<ResourceLocation, Float> map)
    {
        this.resonanceValues = map;
    }
    public PlayerArtifactCapability(HashMap<ResourceLocation, Float> map, boolean initalizedAlready)
    {
        this.resonanceValues = map;
        this.initalizedAlready = initalizedAlready;
    }

    @Override
    public HashMap<ResourceLocation, Float> getResonanceValues() {
        return this.resonanceValues;
    }

    public ImmutableMap<ResourceLocation, Float> getImmutableResonanceValues()
    {
        return ImmutableMap.copyOf(this.resonanceValues);
    }

    @Override
    public boolean isInitializedAlready() {
        return this.initalizedAlready;
    }

    @Override
    public void setInitalized() {
        this.initalizedAlready = true;
    }

    @Override
    public void copyFrom(IArtifactPlayerCap source) {
        this.resonanceValues = new HashMap<>(source.getResonanceValues());
        this.initalizedAlready = source.isInitializedAlready();
        Main.LOGGER.info(String.valueOf((source.isInitializedAlready())));
        Main.LOGGER.info(String.valueOf((this.isInitializedAlready())));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag root = new CompoundTag();
        ListTag resonanceList = new ListTag();
        this.resonanceValues.forEach((resourceLocation, num) -> {
            CompoundTag t = new CompoundTag();
            t.putFloat(resourceLocation.getNamespace() + resourceLocation.getPath(), num);
            resonanceList.add(t);
        });
        root.putBoolean(FIRST_INITALIZE_FLAG, this.initalizedAlready);
        root.put(RESONANCE_VALUES_KEY, resonanceList);
        return root;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.resonanceValues.clear();
        this.initalizedAlready = nbt.getBoolean(FIRST_INITALIZE_FLAG);
        ListTag resonanceList = nbt.getList(RESONANCE_VALUES_KEY, Tag.TAG_COMPOUND);
        for (int i = 0; i < resonanceList.size(); i++)
        {
            CompoundTag t = resonanceList.getCompound(i);
            String name = (String) t.getAllKeys().toArray()[0];
            this.resonanceValues.put(ResourceLocation.parse(name), t.getFloat(name));
        }
    }
}
