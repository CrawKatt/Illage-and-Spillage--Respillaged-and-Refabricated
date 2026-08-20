package com.yellowbrossproductions.illageandspillage.items;

import com.mojang.blaze3d.platform.InputConstants;
import com.yellowbrossproductions.illageandspillage.Config;
import com.yellowbrossproductions.illageandspillage.event.custom.ArrowNockCallback;
import com.yellowbrossproductions.illageandspillage.util.EffectRegisterer;
import com.yellowbrossproductions.illageandspillage.util.IllageAndSpillageSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;

public class SpellboundBookItemBase extends Item {
    public SpellboundBookItemBase() {
        super(new Item.Properties().stacksTo(1));
    }

    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (!InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 340)) {
            tooltip.add(Component.translatable("tooltip.illageandspillage.shift"));
        } else {
            if (Config.CommonConfig.spellboundbook_rechargeTime.get() > 0) {
                tooltip.add(Component.translatable("tooltip.illageandspillage.spellbound_book_1").append((Config.CommonConfig.spellboundbook_rechargeTime.get()).toString()).append(Component.translatable("tooltip.illageandspillage.spellbound_book_1_minutes")));
                tooltip.add(Component.translatable("tooltip.illageandspillage.spellbound_book_2"));
                tooltip.add(Component.translatable("tooltip.illageandspillage.spellbound_book_3"));
            }

            tooltip.add(Component.translatable("tooltip.illageandspillage.spellbound_book_4"));
        }

    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        boolean flag = itemstack.getDamageValue() == 0;
        ArrowNockCallback.Event event = new ArrowNockCallback.Event(playerIn, itemstack, handIn, worldIn, flag);
        InteractionResultHolder<ItemStack> ret = ArrowNockCallback.EVENT.invoker().onArrowNock(event);
        if (ret != null) {
            return ret;
        } else if (!playerIn.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), IllageAndSpillageSoundEvents.SPELLBOUND_BOOK_USE, SoundSource.PLAYERS, 2.0F, 1.0F);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
        if (p_41404_.getDamageValue() > 0) {
            p_41404_.setDamageValue(p_41404_.getDamageValue() - 1);
        }

    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public boolean canBeDepleted() {
        return Config.CommonConfig.spellboundbook_rechargeTime.get() > 0;
    }

    public int getMaxDamage(ItemStack stack) {
        return Config.CommonConfig.spellboundbook_rechargeTime.get() * 1200;
    }

    public boolean isEnchantable(ItemStack p_41456_) {
        return false;
    }

    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player) {
            if (!worldIn.isClientSide) {
                LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(worldIn);

                assert bolt != null;

                bolt.setPos(player.getX(), player.getY(), player.getZ());
                bolt.setVisualOnly(true);
                worldIn.addFreshEntity(bolt);
            }

            worldIn.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 2.0F, 0.8F);
            worldIn.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 2.0F, 0.8F);
            worldIn.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.PLAYERS, 2.0F, 0.8F);
            worldIn.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 10000.0F, 0.8F);
            player.addEffect(new MobEffectInstance(EffectRegisterer.MISCONDUCTION, Config.CommonConfig.spellboundbook_effectTime.get() * 1200, 0, true, false));
            if (!player.getAbilities().instabuild) {
                stack.setDamageValue(72000);
            }

            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }
}
