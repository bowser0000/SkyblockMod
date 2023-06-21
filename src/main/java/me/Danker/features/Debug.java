package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.events.PacketReadEvent;
import me.Danker.events.PacketWriteEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Debug {

    @SubscribeEvent
    public void onPacketRead(PacketReadEvent event) {
        if (!ModConfig.debug) return;

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if (ModConfig.debugPacketsIn) {
            String name = "Packet recieved: " + event.packet.getClass().getSimpleName();
            if (player != null) player.addChatMessage(new ChatComponentText(name));
            System.out.println(name);
        }
    }

    @SubscribeEvent
    public void onPacketWrite(PacketWriteEvent event) {
        if (!ModConfig.debug) return;

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if (ModConfig.debugPacketsOut) {
            String name = "Packet sent: " + event.packet.getClass().getSimpleName();
            if (player != null) player.addChatMessage(new ChatComponentText(name));
            System.out.println(name);
        }

        if (ModConfig.debugChat && event.packet instanceof C01PacketChatMessage) {
            C01PacketChatMessage packet = (C01PacketChatMessage) event.packet;

            String message = packet.getMessage();
            if (message.charAt(0) == '/') {
                message = "Command sent: " + message;
            } else {
                message = "Message sent: " + message;
            }

            if (player != null) player.addChatMessage(new ChatComponentText(message));
            System.out.println(message);
        }
    }

}
