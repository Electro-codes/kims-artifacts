package net.keb4.kims_artifacts.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class FlamepoofParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet; // Used to select the particle texture

    // Constructor: Takes common particle parameters + a SpriteSet
    public FlamepoofParticle(ClientLevel level, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(level, x, y, z, vx, vy, vz);
        this.spriteSet = spriteSet;
    }

    @Override
    public void tick() {
        super.tick();
        this.move(this.xd, this.yd, this.zd);
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        // Use the standard PARTICLE_SHEET_OPAQUE for most texture-based particles
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }


    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double vx, double vy, double vz) {
            FlamepoofParticle p = new FlamepoofParticle(level, x,y,z,vx*6,vy*6,vz*6,sprites);
            p.pickSprite(this.sprites);
            p.quadSize = 1f + (p.random.nextFloat() * 0.3f);
            p.hasPhysics = true;

            return p;
        }
    }
}
