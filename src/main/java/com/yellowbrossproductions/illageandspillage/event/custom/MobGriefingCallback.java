package com.yellowbrossproductions.illageandspillage.event.custom;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface MobGriefingCallback {
    Event<MobGriefingCallback> EVENT = EventFactory.createArrayBacked(
            MobGriefingCallback.class,
            callbacks -> (level, entity) -> {
                boolean griefing = level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
                for (MobGriefingCallback callback : callbacks) {
                    if (!callback.onMobGriefing(level, entity)) {
                        return false;
                    }
                }
                return griefing;
            }
    );

    boolean onMobGriefing(Level level, @Nullable Entity entity);

    static boolean getMobGriefingEvent(Level level, @Nullable Entity entity) {
        return EVENT.invoker().onMobGriefing(level, entity);
    }
}