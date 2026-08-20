package com.yellowbrossproductions.illageandspillage.util;

import com.yellowbrossproductions.illageandspillage.IllageAndSpillage;
import com.yellowbrossproductions.illageandspillage.init.ModEntityTypes;
import com.yellowbrossproductions.illageandspillage.items.BagOfHorrorsItemBase;
import com.yellowbrossproductions.illageandspillage.items.NothingItemBase;
import com.yellowbrossproductions.illageandspillage.items.SpellboundBookItemBase;
import com.yellowbrossproductions.illageandspillage.items.TotemOfBanishmentItemBase;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.SpawnEggItem;

public class ItemRegisterer {
    public static final Item TOTEM_OF_BANISHMENT;
    public static final Item SPELLBOUND_BOOK;
    public static final Item BAG_OF_HORRORS;
    public static final Item MAGISPELLER_DISC;
    public static final Item SPIRITCALLER_DISC;
    public static final Item FREAKAGER_DISC;
    public static final Item IGNITER_SPAWN_EGG;
    public static final Item ENGINEER_SPAWN_EGG;
    public static final Item TWITTOLLAGER_SPAWN_EGG;
    public static final Item PRESERVER_SPAWN_EGG;
    public static final Item ABSORBER_SPAWN_EGG;
    public static final Item CROCOFANG_SPAWN_EGG;
    public static final Item MAGISPELLER_SPAWN_EGG;
    public static final Item SPIRITCALLER_SPAWN_EGG;
    public static final Item FREAKAGER_SPAWN_EGG;
    public static final Item RAGNO_SPAWN_EGG;
    public static final Item FUNNYBONE_SPAWN_EGG;
    public static final Item EYESORE_SPAWN_EGG;
    public static final Item ILLASHOOTER_SPAWN_EGG;
    public static final Item TREAT1;
    public static final Item TREAT2;
    public static final Item TREAT3;
    public static final Item TREAT4;
    public static final Item TREAT5;
    public static final Item TREAT6;
    public static final Item GREENBONE;
    public static final Item DARK_DRINK;
    public static final Item DARK_SPLASH;
    public static final Item DARK_LINGER;
    public static final Item LOGO;
    public static final Item INVALID;

    static {
        TOTEM_OF_BANISHMENT = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "totem_of_banishment"), new TotemOfBanishmentItemBase());
        SPELLBOUND_BOOK = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "spellbound_book"), new SpellboundBookItemBase());
        BAG_OF_HORRORS = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "bag_of_horrors"), new BagOfHorrorsItemBase());
        MAGISPELLER_DISC = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "magi_disc"), new RecordItem(6, IllageAndSpillageSoundEvents.MAGI_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1722));
        SPIRITCALLER_DISC = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "spiri_disc"), new RecordItem(6, IllageAndSpillageSoundEvents.SPIRI_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 2782));
        FREAKAGER_DISC = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "freaky_disc"), new RecordItem(6, IllageAndSpillageSoundEvents.FREAKY_DISC, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 5487));
        IGNITER_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "igniter_spawn_egg"), new SpawnEggItem(ModEntityTypes.Igniter, 1315860, 9804699, new Item.Properties()));
        ENGINEER_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "engineer_spawn_egg"), new SpawnEggItem(ModEntityTypes.Engineer, 0x8e9393, 0xfed93f, new Item.Properties()));
        TWITTOLLAGER_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "twittollager_spawn_egg"), new SpawnEggItem(ModEntityTypes.Twittollager, 9224763, 15592941, new Item.Properties()));
        PRESERVER_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "preserver_spawn_egg"), new SpawnEggItem(ModEntityTypes.Preserver, 13480456, 9804699, new Item.Properties()));
        ABSORBER_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "absorber_spawn_egg"), new SpawnEggItem(ModEntityTypes.Absorber, 8148798, 9804699, new Item.Properties()));
        CROCOFANG_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "crocofang_spawn_egg"), new SpawnEggItem(ModEntityTypes.Crocofang, 6315866, 14407360, new Item.Properties()));
        MAGISPELLER_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "magispeller_spawn_egg"), new SpawnEggItem(ModEntityTypes.Magispeller, 3407872, 9804699, new Item.Properties()));
        SPIRITCALLER_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "spiritcaller_spawn_egg"), new SpawnEggItem(ModEntityTypes.Spiritcaller, 11232972, 6445926, new Item.Properties()));
        FREAKAGER_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "freakager_spawn_egg"), new SpawnEggItem(ModEntityTypes.Freakager, 2565927, 0xdcdcdc, new Item.Properties()));
        RAGNO_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "ragno_spawn_egg"), new SpawnEggItem(ModEntityTypes.Ragno, 7500402, 5855577, new Item.Properties()));
        FUNNYBONE_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "funnybone_spawn_egg"), new SpawnEggItem(ModEntityTypes.Funnybone, 0x658363, 0xb3d2b1, new Item.Properties()));
        EYESORE_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "eyesore_spawn_egg"), new SpawnEggItem(ModEntityTypes.Eyesore, 0xc20000, 0xb9a48a, new Item.Properties()));
        ILLASHOOTER_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "illashooter_spawn_egg"), new SpawnEggItem(ModEntityTypes.Illashooter, 16775294, 16775294, new Item.Properties()));
        TREAT1 = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "treat1"), new NothingItemBase());
        TREAT2 = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "treat2"), new NothingItemBase());
        TREAT3 = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "treat3"), new NothingItemBase());
        TREAT4 = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "treat4"), new NothingItemBase());
        TREAT5 = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "treat5"), new NothingItemBase());
        TREAT6 = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "treat6"), new NothingItemBase());
        GREENBONE = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "greenbone"), new NothingItemBase());
        DARK_DRINK = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "dark_drink"), new NothingItemBase());
        DARK_SPLASH = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "dark_splash"), new NothingItemBase());
        DARK_LINGER = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "dark_linger"), new NothingItemBase());
        LOGO = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "logo"), new NothingItemBase());
        INVALID = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(IllageAndSpillage.MOD_ID, "invalid"), new NothingItemBase());
    }

    public static void init() {

    }
}