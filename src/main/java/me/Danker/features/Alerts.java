package me.Danker.features;

import com.google.gson.GsonBuilder;
import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Alerts {

    public static List<Alert> alerts = new ArrayList<>();
    public static String configFile;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!ToggleCommand.alerts || event.type == 2) return;

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        for (Alert alert : alerts) {
            if (!alert.toggled) continue;

            boolean location;
            switch (alert.location) {
                case "Skyblock":
                    location = Utils.inSkyblock;
                    break;
                case "Dungeons":
                    location = Utils.inDungeons;
                    break;
                default:
                    location = true;
            }
            if (!location) continue;

            boolean trigger;
            switch (alert.mode) {
                case "Starts With":
                    trigger = message.startsWith(alert.message);
                    break;
                case "Contains":
                    trigger = message.contains(alert.message);
                    break;
                case "Ends With":
                    trigger = message.endsWith(alert.message);
                    break;
                default:
                    continue;
            }

            if (trigger) {
                Utils.createTitle(EnumChatFormatting.RED + alert.alert.replace("&", "§"), 2);

                if (alert.desktop) {
                    try {
                        final SystemTray tray = SystemTray.getSystemTray();
                        final Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                        final TrayIcon trayIcon = new TrayIcon(image, "Alert");
                        trayIcon.setImageAutoSize(true);
                        trayIcon.setToolTip("Alert");
                        tray.add(trayIcon);
                        trayIcon.displayMessage(StringUtils.stripControlCodes(alert.alert), message, TrayIcon.MessageType.INFO);
                        tray.remove(trayIcon);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                return;
            }
        }
    }

    public static void saveToFile() {
        try (FileWriter writer = new FileWriter(configFile)) {
            new GsonBuilder().create().toJson(alerts, writer);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static class Alert {

        public String mode;
        public String location;
        public String message;
        public String alert;
        public boolean desktop;
        public boolean toggled;

        public Alert(String mode, String location, String message, String alert, boolean desktop, boolean toggled) {
            this.mode = mode;
            this.location = location;
            this.message = message;
            this.alert = alert;
            this.desktop = desktop;
            this.toggled = toggled;
        }

        public void toggle() {
            toggled = !toggled;
        }

        public void toggleDesktop() {
            desktop = !desktop;
        }

    }

}
