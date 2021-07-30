package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoAcceptReparty {

    String partyLeader = null;
    long lastDisband = 0;

    @SubscribeEvent(receiveCanceled = true)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (ToggleCommand.autoAcceptReparty) {
            String[] split = message.split("\\s");

            if (message.contains("has disbanded the party!")) {
                lastDisband = System.currentTimeMillis() / 1000;
                partyLeader = split[0].contains("[") ? split[1] : split[0];
            } else if (message.contains("has invited you to join their party!")) {
                String inviter = split[1].contains("[") ? split[2] : split[1];
                if (inviter.equals(partyLeader) && System.currentTimeMillis() / 1000 - lastDisband <= 120) {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/party accept " + partyLeader);
                }
            }
        }
    }

}
