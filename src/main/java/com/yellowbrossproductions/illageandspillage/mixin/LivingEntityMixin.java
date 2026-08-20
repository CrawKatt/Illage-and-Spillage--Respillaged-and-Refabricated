package com.yellowbrossproductions.illageandspillage.mixin;

import com.yellowbrossproductions.illageandspillage.access.LivingEntityAccess;
import com.yellowbrossproductions.illageandspillage.entities.*;
import com.yellowbrossproductions.illageandspillage.event.custom.LivingHurtCallback;
import com.yellowbrossproductions.illageandspillage.event.custom.LivingTickCallback;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements LivingEntityAccess {
    @Unique
    private static final EntityDataAccessor<Boolean> WEBBED = SynchedEntityData.defineId(LivingEntityMixin.class, EntityDataSerializers.BOOLEAN);
    @Unique
    private static final EntityDataAccessor<Boolean> PRESERVED = SynchedEntityData.defineId(LivingEntityMixin.class, EntityDataSerializers.BOOLEAN);

    public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Unique
    public boolean isWebbed() {
        return this.entityData.get(WEBBED);
    }

    @Unique
    public void setWebbed(boolean webbed) {
        this.entityData.set(WEBBED, webbed);
    }

    @Unique
    public boolean isPreserved() {
        return this.entityData.get(PRESERVED);
    }

    @Unique
    public void setPreserved(boolean preserved) {
        this.entityData.set(PRESERVED, preserved);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    public void defineSynchedData(CallbackInfo ci) {
        this.entityData.define(WEBBED, false);
        this.entityData.define(PRESERVED, false);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void onLivingTick(CallbackInfo ci) {
        LivingTickCallback.EVENT.invoker().onLivingTick((LivingEntity) (Object) this);
    }

    @ModifyVariable(method = "actuallyHurt", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    public float onHurtDamage(float amount, DamageSource source) {
        LivingHurtCallback.Event event = new LivingHurtCallback.Event((LivingEntity) (Object) this, source, amount);
        LivingHurtCallback.EVENT.invoker().onLivingHurt(event);
        return event.getAmount();
    }

    @Inject(method = "canAttack*", at = @At("HEAD"), cancellable = true)
    public void canAttack(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if ((target instanceof SpiritcallerEntity && ((SpiritcallerEntity) target).areIllagersNearby())
                || (target instanceof FreakagerEntity && ((FreakagerEntity) target).areIllagersNearby())
                || (target instanceof OldFreakagerEntity && ((OldFreakagerEntity) target).areIllagersNearby())
                || (target instanceof MagispellerEntity && ((MagispellerEntity) target).areIllagersNearby())
                || (target instanceof OldMagispellerEntity && ((OldMagispellerEntity) target).areIllagersNearby())) {
            cir.setReturnValue(false);
        }
    }

    @Override
    public boolean callIsWebbed() {
        return this.isWebbed();
    }

    @Override
    public void callSetWebbed(boolean webbed) {
        this.setWebbed(webbed);
    }

    @Override
    public boolean callIsPreserved() {
        return this.isPreserved();
    }

    @Override
    public void callSetPreserved(boolean preserved) {
        this.setPreserved(preserved);
    }
}
