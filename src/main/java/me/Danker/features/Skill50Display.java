package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.RenderOverlay;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
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
            if (ToggleCommand.skill50DisplayToggled && section.contains("+") && section.contains("(") && section.contains(")") && !section.contains("Runecrafting")) {
                if (section.contains("/")) {
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

                    int nextLevelXp;
                    String nextLevelXpString = section.substring(section.indexOf("/") + 1, section.indexOf(")")).replaceAll(",", "");
                    if (nextLevelXpString.contains("k")) {
                        nextLevelXp = (int) (Double.parseDouble(nextLevelXpString.substring(0, nextLevelXpString.indexOf("k"))) * 1000);
                    } else {
                        nextLevelXp = Integer.parseInt(nextLevelXpString);
                    }

                    int previousXp = Utils.getPastXpEarned(nextLevelXp, limit);
                    double percentage = Math.floor(((currentXp + previousXp) / totalXp) * 10000D) / 100D;

                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                    skillTimer = SKILL_TIME;
                    showSkill = true;
                    skillText = SKILL_50_COLOUR + xpGained + " (" + nf.format(currentXp + previousXp) + "/" + nf.format(totalXp) + ") " + percentage + "%";
                } else {
                    if (!Utils.skillsInitialized()) {
                        return;
                    }

                    String xpGained = section.substring(section.indexOf("+"), section.indexOf("(") - 1);
                    double percentage = Double.parseDouble(section.substring(section.indexOf("(") + 1, section.indexOf("%")));
                    int level = 1;
                    if (section.contains("Farming")) {
                        level = DankersSkyblockMod.farmingLevel;
                    } else if (section.contains("Mining")) {
                        level = DankersSkyblockMod.miningLevel;
                    } else if (section.contains("Combat")) {
                        level = DankersSkyblockMod.combatLevel;
                    } else if (section.contains("Foraging")) {
                        level = DankersSkyblockMod.foragingLevel;
                    } else if (section.contains("Fishing")) {
                        level = DankersSkyblockMod.fishingLevel;
                    } else if (section.contains("Enchanting")) {
                        level = DankersSkyblockMod.enchantingLevel;
                    } else if (section.contains("Alchemy")) {
                        level = DankersSkyblockMod.alchemyLevel;
                    } else if (section.contains("Carpentry")) {
                        level = DankersSkyblockMod.carpentryLevel;
                    }

                    double currentXp = Utils.getTotalXpEarned(level, percentage);
                    int totalXp = section.contains("Farming") || section.contains("Enchanting") || section.contains("Mining") || section.contains("Combat") ? 111672425 : 55172425;
                    double percentageTo50 = Math.floor((currentXp / totalXp) * 10000D) / 100D;

                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                    skillTimer = SKILL_TIME;
                    showSkill = true;
                    skillText = SKILL_50_COLOUR + xpGained + " (" + nf.format(currentXp) + "/" + nf.format(totalXp) + ") " + percentageTo50 + "%";
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
        if (!Utils.skillsInitialized()) {
            new TextRenderer(Minecraft.getMinecraft(), EnumChatFormatting.RED + "Please open the skill menu to use skill features.", MoveCommand.skill50XY[0], MoveCommand.skill50XY[0], ScaleCommand.skill50Scale);
            return;
        }

        if (showSkill) {
            new TextRenderer(Minecraft.getMinecraft(), skillText, MoveCommand.skill50XY[0], MoveCommand.skill50XY[1], ScaleCommand.skill50Scale);
        }
    }

}
