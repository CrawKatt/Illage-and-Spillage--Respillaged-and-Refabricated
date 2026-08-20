package com.yellowbrossproductions.illageandspillage.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.yellowbrossproductions.illageandspillage.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Random;

public class JumpscareOverlay {
    private static final ResourceLocation TEXTURE = new ResourceLocation("illageandspillage", "textures/entity/freakager/jumpscare1.png");
    private static final ResourceLocation TEXTURE_OUTLINE = new ResourceLocation("illageandspillage", "textures/entity/freakager/jumpscare2.png");

    public static JumpscareOverlay JUMPSCARE_OVERLAY = new JumpscareOverlay();

    private static final int ANIMATION_APPEAR = 10;
    private static final int ANIMATION_BLINK = 60;
    private static final int ANIMATION_SCARE1 = 20;
    private static final int ANIMATION_BLINK_START = ANIMATION_APPEAR;
    private static final int ANIMATION_SCARE_START = ANIMATION_BLINK_START + ANIMATION_BLINK;
    private static final int ANIMATION_TOTAL = ANIMATION_APPEAR + ANIMATION_BLINK
            + ANIMATION_SCARE1;
    private boolean visible = false;
    private float progress = 0;
    private final Random random = new Random();

    public void show() {
        this.visible = true;
    }

    public void clientTick() {
        if (JUMPSCARE_OVERLAY.visible) {
            JUMPSCARE_OVERLAY.progress++;
            if (JUMPSCARE_OVERLAY.progress >= ANIMATION_TOTAL) {
                JUMPSCARE_OVERLAY.visible = false;
                JUMPSCARE_OVERLAY.progress = 0;
            }
        }
    }

    public void render(GuiGraphics guiGraphics, float partialTicks, int screenWidth, int screenHeight) {
        if (!visible || !Config.ClientConfig.doJumpscare.get()) return;

        float time = progress + partialTicks;
        if (time >= ANIMATION_TOTAL) {
            visible = false;
            progress = 0;
            return;
        }

        float shakeX = (random.nextFloat() * 2 - 1) * 5.0F;
        float shakeY = (random.nextFloat() * 2 - 1) * 5.0F;

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        float darkening = 1;

        boolean showOutline = false;
        int blinkstate = 0;
        if (time >= ANIMATION_BLINK_START) {
            if (time >= ANIMATION_SCARE_START) {
                blinkstate = 1;
                showOutline = (time - ANIMATION_SCARE_START) > ANIMATION_SCARE1;
            } else {
                float fade = Math.max(0, (time - ANIMATION_BLINK_START) / ANIMATION_BLINK);
                float blinkspeed = (float) (1 + Math.pow(fade, 3));
                blinkstate = Mth.floor(20 * blinkspeed) & 1;
                showOutline = blinkstate == 1;
            }
        }

        int alpha = Mth.floor(darkening * 255);

        guiGraphics.fill(0, 0, screenWidth, screenHeight, alpha << 24);

        if (showOutline) {
            int texW = 2048;
            int texH = 1024;

            float scale1 = screenHeight / (float) texH;
            int drawY = 0;
            int drawW = Mth.floor(texW * scale1);
            int drawX = (screenWidth - drawW) / 2;

            drawX += shakeX;
            drawY += shakeY;

            drawScaledOutlineTexture(drawX, drawY, drawW, screenHeight);
        }

        if (blinkstate != 1) {
            int texW = 2048;
            int texH = 1024;

            float scaleX = screenWidth / (float) texW;
            float scaleY = screenHeight / (float) texH;
            float scale = Math.max(scaleX, scaleY);

            float timeScale = Math.min(1, (1 + time) / (1 + ANIMATION_APPEAR));
            scale *= timeScale;

            float drawW = texW * scale;
            float drawH = texH * scale;

            float drawX = (screenWidth - drawW) / 2.0f + shakeX;
            float drawY = (screenHeight - drawH) / 2.0f + shakeY;

            drawScaledTexture(drawX, drawY, drawW, drawH);
        }

        poseStack.popPose();
    }


    private void drawScaledTexture(float targetX, float targetY, float targetW, float targetH) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, JumpscareOverlay.TEXTURE);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(targetX, targetY + targetH, -90.0).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex(targetX + targetW, targetY + targetH, -90.0).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(targetX + targetW, targetY, -90.0).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(targetX, targetY, -90.0).uv(0.0F, 0.0F).endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private void drawScaledOutlineTexture(float targetX, float targetY, float targetW, float targetH) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, JumpscareOverlay.TEXTURE_OUTLINE);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(targetX, targetY + targetH, -90.0).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex(targetX + targetW, targetY + targetH, -90.0).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(targetX + targetW, targetY, -90.0).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(targetX, targetY, -90.0).uv(0.0F, 0.0F).endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}