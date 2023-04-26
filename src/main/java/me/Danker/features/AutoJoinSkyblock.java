package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class AutoJoinSkyblock {

    static boolean joinedServer = false;

    @SubscribeEvent
    public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (ModConfig.autoJoinSkyblock && !joinedServer) {
            joinedServer = true;
            new Thread(() -> {
                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

                try {
                    while (player == null) {
                        Thread.sleep(100);
                        player = Minecraft.getMinecraft().thePlayer;
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (Utils.isOnHypixel()) player.sendChatMessage("/play sb");
            }).start();
        }
    }

    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        joinedServer = false;
    }

}
