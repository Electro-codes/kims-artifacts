package net.keb4.kims_artifacts.client.util;


import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParticleHelper {

    public static void circleTest()
    {
        if (Minecraft.getInstance().player != null) {
            LocalPlayer player = Minecraft.getInstance().player;
            float partial = Minecraft.getInstance().getPartialTick();
            int total = player.tickCount;

            float secs = (partial + total) / 20;
            ClientLevel world = Minecraft.getInstance().level;


            for (int i = 0; i < 25; i++)
            {
                float x = (float) (player.getX() + (2 * Math.cos(4*i + secs)));
                float z = (float) (player.getZ() + (2 * Math.sin(4*i + secs)));
                double y = player.getEyeY();

                world.addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, x, y, z, 0.0D, 0.25D, 0.0D); // Slightly moves upwards

            }
}
    }



}
