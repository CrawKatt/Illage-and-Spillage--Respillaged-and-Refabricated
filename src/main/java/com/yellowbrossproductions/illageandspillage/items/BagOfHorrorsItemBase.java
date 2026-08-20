package com.yellowbrossproductions.illageandspillage.items;

import com.yellowbrossproductions.illageandspillage.entities.TrickOrTreatEntity;
import com.yellowbrossproductions.illageandspillage.entities.projectile.PumpkinBombEntity;
import com.yellowbrossproductions.illageandspillage.entities.projectile.ScytheEntity;
import com.yellowbrossproductions.illageandspillage.init.ModEntityTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BagOfHorrorsItemBase extends Item {
    public BagOfHorrorsItemBase() {
        super(new Item.Properties());
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.playSound(SoundEvents.SNOWBALL_THROW, 1.0F, 0.5F);
        int randomSelection = player.getRandom().nextInt(3);
        if (randomSelection == 0) {
            for (int i = 0; i < 4; ++i) {
                double throwSpeed = 0.7;
                PumpkinBombEntity s1 = ModEntityTypes.PumpkinBomb.create(level);

                assert s1 != null;

                s1.setPos(player.getX(), player.getY() + 0.25, player.getZ());
                s1.setOwner(player);
                if (player.getLastHurtByMob() != null && player.getLastHurtByMob().isAlive() && !player.getLastHurtByMob().isRemoved()) {
                    s1.setTarget(player.getLastHurtByMob());
                } else {
                    s1.setTarget(level.getNearestEntity(Monster.class, TargetingConditions.DEFAULT, player, player.getX(), player.getY(), player.getZ(), player.getBoundingBox().inflate(40.0)));
                }
                if (i == 0) {
                    s1.setDeltaMovement(-throwSpeed, 0.3, -throwSpeed);
                } else if (i == 1) {
                    s1.setDeltaMovement(-throwSpeed, 0.3, throwSpeed);
                } else if (i == 2) {
                    s1.setDeltaMovement(throwSpeed, 0.3, -throwSpeed);
                } else {
                    s1.setDeltaMovement(throwSpeed, 0.3, throwSpeed);
                }

                if (player.getTeam() != null) {
                    level.getScoreboard().addPlayerToTeam(s1.getStringUUID(), level.getScoreboard().getPlayerTeam(player.getTeam().getName()));
                }

                level.addFreshEntity(s1);
            }
        } else if (randomSelection == 1) {
            ScytheEntity scythe = ModEntityTypes.Scythe.create(level);

            assert scythe != null;

            scythe.setPos(player.getX(), player.getY() + 1.5, player.getZ());
            scythe.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.0F, 0.0F);
            scythe.setShooter(player);
            level.addFreshEntity(scythe);
        } else {
            int amount = 3;

            for (int i = 0; i < amount; ++i) {
                TrickOrTreatEntity treat = ModEntityTypes.TrickOrTreat.create(level);

                assert treat != null;

                treat.circleTime = i * 20;
                treat.bounceTime = i;
                treat.setPos(player.getX(), player.getY(), player.getZ());
                treat.setOwner(player);
                treat.setTreat(player.getRandom().nextInt(6) + 1);
                if (player.getTeam() != null) {
                    level.getScoreboard().addPlayerToTeam(treat.getStringUUID(), level.getScoreboard().getPlayerTeam(player.getTeam().getName()));
                }

                this.circleTreat(treat, i, amount);
                level.addFreshEntity(treat);
            }
        }

        player.getCooldowns().addCooldown(this, 300);
        return super.use(level, player, hand);
    }

    private void circleTreat(Entity entity, int number, int amount) {
        float yaw = (float) number * (6.2831855F / (float) amount);
        float vy = 0.3F;
        float vx = 0.5F * Mth.cos(yaw);
        float vz = 0.5F * Mth.sin(yaw);
        entity.setDeltaMovement(vx, vy, vz);
    }

    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }
}
