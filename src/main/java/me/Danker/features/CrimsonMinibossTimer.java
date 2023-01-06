package me.Danker.features;

import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import me.Danker.config.ModConfig;
import me.Danker.handlers.TextRenderer;
import me.Danker.locations.Location;
import me.Danker.utils.RenderUtils;
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

        if (ModConfig.minibossTimerHud.isEnabled() && Utils.currentLocation == Location.CRIMSON_ISLE) {
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

    public static class MinibossTimerHud extends Hud {

        @Switch(
                name = "Miniboss Spawn Soon Alert",
                description = "Makes an alert noise when a miniboss is about to spawn soon."
        )
        public static boolean alert = true;

        @Exclude
        String exampleText = EnumChatFormatting.GRAY + "Bladesoul: " + ModConfig.getColour(minibossTimerColour) + "0m24s" + "\n" +
                             EnumChatFormatting.RED + "Barbarian Duke: " + ModConfig.getColour(minibossTimerColour) + "1m27s" + "\n" +
                             EnumChatFormatting.DARK_PURPLE + "Mage Outlaw: " + ModConfig.getColour(minibossTimerColour) + "2m0s" + "\n" +
                             EnumChatFormatting.GOLD + "Ashfang: " + ModConfig.getColour(minibossTimerUnknownColour) + "?" + "\n" +
                             EnumChatFormatting.DARK_RED + "Magma Boss: " + ModConfig.getColour(minibossTimerUnknownColour) + "?";

        @Dropdown(
                name = "Miniboss Timer Text Color",
                options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"}
        )
        public static int minibossTimerColour = 6;

        @Dropdown(
                name = "Miniboss Unknown Text Color",
                options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"}
        )
        public static int minibossTimerUnknownColour = 12;

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            if (example) {
                TextRenderer.drawHUDText(exampleText, x, y, scale);
                return;
            }

            if (enabled && Utils.currentLocation == Location.CRIMSON_ISLE) {
                TextRenderer.drawHUDText(getText(), x, y, scale);
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
            double timeNow = System.currentTimeMillis() / 1000;
            return EnumChatFormatting.GRAY + "Bladesoul: " + getTime(timeNow, bladesoul) + "\n" +
                   EnumChatFormatting.RED + "Barbarian Duke: " + getTime(timeNow, duke) + "\n" +
                   EnumChatFormatting.DARK_PURPLE + "Mage Outlaw: " + getTime(timeNow, outlaw) + "\n" +
                   EnumChatFormatting.GOLD + "Ashfang: " + getTime(timeNow, ashfang) + "\n" +
                   EnumChatFormatting.DARK_RED + "Magma Boss: " + getTime(timeNow, magma);
        }

        String getTime(double timeNow, double bossTime) {
            if (alert && timeNow == bossTime) Minecraft.getMinecraft().thePlayer.playSound(ModConfig.alertNoise, 1, (float) 0.5);
            if (timeNow < bossTime) {
                return ModConfig.getColour(minibossTimerColour) + Utils.getTimeBetween(timeNow, bossTime);
            }
            return ModConfig.getColour(minibossTimerUnknownColour) + "?";
        }

    }

}
