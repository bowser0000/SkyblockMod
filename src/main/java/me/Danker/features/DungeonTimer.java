package me.Danker.features;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.RenderOverlay;
import me.Danker.handlers.TextRenderer;
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

        if (!Utils.inDungeons) return;

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

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event) {
        if (ToggleCommand.dungeonTimerToggled && Utils.inDungeons) {
            Minecraft mc = Minecraft.getMinecraft();
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
            new TextRenderer(mc, dungeonTimerText, MoveCommand.dungeonTimerXY[0], MoveCommand.dungeonTimerXY[1], ScaleCommand.dungeonTimerScale);
            new TextRenderer(mc, dungeonTimers, (int) (MoveCommand.dungeonTimerXY[0] + (80 * ScaleCommand.dungeonTimerScale)), MoveCommand.dungeonTimerXY[1], ScaleCommand.dungeonTimerScale);
        }
    }

}
