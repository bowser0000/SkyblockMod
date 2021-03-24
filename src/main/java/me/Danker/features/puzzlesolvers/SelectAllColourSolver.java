package me.Danker.features.puzzlesolvers;

import me.Danker.commands.ToggleCommand;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectAllColourSolver {

    static Pattern selectAllTerminalPattern = Pattern.compile("[A-Z]{2,}");
    static String terminalColorNeeded;

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        String displayName = event.displayName;
        if (ToggleCommand.selectAllToggled && Utils.inDungeons && displayName.startsWith("Select all the")) {
            String colour;
            List<String> colourParts = new ArrayList<>();
            Matcher colourMatcher = selectAllTerminalPattern.matcher(displayName);
            while (colourMatcher.find()) {
                colourParts.add(colourMatcher.group());
            }
            colour = String.join(" ", colourParts);
            terminalColorNeeded = colour;

            for (Slot slot : event.slots) {
                if (slot.inventory == Minecraft.getMinecraft().thePlayer.inventory) continue;
                ItemStack item = slot.getStack();
                if (item == null) continue;
                if (item.isItemEnchanted()) continue;
                String itemName = StringUtils.stripControlCodes(item.getDisplayName()).toUpperCase();
                if (itemName.contains(terminalColorNeeded) ||
                        (terminalColorNeeded.equals("SILVER") && itemName.contains("LIGHT GRAY")) ||
                        (terminalColorNeeded.equals("WHITE") && (itemName.equals("WOOL") || itemName.equals("BONE MEAL"))) ||
                        (terminalColorNeeded.equals("BLACK") && itemName.equals("INK SACK")) ||
                        (terminalColorNeeded.equals("BLUE") && itemName.equals("LAPIS LAZULI")) ||
                        (terminalColorNeeded.equals("BROWN") && itemName.equals("COCOA BEAN"))) {
                    Utils.drawOnSlot(event.chestSize, slot.xDisplayPosition, slot.yDisplayPosition, 0xBF40FF40);
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        terminalColorNeeded = null;
    }

}
