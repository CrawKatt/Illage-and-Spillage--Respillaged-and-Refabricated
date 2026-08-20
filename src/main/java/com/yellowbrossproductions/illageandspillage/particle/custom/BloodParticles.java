package com.yellowbrossproductions.illageandspillage.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

public class BloodParticles extends TextureSheetParticle {
    BloodParticles(ClientLevel p_106051_, double p_106052_, double p_106053_, double p_106054_, SpriteSet spriteSet, double xd, double yd, double zd) {
        super(p_106051_, p_106052_, p_106053_, p_106054_);
        this.setSize(0.01F, 0.01F);
        this.gravity = 0.06F;
        this.setSpriteFromAge(spriteSet);
        this.quadSize *= 1.5;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;

        this.rCol = 0.5f;
        this.gCol = 0.5f;
        this.bCol = 0.5f;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (!this.removed) {
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.postMoveUpdate();
            if (!this.removed) {
                this.xd *= 0.98F;
                this.yd *= 0.98F;
                this.zd *= 0.98F;
            }
        }
    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }

    protected void postMoveUpdate() {
        if (this.onGround) {
            this.remove();
            this.level.playLocalSound(this.x, this.y, this.z, SoundEvents.POINTED_DRIPSTONE_DRIP_WATER, SoundSource.BLOCKS, Mth.randomBetween(this.random, 0.3F, 1.0F), 1.0F, false);
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            return new BloodParticles(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}