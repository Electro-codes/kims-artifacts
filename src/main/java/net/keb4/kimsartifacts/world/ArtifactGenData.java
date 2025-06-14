package net.keb4.kimsartifacts.world;

import net.keb4.kimsartifacts.config.Config;
import net.keb4.kimsartifacts.Main;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ArtifactGenData extends SavedData {

    public ArtifactGenData(){}

    public ArtifactGenData(CompoundTag tag)
    {
        ListTag list = tag.getList(LOCATION, Tag.TAG_COMPOUND);
        populateItemMap();
        for (int i = 0; i < list.size(); i++)
        {
            CompoundTag in = list.getCompound(i);
            ResourceLocation loc = ResourceLocation.parse(String.valueOf(in.getAllKeys().toArray()[0]));
            if (in.getBoolean(loc.toString())) generatedItems.replace(loc, true);
        }
    }


    private static final String LOCATION = "kartifact_gen_data";
    private LinkedHashMap<ResourceLocation, Boolean> generatedItems = new LinkedHashMap<>();


    /**
     * Defaults the list of generated items as false.
     * **/
    public void populateItemMap()
    {
        if (generatedItems.isEmpty()) {
            List<Item> configItems = Config.artifacts;

            for (Item item : configItems) {
                //all put as false for now, but will be updated accordingly on world load
                generatedItems.put(ForgeRegistries.ITEMS.getKey(item), false);
            }
        }
    }


    /**
     * Prints all item resource locations and whether or not they have generated.
     * **/
    public void debugPrint()
    {
        for (Map.Entry<ResourceLocation, Boolean> i : generatedItems.entrySet())
        {
            Main.LOGGER.info(i.getKey() + ": " + i.getValue());
        }
    }




    public boolean isGenerated(ResourceLocation loc)
    {
        return (generatedItems.get(loc) == null ? generatedItems.get(loc) : false);
    }

    public void setGenerated(ResourceLocation loc)
    {
        generatedItems.replace(loc, true);
        this.setDirty(true);
    }

    public HashMap<ResourceLocation, Boolean> getItemMap() {
        return generatedItems;
    }




    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        for (Map.Entry<ResourceLocation, Boolean> data : generatedItems.entrySet())
        {
            Main.LOGGER.info("Saved '" + data.getKey() + "' as " + data.getValue());
            CompoundTag nbt = new CompoundTag();
            nbt.putBoolean(data.getKey().toString(), data.getValue());
            list.add(nbt);
        }

        tag.put(LOCATION, list);
        return tag;
    }

    public static ArtifactGenData load(CompoundTag tag)
    {
        return new ArtifactGenData(tag);
    }

    /**
     * @param lvl Can take any server level. All data is fetched from Overworld
     * @return The instance of artifact generation data for this world.
     * **/
    public static ArtifactGenData get(ServerLevel lvl)
    {
        return lvl.getServer().getLevel(ServerLevel.OVERWORLD).getDataStorage().computeIfAbsent(
                ArtifactGenData::load,
                ArtifactGenData::new,
                LOCATION
        );
    }


}
