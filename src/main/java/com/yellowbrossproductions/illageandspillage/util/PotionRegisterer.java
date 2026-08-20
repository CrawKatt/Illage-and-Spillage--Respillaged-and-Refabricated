package com.yellowbrossproductions.illageandspillage.util;

import com.yellowbrossproductions.illageandspillage.IllageAndSpillage;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class PotionRegisterer {
    public static final Potion MUTATION;

    static {
        MUTATION = Registry.register(BuiltInRegistries.POTION, new ResourceLocation(IllageAndSpillage.MOD_ID, "mutation"), new Potion(new MobEffectInstance(EffectRegisterer.MUTATION, 600)));
    }

    public static void init() {

    }
}