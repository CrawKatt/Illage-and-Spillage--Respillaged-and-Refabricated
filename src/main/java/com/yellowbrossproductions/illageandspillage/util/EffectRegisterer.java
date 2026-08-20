package com.yellowbrossproductions.illageandspillage.util;

import com.yellowbrossproductions.illageandspillage.IllageAndSpillage;
import com.yellowbrossproductions.illageandspillage.effect.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectRegisterer {
    public static final MobEffect DISABILITY;
    public static final MobEffect MISCONDUCTION;
    public static final MobEffect PRESERVED;
    public static final MobEffect MUTATION;
    public static final MobEffect WEBBED;

    public EffectRegisterer() {
    }

    static {
        DISABILITY = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(IllageAndSpillage.MOD_ID, "disability"), new DisabilityEffect(MobEffectCategory.HARMFUL, 3484199));
        MISCONDUCTION = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(IllageAndSpillage.MOD_ID, "misconduction"), new MisconductionEffect(MobEffectCategory.BENEFICIAL, 3484199));
        PRESERVED = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(IllageAndSpillage.MOD_ID, "preserved"), new PreservedEffect(MobEffectCategory.BENEFICIAL, 3484199));
        MUTATION = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(IllageAndSpillage.MOD_ID, "mutation"), new MutationEffect(MobEffectCategory.HARMFUL, 3484199));
        WEBBED = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(IllageAndSpillage.MOD_ID, "webbed"), new WebbedEffect(MobEffectCategory.HARMFUL, 3484199));
    }

    public static void init() {

    }
}
