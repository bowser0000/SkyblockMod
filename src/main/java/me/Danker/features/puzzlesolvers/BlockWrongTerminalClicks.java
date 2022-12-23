package me.Danker.features.puzzlesolvers;

import me.Danker.config.ModConfig;
import me.Danker.events.ChestSlotClickedEvent;
import me.Danker.utils.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class BlockWrongTerminalClicks {

    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        if (ModConfig.blockWrongTerminalClicks && Utils.isInDungeons()) {
            IInventory inventory = event.inventory;
            String inventoryName = event.inventoryName;
            Slot slot = event.slot;
            ItemStack item = event.item;
            boolean shouldCancel = false;

            if (item == null) return;

            //most of these are extra but who cares

            switch (inventoryName) {
                case "Correct all the panes!":
                    shouldCancel = !StringUtils.stripControlCodes(item.getDisplayName()).startsWith("Off");
                    break;
                case "Click in order!":

                    if (slot.getSlotIndex() > 35) {
                        break;
                    }

                    if ((item.getItem() != Item.getItemFromBlock(Blocks.stained_glass_pane))) {
                        shouldCancel = true;
                        break;
                    }
                    if (item.getItemDamage() != 14) {
                        shouldCancel = true;
                        break;
                    }
                    int needed = ClickInOrderSolver.terminalNumberNeeded[0];
                    if (needed == 0) break;
                    shouldCancel = needed != -1 && item.stackSize != needed;
                    break;
                case "Change all to same color!":
                    if (SameColourSolver.foundColour) {
                        shouldCancel = SameColourSolver.getDistance(item.getItemDamage(), SameColourSolver.correctColour) == 0;
                    }
                    break;
                case "Click the button on time!":
                    int correctPos = -1;
                    for (int i = 0; i <= 50; i++) {
                        ItemStack stack = inventory.getStackInSlot(i);
                        if (stack == null || stack.getItem() != Item.getItemFromBlock(Blocks.stained_glass_pane)) continue;

                        if (stack.getItemDamage() == 10) {
                            correctPos = i % 9;
                        } else if (stack.getItemDamage() == 5 && correctPos != -1) {
                            shouldCancel = i % 9 != correctPos;
                            break;
                        }
                    }
                    break;
            }

            if (!shouldCancel) {
                if (inventoryName.startsWith("What starts with:")) {
                    char letter = inventoryName.charAt(inventoryName.indexOf("'") + 1);
                    shouldCancel = !(StringUtils.stripControlCodes(item.getDisplayName()).charAt(0) == letter);
                } else if (inventoryName.startsWith("Select all the")) {
                    if (SelectAllColourSolver.terminalColorNeeded == null) return;
                    String itemName = StringUtils.stripControlCodes(item.getDisplayName()).toUpperCase();
                    shouldCancel = !(itemName.contains(SelectAllColourSolver.terminalColorNeeded) ||
                            (SelectAllColourSolver.terminalColorNeeded.equals("SILVER") && itemName.contains("LIGHT GRAY")) ||
                            (SelectAllColourSolver.terminalColorNeeded.equals("WHITE") && (itemName.equals("WOOL") || itemName.equals("BONE MEAL"))) ||
                            (SelectAllColourSolver.terminalColorNeeded.equals("BLACK") && itemName.equals("INK SACK")) ||
                            (SelectAllColourSolver.terminalColorNeeded.equals("BLUE") && itemName.equals("LAPIS LAZULI")) ||
                            (SelectAllColourSolver.terminalColorNeeded.equals("BROWN") && itemName.equals("COCOA BEAN")));
                }
            }

            event.setCanceled(shouldCancel && !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !Keyboard.isKeyDown(Keyboard.KEY_RCONTROL));
        }
    }

}
