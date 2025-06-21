package net.keb4.kims_artifacts.util;

import net.keb4.kims_artifacts.entity.damage.DamageTypes;
import net.keb4.kims_artifacts.network.PacketNetwork;
import net.keb4.kims_artifacts.network.s2c.ManualDeltaSyncPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ExplosionHelper {

    public static void generateKnockback(ServerPlayer sender, Vec3 center, Vec3 size, float pFac)
    {
        AABB box = AABB.ofSize(center, size.x,size.y,size.z);
        for (Entity e : sender.level().getEntities(null, box))
        {
            e.addDeltaMovement(e.position().add(0,1,0).subtract(center).scale(pFac));
            if (e instanceof ServerPlayer affectedP)
            {
                PacketNetwork.sendToPlayer(new ManualDeltaSyncPacket(affectedP.getDeltaMovement()), affectedP);
            }
        }
    }

    public static Explosion createBasicKnockbackedExplosion(ServerPlayer sender, ServerLevel server, Vec3 pos, float size, Level.ExplosionInteraction interaction, boolean sendingPlayerImmune)
    {
        boolean i = sender.isInvulnerable();
        if (sendingPlayerImmune) {
            sender.setInvulnerable(true);
        }
        Explosion instance = server.explode(null, DamageTypes.ARTIFACT.getSource(server, null), null, pos.x, pos.y, pos.z, size, false, interaction, false);
        if (sendingPlayerImmune) {
            sender.setInvulnerable(i);
        }
        generateKnockback(sender, pos, new Vec3(size, size, size), 1.1f);


        return instance;
    }
    public static void createAoeDamage(ServerPlayer sender,Vec3 pos, int damage, double size,DamageSource damagetype){
        AABB box = AABB.ofSize(pos, size, size, size);
        for (Entity e : sender.level().getEntities(null, box))
        {
            if (!e.equals(sender))
            {
                e.hurt(damagetype, damage);
            }
        }
    }
}
