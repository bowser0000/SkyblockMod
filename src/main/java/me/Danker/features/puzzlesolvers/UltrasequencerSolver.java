package me.Danker.features.puzzlesolvers;

import me.Danker.config.ModConfig;
import me.Danker.events.ChestSlotClickedEvent;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class UltrasequencerSolver {

    static Slot[] clickInOrderSlots = new Slot[36];
    static int lastUltraSequencerClicked = 0;

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

            if (ModConfig.ultrasequencer && chestName.startsWith("Ultrasequencer (")) {
                if (invSlots.get(49).getStack() != null && invSlots.get(49).getStack().getDisplayName().equals("§aRemember the pattern!")) {
                    for (int i = 9; i <= 44; i++) {
                        if (invSlots.get(i) == null || invSlots.get(i).getStack() == null) continue;
                        String itemName = StringUtils.stripControlCodes(invSlots.get(i).getStack().getDisplayName());
                        if (itemName.matches("\\d+")) {
                            int number = Integer.parseInt(itemName);
                            clickInOrderSlots[number - 1] = invSlots.get(i);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        if (ModConfig.ultrasequencer && event.inventoryName.startsWith("Ultrasequencer (")) {
            IInventory inventory = event.inventory;
            if (event.item == null) {
                if (event.isCancelable() && !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
                    event.setCanceled(true);
                return;
            }
            if (inventory.getStackInSlot(49).getDisplayName().equals("§aRemember the pattern!")) {
                if (event.isCancelable()) event.setCanceled(true);
            } else if (inventory.getStackInSlot(49).getDisplayName().startsWith("§7Timer: §a")) {
                if (clickInOrderSlots[lastUltraSequencerClicked] != null && event.slot.getSlotIndex() != clickInOrderSlots[lastUltraSequencerClicked].getSlotIndex()) {
                    if (event.isCancelable() && !Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        if (ModConfig.ultrasequencer && event.displayName.startsWith("Ultrasequencer (")) {
            List<Slot> invSlots = event.slots;
            if (invSlots.size() > 48 && invSlots.get(49).getStack() != null) {
                if (invSlots.get(49).getStack().getDisplayName().startsWith("§7Timer: §a")) {
                    lastUltraSequencerClicked = 0;
                    for (Slot slot : clickInOrderSlots) {
                        if (slot != null && slot.getStack() != null && StringUtils.stripControlCodes(slot.getStack().getDisplayName()).matches("\\d+")) {
                            int number = Integer.parseInt(StringUtils.stripControlCodes(slot.getStack().getDisplayName()));
                            if (number > lastUltraSequencerClicked) {
                                lastUltraSequencerClicked = number;
                            }
                        }
                    }
                    if (lastUltraSequencerClicked < clickInOrderSlots.length) {
                        if (clickInOrderSlots[lastUltraSequencerClicked] != null) {
                            Slot nextSlot = clickInOrderSlots[lastUltraSequencerClicked];
                            RenderUtils.drawOnSlot(event.chestSize, nextSlot.xDisplayPosition, nextSlot.yDisplayPosition, ModConfig.ultrasequencerNextColour.getRGB());
                        }
                    }
                    if (lastUltraSequencerClicked + 1 < clickInOrderSlots.length) {
                        if (clickInOrderSlots[lastUltraSequencerClicked + 1] != null) {
                            Slot nextSlot = clickInOrderSlots[lastUltraSequencerClicked + 1];
                            RenderUtils.drawOnSlot(event.chestSize, nextSlot.xDisplayPosition, nextSlot.yDisplayPosition, ModConfig.ultrasequencerNextToNextColour.getRGB());
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        clickInOrderSlots = new Slot[36];
    }

}
