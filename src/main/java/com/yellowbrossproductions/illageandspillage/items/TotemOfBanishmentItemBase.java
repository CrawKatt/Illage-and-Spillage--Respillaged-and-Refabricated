package com.yellowbrossproductions.illageandspillage.items;

import com.yellowbrossproductions.illageandspillage.Config;
import com.yellowbrossproductions.illageandspillage.util.IllageAndSpillageSoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class TotemOfBanishmentItemBase extends Item {
    public TotemOfBanishmentItemBase() {
        super(new Item.Properties());
    }

    public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
        List<LivingEntity> list = p_77659_2_.level().getEntitiesOfClass(LivingEntity.class, p_77659_2_.getBoundingBox().inflate(20.0), (predicate) -> predicate.getEncodeId() != null && Config.CommonConfig.banishable_mobs.get().contains(predicate.getEncodeId()));
        if (!list.isEmpty()) {
            for (LivingEntity entity : list) {
                entity.kill();
                if (!entity.level().isClientSide && !entity.isRemoved()) {
                    entity.level().broadcastEntityEvent(entity, (byte) 60);
                    entity.remove(Entity.RemovalReason.KILLED);
                }
            }

            p_77659_2_.playSound(IllageAndSpillageSoundEvents.TOTEM_BANISHMENT, 1.0F, 1.0F);
            p_77659_2_.getCooldowns().addCooldown(this, 300);
        }

        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
    }

    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }
}
