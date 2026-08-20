package com.yellowbrossproductions.illageandspillage.init;

import com.yellowbrossproductions.illageandspillage.Config;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raid.RaiderType;
import net.minecraft.world.entity.raid.Raider;

import java.util.ArrayList;
import java.util.List;

public class RaidWaveMembers {
    public static final List<Raid.RaiderType> CUSTOM_RAID_MEMBERS = new ArrayList<>();
    public static Raid.RaiderType BOSS_RANDOMIZER;
    public static Raid.RaiderType IGNITER;
    public static Raid.RaiderType ENGINEER;
    public static Raid.RaiderType TWITTOLLAGER;
    public static Raid.RaiderType MAGISPELLER;
    public static Raid.RaiderType SPIRITCALLER;
    public static Raid.RaiderType CROCOFANG;
    public static Raid.RaiderType ABSORBER;
    public static Raid.RaiderType PRESERVER;
    public static Raid.RaiderType FREAKAGER;
    public static Raid.RaiderType OLD_FREAKAGER;
    public static Raid.RaiderType OLD_MAGISPELLER;

    public static void registerWaveMembers() {
        BOSS_RANDOMIZER = translateToWaves(ModEntityTypes.BossRandomizer, Config.CommonConfig.bossrandomizer_raidcount.get());
        IGNITER = translateToWaves(ModEntityTypes.Igniter, Config.CommonConfig.igniter_raidcount.get());
        ENGINEER = translateToWaves(ModEntityTypes.Engineer, Config.CommonConfig.engineer_raidcount.get());
        TWITTOLLAGER = translateToWaves(ModEntityTypes.Twittollager, Config.CommonConfig.twittollager_raidcount.get());
        MAGISPELLER = translateToWaves(ModEntityTypes.Magispeller, Config.CommonConfig.magispeller_raidcount.get());
        SPIRITCALLER = translateToWaves(ModEntityTypes.Spiritcaller, Config.CommonConfig.spiritcaller_raidcount.get());
        CROCOFANG = translateToWaves(ModEntityTypes.Crocofang, Config.CommonConfig.crocofang_raidcount.get());
        ABSORBER = translateToWaves(ModEntityTypes.Absorber, Config.CommonConfig.absorber_raidcount.get());
        PRESERVER = translateToWaves(ModEntityTypes.Preserver, Config.CommonConfig.preserver_raidcount.get());
        FREAKAGER = translateToWaves(ModEntityTypes.Freakager, Config.CommonConfig.freakager_raidcount.get());
        OLD_FREAKAGER = translateToWaves(ModEntityTypes.OldFreakager, Config.CommonConfig.old_freakager_raidcount.get());
        OLD_MAGISPELLER = translateToWaves(ModEntityTypes.OldMagispeller, Config.CommonConfig.old_magispeller_raidcount.get());
    }

    private static Raid.RaiderType translateToWaves(EntityType<? extends Raider> type, List<? extends Integer> list) {
        Raid.RaiderType member = RaiderType.create(type.getDescriptionId(), type, new int[]{list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6), list.get(7)});
        CUSTOM_RAID_MEMBERS.add(member);
        return member;
    }
}
