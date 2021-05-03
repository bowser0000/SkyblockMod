package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColouredNames {

    public static List<String> users = new ArrayList<>();

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!ToggleCommand.customColouredNames || !Utils.inSkyblock || event.type != 0) return;
        long startTime = System.currentTimeMillis();

        for (String user : users) {
            if (event.message.getFormattedText().contains(user)) {
                event.message = replaceChat(event.message, user);
            }
        }
        System.out.println("Chat time: " + ((System.currentTimeMillis() - startTime) / 1000D) + "s");
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (!ToggleCommand.customColouredNames || !Utils.inSkyblock) return;

        for (String user : users) {
            for (int i = 0; i < event.toolTip.size(); i++) {
                if (event.toolTip.get(i).contains(user)) {
                    event.toolTip.set(i, replaceName(event.toolTip.get(i), user, getColourFromName(user)));
                }
            }
        }
    }

    // https://github.com/SteveKunG/SkyBlockcatia/blob/1.8.9/src/main/java/com/stevekung/skyblockcatia/utils/SupporterUtils.java#L53
    public static String replaceName(String text, String name, String colour) {
        String namePattern = "(?:(?:\\u00a7[0-9a-fbr])\\B(?:" + name + ")\\b)|(?:\\u00a7[rb]" + name + "\\u00a7r)|\\b" + name + "\\b";
        Matcher prevColourMat = Pattern.compile("(?:.*(?:(?<colour>\\u00a7[0-9a-fbr])" + name + ")\\b.*)").matcher(text);

        if (prevColourMat.matches()) {
            return text.replaceAll(namePattern, colour + name + prevColourMat.group("colour"));
        }
        return text.replaceAll(namePattern, colour + name + EnumChatFormatting.WHITE);
    }

    // https://github.com/Moulberry/Hychat/blob/master/src/main/java/io/github/moulberry/hychat/util/TextProcessing.java#L23
    public static IChatComponent replaceChat(IChatComponent component, String user) {
        IChatComponent newComponent;
        ChatComponentText text = (ChatComponentText) component;

        newComponent = new ChatComponentText(replaceName(text.getUnformattedTextForChat(), user, getColourFromName(user)));
        newComponent.setChatStyle(text.getChatStyle().createShallowCopy());

        for (IChatComponent sibling : text.getSiblings()) {
            newComponent.appendSibling(replaceChat(sibling, user));
        }

        return newComponent;
    }

    public static String getColourFromName(String name) {
        return "§" + DankersSkyblockMod.data.get("colourednames").getAsJsonObject().get(name).getAsString();
    }

}
