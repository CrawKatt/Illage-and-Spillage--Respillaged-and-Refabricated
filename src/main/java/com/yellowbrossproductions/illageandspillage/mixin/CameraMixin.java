package com.yellowbrossproductions.illageandspillage.mixin;

import com.yellowbrossproductions.illageandspillage.event.custom.ComputeCameraAnglesCallback;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {
    @Shadow
    private float xRot;

    @Shadow
    private float yRot;

    @Shadow
    @Final
    private Quaternionf rotation;

    @Inject(method = "setup", at = @At("TAIL"))
    private void onSetupCamera(BlockGetter level, Entity entity, boolean detached, boolean thirdPersonReverse, float partialTick, CallbackInfo ci) {
        ComputeCameraAnglesCallback.Event event = new ComputeCameraAnglesCallback.Event((Camera) (Object) this, partialTick, this.yRot, this.xRot, 0.0F);
        ComputeCameraAnglesCallback.EVENT.invoker().onComputeCameraAngles(event);
        this.yRot = event.getYaw();
        this.xRot = event.getPitch();
        this.rotation.rotationYXZ(-event.getYaw() * ((float) Math.PI / 180.0F), event.getPitch() * ((float) Math.PI / 180.0F), event.getRoll() * ((float) Math.PI / 180.0F));
    }
}