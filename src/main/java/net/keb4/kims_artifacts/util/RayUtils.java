package net.keb4.kims_artifacts.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
/**
 * @author Electro
 * @apiNote Contains various raycast utilities.
 * **/
public class RayUtils {

    public static BlockHitResult simpleBlockRay(Player player, double length, boolean ignoreFluids)
    {
        Vec3 eyes = player.getEyePosition();
        Vec3 angle = player.getLookAngle();
        Vec3 end = eyes.add(angle.x * length, angle.y * length, angle.z * length);
        return player.level().clip(new ClipContext(eyes, end, ClipContext.Block.OUTLINE, (ignoreFluids ? ClipContext.Fluid.NONE : ClipContext.Fluid.ANY), player));
    }
}
