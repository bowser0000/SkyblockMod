package me.Danker.features;

import me.Danker.commands.ToggleCommand;
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

        if (ToggleCommand.gpartyToggled) {
            if (message.contains(" has invited all members of ")) {
                try {
                    final SystemTray tray = SystemTray.getSystemTray();
                    final Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                    final TrayIcon trayIcon = new TrayIcon(image, "Guild Party Notifier");
                    trayIcon.setImageAutoSize(true);
                    trayIcon.setToolTip("Guild Party Notifier");
                    tray.add(trayIcon);
                    trayIcon.displayMessage("Guild Party", message, TrayIcon.MessageType.INFO);
                    tray.remove(trayIcon);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
