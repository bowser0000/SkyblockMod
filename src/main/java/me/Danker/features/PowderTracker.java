package me.Danker.features;

import cc.polyfrost.oneconfig.config.annotations.Button;
import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.handlers.TextRenderer;
import me.Danker.locations.Location;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
            if (Utils.currentLocation == Location.DWARVEN_MINES || Utils.currentLocation == Location.CRYSTAL_HOLLOWS) {
                if (Minecraft.getMinecraft().getNetHandler() == null) return;
                Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
                for (NetworkPlayerInfo player : players) {
                    if (player == null || player.getDisplayName() == null) continue;
                    String text = player.getDisplayName().getUnformattedText();
                    if (text.startsWith(" Mithril Powder:")) {
                        int mithril = Integer.parseInt(text.replaceAll("\\D", ""));
                        if (powderStopwatch.isStarted() && !powderStopwatch.isSuspended() && lastMithril != -1 && mithril > lastMithril) mithrilGained += mithril - lastMithril;
                        lastMithril = mithril;
                    } else if (text.startsWith(" Gemstone Powder:")) {
                        int gemstone = Integer.parseInt(text.replaceAll("\\D", ""));
                        if (powderStopwatch.isStarted() && !powderStopwatch.isSuspended() && lastGemstone != -1 && gemstone > lastGemstone) gemstoneGained += gemstone - lastGemstone;
                        lastGemstone = gemstone;
                    }
                }
            }
        }
    }

    public static void onKey() {
        if (!Utils.inSkyblock) return;

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
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

    public static void reset() {
        powderStopwatch = new StopWatch();
        lastMithril = -1;
        mithrilGained = 0;
        lastGemstone = -1;
        gemstoneGained = 0;
    }

    public static class PowderTrackerHud extends Hud {

        @Exclude
        String exampleText = EnumChatFormatting.DARK_GREEN + "Mithril Gained: 74,264\n" +
                             EnumChatFormatting.DARK_GREEN + "Mithril Per Hour: 107,326\n" +
                             EnumChatFormatting.LIGHT_PURPLE + "Gemstone Gained: 101,299\n" +
                             EnumChatFormatting.LIGHT_PURPLE + "Gemstone Per Hour: 146,397\n" +
                             ModConfig.getColour(powderTrackerColour) + "Time Elapsed: " + Utils.getTimeBetween(0, 2491);

        @Button(
                name = "Start Powder Tracker",
                text = "Start",
                category = "Trackers",
                subcategory = "Powder Tracker"
        )
        Runnable startPowderTracker = () -> {
            if (PowderTracker.powderStopwatch.isStarted() && PowderTracker.powderStopwatch.isSuspended()) {
                PowderTracker.powderStopwatch.resume();
            } else if (!PowderTracker.powderStopwatch.isStarted()) {
                PowderTracker.powderStopwatch.start();
            }
        };

        @Button(
                name = "Stop Powder Tracker",
                text = "Stop",
                category = "Trackers",
                subcategory = "Powder Tracker"
        )
        Runnable stopPowderTracker = () -> {
            if (PowderTracker.powderStopwatch.isStarted() && !PowderTracker.powderStopwatch.isSuspended()) {
                PowderTracker.powderStopwatch.suspend();
            }
        };

        @Dropdown(
                name = "Powder Tracker Text Color",
                options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"},
                category = "Trackers",
                subcategory = "Powder Tracker"
        )
        public static int powderTrackerColour = 11;

        @Button(
                name = "Reset Powder Tracker",
                text = "Reset",
                category = "Trackers",
                subcategory = "Powder Tracker"
        )
        Runnable resetPowderTracker = PowderTracker::reset;

        @Switch(
                name = "Only show in CH and DM",
                description = "Only display if you're in the Crystal Hollows or Dwarven Mines.",
                category = "Trackers",
                subcategory = "Powder Tracker"
        )
        public static boolean onlyInArea = true;

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            if (example) {
                TextRenderer.drawHUDText(exampleText, x, y, scale);
                return;
            }

            if (enabled && Utils.inSkyblock) {
                if (!onlyInArea || (Utils.currentLocation == Location.DWARVEN_MINES || Utils.currentLocation == Location.CRYSTAL_HOLLOWS)) {
                    TextRenderer.drawHUDText(getText(), x, y, scale);
                }
            }
        }

        @Override
        protected float getWidth(float scale, boolean example) {
            return RenderUtils.getWidthFromText(example ? exampleText : getText()) * scale;
        }

        @Override
        protected float getHeight(float scale, boolean example) {
            return RenderUtils.getHeightFromText(example ? exampleText : getText()) * scale;
        }

        String getText() {
            int mithrilPerHour = (int) Math.round(mithrilGained / ((powderStopwatch.getTime() + 1) / 3600000d));
            int gemstonePerHour = (int) Math.round(gemstoneGained / ((powderStopwatch.getTime() + 1) / 3600000d));

            NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
            String powderTrackerText = EnumChatFormatting.DARK_GREEN + "Mithril Gained: " + nf.format(mithrilGained) + "\n" +
                                       EnumChatFormatting.DARK_GREEN + "Mithril Per Hour: " + nf.format(mithrilPerHour) + "\n" +
                                       EnumChatFormatting.LIGHT_PURPLE + "Gemstone Gained: " + nf.format(gemstoneGained) + "\n" +
                                       EnumChatFormatting.LIGHT_PURPLE + "Gemstone Per Hour: " + nf.format(gemstonePerHour) + "\n" +
                                       ModConfig.getColour(powderTrackerColour) + "Time Elapsed: " + Utils.getTimeBetween(0, powderStopwatch.getTime() / 1000d);
            if (!powderStopwatch.isStarted() || powderStopwatch.isSuspended()) {
                powderTrackerText += "\n" + EnumChatFormatting.RED + "PAUSED";
            }

            return powderTrackerText;
        }

    }

}
