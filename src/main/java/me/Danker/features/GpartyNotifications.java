package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.utils.Utils;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class GpartyNotifications {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (message.contains(":")) return;

        if (ModConfig.gparty) {
            if (message.contains(" has invited all members of ")) {
                Utils.desktopNotification("Guild Party Notifier", "Guild Party", message, TrayIcon.MessageType.INFO);
            }
        }
    }

}
