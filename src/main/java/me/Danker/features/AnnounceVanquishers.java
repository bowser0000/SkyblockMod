package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AnnounceVanquishers {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.contains(":")) return;

        if (ToggleCommand.announceVanqs && Utils.tabLocation.equals("Crimson Isle")) {
            if (message.contains("A Vanquisher is spawning nearby!")) {
                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
                player.sendChatMessage("Vanquisher spawned! " + (int) player.posX + " " + (int) player.posY + " " + (int) player.posZ);
            }
        }
    }
}
