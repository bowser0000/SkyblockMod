package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.config.ModConfig;
import me.Danker.events.RenderOverlayEvent;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.time.StopWatch;

import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

public class PowderTracker {

    public static StopWatch powderStopwatch = new StopWatch();
    static int lastMithril = -1;
    static int mithrilGained = 0;
    static int lastGemstone = -1;
    static int gemstoneGained = 0;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            if (Utils.tabLocation.equals("Dwarven Mines") || Utils.tabLocation.equals("Crystal Hollows")) {
                if (Minecraft.getMinecraft().getNetHandler() == null) return;
                Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
                for (NetworkPlayerInfo player : players) {
                    if (player == null || player.getDisplayName() == null) continue;
                    String text = player.getDisplayName().getUnformattedText();
                    if (text.startsWith(" Mithril Powder:")) {
                        int mithril = Integer.parseInt(text.replaceAll("[^\\d]", ""));
                        if (powderStopwatch.isStarted() && !powderStopwatch.isSuspended() && lastMithril != -1 && mithril > lastMithril) mithrilGained += mithril - lastMithril;
                        lastMithril = mithril;
                    } else if (text.startsWith(" Gemstone Powder:")) {
                        int gemstone = Integer.parseInt(text.replaceAll("[^\\d]", ""));
                        if (powderStopwatch.isStarted() && !powderStopwatch.isSuspended() && lastGemstone != -1 && gemstone > lastGemstone) gemstoneGained += gemstone - lastGemstone;
                        lastGemstone = gemstone;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlayEvent event) {
        if (ModConfig.showPowderTracker && Utils.inSkyblock) {
            int mithrilPerHour = (int) Math.round(mithrilGained / ((powderStopwatch.getTime() + 1) / 3600000d));
            int gemstonePerHour = (int) Math.round(gemstoneGained / ((powderStopwatch.getTime() + 1) / 3600000d));

            NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
            String powderTrackerText = EnumChatFormatting.DARK_GREEN + "Mithril Gained: " + nf.format(mithrilGained) + "\n" +
                    EnumChatFormatting.DARK_GREEN + "Mithril Per Hour: " + nf.format(mithrilPerHour) + "\n" +
                    EnumChatFormatting.LIGHT_PURPLE + "Gemstone Gained: " + nf.format(gemstoneGained) + "\n" +
                    EnumChatFormatting.LIGHT_PURPLE + "Gemstone Per Hour: " + nf.format(gemstonePerHour) + "\n" +
                    ModConfig.getColour(ModConfig.powderTrackerColour) + "Time Elapsed: " + Utils.getTimeBetween(0, powderStopwatch.getTime() / 1000d);
            if (!powderStopwatch.isStarted() || powderStopwatch.isSuspended()) {
                powderTrackerText += "\n" + EnumChatFormatting.RED + "PAUSED";
            }

            new TextRenderer(Minecraft.getMinecraft(), powderTrackerText, MoveCommand.powderTrackerXY[0], MoveCommand.powderTrackerXY[1], ScaleCommand.powderTrackerScale);
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (!Utils.inSkyblock) return;

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (DankersSkyblockMod.keyBindings[4].isPressed()) {
            if (powderStopwatch.isStarted() && powderStopwatch.isSuspended()) {
                powderStopwatch.resume();
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Powder tracker started."));
            } else if (!powderStopwatch.isStarted()) {
                powderStopwatch.start();
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Powder tracker started."));
            } else if (powderStopwatch.isStarted() && !powderStopwatch.isSuspended()) {
                powderStopwatch.suspend();
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Powder tracker paused."));
            }
        }
    }

    public static void reset() {
        powderStopwatch = new StopWatch();
        lastMithril = -1;
        mithrilGained = 0;
        lastGemstone = -1;
        gemstoneGained = 0;
    }

}
