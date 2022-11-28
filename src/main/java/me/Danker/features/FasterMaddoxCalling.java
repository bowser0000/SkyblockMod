package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

import java.util.List;

public class FasterMaddoxCalling {

    static String lastMaddoxCommand = "/cb placeholder";
    static double lastMaddoxTime = 0;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (message.contains(":")) return;

        if (message.contains("[OPEN MENU]")) {
            List<IChatComponent> listOfSiblings = event.message.getSiblings();
            for (IChatComponent sibling : listOfSiblings) {
                if (sibling.getUnformattedText().contains("[OPEN MENU]")) {
                    lastMaddoxCommand = sibling.getChatStyle().getChatClickEvent().getValue();
                    lastMaddoxTime = System.currentTimeMillis() / 1000;
                }
            }
            if (ModConfig.chatMaddox) Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Open chat then click anywhere on-screen to open Maddox"));
        }
    }

    @SubscribeEvent
    public void onMouseInputPost(GuiScreenEvent.MouseInputEvent.Post event) {
        if (!Utils.inSkyblock) return;
        if (Mouse.getEventButton() == 0 && event.gui instanceof GuiChat) {
            if (ModConfig.chatMaddox && System.currentTimeMillis() / 1000 - lastMaddoxTime < 10) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(lastMaddoxCommand);
            }
        }
    }

    public static void onKey() {
        if (!Utils.inSkyblock) return;

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        player.sendChatMessage(lastMaddoxCommand);
    }

}
