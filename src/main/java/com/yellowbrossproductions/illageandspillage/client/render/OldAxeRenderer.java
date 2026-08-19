package com.yellowbrossproductions.illageandspillage.client.render;

import com.yellowbrossproductions.illageandspillage.client.model.AxeModel;
import com.yellowbrossproductions.illageandspillage.entities.projectile.OldAxeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OldAxeRenderer extends MobRenderer<OldAxeEntity, AxeModel<OldAxeEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("illageandspillage", "textures/entity/freakager/axe.png");

    public OldAxeRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new AxeModel<>(renderManagerIn.bakeLayer(AxeModel.LAYER_LOCATION)), 0.3F);
    }

    public ResourceLocation getTextureLocation(OldAxeEntity p_110775_1_) {
        return TEXTURE;
    }
}