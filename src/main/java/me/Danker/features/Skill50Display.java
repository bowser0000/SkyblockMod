package me.Danker.features;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.RenderOverlay;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.text.NumberFormat;
import java.util.Locale;

public class Skill50Display {

    public static int SKILL_TIME;
    public static int skillTimer = -1;
    public static boolean showSkill = false;
    public static String skillText = "";
    public static String SKILL_50_COLOUR;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock || event.type != 2) return;

        String[] actionBarSections = event.message.getUnformattedText().split(" {3,}");

        for (String section : actionBarSections) {
            if (section.contains("+") && section.contains("/") && section.contains("(")) {
                if (ToggleCommand.skill50DisplayToggled && !section.contains("Runecrafting")) {
                    String xpGained = section.substring(section.indexOf("+"), section.indexOf("(") - 1);
                    double currentXp = Double.parseDouble(section.substring(section.indexOf("(") + 1, section.indexOf("/")).replace(",", ""));
                    int limit;
                    int totalXp;
                    if (section.contains("Farming") || section.contains("Enchanting") || section.contains("Mining") || section.contains("Combat")) {
                        limit = 60;
                        totalXp = 111672425;
                    } else {
                        limit = 50;
                        totalXp = 55172425;
                    }
                    int previousXp = Utils.getPastXpEarned(Integer.parseInt(section.substring(section.indexOf("/") + 1, section.indexOf(")")).replaceAll(",", "")), limit);
                    double percentage = Math.floor(((currentXp + previousXp) / totalXp) * 10000D) / 100D;

                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                    skillTimer = SKILL_TIME;
                    showSkill = true;
                    skillText = SKILL_50_COLOUR + xpGained + " (" + nf.format(currentXp + previousXp) + "/" + nf.format(totalXp) + ") " + percentage + "%";
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        if (skillTimer >= 0) {
            if (skillTimer == 0) {
                showSkill = false;
            }
            skillTimer--;
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event) {
        if (showSkill) {
            new TextRenderer(Minecraft.getMinecraft(), skillText, MoveCommand.skill50XY[0], MoveCommand.skill50XY[1], ScaleCommand.skill50Scale);
        }
    }

}
