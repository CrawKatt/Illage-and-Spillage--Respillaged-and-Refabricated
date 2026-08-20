package com.yellowbrossproductions.illageandspillage.util;

import com.yellowbrossproductions.illageandspillage.IllageAndSpillage;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class CreativeTabRegisterer {
    public static final CreativeModeTab ILLAGE_AND_SPILLAGE_TAB = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            new ResourceLocation(IllageAndSpillage.MOD_ID, "illageandspillage"),
            FabricItemGroup.builder()
                    .title(Component.translatable("itemGroup.illageandspillage.creative_tab"))
                    .icon(ItemRegisterer.LOGO::getDefaultInstance)
                    .displayItems((displayParameters, output) -> {
                        output.accept(ItemRegisterer.IGNITER_SPAWN_EGG);
                        output.accept(ItemRegisterer.TWITTOLLAGER_SPAWN_EGG);
                        output.accept(ItemRegisterer.PRESERVER_SPAWN_EGG);
                        output.accept(ItemRegisterer.ENGINEER_SPAWN_EGG);
                        output.accept(ItemRegisterer.ABSORBER_SPAWN_EGG);
                        output.accept(ItemRegisterer.CROCOFANG_SPAWN_EGG);
                        output.accept(ItemRegisterer.SPIRITCALLER_SPAWN_EGG);
                        output.accept(ItemRegisterer.FREAKAGER_SPAWN_EGG);
                        output.accept(ItemRegisterer.RAGNO_SPAWN_EGG);
                        output.accept(ItemRegisterer.FUNNYBONE_SPAWN_EGG);
                        output.accept(ItemRegisterer.EYESORE_SPAWN_EGG);
                        output.accept(ItemRegisterer.MAGISPELLER_SPAWN_EGG);
                        output.accept(ItemRegisterer.ILLASHOOTER_SPAWN_EGG);
                        output.accept(ItemRegisterer.SPELLBOUND_BOOK);
                        output.accept(ItemRegisterer.BAG_OF_HORRORS);
                        output.accept(ItemRegisterer.TOTEM_OF_BANISHMENT);
                        output.accept(ItemRegisterer.SPIRITCALLER_DISC);
                        output.accept(ItemRegisterer.FREAKAGER_DISC);
                        output.accept(ItemRegisterer.MAGISPELLER_DISC);
                    }).build()
    );

    public static void init() {

    }
}