package me.Danker.features;

import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.annotations.Number;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.text.NumberFormat;
import java.util.Locale;

public class Skill50Display {

    public static int skillTimer = -1;
    public static boolean showSkill = false;
    public static String skillText = "";

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock || event.type != 2) return;

        String[] actionBarSections = event.message.getUnformattedText().split(" {3,}");

        for (String section : actionBarSections) {
            if (ModConfig.maxSkillHud.isEnabled() && section.contains("+") && section.contains("(") && section.contains(")") && !section.contains("Runecrafting")) {
                if (section.contains("/")) {
                    String xpGained = section.substring(section.indexOf("+"), section.indexOf("(") - 1);

                    double currentXp;
                    try {
                        currentXp = Double.parseDouble(section.substring(section.indexOf("(") + 1, section.indexOf("/")).replace(",", ""));
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        return;
                    }

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
                    skillTimer = MaxSkillHud.maxSkillTime * 20;
                    showSkill = true;
                    skillText = ModConfig.getColour(MaxSkillHud.maxSkillDisplayColour) + xpGained + " (" + nf.format(currentXp + previousXp) + "/" + nf.format(totalXp) + ") " + percentage + "%";
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
                    skillTimer = MaxSkillHud.maxSkillTime * 20;
                    showSkill = true;
                    skillText = ModConfig.getColour(MaxSkillHud.maxSkillDisplayColour) + xpGained + " (" + nf.format(currentXp) + "/" + nf.format(totalXp) + ") " + percentageTo50 + "%";
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

    public static class MaxSkillHud extends Hud {

        @Exclude
        String exampleText = ModConfig.getColour(maxSkillDisplayColour) + "+3.5 Farming (28,882,117.7/55,172,425) 52.34%";

        @Dropdown(
                name = "Progress to Max Skill Text Color",
                options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"}
        )
        public static int maxSkillDisplayColour = 11;

        @Number(
                name = "Length to Display Progress (seconds)",
                description = "The amount of time to display progress to max skill level in seconds.",
                min = 1, max = 3600
        )
        public static int maxSkillTime = 3;

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            if (example) {
                TextRenderer.drawHUDText(exampleText, x, y, scale);
                return;
            }

            if (enabled) {
                if (!Utils.skillsInitialized() && Utils.inSkyblock) {
                    TextRenderer.drawHUDText(EnumChatFormatting.RED + "Please open the skill menu to use skill features. (/skills)", x, y, scale);
                    return;
                }

                if (showSkill) {
                    TextRenderer.drawHUDText(skillText, x, y, scale);
                }
            }
        }

        @Override
        protected float getWidth(float scale, boolean example) {
            return RenderUtils.getWidthFromText(example ? exampleText : skillText) * scale;
        }

        @Override
        protected float getHeight(float scale, boolean example) {
            return RenderUtils.getHeightFromText(example ? exampleText : skillText) * scale;
        }

    }

}
