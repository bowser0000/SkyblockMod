package me.Danker.features;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.config.ModConfig;
import me.Danker.events.RenderOverlayEvent;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CrimsonMinibossTimer {

    static double bladesoul;
    static double duke;
    static double outlaw;
    static double magma;
    static double ashfang;

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        bladesoul = 0;
        duke = 0;
        outlaw = 0;
        magma = 0;
        ashfang = 0;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.contains(":")) return;

        if (ModConfig.crimsonMinibossTimer && Utils.tabLocation.equals("Crimson Isle")) {
            if (message.contains("BLADESOUL DOWN!")) {
                bladesoul = System.currentTimeMillis() / 1000 + 120;
            } else if (message.contains("BARBARIAN DUKE X DOWN!")) {
                duke = System.currentTimeMillis() / 1000 + 120;
            } else if (message.contains("MAGE OUTLAW DOWN!")) {
                outlaw = System.currentTimeMillis() / 1000 + 120;
            } else if (message.contains("ASHFANG DOWN!")) {
                ashfang = System.currentTimeMillis() / 1000 + 120;
            } else if (message.contains("MAGMA BOSS DOWN!")) {
                magma = System.currentTimeMillis() / 1000 + 120;
            }
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlayEvent event) {
        if (ModConfig.crimsonMinibossTimer && Utils.tabLocation.equals("Crimson Isle")) {
            double timeNow = System.currentTimeMillis() / 1000;

            String timerText = EnumChatFormatting.GRAY + "Bladesoul: " + getTime(timeNow, bladesoul) + "\n" +
                               EnumChatFormatting.RED + "Barbarian Duke: " + getTime(timeNow, duke) + "\n" +
                               EnumChatFormatting.DARK_PURPLE + "Mage Outlaw: " + getTime(timeNow, outlaw) + "\n" +
                               EnumChatFormatting.GOLD + "Ashfang: " + getTime(timeNow, ashfang) + "\n" +
                               EnumChatFormatting.DARK_RED + "Magma Boss: " + getTime(timeNow, magma);

            new TextRenderer(Minecraft.getMinecraft(), timerText, MoveCommand.minibossTimerXY[0], MoveCommand.minibossTimerXY[1], ScaleCommand.minibossTimerScale);
        }
    }

    static String getTime(double timeNow, double bossTime) {
        if (timeNow == bossTime) Minecraft.getMinecraft().thePlayer.playSound(ModConfig.alertNoise, 1, (float) 0.5);
        if (timeNow < bossTime) {
            return ModConfig.getColour(ModConfig.minibossTimerColour) + Utils.getTimeBetween(timeNow, bossTime);
        }
        return ModConfig.getColour(ModConfig.minibossTimerUnknownColour) + "?";
    }

}
