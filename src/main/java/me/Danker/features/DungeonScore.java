package me.Danker.features;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.handlers.TextRenderer;
import me.Danker.locations.DungeonFloor;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Collection;

public class DungeonScore {

    static int failedPuzzles;
    static int deaths;
    static int skillScore;
    static String secrets;
    static int exploreScore;
    static int timeScore;
    static int bonusScore;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        failedPuzzles = 0;
        deaths = 0;
        skillScore = 100;
        secrets = "";
        exploreScore = 0;
        timeScore = 100;
        bonusScore = 0;
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!ModConfig.dungeonScoreHud.isEnabled() || !Utils.isInDungeons()) return;

        if (message.contains(":")) return;

        if (message.contains("PUZZLE FAIL! ") || message.contains("chose the wrong answer! I shall never forget this moment")) {
            failedPuzzles++;
        } else if (message.contains(" and became a ghost.")) {
            deaths++;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        if (!ModConfig.dungeonScoreHud.isEnabled() || !Utils.isInDungeons()) return;

        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            int missingPuzzles = 0;
            double openedRooms = 0;
            double completedRooms = 0;
            double roomScore = 0;
            double secretScore = 0;

            if (Minecraft.getMinecraft().getNetHandler() == null) return;
            Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
            for (NetworkPlayerInfo player : players) {
                if (player == null || player.getDisplayName() == null) continue;
                String display = player.getDisplayName().getUnformattedText();

                if (display.startsWith(" Opened Rooms: ")) {
                    openedRooms = Double.parseDouble(display.replaceAll("\\D", ""));
                } else if (display.startsWith(" Completed Rooms: ")) {
                    completedRooms = Double.parseDouble(display.replaceAll("\\D", ""));
                } else if (display.startsWith(" Secrets Found: ") && display.endsWith("%")) {
                    secrets = player.getDisplayName().getFormattedText();

                    double secretCount = Double.parseDouble(display.replaceAll("[^\\d.]", ""));

                    switch (Utils.currentFloor) {
                        case F1:
                            secretScore = secretCount / 30D;
                            break;
                        case F2:
                            secretScore = secretCount / 40D;
                            break;
                        case F3:
                            secretScore = secretCount / 50D;
                            break;
                        case F4:
                            secretScore = secretCount / 60D;
                            break;
                        case F5:
                            secretScore = secretCount / 70D;
                            break;
                        case F6:
                            secretScore = secretCount / 85D;
                            break;
                        default:
                            secretScore = secretCount / 100D;
                    }
                } else if (display.startsWith("Time Elapsed: ")) {
                    String timeText = display.substring(display.indexOf(":") + 2).replaceAll("\\s", "");
                    int minutes = Integer.parseInt(timeText.substring(0, timeText.indexOf("m")));
                    int seconds = Integer.parseInt(timeText.substring(timeText.indexOf("m") + 1, timeText.indexOf("s")));
                    int time = minutes * 60 + seconds;

                    if (Utils.currentFloor == DungeonFloor.F2) time -= 120;

                    int base;
                    switch (Utils.currentFloor) {
                        case F1:
                        case F2:
                        case F3:
                        case F5:
                            base = 600;
                            break;
                        case F4:
                        case F6:
                        case F7:
                            base = 720;
                            break;
                        default:
                            base = 480;
                    }

                    if (time <= base) {
                        timeScore = 100;
                    } else if (time <= base + 100) {
                        timeScore = (int) Math.ceil(100 - 0.1 * (time - base));
                    } else if (time <= base + 500) {
                        timeScore = (int) Math.ceil(90 - 0.05 * (time - base - 100));
                    } else if (time < base + 2600) {
                        timeScore = (int) Math.ceil(70 - (1/30D) * (time - base - 1100));
                    } else {
                        timeScore = 0;
                    }
                } else if (display.startsWith(" Crypts: ")) {
                    bonusScore = MathHelper.clamp_int(Integer.parseInt(display.replaceAll("\\D", "")), 0, 5);
                } else if (display.contains("[✦]")) {
                    missingPuzzles++;
                }
            }

            if (openedRooms != 0) {
                roomScore = completedRooms / openedRooms;
            }

            skillScore = MathHelper.clamp_int(100 - 14 * (failedPuzzles + missingPuzzles) - 2 * deaths, 0, 100);
            exploreScore = (int) (60 * roomScore + 40 * MathHelper.clamp_double(secretScore, 0, 1));
        }
    }

    public static class DungeonScoreHud extends Hud {

        @Exclude
        String exampleText = " Secrets Found: " + EnumChatFormatting.GREEN + "100.0%\n" +
                             EnumChatFormatting.GOLD + "Skill:\n" +
                             EnumChatFormatting.GOLD + "Explore:\n" +
                             EnumChatFormatting.GOLD + "Speed:\n" +
                             EnumChatFormatting.GOLD + "Bonus:\n" +
                             EnumChatFormatting.GOLD + "Total:";

        @Exclude
        String exampleNums = "\n" +
                             EnumChatFormatting.GOLD + "100\n" +
                             EnumChatFormatting.GOLD + "100\n" +
                             EnumChatFormatting.GOLD + "100\n" +
                             EnumChatFormatting.GOLD + "5\n" +
                             EnumChatFormatting.GOLD + "305 " + EnumChatFormatting.GRAY + "(" + EnumChatFormatting.GOLD + "S+" + EnumChatFormatting.GRAY + ")";

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            Minecraft mc = Minecraft.getMinecraft();

            if (example) {
                TextRenderer.drawHUDText(exampleText, x, y, scale);
                TextRenderer.drawHUDText(exampleNums, (int) (x + (80 * scale)), y, scale);
                return;
            }

            if (enabled && Utils.isInDungeons()) {
                int totalScore = skillScore + exploreScore + timeScore + bonusScore;
                String total;
                if (totalScore >= 300) {
                    total = EnumChatFormatting.GOLD + "S+";
                } else if (totalScore >= 270) {
                    total = EnumChatFormatting.GOLD + "S";
                } else if (totalScore >= 230) {
                    total = EnumChatFormatting.DARK_PURPLE + "A";
                } else if (totalScore >= 160) {
                    total = EnumChatFormatting.GREEN + "B";
                } else if (totalScore >= 100) {
                    total = EnumChatFormatting.BLUE + "C";
                } else {
                    total = EnumChatFormatting.RED + "D";
                }

                String scoreText = secrets + "\n" +
                                   EnumChatFormatting.GOLD + "Skill:\n" +
                                   EnumChatFormatting.GOLD + "Explore:\n" +
                                   EnumChatFormatting.GOLD + "Speed:\n" +
                                   EnumChatFormatting.GOLD + "Bonus:\n" +
                                   EnumChatFormatting.GOLD + "Total:";
                String score = "\n" +
                               EnumChatFormatting.GOLD + skillScore + "\n" +
                               EnumChatFormatting.GOLD + exploreScore + "\n" +
                               EnumChatFormatting.GOLD + timeScore + "\n" +
                               EnumChatFormatting.GOLD + bonusScore + "\n" +
                               EnumChatFormatting.GOLD + totalScore + EnumChatFormatting.GRAY + " (" + total + EnumChatFormatting.GRAY + ")";
                TextRenderer.drawHUDText(scoreText, x, y, scale);
                TextRenderer.drawHUDText(score, (int) (x + (80 * scale)), y, scale);
            }
        }

        @Override
        protected float getWidth(float scale, boolean example) {
            return (RenderUtils.getWidthFromText(exampleNums) + 80 * scale) * scale;
        }

        @Override
        protected float getHeight(float scale, boolean example) {
            return RenderUtils.getHeightFromText(exampleNums) * scale;
        }

    }

}
