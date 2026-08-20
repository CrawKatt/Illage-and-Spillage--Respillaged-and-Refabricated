package com.yellowbrossproductions.illageandspillage.events;

import com.yellowbrossproductions.illageandspillage.Config;
import com.yellowbrossproductions.illageandspillage.IllageAndSpillage;
import com.yellowbrossproductions.illageandspillage.entities.*;
import com.yellowbrossproductions.illageandspillage.entities.goal.LoseAIGoal;
import com.yellowbrossproductions.illageandspillage.entities.goal.RunFromIntroBossGoal;
import com.yellowbrossproductions.illageandspillage.entities.goal.RunToHinderGoal;
import com.yellowbrossproductions.illageandspillage.entities.projectile.PumpkinBombEntity;
import com.yellowbrossproductions.illageandspillage.init.ModEntityTypes;
import com.yellowbrossproductions.illageandspillage.init.RaidWaveMembers;
import com.yellowbrossproductions.illageandspillage.util.EffectRegisterer;
import com.yellowbrossproductions.illageandspillage.util.EntityUtil;
import com.yellowbrossproductions.illageandspillage.util.ModTags;
import com.yellowbrossproductions.illageandspillage.event.custom.LivingHurtCallback;
import com.yellowbrossproductions.illageandspillage.event.custom.LivingTickCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raid.RaiderType;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class ForgeEventBusSubscriber {
    public static void init() {
        ServerWorldEvents.LOAD.register((server, world) -> addRaidMembers());
        ServerWorldEvents.UNLOAD.register((server, world) -> removeRaidMembers());
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            addGoals(entity);
            stopMobs(entity);
        });
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (preventGettingHurt(entity, source)) {
                return false;
            }
            extinguishIllagers(entity, source);
            misconductionAttack2(entity, source);
            absorberGetsHurt(entity);
            tickOffBosses(entity, source);
            return true;
        });
        UseBlockCallback.EVENT.register(ForgeEventBusSubscriber::misconductionAttack1);
        LivingHurtCallback.EVENT.register(ForgeEventBusSubscriber::calculatePreservedDamage);
        LivingHurtCallback.EVENT.register(ForgeEventBusSubscriber::magispellerNegateDamage);
        LivingTickCallback.EVENT.register(ForgeEventBusSubscriber::onLivingTick);
    }

    private static void addGoals(Entity entity) {
        if (entity instanceof AbstractVillager) {
            double runSpeed = 1.0;
            if (entity instanceof Villager) {
                runSpeed = 0.8;
            }

            if (entity instanceof WanderingTrader) {
                runSpeed = 0.5;
            }

            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, BossRandomizerEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, IgniterEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, EngineerEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, ChagrinSentryEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, BeeperEntity.class, 4.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, SniperEntity.class, 4.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, PokerEntity.class, 4.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, TwittollagerEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, OldMagispellerEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, SpiritcallerEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, CrocofangEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, PreserverEntity.class, 8.0F, runSpeed, runSpeed));
//            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, DevastatorEntity.class, 24.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, AbsorberEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, FreakagerEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, RagnoEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, OldFreakagerEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, OldRagnoEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, PumpkinBombEntity.class, 4.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, FunnyboneEntity.class, 8.0F, runSpeed, runSpeed));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new AvoidEntityGoal<>((PathfinderMob) entity, MagispellerEntity.class, 8.0F, runSpeed, runSpeed));
        }

        if (entity instanceof Raider raider && !(entity instanceof IllagerAttack)) {
            raider.goalSelector.addGoal(0, new RunToHinderGoal(raider));
        }
    }

    private static void stopMobs(Entity entity) {
        if (entity instanceof Mob) {
            ((Mob) entity).goalSelector.addGoal(0, new LoseAIGoal((Mob) entity));
        }

        if (entity instanceof PathfinderMob && Config.CommonConfig.mobs_watch_intros.get()) {
            ((PathfinderMob) entity).goalSelector.addGoal(0, new RunFromIntroBossGoal((PathfinderMob) entity, SpiritcallerEntity.class, 8.0F, 1.0F, 1.0F));
            ((PathfinderMob) entity).goalSelector.addGoal(0, new RunFromIntroBossGoal((PathfinderMob) entity, FreakagerEntity.class, 8.0F, 1.0F, 1.0F));
        }
    }

    private static void addRaidMembers() {
        RaidWaveMembers.registerWaveMembers();
    }

    private static void removeRaidMembers() {
        Raid.RaiderType[] members = RaiderType.values();

        for (RaiderType member : members) {
            if (RaidWaveMembers.CUSTOM_RAID_MEMBERS.contains(member)) {
                ArrayUtils.remove(members, member.ordinal());
                IllageAndSpillage.LOGGER.info("Removed " + member.name() + " from Raids to prevent a post-mod-removal crash");
            }
        }
    }

    private static void extinguishIllagers(LivingEntity entity, DamageSource reason) {
        if (reason.getEntity() instanceof IgniterEntity && reason.is(DamageTypeTags.IS_PROJECTILE) && !reason.is(DamageTypeTags.IS_FIRE) && entity.isOnFire()) {
            entity.clearFire();
        }
    }

    private static InteractionResult misconductionAttack1(Player player, Level level, BlockHitResult hitResult) {
        if (player.getMainHandItem() == ItemStack.EMPTY && hitResult.getDirection() == Direction.UP && player.hasEffect(EffectRegisterer.MISCONDUCTION)) {
            BlockPos blockpos = hitResult.getBlockPos();
            if (level.isClientSide) {
                player.swing(InteractionHand.MAIN_HAND);
            }

            EntityUtil.createLineImpsAttack(blockpos, player, player.getX(), player.getY(), player.getZ(), level);
        }

        return InteractionResult.PASS;
    }

    private static void misconductionAttack2(LivingEntity entity, DamageSource source) {
        Entity sourceEntity = source.getEntity();
        if (sourceEntity instanceof LivingEntity attacker && attacker.getMainHandItem() == ItemStack.EMPTY && attacker.hasEffect(EffectRegisterer.MISCONDUCTION) && source.is(DamageTypes.PLAYER_ATTACK)) {
            List<IllagerSoulEntity> list = attacker.level().getEntitiesOfClass(IllagerSoulEntity.class, attacker.getBoundingBox().inflate(100.0), (predicate) -> predicate.getTarget() == entity && predicate.getOwner() == attacker);
            if (list.isEmpty()) {
                for (int i = 0; i < 3; ++i) {
                    if (!entity.level().isClientSide) {
                        IllagerSoulEntity soul = ModEntityTypes.IllagerSoul.create(entity.level());

                        assert soul != null;

                        soul.setPos(entity.getX() + -4.0 + attacker.getRandom().nextInt(8), entity.getY() + (double) (1 + attacker.getRandom().nextInt(4)), entity.getZ() + -4.0 + attacker.getRandom().nextInt(8));
                        soul.setOwner(attacker);
                        soul.setAngelOrDevil(attacker.getRandom().nextBoolean());
                        soul.setTarget(entity);
                        soul.setDeltaMovement(0.0, 0.1, 0.0);
                        if (attacker.getTeam() != null) {
                            entity.level().getScoreboard().addPlayerToTeam(soul.getStringUUID(), entity.level().getScoreboard().getPlayerTeam(attacker.getTeam().getName()));
                        }

                        entity.level().addFreshEntity(soul);
                    }
                }
            }
        }
    }

    private static boolean preventGettingHurt(LivingEntity entity, DamageSource source) {
        Entity var2 = source.getEntity();
        if (var2 instanceof IllagerSoulEntity soul) {
            if (soul.getOwner() == entity) {
                return true;
            }
        }

        return false;
    }

    private static void absorberGetsHurt(LivingEntity entity) {
        if (entity instanceof AbsorberEntity) {
            entity.invulnerableTime = 0;
        }
    }

    public static void onLivingTick(LivingEntity mob) {
        if (mob instanceof PreserverEntity thing) {
            if (thing.isOnFire() && thing.getRemainingFireTicks() % 5 == 1) {
                thing.invulnerableTime = 3;
                thing.hurt(thing.damageSources().onFire(), 2.0F);
            }
        }

        if (mob.hasEffect(EffectRegisterer.PRESERVED) && mob.isOnFire() && mob.getRemainingFireTicks() % 5 == 1) {
            mob.invulnerableTime = 3;
            mob.hurt(mob.damageSources().onFire(), 2.0F);
        }

        if (mob.hasEffect(EffectRegisterer.MUTATION)) {
            mob.setDeltaMovement(mob.getDeltaMovement().add((-0.5 + mob.getRandom().nextDouble()) * 0.1, 0.0, (-0.5 + mob.getRandom().nextDouble()) * 0.1));
            if (mob.tickCount % 15 == 0 && mob.getHealth() > 1.0F && mob.invulnerableTime < 1) {
                mob.hurt(mob.damageSources().magic(), 1.0F);
                mob.invulnerableTime = 0;
            }
        }

        if (mob.hasEffect(EffectRegisterer.WEBBED)) {
            if (mob.getRandom().nextInt(20) == 0) EntityUtil.makeWebParticles(mob.level(), mob);
            if (!mob.level().isClientSide && !EntityUtil.isWebbed(mob)) EntityUtil.setWebbed(mob, true);
        } else if (!mob.level().isClientSide && EntityUtil.isWebbed(mob)) {
            EntityUtil.setWebbed(mob, false);
        }

        if (mob.hasEffect(EffectRegisterer.PRESERVED)) {
            if (!mob.level().isClientSide && !EntityUtil.isPreserved(mob)) {
                EntityUtil.setPreserved(mob, true);
            }
        } else if (!mob.level().isClientSide && EntityUtil.isPreserved(mob)) {
            EntityUtil.setPreserved(mob, false);
        }
    }

    public static void calculatePreservedDamage(LivingHurtCallback.Event event) {
        LivingEntity mob = event.getEntity();
        if (mob.hasEffect(EffectRegisterer.PRESERVED) && !event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD) && !event.getSource().is(DamageTypes.GENERIC_KILL) && !event.getSource().is(DamageTypeTags.IS_FIRE)) {
            event.setAmount(event.getAmount() * 0.5F);
        }
    }

    public static void magispellerNegateDamage(LivingHurtCallback.Event event) {
        LivingEntity mob = event.getEntity();
        if (mob instanceof MagispellerEntity && ((MagispellerEntity) mob).isWavingArms() && !event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD) && !event.getSource().is(DamageTypes.GENERIC_KILL)) {
            ((MagispellerEntity) mob).addDamageTaken(event.getAmount());
            event.setAmount(0.0F);
        }
    }

    private static void tickOffBosses(LivingEntity mob, DamageSource source) {
        if (mob.getType().is(ModTags.EntityTypes.ILLAGER_BOSSES) && mob instanceof Mob targeter) {
            Entity var4 = source.getEntity();
            if (var4 instanceof Player player) {
                if (!player.getAbilities().invulnerable) {
                    targeter.setTarget(player);
                }
            }
        }
    }
}