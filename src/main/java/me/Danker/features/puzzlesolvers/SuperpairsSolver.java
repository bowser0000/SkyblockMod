package me.Danker.features.puzzlesolvers;

import me.Danker.commands.ToggleCommand;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.List;
import java.util.*;

public class SuperpairsSolver {

    static ItemStack[] experimentTableSlots = new ItemStack[54];

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onTooltip(ItemTooltipEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.toolTip == null) return;

        ItemStack item = event.itemStack;
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        if (mc.currentScreen instanceof GuiChest) {
            ContainerChest chest = (ContainerChest) player.openContainer;
            IInventory inv = chest.getLowerChestInventory();
            String chestName = inv.getDisplayName().getUnformattedText();

            if (ToggleCommand.superpairsToggled && chestName.contains("Superpairs (")) {
                if (Item.getIdFromItem(item.getItem()) != 95) return;
                if (item.getDisplayName().contains("Click any button") || item.getDisplayName().contains("Click a second button") || item.getDisplayName().contains("Next button is instantly rewarded") || item.getDisplayName().contains("Stained Glass")) {
                    Slot slot = ((GuiChest) mc.currentScreen).getSlotUnderMouse();
                    ItemStack itemStack = experimentTableSlots[slot.getSlotIndex()];
                    if (itemStack == null) return;
                    String itemName = itemStack.getDisplayName();

                    if (event.toolTip.stream().anyMatch(x -> StringUtils.stripControlCodes(x).equals(StringUtils.stripControlCodes(itemName))))
                        return;
                    event.toolTip.removeIf(x -> {
                        x = StringUtils.stripControlCodes(x);
                        if (x.equals("minecraft:stained_glass")) return true;
                        return x.startsWith("NBT: ");
                    });
                    event.toolTip.add(itemName);
                    event.toolTip.add(itemStack.getItem().getRegistryName());
                }

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

            if (ToggleCommand.superpairsToggled && chestName.startsWith("Superpairs (")) {
                for (int i = 0; i < 53; i++) {
                    ItemStack itemStack = invSlots.get(i).getStack();
                    if (itemStack == null) continue;
                    String itemName = itemStack.getDisplayName();
                    if (Item.getIdFromItem(itemStack.getItem()) == 95 || Item.getIdFromItem(itemStack.getItem()) == 160) continue;
                    if (itemName.contains("Instant Find") || itemName.contains("Gained +")) continue;
                    if (itemName.contains("Enchanted Book")) {
                        itemName = itemStack.getTooltip(mc.thePlayer, false).get(3);
                    }
                    if (itemStack.stackSize > 1) {
                        itemName = itemStack.stackSize + " " + itemName;
                    }
                    if (experimentTableSlots[i] != null) continue;
                    experimentTableSlots[i] = itemStack.copy().setStackDisplayName(itemName);
                }
            }
        }
    }

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        if (ToggleCommand.superpairsToggled && event.displayName.contains("Superpairs (")) {
            HashMap<String, HashSet<Integer>> matches = new HashMap<>();
            for (int i = 0; i < 53; i++) {
                ItemStack itemStack = experimentTableSlots[i];
                if (itemStack == null) continue;

                //Utils.renderItem(itemStack, x, y, -100);

                String itemName = itemStack.getDisplayName();
                String keyName = itemName + itemStack.getUnlocalizedName();
                matches.computeIfAbsent(keyName, k -> new HashSet<>());
                matches.get(keyName).add(i);
            }

            Color[] colors = {
                    new Color(255, 0, 0, 100),
                    new Color(0, 0, 255, 100),
                    new Color(100, 179, 113, 100),
                    new Color(255, 114, 255, 100),
                    new Color(255, 199, 87, 100),
                    new Color(119, 105, 198, 100),
                    new Color(135, 199, 112, 100),
                    new Color(240, 37, 240, 100),
                    new Color(178, 132, 190, 100),
                    new Color(63, 135, 163, 100),
                    new Color(146, 74, 10, 100),
                    new Color(255, 255, 255, 100),
                    new Color(217, 252, 140, 100),
                    new Color(255, 82, 82, 100)
            };

            Iterator<Color> colorIterator = Arrays.stream(colors).iterator();

            matches.forEach((itemName, slotSet) -> {
                if (slotSet.size() < 2) return;
                ArrayList<Slot> slots = new ArrayList<>();
                slotSet.forEach(slotNum -> slots.add(event.slots.get(slotNum)));
                Color color = colorIterator.next();
                slots.forEach(slot -> {
                    Utils.drawOnSlot(event.chestSize, slot.xDisplayPosition, slot.yDisplayPosition, color.getRGB());
                });
            });
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        experimentTableSlots = new ItemStack[54];
    }

}
