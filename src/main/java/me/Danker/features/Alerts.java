package me.Danker.features;

import com.google.gson.GsonBuilder;
import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Alerts {

    public static List<Alert> alerts = new ArrayList<>();
    public static String configFile;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!ToggleCommand.alerts || !Utils.inSkyblock || event.type == 2) return;

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        for (Alert alert : alerts) {
            if (!alert.toggled) continue;

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
        public String message;
        public String alert;
        public boolean toggled;

        public Alert(String mode, String message, String alert, boolean toggled) {
            this.mode = mode;
            this.message = message;
            this.alert = alert;
            this.toggled = toggled;
        }

        public void toggle() {
            toggled = !toggled;
        }

    }

}
