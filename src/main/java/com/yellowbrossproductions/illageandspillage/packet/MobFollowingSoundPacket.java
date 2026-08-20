package com.yellowbrossproductions.illageandspillage.packet;

import com.yellowbrossproductions.illageandspillage.util.ClientHelper;
import com.yellowbrossproductions.illageandspillage.util.MobFollowingSoundPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class MobFollowingSoundPacket {
    private final int entityId;
    private final SoundEvent sound;
    private final float volume;
    private final float pitch;
    private final boolean loop;

    public MobFollowingSoundPacket(int entityId, SoundEvent sound, float volume, float pitch, boolean loop) {
        this.entityId = entityId;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.loop = loop;
    }

    public static void encode(MobFollowingSoundPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        ResourceLocation soundLocation = BuiltInRegistries.SOUND_EVENT.getKey(msg.sound);
        buf.writeResourceLocation(soundLocation == null ? new ResourceLocation("") : soundLocation);
        buf.writeFloat(msg.volume);
        buf.writeFloat(msg.pitch);
        buf.writeBoolean(msg.loop);
    }

    public static MobFollowingSoundPacket decode(FriendlyByteBuf buf) {
        return new MobFollowingSoundPacket(buf.readInt(), BuiltInRegistries.SOUND_EVENT.get(buf.readResourceLocation()), buf.readFloat(), buf.readFloat(), buf.readBoolean());
    }

    public static class Handler {
        public static void onMessage(Minecraft client, MobFollowingSoundPacket message) {
            final Level level = ClientHelper.getLevel();
            if (level != null) {
                Entity entity = level.getEntity(message.entityId);
                if (entity != null && message.sound != null) {
                    MobFollowingSoundPlayer.playSound(level, entity, message.sound, message.volume, message.pitch, message.loop);
                }
            }
        }
    }
}