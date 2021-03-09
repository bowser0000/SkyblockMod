package me.Danker.features.puzzlesolvers;

import me.Danker.commands.ToggleCommand;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class ClickInOrderSolver {

    static Slot[] clickInOrderSlots = new Slot[36];
    static int[] terminalNumberNeeded = new int[4];
    public static int CLICK_IN_ORDER_NEXT;
    public static int CLICK_IN_ORDER_NEXT_TO_NEXT;

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onTooltipLow(ItemTooltipEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.toolTip == null) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        if (mc.currentScreen instanceof GuiChest) {
            ContainerChest chest = (ContainerChest) player.openContainer;
            IInventory inv = chest.getLowerChestInventory();
            String chestName = inv.getDisplayName().getUnformattedText();

            if (ToggleCommand.clickInOrderToggled && chestName.equals("Click in order!")) {
                event.toolTip.clear();
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (mc.currentScreen instanceof GuiChest) {
            if (player == null) return;
            ContainerChest chest = (ContainerChest) player.openContainer;
            List<Slot> invSlots = ((GuiChest) mc.currentScreen).inventorySlots.inventorySlots;
            String chestName = chest.getLowerChestInventory().getDisplayName().getUnformattedText().trim();

            if (ToggleCommand.clickInOrderToggled && chestName.equals("Click in order!")) {
                if (terminalNumberNeeded[0] == 0) terminalNumberNeeded[0] = 15;
                if (terminalNumberNeeded[2] == 0) terminalNumberNeeded[2] = 15;
                for (int i = 10; i <= 25; i++) {
                    if (i == 17 || i == 18) continue;
                    ItemStack prevStack = invSlots.get(terminalNumberNeeded[1]).getStack();
                    if (prevStack == null) terminalNumberNeeded[0] = 15;
                    else if (prevStack.getItem() != Item.getItemFromBlock(Blocks.stained_glass_pane))
                        terminalNumberNeeded[0] = 15;
                    else if (prevStack.getItemDamage() == 5) terminalNumberNeeded[0] = 15;

                    ItemStack itemStack = invSlots.get(i).getStack();
                    if (itemStack == null) continue;
                    if (itemStack.getItem() != Item.getItemFromBlock(Blocks.stained_glass_pane)) continue;
                    if (itemStack.getItemDamage() != 14) continue;
                    if (itemStack.stackSize < terminalNumberNeeded[0]) {
                        terminalNumberNeeded[0] = itemStack.stackSize;
                        terminalNumberNeeded[1] = i;
                    } else if (itemStack.stackSize == terminalNumberNeeded[0] + 1) {
                        terminalNumberNeeded[2] = itemStack.stackSize;
                        terminalNumberNeeded[3] = i;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        if (ToggleCommand.clickInOrderToggled && event.displayName.equals("Click in order!")) {
            int chestSize = event.chestSize;
            List<Slot> invSlots = event.slots;
            Slot slot = invSlots.get(terminalNumberNeeded[1]);
            Utils.drawOnSlot(chestSize, slot.xDisplayPosition, slot.yDisplayPosition, CLICK_IN_ORDER_NEXT + 0xFF000000);
            Slot nextSlot = invSlots.get(terminalNumberNeeded[3]);
            if (nextSlot != slot && nextSlot.getSlotIndex() != 0) {
                Utils.drawOnSlot(chestSize, nextSlot.xDisplayPosition, nextSlot.yDisplayPosition, CLICK_IN_ORDER_NEXT_TO_NEXT + 0xFF000000);
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        terminalNumberNeeded = new int[4];
    }

}
