package net.keb4.kims_artifacts.client.util;


import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParticleHelper {

    public static void sphere(Vec3 center)
    {
        double radius = 5;
        ClientLevel world = Minecraft.getInstance().level;

        for (int z = 0; z < 10; z++)
        {
            double az = z/10;
            double interpolateVal = (az/(az-2))+1;
            for (int i = 0; i < 25; i++)
            {
                world.addParticle(ParticleTypes.SCULK_SOUL, (radius * Math.sin(z) * Math.cos(i)) + center.x, (radius * Math.sin(z) * Math.sin(i)) + center.y, (radius * Math.cos(z)) + center.z, 0.0, 0.2, 0.0);
            }
        }
    }



}
