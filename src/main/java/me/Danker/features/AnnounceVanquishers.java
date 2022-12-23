package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.locations.Location;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AnnounceVanquishers {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.contains(":")) return;

        if (ModConfig.announceVanqs && Utils.currentLocation == Location.CRIMSON_ISLE) {
            if (message.contains("A Vanquisher is spawning nearby!")) {
                Minecraft mc = Minecraft.getMinecraft();
                EntityPlayerSP player = mc.thePlayer;
                GuiNewChat chat = mc.ingameGUI.getChatGUI();

                String vanq = "Vanquisher spawned! " + (int) player.posX + " " + (int) player.posY + " " + (int) player.posZ;
                player.sendChatMessage(vanq);
                chat.addToSentMessages(vanq);
            }
        }
    }
}
