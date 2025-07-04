package net.keb4.kims_artifacts.client.util;



import net.keb4.kims_artifacts.Main;
import net.keb4.kims_artifacts.client.particle.ParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.ThreadLocalRandom;


public class ParticleHelper {

    public static void sphere(Vec3 center, double radius, double densityConstant, ParticleOptions particleType)
    {
        ClientLevel world = Minecraft.getInstance().level;
        if (world.isClientSide() == false)
        {
            Main.LOGGER.error("Tried to spawn particles on the server side! This is not allowed!");
            return;
            
        }
        int totalParticles = (int) (densityConstant * radius * radius);
        for (int i = 0; i < totalParticles; i++)
        {
            double theta = Math.acos(ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D));
            double phi = ThreadLocalRandom.current().nextDouble(0.0D, 2.0D * Math.PI);
            double x_relative = radius * Math.sin(theta) * Math.cos(phi);
            double y_relative = radius * Math.cos(theta);
            double z_relative = radius * Math.sin(theta) * Math.sin(phi);
            double x = center.x + x_relative;
            double y = center.y + y_relative;
            double z = center.z + z_relative;
            world.addParticle(particleType, x, y, z, 0.0D, 0.0D, 0.0D);
        }


    }
    public static void explosion(Vec3 center, double radius,double densityConstant,double force, ParticleOptions particleType)
    {
        ClientLevel world = Minecraft.getInstance().level;
        if (world.isClientSide() == false)
        {
            Main.LOGGER.error("Tried to spawn particles on the server side! This is not allowed!");
            return;
            
        }
        int totalParticles = (int) (densityConstant * radius * radius); // Adjust density as needed
        for (int i = 0; i < totalParticles; i++)
        {
            double trueRadius = radius * ThreadLocalRandom.current().nextDouble(); // Randomize radius for explosion effect
            double theta = Math.acos(ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D));
            double phi = ThreadLocalRandom.current().nextDouble(0.0D, 2.0D * Math.PI);
            double x_relative = trueRadius * Math.sin(theta) * Math.cos(phi);
            double y_relative = trueRadius * Math.cos(theta);
            double z_relative = trueRadius * Math.sin(theta) * Math.sin(phi);
            double x = center.x + x_relative;
            double y = center.y + y_relative;
            double z = center.z + z_relative;
            world.addParticle(particleType, x, y, z, x_relative*force, y_relative*force, z_relative*force);
        }
    }
    public static void line(Vec3 start, Vec3 end, double densityConstant,double force, ParticleOptions particleType) {
        ClientLevel world = Minecraft.getInstance().level;
        if (world.isClientSide() == false) {
            Main.LOGGER.error("Tried to spawn particles on the server side! This is not allowed!");
            return;
        }
        Vec3 direction = end.subtract(start).normalize();
        double distance = start.distanceTo(end);
        int totalParticles = (int) (densityConstant * distance);
        for (int i = 0; i < totalParticles; i++) {
            double t = (double) i / (totalParticles - 1);
            Vec3 position = start.add(direction.scale(t * distance));
            world.addParticle(particleType, position.x, position.y, position.z, direction.x*force, direction.y*force, direction.z*force);
        }
    }
}
