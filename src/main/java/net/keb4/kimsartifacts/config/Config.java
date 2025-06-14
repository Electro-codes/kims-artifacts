package net.keb4.kimsartifacts.config;

import net.keb4.kimsartifacts.Main;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.ArrayList;
import java.util.List;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    //list of artifacts, perhaps implement it so its configurable as well
    public static final List<Item> artifacts = new ArrayList<>();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {

    }
}
