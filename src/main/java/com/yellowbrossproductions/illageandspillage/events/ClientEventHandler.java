package com.yellowbrossproductions.illageandspillage.events;

import com.yellowbrossproductions.illageandspillage.Config;
import com.yellowbrossproductions.illageandspillage.entities.CameraShakeEntity;
import com.yellowbrossproductions.illageandspillage.event.custom.ComputeCameraAnglesCallback;
import com.yellowbrossproductions.illageandspillage.util.EntityUtil;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;

public class ClientEventHandler {
    public static void initClient() {
        HudRenderCallback.EVENT.register((guiGraphics, tickDelta) -> onPreRenderHUD());
        ComputeCameraAnglesCallback.EVENT.register(ClientEventHandler::onComputeCameraAngles);
    }

    private static void onPreRenderHUD() {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.isPassenger() && EntityUtil.isEntityCrazyRagno(player.getVehicle()) && Minecraft.getInstance().options.getCameraType().isFirstPerson() && (player.getItemBySlot(EquipmentSlot.HEAD).is(Items.PUMPKIN) || player.getItemBySlot(EquipmentSlot.HEAD).is(Items.CARVED_PUMPKIN))) {
            Minecraft.getInstance().gui.setOverlayMessage(Component.translatable("entity.illageandspillage.no_escape"), false);
        }
    }

    public static void onComputeCameraAngles(ComputeCameraAnglesCallback.Event event) {
        Player player = Minecraft.getInstance().player;
        if (Config.ClientConfig.cameraShakesAllowed.get() && !Minecraft.getInstance().isPaused() && player != null) {
            float delta = Minecraft.getInstance().getFrameTime();
            float ticksExistedDelta = (float) player.tickCount + delta;
            float shakeAmplitude = 0.0F;

            for (CameraShakeEntity cameraShake : player.level().getEntitiesOfClass(CameraShakeEntity.class, player.getBoundingBox().inflate(100.0))) {
                if (cameraShake.distanceTo(player) < cameraShake.getRadius()) {
                    shakeAmplitude += cameraShake.getShakeAmount(player, delta);
                }
            }

            if (shakeAmplitude > 1.0F) {
                shakeAmplitude = 1.0F;
            }

            event.setPitch((float) ((double) event.getPitch() + (double) shakeAmplitude * Math.cos((ticksExistedDelta * 3.0F + 2.0F)) * 25.0));
            event.setYaw((float) ((double) event.getYaw() + (double) shakeAmplitude * Math.cos((ticksExistedDelta * 5.0F + 1.0F)) * 25.0));
            event.setRoll((float) ((double) event.getRoll() + (double) shakeAmplitude * Math.cos((ticksExistedDelta * 4.0F)) * 25.0));
        }
    }
}