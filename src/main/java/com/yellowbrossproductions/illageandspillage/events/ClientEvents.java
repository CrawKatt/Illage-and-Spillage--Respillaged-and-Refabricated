package com.yellowbrossproductions.illageandspillage.events;

import com.yellowbrossproductions.illageandspillage.gui.overlay.JumpscareOverlay;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ClientEvents {
    public static void clientTick() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> JumpscareOverlay.JUMPSCARE_OVERLAY.clientTick());
    }
}