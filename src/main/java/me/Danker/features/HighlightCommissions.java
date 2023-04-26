package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemWritableBook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class HighlightCommissions {

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        if (!Utils.inSkyblock) return;
        if (!ModConfig.highlightCommissions) return;
        List<Slot> slots = event.slots;
        if (!event.displayName.equals("Commissions")) return;

        for (Slot slot : slots) {
            if (slot != null && slot.getStack() == null) continue;
            if (slot.getStack().getItem() instanceof ItemWritableBook) {
                for (String line : Utils.getItemLore(slot.getStack())) {
                    if (line.contains("COMPLETED")) {
                        RenderUtils.drawOnSlot(event.chestSize, slot.xDisplayPosition, slot.yDisplayPosition, ModConfig.commissionColour.getRGB());
                        break;
                    }
                }

            }
        }
    }

}
