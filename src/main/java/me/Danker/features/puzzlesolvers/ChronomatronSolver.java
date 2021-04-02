package me.Danker.features.puzzlesolvers;

import me.Danker.commands.ToggleCommand;
import me.Danker.events.ChestSlotClickedEvent;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ChronomatronSolver {

    static int lastChronomatronRound = 0;
    static List<String> chronomatronPattern = new ArrayList<>();
    static int chronomatronMouseClicks = 0;
    public static int CHRONOMATRON_NEXT;
    public static int CHRONOMATRON_NEXT_TO_NEXT;

    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        if (ToggleCommand.chronomatronToggled && event.inventoryName.startsWith("Chronomatron (")) {
            IInventory inventory = event.inventory;
            ItemStack item = event.item;
            if (item == null) return;

            if (inventory.getStackInSlot(49).getDisplayName().startsWith("§7Timer: §a") && (item.getItem() == Item.getItemFromBlock(Blocks.stained_glass) || item.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay))) {
                chronomatronMouseClicks++;
            }
        }
    }

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        if (ToggleCommand.chronomatronToggled && event.displayName.startsWith("Chronomatron (")) {
            int chestSize = event.chestSize;
            List<Slot> invSlots = event.slots;
            if (invSlots.size() > 48 && invSlots.get(49).getStack() != null) {
                if (invSlots.get(49).getStack().getDisplayName().startsWith("§7Timer: §a") && invSlots.get(4).getStack() != null) {
                    int round = invSlots.get(4).getStack().stackSize;
                    int timerSeconds = Integer.parseInt(StringUtils.stripControlCodes(invSlots.get(49).getStack().getDisplayName()).replaceAll("[^\\d]", ""));
                    if (round != lastChronomatronRound && timerSeconds == round + 2) {
                        lastChronomatronRound = round;
                        for (int i = 10; i <= 43; i++) {
                            ItemStack stack = invSlots.get(i).getStack();
                            if (stack == null) continue;
                            if (stack.getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay)) {
                                chronomatronPattern.add(stack.getDisplayName());
                                break;
                            }
                        }
                    }
                    if (chronomatronMouseClicks < chronomatronPattern.size()) {
                        for (int i = 10; i <= 43; i++) {
                            ItemStack glass = invSlots.get(i).getStack();
                            if (glass == null) continue;

                            Slot glassSlot = invSlots.get(i);

                            if (chronomatronMouseClicks + 1 < chronomatronPattern.size()) {
                                if (chronomatronPattern.get(chronomatronMouseClicks).equals(chronomatronPattern.get(chronomatronMouseClicks + 1))) {
                                    if (glass.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks))) {
                                        Utils.drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition, CHRONOMATRON_NEXT + 0xE5000000);
                                    }
                                } else if (glass.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks))) {
                                    Utils.drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition, CHRONOMATRON_NEXT + 0xE5000000);
                                } else if (glass.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks + 1))) {
                                    Utils.drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition, CHRONOMATRON_NEXT_TO_NEXT + 0XBE000000);
                                }
                            } else if (glass.getDisplayName().equals(chronomatronPattern.get(chronomatronMouseClicks))) {
                                Utils.drawOnSlot(chestSize, glassSlot.xDisplayPosition, glassSlot.yDisplayPosition, CHRONOMATRON_NEXT + 0xE5000000);
                            }
                        }
                    }
                } else if (invSlots.get(49).getStack().getDisplayName().equals("§aRemember the pattern!")) {
                    chronomatronMouseClicks = 0;
                }
            }
            Minecraft mc = Minecraft.getMinecraft();
            ScaledResolution sr = new ScaledResolution(mc);
            int guiLeft = (sr.getScaledWidth() - 176) / 2;
            new TextRenderer(mc, String.join("\n", chronomatronPattern), (int) (guiLeft * 0.8), 10, 1);
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        lastChronomatronRound = 0;
        chronomatronPattern.clear();
        chronomatronMouseClicks = 0;
    }

}
