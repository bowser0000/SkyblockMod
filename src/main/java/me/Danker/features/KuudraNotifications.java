package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.events.PacketReadEvent;
import me.Danker.locations.Location;
import me.Danker.utils.Utils;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KuudraNotifications {

    @SubscribeEvent(receiveCanceled = true)
    public void onChat(ClientChatReceivedEvent event) {
        if (!ModConfig.kuudraNotifications) return;
        if (Utils.currentLocation != Location.KUUDRA) return;

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        
        if (message.startsWith("[NPC] Elle: That looks like it hurt!")) {
            Utils.createTitle(EnumChatFormatting.RED + "KUUDRA STUNNED!", 2);
        } else if (message.equals("[NPC] Elle: Phew! The Ballista is finally ready! It should be strong enough to tank Kuudra's blows now!")) {
            Utils.createTitle(EnumChatFormatting.GREEN + "BALLISTA BUILT!", 2);
        }

        if (message.contains(":")) return;

        if (message.contains("recovered a Fuel Cell and charged the Ballista! (100%)")) {
            Utils.createTitle(EnumChatFormatting.GREEN + "BALLISTA CHARGED!", 2);
        }
    }

    @SubscribeEvent
    public void onPacketRead(PacketReadEvent event) {
        if (!ModConfig.kuudraNotifications || Utils.currentLocation != Location.KUUDRA) return;

        if (event.packet instanceof S45PacketTitle) {
            S45PacketTitle packet = (S45PacketTitle) event.packet;

            if (packet.getMessage() == null) return;

            String message = packet.getMessage().getUnformattedText();
            if (message.endsWith(" INCOMING")) {
                new Thread(() -> {
                    try {
                        Thread.sleep(45000);
                        if (Utils.currentLocation == Location.KUUDRA) Utils.createTitle(EnumChatFormatting.AQUA + "CLOAK!", 3);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        }
    }

}
