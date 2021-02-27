package me.Danker.events;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class ChestSlotClickedEvent extends Event {
    public final GuiChest chest;
    public final IInventory inventory;
    public final String inventoryName;
    public final Slot slot;
    public final ItemStack item;

    public ChestSlotClickedEvent(GuiChest chest, IInventory inventory, String inventoryName, Slot slot, ItemStack item) {
        this.chest = chest;
        this.inventory = inventory;
        this.inventoryName = inventoryName;
        this.slot = slot;
        this.item = item;
    }

    public ChestSlotClickedEvent(GuiChest chest, IInventory inventory, String inventoryName, Slot slot) {
        this.chest = chest;
        this.inventory = inventory;
        this.inventoryName = inventoryName;
        this.slot = slot;
        item = null;
    }

}
