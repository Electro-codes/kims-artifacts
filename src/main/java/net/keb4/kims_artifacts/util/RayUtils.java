package net.keb4.kims_artifacts.util;

import net.keb4.kims_artifacts.Main;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Electro
 * @apiNote Contains various raycast utilities.
 * **/
public class RayUtils {

    public static List<? extends Entity> getEntitiesFromBox(ServerLevel level, Vec3 center, Vec3 dimensions, Predicate<? super Entity> whitelist)
    {
        return level.getEntities((Entity)null, AABB.ofSize(center, dimensions.x,dimensions.y,dimensions.z), whitelist);
    }

    public static BlockHitResult simpleBlockRay(Player player, double length, boolean ignoreFluids)
    {
        Vec3 eyes = player.getEyePosition();
        Vec3 angle = player.getLookAngle();
        Vec3 end = eyes.add(angle.x * length, angle.y * length, angle.z * length);
        return player.level().clip(new ClipContext(eyes, end, ClipContext.Block.OUTLINE, (ignoreFluids ? ClipContext.Fluid.NONE : ClipContext.Fluid.ANY), player));
    }

    public static HitResult simpleEntityBlockRay(Player player, double length, boolean ignoreFluids)
    {
        Level level = player.level();
        Vec3 eyes = player.getEyePosition();
        Vec3 angle = player.getLookAngle();
        Vec3 end = eyes.add(angle.x * length, angle.y * length, angle.z * length);

        AABB searchBox = new AABB(eyes, end).inflate(0.5);
        Entity closestEntity = null;

        List<Entity> entities = level.getEntities(player, searchBox, entity -> !(entity instanceof ItemEntity));

        double closestDistance = length * length;

        for (Entity e : entities)
        {
            AABB entityBB = e.getBoundingBox();
            Optional<Vec3> hit = entityBB.clip(eyes, end);
            if (hit.isPresent())
            {
                double distanceToHitSq = eyes.distanceToSqr(hit.get());
                if (distanceToHitSq < closestDistance) {
                    closestEntity = e;
                    closestDistance = distanceToHitSq;
                }
            }
        }

        BlockHitResult block = player.level().clip(
                new ClipContext(eyes, end, ClipContext.Block.OUTLINE, (ignoreFluids ? ClipContext.Fluid.NONE : ClipContext.Fluid.ANY), player));
        if (closestEntity == null)
        {
            return block;
        }
        EntityHitResult entityHitResult = new EntityHitResult(closestEntity, closestEntity.position());
        

                
        Main.LOGGER.info("Block rng: {}", block.getLocation().distanceTo(eyes));
        Main.LOGGER.info("Entity rng: {}", entityHitResult.getLocation().distanceTo(eyes));
        if ((entityHitResult.getLocation().distanceTo(eyes) <= block.getLocation().distanceTo(eyes)))
        {
            return entityHitResult;
        }  else
        {
            return block;
        }
    }
}
