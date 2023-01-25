package me.Danker.features.puzzlesolvers;

import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.utils.RenderUtils;
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
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.List;

public class SameColourSolver {

    public static boolean foundColour = false;
    public static int correctColour = 0;
    static List<Integer> colourLoop = Arrays.asList(14, 1, 4, 13, 11);

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onTooltipLow(ItemTooltipEvent event) {
        if (!ModConfig.sameColour || !Utils.isInDungeons()) return;
        if (event.toolTip == null) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        if (mc.currentScreen instanceof GuiChest) {
            ContainerChest chest = (ContainerChest) player.openContainer;
            IInventory inv = chest.getLowerChestInventory();
            String chestName = inv.getDisplayName().getUnformattedText();

            if (chestName.equals("Change all to same color!")) {
                event.toolTip.clear();
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!ModConfig.sameColour || foundColour || !Utils.isInDungeons()) return;

        if (DankersSkyblockMod.tickAmount % 5 == 0) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.thePlayer;
            if (mc.currentScreen instanceof GuiChest) {
                if (player == null) return;
                ContainerChest chest = (ContainerChest) player.openContainer;
                List<Slot> invSlots = ((GuiChest) mc.currentScreen).inventorySlots.inventorySlots;
                String chestName = chest.getLowerChestInventory().getDisplayName().getUnformattedText().trim();

                if (chestName.equals("Change all to same color!")) {
                    int red = 0;
                    int orange = 0;
                    int yellow = 0;
                    int green = 0;
                    int blue = 0;

                    for (int i = 12; i <= 32; i++) {
                        ItemStack stack = invSlots.get(i).getStack();
                        if (stack == null || stack.getItem() != Item.getItemFromBlock(Blocks.stained_glass_pane))
                            continue;
                        if (stack.getItemDamage() == 7) continue;

                        switch (stack.getItemDamage()) {
                            case 1:
                                orange++;
                                break;
                            case 4:
                                yellow++;
                                break;
                            case 11:
                                blue++;
                                break;
                            case 13:
                                green++;
                                break;
                            case 14:
                                red++;
                                break;
                        }
                    }

                    int max = NumberUtils.max(new int[]{red, orange, yellow, green, blue});
                    if (max == red) {
                        correctColour = 14;
                    } else if (max == orange) {
                        correctColour = 1;
                    } else if (max == yellow) {
                        correctColour = 4;
                    } else if (max == green) {
                        correctColour = 13;
                    } else {
                        correctColour = 11;
                    }
                    foundColour = true;
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        if (!ModConfig.sameColour || !foundColour || !Utils.isInDungeons()) return;

        if (event.displayName.equals("Change all to same color!")) {
            int chestSize = event.chestSize;
            List<Slot> invSlots = event.slots;

            for (int i = 12; i <= 32; i++) {
                Slot slot = invSlots.get(i);
                ItemStack stack = slot.getStack();
                if (stack == null || stack.getItem() != Item.getItemFromBlock(Blocks.stained_glass_pane)) continue;
                if (stack.getItemDamage() == 7) continue;

                int distance = getDistance(stack.getItemDamage(), correctColour);
                if (distance == 0) continue;

                RenderUtils.drawTextOnSlot(chestSize, slot.xDisplayPosition, slot.yDisplayPosition, "" + distance);
            }
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (!(event.gui instanceof GuiChest)) {
            foundColour = false;
            correctColour = 0;
        }
    }

    public static int getDistance(int colour, int endColour) {
        int index = colourLoop.indexOf(colour);
        int finalIndex = colourLoop.indexOf(endColour);
        if (index == -1 || finalIndex == -1) return 0;

        if (finalIndex < index) {
            return finalIndex - index + 5;
        }
        return finalIndex - index;
    }

}
