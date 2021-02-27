package me.Danker.features.puzzlesolvers;

import me.Danker.commands.ToggleCommand;
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
        if (ToggleCommand.blockWrongTerminalClicksToggled && Utils.inDungeons) {
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
                case "Navigate the maze!":
                    if (item.getItem() != Item.getItemFromBlock(Blocks.stained_glass_pane)) {
                        shouldCancel = true;
                        break;
                    }

                    if (item.getItemDamage() != 0) {
                        shouldCancel = true;
                        break;
                    }

                    boolean isValid = false;

                    int slotIndex = slot.getSlotIndex();

                    if (slotIndex % 9 != 8 && slotIndex != 53) {
                        ItemStack itemStack = inventory.getStackInSlot(slotIndex + 1);
                        if (itemStack != null && itemStack.getItemDamage() == 5) isValid = true;
                    }

                    if (!isValid && slotIndex % 9 != 0 && slotIndex != 0) {
                        ItemStack itemStack = inventory.getStackInSlot(slotIndex - 1);
                        if (itemStack != null && itemStack.getItemDamage() == 5) isValid = true;
                    }

                    if (!isValid && slotIndex <= 44) {
                        ItemStack itemStack = inventory.getStackInSlot(slotIndex + 9);
                        if (itemStack != null && itemStack.getItemDamage() == 5) isValid = true;
                    }

                    if (!isValid && slotIndex >= 9) {
                        ItemStack itemStack = inventory.getStackInSlot(slotIndex - 9);
                        if (itemStack != null && itemStack.getItemDamage() == 5) isValid = true;
                    }

                    shouldCancel = !isValid;

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
                            (SelectAllColourSolver.terminalColorNeeded.equals("BROWN") && itemName.equals("COCOA BEANS")));
                }
            }

            event.setCanceled(shouldCancel && !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !Keyboard.isKeyDown(Keyboard.KEY_RCONTROL));
        }
    }

}
