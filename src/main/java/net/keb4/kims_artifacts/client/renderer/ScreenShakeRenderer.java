package net.keb4.kims_artifacts.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.ThreadLocalRandom;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ScreenShakeRenderer {

    private static int shakeLength = 0;
    private static float shakeIntensity = 0;
    private static Types type;
    public static boolean screenShaking = false;

    public static void shakeScreen(float seconds, float intensity, Types typeE)
    {
        shakeLength = (int) (seconds * 20);
        shakeIntensity = intensity;
        screenShaking = true;
        type = typeE;

    }

    @SubscribeEvent
    public static void camView(ViewportEvent.ComputeCameraAngles event)
    {
        if (Minecraft.getInstance().player == null) return;
        double ticks = event.getPartialTick() + Minecraft.getInstance().player.tickCount;

        if (screenShaking && type == Types.DEFAULT)
        {

            double offsetX = ThreadLocalRandom.current().nextDouble(-0.5D, 0.5D) * shakeIntensity;
            double offsetY = ThreadLocalRandom.current().nextDouble(-0.5D, 0.5D) * shakeIntensity;
            double offsetZ = ThreadLocalRandom.current().nextDouble(-0.5D, 0.5D) * shakeIntensity;
            event.setPitch((float) (event.getPitch() + offsetX));
            event.setRoll((float) (event.getRoll() + offsetY));
            event.setYaw((float) (event.getYaw() + offsetZ));
            shakeLength--;
            if (shakeLength <= 0)
            {
                screenShaking = false;
                shakeLength = 0;
                shakeIntensity = 0;
                type = Types.DEFAULT;
            }
        }

    }

    public static enum Types
    {
        DEFAULT
    }





}
