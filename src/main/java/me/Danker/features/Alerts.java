package me.Danker.features;

import com.google.gson.GsonBuilder;
import me.Danker.config.ModConfig;
import me.Danker.events.ModInitEvent;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Alerts {

    public static List<Alert> alerts = new ArrayList<>();
    public static HashMap<Alert, Pattern> patterns = new HashMap<>();
    public static String configFile;

    @SubscribeEvent
    public void init(ModInitEvent event) {
        configFile = event.configDirectory + "/dsm/dsmalerts.json";
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!ModConfig.alerts || event.type == 2) return;

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        for (Alert alert : alerts) {
            if (!alert.toggled) continue;

            boolean location;
            switch (alert.location) {
                case "Skyblock":
                    location = Utils.inSkyblock;
                    break;
                case "Dungeons":
                    location = Utils.isInDungeons();
                    break;
                default:
                    location = true;
            }
            if (!location) continue;

            if (alert.mode.equals("Regex")) {
                Matcher matcher = patterns.get(alert).matcher(message);
                if (matcher.matches()) {
                    matcher.reset();
                    String alertText = alert.alert;

                    int i = 0;
                    while (matcher.find()) {
                        for (int j = 0; j <= matcher.groupCount(); j++) {
                            alertText = alertText.replace("$$" + i + "$$", matcher.group(j));
                            i++;
                        }
                    }

                    Utils.createTitle(EnumChatFormatting.RED + alertText.replace("&", "§"), 2);
                    if (alert.desktop) Utils.desktopNotification("Alert", alertText, message, TrayIcon.MessageType.INFO);

                    return;
                }
            } else {
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
                    if (alert.desktop) Utils.desktopNotification("Alert", alert.alert, message, TrayIcon.MessageType.INFO);

                    return;
                }
            }
        }
    }

    public static void save() {
        for (Alert alert : alerts) {
            if (alert.mode.equals("Regex")) {
                Pattern pattern = Pattern.compile(alert.message);
                patterns.put(alert, pattern);
            }
        }

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
