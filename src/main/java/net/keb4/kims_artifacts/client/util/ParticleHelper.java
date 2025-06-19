package net.keb4.kims_artifacts.client.util;



import net.keb4.kims_artifacts.Main;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;



public class ParticleHelper {

    public static void sphere(Vec3 center, Level level)
    {
        double radius = 5;

        if (level instanceof ServerLevel serverLevel)
        {
            Main.LOGGER.info("Ran 'sphere' on server! Debug: nothing happened lol.");
        } else {
            for (int z = 0; z < 10; z++) {
                double az = z / 10;
                double interpolateVal = (az / (az - 2)) + 1;
                for (int i = 0; i < 25; i++) {
                    ((ClientLevel) level).addParticle(ParticleTypes.SCULK_SOUL, (radius * Math.sin(z) * Math.cos(i)) + center.x, (radius * Math.sin(z) * Math.sin(i)) + center.y, (radius * Math.cos(z)) + center.z, 0.0, 0.2, 0.0);
                }
            }
        }
    }



}
