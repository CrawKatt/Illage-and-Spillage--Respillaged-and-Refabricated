package com.yellowbrossproductions.illageandspillage.events;

import com.yellowbrossproductions.illageandspillage.client.model.*;
import com.yellowbrossproductions.illageandspillage.client.render.*;
import com.yellowbrossproductions.illageandspillage.client.render.layer.HayArmorLayer;
import com.yellowbrossproductions.illageandspillage.client.render.layer.WebbedLayer;
import com.yellowbrossproductions.illageandspillage.gui.overlay.JumpscareOverlay;
import com.yellowbrossproductions.illageandspillage.gui.overlay.WebbedOverlay;
import com.yellowbrossproductions.illageandspillage.init.ModEntityTypes;
import com.yellowbrossproductions.illageandspillage.particle.ParticleRegisterer;
import com.yellowbrossproductions.illageandspillage.particle.custom.BloodParticles;
import com.yellowbrossproductions.illageandspillage.particle.custom.MutationDripParticles;
import com.yellowbrossproductions.illageandspillage.particle.custom.MutationParticles;
import com.yellowbrossproductions.illageandspillage.particle.custom.MutationParticles2;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class ClientModEventBusSubscriber {
    public static void initClient() {
        registerEntityRenders();
        registerGuiOverlays();
        registerParticleFactories();
        registerLayers();
        onClientSetup();
    }

    private static void registerEntityRenders() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            registrationHelper.register(new WebbedLayer<>(entityRenderer));
            registrationHelper.register(new HayArmorLayer<>(entityRenderer));
        });
    }

    private static void registerGuiOverlays() {
        HudRenderCallback.EVENT.register((guiGraphics, tickDelta) -> {
            int screenWidth = guiGraphics.guiWidth();
            int screenHeight = guiGraphics.guiHeight();
            WebbedOverlay.renderWebbed(guiGraphics, tickDelta, screenWidth, screenHeight);
            JumpscareOverlay.JUMPSCARE_OVERLAY.render(guiGraphics, tickDelta, screenWidth, screenHeight);
        });
    }

    private static void registerParticleFactories() {
        ParticleFactoryRegistry.getInstance().register(ParticleRegisterer.MUTATION_PARTICLES, MutationParticles.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegisterer.MUTATION_PARTICLES2, MutationParticles2.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegisterer.MUTATION_DRIP_PARTICLES, MutationDripParticles.Provider::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegisterer.BLOOD_PARTICLES, BloodParticles.Provider::new);
    }

    private static void registerLayers() {
        EntityModelLayerRegistry.registerModelLayer(IgniterModel.LAYER_LOCATION, IgniterModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(EngineerModel.LAYER_LOCATION, EngineerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ChagrinSentryModel.LAYER_LOCATION, ChagrinSentryModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(HinderModel.LAYER_LOCATION, HinderModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(FactoryModel.LAYER_LOCATION, FactoryModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BeeperModel.LAYER_LOCATION, BeeperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SniperModel.LAYER_LOCATION, SniperModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PokerModel.LAYER_LOCATION, PokerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(MagispellerModel.LAYER_LOCATION, MagispellerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(FakerModel.LAYER_LOCATION, FakerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(DispenserModel.LAYER_LOCATION, DispenserModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(IllashooterModel.LAYER_LOCATION, IllashooterModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CrashagerModel.LAYER_LOCATION, CrashagerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BossRandomizerModel.LAYER_LOCATION, BossRandomizerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(TwittollagerModel.LAYER_LOCATION, TwittollagerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SpiritcallerModel.LAYER_LOCATION, SpiritcallerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(MobSpiritModel.LAYER_LOCATION, MobSpiritModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(IllagerSoulModel.LAYER_LOCATION, IllagerSoulModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ImpModel.LAYER_LOCATION, ImpModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SpiritHandModel.LAYER_LOCATION, SpiritHandModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CrocofangModel.LAYER_LOCATION, CrocofangModel::createBodyLayer);
//        EntityModelLayerRegistry.registerModelLayer(DevastatorModel.LAYER_LOCATION, DevastatorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AbsorberModel.LAYER_LOCATION, AbsorberModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PreserverModel.LAYER_LOCATION, PreserverModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(HayArmorModel.LAYER_LOCATION, HayArmorModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(FreakagerModel.LAYER_LOCATION, FreakagerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(RagnoModel.LAYER_LOCATION, RagnoModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(EyesoreModel.LAYER_LOCATION, EyesoreModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(FunnyboneModel.LAYER_LOCATION, FunnyboneModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BoneModel.LAYER_LOCATION, BoneModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SkullBombModel.LAYER_LOCATION, SkullBombModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(OldFreakagerModel.LAYER_LOCATION, OldFreakagerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(OldRagnoModel.LAYER_LOCATION, OldRagnoModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(OldMagispellerModel.LAYER_LOCATION, OldMagispellerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(MagiHealModel.LAYER_LOCATION, MagiHealModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(KaboomerModel.LAYER_LOCATION, KaboomerModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PumpkinBombModel.LAYER_LOCATION, PumpkinBombModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(AxeModel.LAYER_LOCATION, AxeModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ScytheModel.LAYER_LOCATION, ScytheModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(OldScytheModel.LAYER_LOCATION, OldScytheModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(TrickOrTreatModel.LAYER_LOCATION, TrickOrTreatModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(WebNetModel.LAYER_LOCATION, WebNetModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(VillagerSoulModel.LAYER_LOCATION, VillagerSoulModel::createBodyLayer);
    }

    private static void onClientSetup() {
        EntityRendererRegistry.register(ModEntityTypes.Igniter, IgniterRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Engineer, EngineerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.ChagrinSentry, ChagrinSentryRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Hinder, HinderRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Factory, FactoryRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Beeper, BeeperRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Sniper, SniperRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Poker, PokerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Magispeller, MagispellerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Faker, FakeMagispellerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Dispenser, DispenserRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Illashooter, IllashooterRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Crashager, CrashagerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.BossRandomizer, BossRandomizerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Twittollager, TwittollagerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Spiritcaller, SpiritcallerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.MobSpirit, MobSpiritRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.IllagerSoul, IllagerSoulRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Imp, ImpRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SpiritHand, SpiritHandRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SoulBeam, SoulBeamRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Crocofang, CrocofangRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.IgniterFireball, (context) -> new ThrownItemRenderer<>(context, 0.75F, true));
        EntityRendererRegistry.register(ModEntityTypes.CameraShake, NothingRenderer::new);
//        EntityRendererRegistry.register(ModEntityTypes.Devastator, DevastatorRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Absorber, AbsorberRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Preserver, PreserverRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Freakager, FreakagerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Ragno, RagnoRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Eyesore, EyesoreRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Funnybone, FunnyboneRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Bone, BoneRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SkullBomb, SkullBombRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.OldFreakager, OldFreakagerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.OldRagno, OldRagnoRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.OldMagispeller, OldMagispellerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.MagiFireball, MagiFireballRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.MagiArrow, MagiArrowRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.MagiHeal, MagiHealRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Kaboomer, KaboomerRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.PumpkinBomb, PumpkinBombRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Axe, AxeRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.OldAxe, OldAxeRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.DarkPotion, (context) -> new ThrownItemRenderer<>(context, 0.75F, true));
        EntityRendererRegistry.register(ModEntityTypes.Scythe, ScytheRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.OldScythe, OldScytheRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.TrickOrTreat, TrickOrTreatRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.Web, (context) -> new ThrownItemRenderer<>(context, 1.5F, true));
        EntityRendererRegistry.register(ModEntityTypes.WebNet, WebNetRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.VillagerSoul, VillagerSoulRenderer::new);
    }
}