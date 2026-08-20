package com.yellowbrossproductions.illageandspillage.client;

import com.yellowbrossproductions.illageandspillage.client.sound.BossMusicPlayer;
import com.yellowbrossproductions.illageandspillage.events.ClientEventHandler;
import com.yellowbrossproductions.illageandspillage.events.ClientEvents;
import com.yellowbrossproductions.illageandspillage.events.ClientModEventBusSubscriber;
import net.fabricmc.api.ClientModInitializer;

public class IllageAndSpillageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BossMusicPlayer.onClientTick();
        ClientEvents.clientTick();
        ClientModEventBusSubscriber.initClient();
        ClientEventHandler.initClient();
    }
}
