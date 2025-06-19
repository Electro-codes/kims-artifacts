package net.keb4.kims_artifacts.client.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.keb4.kims_artifacts.Main;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.keymap.KeyMap;
import org.lwjgl.glfw.GLFW;

import javax.swing.text.JTextComponent;
import java.util.HashSet;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Keybinds {
    public static final String KEY_CATEGORY = "key.category." + Main.MODID;
    private static final HashSet<KeyMapping> keybindList = new HashSet<>();

    public static final KeyMapping SMR_SMALL_EXPLOSION = Util.buildKey("smr_small_explosion", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C);


    @SubscribeEvent
    public static void registerMappings(RegisterKeyMappingsEvent event)
    {
        //testing out a new "auto-register" idea I had in mind. Once a key is defined through Util.buildKey, it will automatically be registered :D
        for (KeyMapping key : keybindList)
        {
            event.register(key);
        }
    }









    public static class Util {


        public static String buildTranslation(String name)
        {
            return "key." + Main.MODID + "." + name;
        }

        /**
         * @param name Name of the keybind. Should be lowercase with underscores (e.x, "example_trigger")
         * @param inputType The Type of keybind; {@link InputConstants.Type#KEYSYM} for keyboard and {@link InputConstants.Type#MOUSE} for mouse.
         * @param key Key number expressed as integer. Use {@link GLFW} enums for this. ({@link GLFW#GLFW_KEY_T} is T)
         * **/
        public static KeyMapping buildKey(String name, InputConstants.Type inputType, int key)
        {
            KeyMapping created = new KeyMapping(buildTranslation(name), inputType, key, KEY_CATEGORY);
            if (!keybindList.add(created))
            {
                Main.LOGGER.warn("Duplicate keymapping detected: '{}'", name);
            }
            return created;
        }

    }




}
