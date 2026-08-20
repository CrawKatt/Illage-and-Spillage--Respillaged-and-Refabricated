package com.yellowbrossproductions.illageandspillage.events;

import com.yellowbrossproductions.illageandspillage.Config;
import com.yellowbrossproductions.illageandspillage.IllageAndSpillage;
import com.yellowbrossproductions.illageandspillage.entities.*;
import com.yellowbrossproductions.illageandspillage.event.custom.LivingHurtCallback;
import com.yellowbrossproductions.illageandspillage.event.custom.LivingTickCallback;
import com.yellowbrossproductions.illageandspillage.init.ModEntityTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;

public class NightmareEvents {
//    private static float previousYaw;
//    private static float previousPitch;

    public static void init() {
        LivingHurtCallback.EVENT.register(NightmareEvents::onHurt);
        LivingTickCallback.EVENT.register(NightmareEvents::onLivingTick);
    }

    public static void onHurt(LivingHurtCallback.Event event) {
        LivingEntity victim = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        if (Config.CommonConfig.nightmare_mode.get() && (attacker instanceof MagispellerEntity || attacker instanceof FakeMagispellerEntity || attacker instanceof IllashooterEntity || attacker instanceof CrashagerEntity || attacker instanceof KaboomerEntity)) {
            event.setAmount((float) (event.getAmount() * Config.CommonConfig.magi_damage_multiplier.get()));
        }
        if (Config.CommonConfig.nightmare_mode.get() && (attacker instanceof FreakagerEntity || attacker instanceof OldFreakagerEntity || attacker instanceof EyesoreEntity || attacker instanceof FunnyboneEntity)) {
            event.setAmount((float) (event.getAmount() * Config.CommonConfig.freaky_damage_multiplier.get()));
        }
        if (Config.CommonConfig.nightmare_mode.get() && (attacker instanceof RagnoEntity || attacker instanceof OldRagnoEntity)) {
            event.setAmount((float) (event.getAmount() * Config.CommonConfig.ragno_damage_multiplier.get()));
        }
        if (Config.CommonConfig.nightmare_mode.get() && (attacker instanceof SpiritcallerEntity || attacker instanceof MobSpiritEntity || attacker instanceof IllagerSoulEntity)) {
            event.setAmount((float) (event.getAmount() * Config.CommonConfig.spiri_damage_multiplier.get()));
        }
        if (Config.CommonConfig.nightmare_mode.get() && attacker instanceof KaboomerEntity && victim.getUseItem().getItem() instanceof ShieldItem) {
            victim.getUseItem().shrink(victim.getUseItem().getCount());
        }
    }

    public static void onLivingTick(LivingEntity entity) {
        Level level = entity.level();

        if (!Config.CommonConfig.ULTIMATE_NIGHTMARE.get() || level.isClientSide() || !(level instanceof ServerLevel) || (level.dimension() == Level.NETHER && entity instanceof EnderMan) || entity instanceof Blaze || entity instanceof EnderDragon || BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()) == null || BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getNamespace().equals(IllageAndSpillage.MOD_ID)) {
            return;
        }

        if (entity instanceof Mob && (((Mob) entity).getMobType() == MobType.WATER || ((Mob) entity).getRandom().nextDouble() <= 0.1)) {
            entity.discard();
            return;
        }

        if (entity instanceof Mob mob) {
            Mob newEntity;

            if (mob.getRandom().nextDouble() < 0.05) {
                int randomSelection = mob.getRandom().nextInt(0, 4);
                if (randomSelection == 0) {
                    newEntity = ModEntityTypes.Magispeller.create(level);
                    assert newEntity != null;
                    ((MagispellerEntity) newEntity).setActive(true);
                } else if (randomSelection == 1) {
                    newEntity = ModEntityTypes.Spiritcaller.create(level);
                    assert newEntity != null;
                    ((SpiritcallerEntity) newEntity).setActive(true);
                } else if (randomSelection == 2) {
                    newEntity = ModEntityTypes.Freakager.create(level);
                    assert newEntity != null;
                    ((FreakagerEntity) newEntity).setActive(true);

                    RagnoEntity ragno = ModEntityTypes.Ragno.create(level);
                    assert ragno != null;
                    ragno.moveTo(mob.getX(), mob.getY(), mob.getZ(), mob.getYRot(), mob.getXRot());
                    ragno.setOwner(newEntity);
                    ragno.setShakeMultiplier(10);
                    ragno.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(ragno.blockPosition()), MobSpawnType.NATURAL, null, null);
                    level.addFreshEntity(ragno);

                    newEntity.startRiding(ragno);
                } else {
                    newEntity = ModEntityTypes.Ragno.create(level);
                }
            } else {
                int randomSelection = mob.getRandom().nextInt(0, 6);
                if (randomSelection == 0) {
                    newEntity = ModEntityTypes.Igniter.create(level);
                } else if (randomSelection == 1) {
                    newEntity = ModEntityTypes.Engineer.create(level);
                } else if (randomSelection == 2) {
                    newEntity = ModEntityTypes.Twittollager.create(level);
                } else if (randomSelection == 3) {
                    newEntity = ModEntityTypes.Preserver.create(level);
                } else if (randomSelection == 4) {
                    newEntity = ModEntityTypes.Absorber.create(level);
                } else {
                    newEntity = ModEntityTypes.Crocofang.create(level);
                }
            }

            if (newEntity != null) {
                newEntity.moveTo(mob.getX(), mob.getY(), mob.getZ(), mob.getYRot(), mob.getXRot());
                newEntity.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(newEntity.blockPosition()), MobSpawnType.NATURAL, null, null);
                level.addFreshEntity(newEntity);
            }

            mob.discard();
        }
    }

//    @SubscribeEvent
//    public static void onEffectWearOff(MobEffectEvent event) {
//        if (event.getEffectInstance() != null && event.getEffectInstance().getEffect() instanceof RapidFireEffect && event.getEffectInstance().getDuration() == 0) {
//            LivingEntity entity = event.getEntity();
//            ItemStack crossbow = null;
//            if (entity.getMainHandItem().getItem() == ItemRegisterer.MAGI_CROSSBOW_ITEM.get()) {
//                crossbow = entity.getMainHandItem();
//            } else if (entity.getOffhandItem().getItem() == ItemRegisterer.MAGI_CROSSBOW_ITEM.get()) {
//                crossbow = entity.getOffhandItem();
//            }
//            if (entity instanceof Player && crossbow != null) {
//                CrossbowItem.onCrossbowShot(event.getEntity().level(), entity, crossbow);
//            }
//        }
//    }

//    @SubscribeEvent
//    public static void onClientTick(TickEvent.ClientTickEvent event) {
//        Minecraft mc = Minecraft.getInstance();
//        Player player = mc.player;
//        if (player != null && player.hasEffect(EffectRegisterer.CHANNELING.get())) {
//            player.setYRot(previousYaw);
//            player.setXRot(previousPitch);
//        } else if (player != null) {
//            previousYaw = player.getYRot();
//            previousPitch = player.getXRot();
//        }
//    }

//    @SubscribeEvent
//    public static void onJoinLevel(EntityJoinLevelEvent event) {
//        Entity entity = event.getEntity();
//        if (Config.CommonConfig.nightmare_mode.get() && entity instanceof SpiritcallerEntity) {
//            ItemStack head = new ItemStack(Items.NETHERITE_HELMET);
//            head.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 3);
//            head.enchant(Enchantments.UNBREAKING, 255);
//            head.enchant(Enchantments.VANISHING_CURSE, 1);
//            ItemStack chest = new ItemStack(Items.NETHERITE_CHESTPLATE);
//            chest.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 3);
//            chest.enchant(Enchantments.UNBREAKING, 255);
//            chest.enchant(Enchantments.VANISHING_CURSE, 1);
//            ItemStack legs = new ItemStack(Items.NETHERITE_LEGGINGS);
//            legs.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 3);
//            legs.enchant(Enchantments.UNBREAKING, 255);
//            legs.enchant(Enchantments.VANISHING_CURSE, 1);
//            ItemStack feet = new ItemStack(Items.NETHERITE_BOOTS);
//            feet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 3);
//            feet.enchant(Enchantments.UNBREAKING, 255);
//            feet.enchant(Enchantments.VANISHING_CURSE, 1);
//            entity.setItemSlot(EquipmentSlot.HEAD, head);
//            entity.setItemSlot(EquipmentSlot.CHEST, chest);
//            entity.setItemSlot(EquipmentSlot.LEGS, legs);
//            entity.setItemSlot(EquipmentSlot.FEET, feet);
//        } else if (Config.CommonConfig.nightmare_mode.get() && entity instanceof FreakagerEntity) {
//            ItemStack head = new ItemStack(Items.NETHERITE_HELMET);
//            head.enchant(Enchantments.PROJECTILE_PROTECTION, 7);
//            head.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
//            head.enchant(Enchantments.UNBREAKING, 255);
//            head.enchant(Enchantments.VANISHING_CURSE, 1);
//            ItemStack chest = new ItemStack(Items.NETHERITE_CHESTPLATE);
//            chest.enchant(Enchantments.PROJECTILE_PROTECTION, 7);
//            chest.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
//            chest.enchant(Enchantments.UNBREAKING, 255);
//            chest.enchant(Enchantments.VANISHING_CURSE, 1);
//            ItemStack legs = new ItemStack(Items.NETHERITE_LEGGINGS);
//            legs.enchant(Enchantments.PROJECTILE_PROTECTION, 7);
//            legs.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
//            legs.enchant(Enchantments.UNBREAKING, 255);
//            legs.enchant(Enchantments.VANISHING_CURSE, 1);
//            ItemStack feet = new ItemStack(Items.NETHERITE_BOOTS);
//            feet.enchant(Enchantments.PROJECTILE_PROTECTION, 7);
//            feet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
//            feet.enchant(Enchantments.UNBREAKING, 255);
//            feet.enchant(Enchantments.VANISHING_CURSE, 1);
//            entity.setItemSlot(EquipmentSlot.HEAD, head);
//            entity.setItemSlot(EquipmentSlot.CHEST, chest);
//            entity.setItemSlot(EquipmentSlot.LEGS, legs);
//            entity.setItemSlot(EquipmentSlot.FEET, feet);
//        } else if (Config.CommonConfig.nightmare_mode.get() && entity instanceof RagnoEntity) {
//            ItemStack head = new ItemStack(Items.NETHERITE_HELMET);
//            head.enchant(Enchantments.UNBREAKING, 255);
//            head.enchant(Enchantments.VANISHING_CURSE, 1);
//            ItemStack chest = new ItemStack(Items.NETHERITE_CHESTPLATE);
//            chest.enchant(Enchantments.UNBREAKING, 255);
//            chest.enchant(Enchantments.VANISHING_CURSE, 1);
//            ItemStack legs = new ItemStack(Items.NETHERITE_LEGGINGS);
//            legs.enchant(Enchantments.UNBREAKING, 255);
//            legs.enchant(Enchantments.VANISHING_CURSE, 1);
//            ItemStack feet = new ItemStack(Items.NETHERITE_BOOTS);
//            feet.enchant(Enchantments.UNBREAKING, 255);
//            feet.enchant(Enchantments.VANISHING_CURSE, 1);
//            entity.setItemSlot(EquipmentSlot.HEAD, head);
//            entity.setItemSlot(EquipmentSlot.CHEST, chest);
//            entity.setItemSlot(EquipmentSlot.LEGS, legs);
//            entity.setItemSlot(EquipmentSlot.FEET, feet);
//        } else if (Config.CommonConfig.nightmare_mode.get() && entity instanceof MagispellerEntity) {
//            ItemStack head = new ItemStack(Items.NETHERITE_HELMET);
//            head.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
//            head.enchant(Enchantments.PROJECTILE_PROTECTION, 5);
//            head.enchant(Enchantments.UNBREAKING, 255);
//            head.enchant(Enchantments.VANISHING_CURSE, 1);
//            ItemStack chest = new ItemStack(Items.NETHERITE_CHESTPLATE);
//            chest.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
//            chest.enchant(Enchantments.PROJECTILE_PROTECTION, 5);
//            chest.enchant(Enchantments.UNBREAKING, 255);
//            chest.enchant(Enchantments.VANISHING_CURSE, 1);
//            ItemStack legs = new ItemStack(Items.NETHERITE_LEGGINGS);
//            legs.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
//            legs.enchant(Enchantments.PROJECTILE_PROTECTION, 5);
//            legs.enchant(Enchantments.UNBREAKING, 255);
//            legs.enchant(Enchantments.VANISHING_CURSE, 1);
//            ItemStack feet = new ItemStack(Items.NETHERITE_BOOTS);
//            feet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 2);
//            feet.enchant(Enchantments.PROJECTILE_PROTECTION, 5);
//            feet.enchant(Enchantments.FALL_PROTECTION, 4);
//            feet.enchant(Enchantments.UNBREAKING, 255);
//            feet.enchant(Enchantments.VANISHING_CURSE, 1);
//            entity.setItemSlot(EquipmentSlot.HEAD, head);
//            entity.setItemSlot(EquipmentSlot.CHEST, chest);
//            entity.setItemSlot(EquipmentSlot.LEGS, legs);
//            entity.setItemSlot(EquipmentSlot.FEET, feet);
//        } else if (Config.CommonConfig.nightmare_mode.get() && entity instanceof DispenserEntity) {
//            Objects.requireNonNull(((DispenserEntity) entity).getAttribute(Attributes.MAX_HEALTH)).setBaseValue(30.0);
//            ((DispenserEntity) entity).heal(50);
//        }
//        if (entity instanceof MagispellerEntity) {
//            entity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ItemRegisterer.MAGI_AXE_ITEM.get()));
//            entity.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(ItemRegisterer.MAGI_CROSSBOW_ITEM.get()));
//        }
//    }

//    @SubscribeEvent
//    public static void misconductionAttack4(PlayerInteractEvent.RightClickEmpty event) {
//        Player player = event.getEntity();
//        if (player.getPersistentData().getInt("BeamCooldown") <= 0 && player.level().isClientSide && player.getMainHandItem().isEmpty() && player.hasEffect(EffectRegisterer.MODIFIED_MISCONDUCTION.get())) {
//            player.playSound(IllageAndSpillageSoundEvents.ENTITY_SPIRITCALLER_LASER.get(), 2.0F, 1.0F);
//            PacketHandler.CHANNEL.sendToServer(new MisconductionLaserAttackPacket());
//            player.getPersistentData().putInt("BeamCooldown", 680);
//        }
//    }
}