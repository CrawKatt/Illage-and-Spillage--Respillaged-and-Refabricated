package com.yellowbrossproductions.illageandspillage.event.custom;

import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ArrowNockCallback {
    net.fabricmc.fabric.api.event.Event<ArrowNockCallback> EVENT = EventFactory.createArrayBacked(
            ArrowNockCallback.class,
            callbacks -> event -> {
                for (ArrowNockCallback callback : callbacks) {
                    InteractionResultHolder<ItemStack> result = callback.onArrowNock(event);
                    if (result != null) {
                        return result;
                    }
                }
                return null;
            }
    );

    InteractionResultHolder<ItemStack> onArrowNock(Event event);

    class Event {
        private final Player entity;
        private final ItemStack item;
        private final InteractionHand hand;
        private final Level level;
        private final boolean hasAmmo;
        private InteractionResultHolder<ItemStack> result;

        public Event(Player entity, ItemStack item, InteractionHand hand, Level level, boolean hasAmmo) {
            this.entity = entity;
            this.item = item;
            this.hand = hand;
            this.level = level;
            this.hasAmmo = hasAmmo;
        }

        public Player getEntity() {
            return this.entity;
        }

        public ItemStack getItem() {
            return this.item;
        }

        public InteractionHand getHand() {
            return this.hand;
        }

        public Level getLevel() {
            return this.level;
        }

        public boolean getHasAmmo() {
            return this.hasAmmo;
        }

        public InteractionResultHolder<ItemStack> getResult() {
            return this.result;
        }

        public void setResult(InteractionResultHolder<ItemStack> result) {
            this.result = result;
        }
    }
}