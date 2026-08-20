package com.yellowbrossproductions.illageandspillage.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

public final class ParticleRegisterer {
    private static final String MOD_ID = "illageandspillage";

    public static final SimpleParticleType MUTATION_PARTICLES = register("mutation_particles");
    public static final SimpleParticleType MUTATION_PARTICLES2 = register("mutation_particles2");
    public static final SimpleParticleType MUTATION_DRIP_PARTICLES = register("mutation_drip_particles");
    public static final SimpleParticleType BLOOD_PARTICLES = register("blood_particles");

    private static SimpleParticleType register(String name) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, new ResourceLocation(MOD_ID, name), FabricParticleTypes.simple(true));
    }

    public static void init() {

    }
}