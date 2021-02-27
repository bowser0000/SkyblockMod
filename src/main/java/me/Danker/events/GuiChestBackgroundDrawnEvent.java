package me.Danker.events;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

public class GuiChestBackgroundDrawnEvent extends Event {
    public final GuiChest chest;
    public final String displayName;
    public final int chestSize;
    public final List<Slot> slots;

    public GuiChestBackgroundDrawnEvent(GuiChest chest, String displayName, int chestSize, List<Slot> slots) {
        this.chest = chest;
        this.displayName = displayName;
        this.chestSize = chestSize;
        this.slots = slots;
    }

}
