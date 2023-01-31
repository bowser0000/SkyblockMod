package me.Danker.features;

import com.google.gson.GsonBuilder;
import me.Danker.config.ModConfig;
import me.Danker.events.ModInitEvent;
import me.Danker.events.PacketWriteEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatAliases {

    public static List<Alias> aliases = new ArrayList<>();
    public static String configFile;

    @SubscribeEvent
    public void init(ModInitEvent event) {
        configFile = event.configDirectory + "/dsm/dsmaliases.json";
    }

    @SubscribeEvent
    public void onPacketWrite(PacketWriteEvent event) {
        if (ModConfig.aliases) {
            if (event.packet instanceof C01PacketChatMessage) {
                C01PacketChatMessage packet = (C01PacketChatMessage) event.packet;
                String message = packet.getMessage();

                for (Alias alias : aliases) {
                    if (!alias.toggled) continue;
                    if (!alias.allowInCommand && message.charAt(0) == '/') continue;
                    message = message.replace(alias.text, alias.alias);
                }

                if (!packet.getMessage().equals(message)) {
                    event.setCanceled(true);
                    Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
                }
            }
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(configFile)) {
            new GsonBuilder().create().toJson(aliases, writer);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static class Alias {

        public String text;
        public String alias;
        public boolean toggled;
        public boolean allowInCommand;

        public Alias(String text, String alias, boolean toggled, boolean allowInCommand) {
            this.text = text;
            this.alias = alias;
            this.toggled = toggled;
            this.allowInCommand = allowInCommand;
        }

        public void toggle() {
            toggled = !toggled;
        }

        public void toggleInCommand() {
            allowInCommand = !allowInCommand;
        }

    }

}
