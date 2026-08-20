package com.yellowbrossproductions.illageandspillage.mixin;

import com.yellowbrossproductions.illageandspillage.Config;
import com.yellowbrossproductions.illageandspillage.init.RaidWaveMembers;
import com.yellowbrossproductions.illageandspillage.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raid.RaiderType;
import net.minecraft.world.entity.raid.Raider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(Raid.class)
public abstract class RaidMixin {
    @Final
    @Shadow
    private static Component RAID_NAME_COMPONENT;
    @Final
    @Shadow
    private Map<Integer, Set<Raider>> groupRaiderMap;
    @Final
    @Shadow
    private ServerBossEvent raidEvent;
    @Shadow
    private float totalHealth;
    @Shadow
    private int groupsSpawned;
    @Shadow
    private int numGroups;
    @Shadow
    private ServerLevel level;
    @Shadow
    private RandomSource random;
    @Shadow
    private Optional<BlockPos> waveSpawnPos;

    @Shadow
    public abstract int getTotalRaidersAlive();

    @Shadow
    public abstract boolean isStopped();

    @Shadow
    public abstract boolean isOver();

    @Shadow
    public abstract void updateBossbar();

    @Shadow
    public abstract int getDefaultNumSpawns(RaiderType type, int wave, boolean flag);

    @Shadow
    public abstract int getPotentialBonusSpawns(RaiderType type, RandomSource random, int wave, DifficultyInstance difficulty, boolean flag);

    @Shadow
    public abstract void setLeader(int wave, Raider raider);

    @Shadow
    public abstract boolean shouldSpawnBonusGroup();

    @Shadow
    public abstract void setDirty();

    @Shadow
    public abstract int getNumGroups(Difficulty difficulty);

    @Shadow
    public abstract void joinRaid(int wave, Raider raider, BlockPos pos, boolean flag);

    @Unique
    private static final String BOSSES_REMAINING = "event.illageandspillage.raid.bosses_remaining";
    @Unique
    private boolean onlyBosses = false;
    @Unique
    private float previousTotalHealth = 0.0F;
    @Unique
    private Set<UUID> oldNonBossUUIDs = new HashSet<>();

    @Unique
    private List<Raider> getBossesInRaid() {
        return this.groupRaiderMap.values().stream().flatMap(Set::stream).filter(r -> r.getType().is(ModTags.EntityTypes.ILLAGER_BOSSES)).collect(Collectors.toList());
    }

    @Unique
    private List<Raider> getNonBossesInRaid() {
        return this.groupRaiderMap.values().stream().flatMap(Set::stream).filter(r -> !r.getType().is(ModTags.EntityTypes.ILLAGER_BOSSES)).collect(Collectors.toList());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (Config.CommonConfig.bossbar_type.get() != 3 || this.isStopped() || this.isOver()) {
            return;
        }

        List<Raider> bosses = this.getBossesInRaid();
        List<Raider> nonBosses = this.getNonBossesInRaid();

        if (!bosses.isEmpty() && bosses.size() == this.getTotalRaidersAlive()) {
            if (!this.onlyBosses) {
                this.onlyBosses = true;
                this.previousTotalHealth = this.totalHealth;
                this.oldNonBossUUIDs = nonBosses.stream().map(Raider::getUUID).collect(Collectors.toSet());
                this.raidEvent.setDarkenScreen(Config.CommonConfig.bosses_darken_sky.get());
            }

            this.totalHealth = this.getBossesMaxHealth(bosses);

            if (bosses.size() > 2) {
                this.raidEvent.setName(RAID_NAME_COMPONENT.copy().append(" - ").append(Component.translatable(BOSSES_REMAINING, bosses.size())));
            } else if (bosses.size() == 2) {
                this.raidEvent.setName(RAID_NAME_COMPONENT.copy().append(" - ").append(bosses.get(0).getDisplayName()).append(" & ").append(bosses.get(1).getDisplayName()));
            } else if (bosses.size() == 1) {
                this.raidEvent.setName(RAID_NAME_COMPONENT.copy().append(" - ").append(bosses.get(0).getDisplayName()));
            }

            this.updateBossbar();
        } else {
            if (this.onlyBosses) {
                this.onlyBosses = false;

                float newRaiderHP = 0.0F;

                Set<UUID> newlyAdded = nonBosses.stream().map(Raider::getUUID).collect(Collectors.toSet());
                newlyAdded.removeAll(this.oldNonBossUUIDs);

                for (Raider newNonBoss : nonBosses) {
                    if (newlyAdded.contains(newNonBoss.getUUID())) {
                        newRaiderHP += newNonBoss.getMaxHealth();
                    }
                }

                this.totalHealth = this.previousTotalHealth + newRaiderHP;
                this.raidEvent.setDarkenScreen(false);
            }
        }
    }

    @Unique
    private float getBossesMaxHealth(List<Raider> bosses) {
        float sum = 0.0F;
        for (Raider boss : bosses) {
            sum += boss.getMaxHealth();
        }
        return sum;
    }

    @Inject(method = "spawnGroup", at = @At("HEAD"), cancellable = true)
    private void spawnGroup(BlockPos pos, CallbackInfo ci) {
        if (RaidWaveMembers.CUSTOM_RAID_MEMBERS.isEmpty()) {
            return;
        }

        this.ias_customSpawnGroup(pos);
        ci.cancel();
    }

    @Unique
    private void ias_customSpawnGroup(BlockPos pos) {
        int leaderSet = 0;
        int wave = this.groupsSpawned + 1;
        this.totalHealth = 0.0F;
        DifficultyInstance difficulty = this.level.getCurrentDifficultyAt(pos);
        boolean flag = this.shouldSpawnBonusGroup();
        for (RaiderType type : RaiderType.values()) {
            int count = this.getDefaultNumSpawns(type, wave, flag) + this.getPotentialBonusSpawns(type, this.random, wave, difficulty, flag);
            int backupCount = 0;
            for (int k = 0; k < count; k++) {
                Raider raider = (Raider) type.entityType.create(this.level);
                if (raider == null) {
                    continue;
                }

                if (leaderSet == 0 && raider.canBeLeader()) {
                    raider.setPatrolLeader(true);
                    this.setLeader(wave, raider);
                    leaderSet = 1;
                }

                this.joinRaid(wave, raider, pos, false);

                if (type.entityType == EntityType.RAVAGER) {
                    Raider backup = null;
                    if (wave == this.getNumGroups(Difficulty.NORMAL)) {
                        backup = (Raider) EntityType.PILLAGER.create(this.level);
                    } else if (wave >= this.getNumGroups(Difficulty.HARD)) {
                        if (backupCount == 0) {
                            backup = (Raider) EntityType.EVOKER.create(this.level);
                        } else {
                            backup = (Raider) EntityType.VINDICATOR.create(this.level);
                        }
                    }

                    backupCount++;
                    if (backup != null) {
                        this.joinRaid(wave, backup, pos, false);
                        backup.moveTo(pos, 0.0F, 0.0F);
                        backup.startRiding(raider);
                    }
                }
            }
        }
        for (RaidWaveMembers.RaidMember member : RaidWaveMembers.CUSTOM_RAID_MEMBERS) {
            int count = member.spawnsPerWaveBeforeBonus()[flag ? this.numGroups : wave];
            for (int k = 0; k < count; k++) {
                Raider raider = (Raider) member.entityType().create(this.level);
                if (raider == null) {
                    continue;
                }

                if (leaderSet == 0 && raider.canBeLeader()) {
                    raider.setPatrolLeader(true);
                    this.setLeader(wave, raider);
                    leaderSet = 1;
                }

                this.joinRaid(wave, raider, pos, false);
            }
        }
        this.waveSpawnPos = Optional.empty();
        this.groupsSpawned++;
        this.updateBossbar();
        this.setDirty();
    }
}