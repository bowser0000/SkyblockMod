package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.RenderOverlayEvent;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.apache.commons.lang3.time.StopWatch;

import java.text.NumberFormat;
import java.util.Locale;

public class SkillTracker {

    static String lastSkill = "Farming";
    public static boolean showSkillTracker;
    public static StopWatch skillStopwatch = new StopWatch();
    static double farmingXP = 0;
    public static double farmingXPGained = 0;
    static boolean ignoreFarming = false;
    static double miningXP = 0;
    public static double miningXPGained = 0;
    static boolean ignoreMining = false;
    static double combatXP = 0;
    public static double combatXPGained = 0;
    static boolean ignoreCombat = false;
    static double foragingXP = 0;
    public static double foragingXPGained = 0;
    static boolean ignoreForaging = false;
    static double fishingXP = 0;
    public static double fishingXPGained = 0;
    static boolean ignoreFishing = false;
    static double enchantingXP = 0;
    public static double enchantingXPGained = 0;
    static boolean ignoreEnchanting = false;
    static double alchemyXP = 0;
    public static double alchemyXPGained = 0;
    static boolean ignoreAlchemy = false;
    static double xpLeft = 0;
    static double timeSinceGained = 0;
    public static String SKILL_TRACKER_COLOUR;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock || event.type != 2) return;

        String[] actionBarSections = event.message.getUnformattedText().split(" {3,}");

        for (String section : actionBarSections) {
            if (section.contains("+") && section.contains("(") && section.contains(")") && !section.contains("Runecrafting") && !section.contains("Carpentry")) {
                if (ToggleCommand.autoSkillTrackerToggled && System.currentTimeMillis() / 1000 - timeSinceGained <= 2) {
                    if (skillStopwatch.isStarted() && skillStopwatch.isSuspended()) {
                        skillStopwatch.resume();
                    } else if (!skillStopwatch.isStarted()) {
                        skillStopwatch.start();
                    }
                }
                timeSinceGained = System.currentTimeMillis() / 1000;

                String skill = section.substring(section.indexOf(" ") + 1, section.lastIndexOf(" "));
                double totalXP;

                if (section.contains("/")) {
                    int limit = section.contains("Farming") || section.contains("Enchanting") || section.contains("Mining") || section.contains("Combat") ? 60 : 50;
                    double currentXP = Double.parseDouble(section.substring(section.indexOf("(") + 1, section.indexOf("/")).replace(",", ""));

                    int xpToLevelUp;
                    String nextLevelXpString = section.substring(section.indexOf("/") + 1, section.indexOf(")")).replaceAll(",", "");
                    if (nextLevelXpString.contains("k")) {
                        xpToLevelUp = (int) (Double.parseDouble(nextLevelXpString.substring(0, nextLevelXpString.indexOf("k"))) * 1000);
                    } else {
                        xpToLevelUp = Integer.parseInt(nextLevelXpString);
                    }

                    xpLeft = xpToLevelUp - currentXP;
                    int previousXP = Utils.getPastXpEarned(xpToLevelUp, limit);
                    totalXP = currentXP + previousXP;
                } else {
                    if (!Utils.skillsInitialized()) {
                        return;
                    }

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

                    totalXP = Utils.getTotalXpEarned(level, Double.parseDouble(section.substring(section.indexOf("(") + 1, section.indexOf("%"))));
                    xpLeft = Utils.getTotalXpEarned(level + 1, 0) - totalXP;
                }

                double add;
                switch (skill) {
                    case "Farming":
                        lastSkill = "Farming";
                        if (ignoreFarming) {
                            ignoreFarming = false;
                            return;
                        }
                        add = addXP(totalXP, farmingXP);
                        if (add < 0) {
                            ignoreFarming = true;
                            return;
                        }
                        farmingXPGained += add;
                        farmingXP = totalXP;
                        break;
                    case "Mining":
                        lastSkill = "Mining";
                        if (ignoreMining) {
                            ignoreMining = false;
                            return;
                        }
                        add = addXP(totalXP, miningXP);
                        if (add < 0) {
                            ignoreMining = true;
                            return;
                        }
                        miningXPGained += add;
                        miningXP = totalXP;
                        break;
                    case "Combat":
                        lastSkill = "Combat";
                        if (ignoreCombat) {
                            ignoreCombat = false;
                            return;
                        }
                        add = addXP(totalXP, combatXP);
                        if (add < 0) {
                            ignoreCombat = true;
                            return;
                        }
                        combatXPGained += add;
                        combatXP = totalXP;
                        break;
                    case "Foraging":
                        lastSkill = "Foraging";
                        if (ignoreForaging) {
                            ignoreForaging = false;
                            return;
                        }
                        add = addXP(totalXP, foragingXP);
                        if (add < 0) {
                            ignoreForaging = true;
                            return;
                        }
                        foragingXPGained += add;
                        foragingXP = totalXP;
                        break;
                    case "Fishing":
                        lastSkill = "Fishing";
                        if (ignoreFishing) {
                            ignoreFishing = false;
                            return;
                        }
                        add = addXP(totalXP, fishingXP);
                        if (add < 0) {
                            ignoreFishing = true;
                            return;
                        }
                        fishingXPGained += add;
                        fishingXP = totalXP;
                        break;
                    case "Enchanting":
                        lastSkill = "Enchanting";
                        if (ignoreEnchanting) {
                            ignoreEnchanting = false;
                            return;
                        }
                        add = addXP(totalXP, enchantingXP);
                        if (add < 0) {
                            ignoreEnchanting = true;
                            return;
                        }
                        enchantingXPGained += add;
                        enchantingXP = totalXP;
                        break;
                    case "Alchemy":
                        lastSkill = "Alchemy";
                        if (ignoreAlchemy) {
                            ignoreAlchemy = false;
                            return;
                        }
                        add = addXP(totalXP, alchemyXP);
                        if (add < 0) {
                            ignoreAlchemy = true;
                            return;
                        }
                        alchemyXPGained += add;
                        alchemyXP = totalXP;
                        break;
                    default:
                        System.err.println("Unknown skill.");
                }
            }
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlayEvent event) {
        if (showSkillTracker && Utils.inSkyblock) {
            if (!Utils.skillsInitialized()) {
                new TextRenderer(Minecraft.getMinecraft(), EnumChatFormatting.RED + "Please open the skill menu to use skill features. (/skills)", MoveCommand.skillTrackerXY[0], MoveCommand.skillTrackerXY[1], ScaleCommand.skillTrackerScale);
                return;
            }

            int xpPerHour;
            double xpToShow = 0;
            switch (lastSkill) {
                case "Farming":
                    xpToShow = farmingXPGained;
                    break;
                case "Mining":
                    xpToShow = miningXPGained;
                    break;
                case "Combat":
                    xpToShow = combatXPGained;
                    break;
                case "Foraging":
                    xpToShow = foragingXPGained;
                    break;
                case "Fishing":
                    xpToShow = fishingXPGained;
                    break;
                case "Enchanting":
                    xpToShow = enchantingXPGained;
                    break;
                case "Alchemy":
                    xpToShow = alchemyXPGained;
                    break;
                default:
                    System.err.println("Unknown skill in rendering.");
            }
            xpPerHour = (int) Math.round(xpToShow / ((skillStopwatch.getTime() + 1) / 3600000d));
            String skillTrackerText = SKILL_TRACKER_COLOUR + lastSkill + " XP Earned: " + NumberFormat.getNumberInstance(Locale.US).format(xpToShow) + "\n" +
                    SKILL_TRACKER_COLOUR + "Time Elapsed: " + Utils.getTimeBetween(0, skillStopwatch.getTime() / 1000d) + "\n" +
                    SKILL_TRACKER_COLOUR + "XP Per Hour: " + NumberFormat.getIntegerInstance(Locale.US).format(xpPerHour);
            if (xpLeft >= 0) {
                String time = xpPerHour == 0 ? "Never" : Utils.getTimeBetween(0, xpLeft / (xpPerHour / 3600D));
                skillTrackerText += "\n" + SKILL_TRACKER_COLOUR + "Time Until Next Level: " + time;
            }
            if (!skillStopwatch.isStarted() || skillStopwatch.isSuspended()) {
                skillTrackerText += "\n" + EnumChatFormatting.RED + "PAUSED";
            }

            new TextRenderer(Minecraft.getMinecraft(), skillTrackerText, MoveCommand.skillTrackerXY[0], MoveCommand.skillTrackerXY[1], ScaleCommand.skillTrackerScale);
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (!Utils.inSkyblock) return;

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (DankersSkyblockMod.keyBindings[2].isPressed()) {
            if (skillStopwatch.isStarted() && skillStopwatch.isSuspended()) {
                skillStopwatch.resume();
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill tracker started."));
            } else if (!skillStopwatch.isStarted()) {
                skillStopwatch.start();
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill tracker started."));
            } else if (skillStopwatch.isStarted() && !skillStopwatch.isSuspended()) {
                skillStopwatch.suspend();
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill tracker paused."));
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.gui instanceof GuiChest && ToggleCommand.autoSkillTrackerToggled && skillStopwatch.isStarted() && !skillStopwatch.isSuspended()) {
            skillStopwatch.suspend();
        }
    }

    static double addXP(double totalXP, double skillXP) {
        if (skillXP != 0) {
            if (skillStopwatch.isStarted() && !skillStopwatch.isSuspended()) {
                if (totalXP > skillXP) {
                    return totalXP - skillXP;
                } else {
                    return -1;
                }
            }
        }
        return 0;
    }

}
