package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoSwapToPickBlock {

    static int pickBlockBind;
    static boolean pickBlockBindSwapped = false;

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        GameSettings gameSettings = mc.gameSettings;
        if (event.gui instanceof GuiChest) {
            Container containerChest = ((GuiChest) event.gui).inventorySlots;
            if (containerChest instanceof ContainerChest) {
                IInventory inventory = ((ContainerChest) containerChest).getLowerChestInventory();
                String inventoryName = inventory.getDisplayName().getUnformattedText();

                if (ToggleCommand.swapToPickBlockToggled) {
                    if (inventoryName.startsWith("Chronomatron (") || inventoryName.startsWith("Superpairs (") || inventoryName.startsWith("Ultrasequencer (") || inventoryName.startsWith("What starts with:") || inventoryName.startsWith("Select all the") || inventoryName.startsWith("Navigate the maze!") || inventoryName.startsWith("Correct all the panes!") || inventoryName.startsWith("Click in order!") || inventoryName.startsWith("Harp -")) {
                        if (!pickBlockBindSwapped) {
                            pickBlockBind = gameSettings.keyBindPickBlock.getKeyCode();
                            gameSettings.keyBindPickBlock.setKeyCode(-100);
                            pickBlockBindSwapped = true;
                        }
                    } else {
                        if (pickBlockBindSwapped) {
                            gameSettings.keyBindPickBlock.setKeyCode(pickBlockBind);
                            pickBlockBindSwapped = false;
                        }
                    }
                }
            }
        } else {
            if (pickBlockBindSwapped) {
                gameSettings.keyBindPickBlock.setKeyCode(pickBlockBind);
                pickBlockBindSwapped = false;
            }
        }
    }

}
