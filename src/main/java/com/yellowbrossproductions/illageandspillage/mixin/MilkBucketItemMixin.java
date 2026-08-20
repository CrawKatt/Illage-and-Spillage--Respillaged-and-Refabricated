package com.yellowbrossproductions.illageandspillage.mixin;

import com.yellowbrossproductions.illageandspillage.util.EffectRegisterer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {
    @Redirect(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;removeAllEffects()Z"))
    private boolean keepWebbedOnMilk(LivingEntity entity) {
        if (!entity.hasEffect(EffectRegisterer.WEBBED)) {
            return entity.removeAllEffects();
        }

        boolean removed = false;
        for (MobEffectInstance effect : new ArrayList<>(entity.getActiveEffects())) {
            if (effect.getEffect() != EffectRegisterer.WEBBED) {
                removed |= entity.removeEffect(effect.getEffect());
            }
        }
        return removed;
    }
}
