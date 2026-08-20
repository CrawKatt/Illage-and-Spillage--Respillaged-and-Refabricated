package com.yellowbrossproductions.illageandspillage.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.yellowbrossproductions.illageandspillage.Config;
import com.yellowbrossproductions.illageandspillage.IllageAndSpillage;
import com.yellowbrossproductions.illageandspillage.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class WebbedOverlay {
    private static final ResourceLocation OVERLAY = new ResourceLocation(IllageAndSpillage.MOD_ID, "textures/entity/freakager/webbed_outline.png");

    public static void renderWebbed(GuiGraphics gui, float partialTick, int screenWidth, int screenHeight) {
        if (Minecraft.getInstance().player != null && EntityUtil.isWebbed(Minecraft.getInstance().player)) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            WebbedOverlay.renderScreenOverlay(OVERLAY, 1.0F, screenWidth, screenHeight);
        }
    }

    public static void renderScreenOverlay(ResourceLocation p_168709_, float p_168710_, int screenWidth, int screenHeight) {
        if (!Config.ClientConfig.webbedScreen.get()) return;
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, p_168710_);
        RenderSystem.setShaderTexture(0, p_168709_);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(0.0, screenHeight, -90.0).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex(screenWidth, screenHeight, -90.0).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(screenWidth, 0.0, -90.0).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(0.0, 0.0, -90.0).uv(0.0F, 0.0F).endVertex();
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
