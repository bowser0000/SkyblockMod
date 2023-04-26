package me.Danker.features;

import cc.polyfrost.oneconfig.libs.universal.UResolution;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.events.ChestSlotClickedEvent;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class BazaarTimeToFill {

    static double lastInstaBuys = -1;
    static double lastInstaSells = -1;
    String textToDisplay;

    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        if (!ModConfig.bazaarTimeToFill) return;

        if (event.inventoryName.contains(" ➜ ") && event.item != null) {
            if (event.slot.slotNumber - (event.chest.inventorySlots.inventorySlots.size() - 36) >= 0) {
                // clicked item in inventory
                lastInstaBuys = -1;
                lastInstaSells = -1;
                return;
            }

            List<String> tooltip = event.item.getTooltip(Minecraft.getMinecraft().thePlayer, false);
            if (tooltip.size() >= 12) {
                for (String line : tooltip) {
                    if (line.endsWith("insta-buys in 7d")) {
                        lastInstaBuys = getAmountFromLine(line);
                    } else if (line.endsWith("insta-sells in 7d")) {
                        lastInstaSells = getAmountFromLine(line);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!ModConfig.bazaarTimeToFill || !Utils.inSkyblock) return;
        if (event.phase != TickEvent.Phase.START) return;
        if (DankersSkyblockMod.tickAmount % 10 != 0) return;

        textToDisplay = "";
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (mc.currentScreen instanceof GuiChest) {
            if (player == null) return;
            ContainerChest chest = (ContainerChest) player.openContainer;
            String chestName = chest.getLowerChestInventory().getDisplayName().getUnformattedText().trim();

            Slot theSlot = ((GuiChest) mc.currentScreen).inventorySlots.inventorySlots.get(10);
            if (theSlot == null || theSlot.getStack() == null) return;
            List<String> tooltip = theSlot.getStack().getTooltip(player, false);

            if (chestName.equals("How much do you want to pay?")) {
                for (String line : tooltip) {
                    String cleaned = StringUtils.stripControlCodes(line);
                    if (cleaned.startsWith("Ordering: ")) {
                        double amount = Double.parseDouble(cleaned.replaceAll("\\D", ""));
                        textToDisplay = getTextToDisplay(amount, lastInstaSells);
                        break;
                    }
                }
            } else if (chestName.equals("At what price are you selling?")) {
                for (String line : tooltip) {
                    String cleaned = StringUtils.stripControlCodes(line);
                    if (cleaned.startsWith("Selling: ")) {
                        double amount = Double.parseDouble(cleaned.replaceAll("\\D", ""));
                        textToDisplay = getTextToDisplay(amount, lastInstaBuys);
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        if (!ModConfig.bazaarTimeToFill) return;
        if (textToDisplay.length() == 0) return;

        int width = UResolution.getScaledWidth();
        int height = (UResolution.getScaledHeight() - 222) / 2;

        RenderUtils.drawCenteredText(EnumChatFormatting.BLUE + "Estimated time to fill: " + textToDisplay, width, height, 1D);
    }

    static double getAmountFromLine(String line) {
        try {
            String amount = StringUtils.stripControlCodes(line.substring(0, line.indexOf(" ")));
            double num = Double.parseDouble(amount.replaceAll("[^\\d.]", ""));

            switch (amount.charAt(amount.length() - 1)) {
                case 'k':
                case 'K':
                    return num * 1000D;
                case 'm':
                case 'M':
                    return num * 1000000D;
                case 'b':
                case 'B':
                    return num * 1000000000D;
                default:
                    return num;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    static String getTextToDisplay(double amount, double volume) {
        if (volume == -1) return "Unknown";
        if (volume == 0) return "Never";

        double volumePerSecond = volume / 604800D;
        return Utils.getTimeBetween(0, amount / volumePerSecond);
    }

}
