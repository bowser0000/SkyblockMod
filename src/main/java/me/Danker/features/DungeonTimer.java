package me.Danker.features;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DungeonTimer {

    static double dungeonStartTime = 0;
    static double bloodOpenTime = 0;
    static double watcherClearTime = 0;
    static double bossClearTime = 0;
    static int witherDoors = 0;
    static int dungeonDeaths = 0;
    static int puzzleFails = 0;

    @SubscribeEvent(receiveCanceled = true)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.isInDungeons()) return;

        if (message.contains("[BOSS] The Watcher: You have proven yourself. You may pass.")) {
            watcherClearTime = System.currentTimeMillis() / 1000;
        } else if (message.contains("PUZZLE FAIL! ") || message.contains("chose the wrong answer! I shall never forget this moment")) {
            puzzleFails++;
        }

        if (message.contains(":")) return;

        if (message.contains("Dungeon starts in 1 second.")) { // Dungeons Stuff
            dungeonStartTime = System.currentTimeMillis() / 1000 + 1;
            bloodOpenTime = dungeonStartTime;
            watcherClearTime = dungeonStartTime;
            bossClearTime = dungeonStartTime;
            witherDoors = 0;
            dungeonDeaths = 0;
            puzzleFails = 0;
        } else if (message.contains("The BLOOD DOOR has been opened!")) {
            bloodOpenTime = System.currentTimeMillis() / 1000;
        } else if (message.contains(" opened a WITHER door!")) {
            witherDoors++;
        } else if (message.contains(" and became a ghost.")) {
            dungeonDeaths++;
        } else if (message.contains(" Defeated ") && message.contains(" in ")) {
            bossClearTime = System.currentTimeMillis() / 1000;
        }
    }

    public static class DungeonTimerHud extends Hud {

        @Exclude
        String exampleText = EnumChatFormatting.GRAY + "Wither Doors:\n" +
                             EnumChatFormatting.DARK_RED + "Blood Open:\n" +
                             EnumChatFormatting.RED + "Watcher Clear:\n" +
                             EnumChatFormatting.BLUE + "Boss Clear:\n" +
                             EnumChatFormatting.YELLOW + "Deaths:\n" +
                             EnumChatFormatting.YELLOW + "Puzzle Fails:";

        @Exclude
        String exampleNums = EnumChatFormatting.GRAY + "" + 5 + "\n" +
                             EnumChatFormatting.DARK_RED + Utils.getTimeBetween(0, 33) + "\n" +
                             EnumChatFormatting.RED + Utils.getTimeBetween(0, 129) + "\n" +
                             EnumChatFormatting.BLUE + Utils.getTimeBetween(0, 169) + "\n" +
                             EnumChatFormatting.YELLOW + 2 + "\n" +
                             EnumChatFormatting.YELLOW + 1;

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            Minecraft mc = Minecraft.getMinecraft();

            if (example) {
                TextRenderer.drawHUDText(exampleText, x, y, scale);
                TextRenderer.drawHUDText(exampleNums, (int) (x + (80 * scale)), y, scale);
                return;
            }

            if (enabled && Utils.isInDungeons()) {
                String dungeonTimerText = EnumChatFormatting.GRAY + "Wither Doors:\n" +
                                          EnumChatFormatting.DARK_RED + "Blood Open:\n" +
                                          EnumChatFormatting.RED + "Watcher Clear:\n" +
                                          EnumChatFormatting.BLUE + "Boss Clear:\n" +
                                          EnumChatFormatting.YELLOW + "Deaths:\n" +
                                          EnumChatFormatting.YELLOW + "Puzzle Fails:";
                String dungeonTimers = EnumChatFormatting.GRAY + "" + witherDoors + "\n" +
                                       EnumChatFormatting.DARK_RED + Utils.getTimeBetween(dungeonStartTime, bloodOpenTime) + "\n" +
                                       EnumChatFormatting.RED + Utils.getTimeBetween(dungeonStartTime, watcherClearTime) + "\n" +
                                       EnumChatFormatting.BLUE + Utils.getTimeBetween(dungeonStartTime, bossClearTime) + "\n" +
                                       EnumChatFormatting.YELLOW + dungeonDeaths + "\n" +
                                       EnumChatFormatting.YELLOW + puzzleFails;

                TextRenderer.drawHUDText(dungeonTimerText, x, y, scale);
                TextRenderer.drawHUDText(dungeonTimers, (int) (x + (80 * scale)), y, scale);
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
