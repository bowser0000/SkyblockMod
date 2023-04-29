package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.events.PacketWriteEvent;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LocrawSpamFix {

    static long lastLocraw = 0;

    @SubscribeEvent
    public void onPacketWrite(PacketWriteEvent event) {
        if (!ModConfig.fixLocraw) return;

        if (event.packet instanceof C01PacketChatMessage) {
            C01PacketChatMessage packet = (C01PacketChatMessage) event.packet;

            if (packet.getMessage().equals("/locraw")) {
                long time = System.currentTimeMillis();
                if (time - lastLocraw <= 5000) event.setCanceled(true);
                lastLocraw = time;
            }
        }
    }

}
