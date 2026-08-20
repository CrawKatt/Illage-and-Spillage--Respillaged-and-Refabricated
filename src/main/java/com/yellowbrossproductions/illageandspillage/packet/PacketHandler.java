package com.yellowbrossproductions.illageandspillage.packet;

import com.yellowbrossproductions.illageandspillage.IllageAndSpillage;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class PacketHandler {
    public static final ResourceLocation PARTICLE_CHANNEL = new ResourceLocation(IllageAndSpillage.MOD_ID, "particle");
    public static final ResourceLocation JUMPSCARE_CHANNEL = new ResourceLocation(IllageAndSpillage.MOD_ID, "jumpscare");
    public static final ResourceLocation MOB_FOLLOWING_SOUND_CHANNEL = new ResourceLocation(IllageAndSpillage.MOD_ID, "mob_following_sound");

    public static void init() {
    }

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(PARTICLE_CHANNEL, (client, handler, buf, responseSender) -> {
            ParticlePacket packet = new ParticlePacket(buf);
            client.execute(() -> ParticlePacket.Handler.onMessage(client, packet));
        });
        ClientPlayNetworking.registerGlobalReceiver(JUMPSCARE_CHANNEL, (client, handler, buf, responseSender) -> {
            JumpscareSyncPacket packet = JumpscareSyncPacket.decode(buf);
            client.execute(() -> JumpscareSyncPacket.Handler.onMessage(client, packet));
        });
        ClientPlayNetworking.registerGlobalReceiver(MOB_FOLLOWING_SOUND_CHANNEL, (client, handler, buf, responseSender) -> {
            MobFollowingSoundPacket packet = MobFollowingSoundPacket.decode(buf);
            client.execute(() -> MobFollowingSoundPacket.Handler.onMessage(client, packet));
        });
    }

    public static void sendToPlayer(ServerPlayer player, ParticlePacket packet) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        packet.encode(buf);
        ServerPlayNetworking.send(player, PARTICLE_CHANNEL, buf);
    }

    public static void sendToPlayer(ServerPlayer player, JumpscareSyncPacket packet) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        JumpscareSyncPacket.encode(packet, buf);
        ServerPlayNetworking.send(player, JUMPSCARE_CHANNEL, buf);
    }

    public static void sendToTrackingAndSelf(Entity entity, MobFollowingSoundPacket packet) {
        for (ServerPlayer player : PlayerLookup.tracking(entity)) {
            sendToPlayer(player, packet);
        }
        if (entity instanceof ServerPlayer player) {
            sendToPlayer(player, packet);
        }
    }

    private static void sendToPlayer(ServerPlayer player, MobFollowingSoundPacket packet) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        MobFollowingSoundPacket.encode(packet, buf);
        ServerPlayNetworking.send(player, MOB_FOLLOWING_SOUND_CHANNEL, buf);
    }
}
