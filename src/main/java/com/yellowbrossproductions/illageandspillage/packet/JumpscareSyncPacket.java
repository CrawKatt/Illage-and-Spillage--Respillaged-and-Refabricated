package com.yellowbrossproductions.illageandspillage.packet;

import com.yellowbrossproductions.illageandspillage.gui.overlay.JumpscareOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

public class JumpscareSyncPacket {


    public JumpscareSyncPacket() {
    }

    public static void encode(JumpscareSyncPacket msg, FriendlyByteBuf buf) {

    }

    public static JumpscareSyncPacket decode(FriendlyByteBuf buf) {
        return new JumpscareSyncPacket();
    }

    public static class Handler {
        public static void onMessage(Minecraft client, JumpscareSyncPacket message) {
            JumpscareOverlay.JUMPSCARE_OVERLAY.show();
        }
    }
}