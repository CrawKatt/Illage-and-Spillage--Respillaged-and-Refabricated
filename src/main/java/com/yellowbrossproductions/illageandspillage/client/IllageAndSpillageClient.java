package com.yellowbrossproductions.illageandspillage.client;

import com.yellowbrossproductions.illageandspillage.events.ClientEvents;
import net.fabricmc.api.ClientModInitializer;

public class IllageAndSpillageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientEvents.clientTick();
    }
}
