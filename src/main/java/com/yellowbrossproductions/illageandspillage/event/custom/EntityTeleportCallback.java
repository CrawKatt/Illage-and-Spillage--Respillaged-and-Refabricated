package com.yellowbrossproductions.illageandspillage.event.custom;

import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;

public interface EntityTeleportCallback {
    net.fabricmc.fabric.api.event.Event<EntityTeleportCallback> EVENT = EventFactory.createArrayBacked(
            EntityTeleportCallback.class,
            callbacks -> event -> {
                for (EntityTeleportCallback callback : callbacks) {
                    callback.onEntityTeleport(event);
                }
            }
    );

    void onEntityTeleport(Event event);

    class Event {
        private final Entity entity;
        private final double prevX;
        private final double prevY;
        private final double prevZ;
        private double targetX;
        private double targetY;
        private double targetZ;
        private boolean canceled;

        public Event(Entity entity, double targetX, double targetY, double targetZ) {
            this.entity = entity;
            this.prevX = entity.getX();
            this.prevY = entity.getY();
            this.prevZ = entity.getZ();
            this.targetX = targetX;
            this.targetY = targetY;
            this.targetZ = targetZ;
        }

        public Entity getEntity() {
            return this.entity;
        }

        public double getPrevX() {
            return this.prevX;
        }

        public double getPrevY() {
            return this.prevY;
        }

        public double getPrevZ() {
            return this.prevZ;
        }

        public double getTargetX() {
            return this.targetX;
        }

        public void setTargetX(double targetX) {
            this.targetX = targetX;
        }

        public double getTargetY() {
            return this.targetY;
        }

        public void setTargetY(double targetY) {
            this.targetY = targetY;
        }

        public double getTargetZ() {
            return this.targetZ;
        }

        public void setTargetZ(double targetZ) {
            this.targetZ = targetZ;
        }

        public boolean isCanceled() {
            return this.canceled;
        }

        public void setCanceled(boolean canceled) {
            this.canceled = canceled;
        }
    }
}