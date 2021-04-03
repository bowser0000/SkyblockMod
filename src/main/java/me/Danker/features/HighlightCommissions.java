package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemWritableBook;

import org.lwjgl.util.Color;
import java.util.List;

public class HighlightCommissions {

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        if(!Utils.inSkyblock) return;
        if(!ToggleCommand.highlightCommissions) return;
        List<Slot> slots = event.slots;
        if (!event.displayName.equals("Commissions")) return;

        for (Slot slot : slots) {
            if (slot != null && slot.getStack() == null) continue;
            if (slot.getStack().getItem() instanceof ItemWritableBook) {
                for (String line : Utils.getItemLore(slot.getStack())) {
                    if (line.contains("COMPLETED")) {
                        Utils.drawOnSlot(event.chestSize, slot.xDisplayPosition, slot.yDisplayPosition, 0x51FF51 + 0xD7000000);
                        break;
                    }
                }

            }
        }
    }

}
