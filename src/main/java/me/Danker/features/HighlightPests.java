package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.locations.Location;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class HighlightPests {

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        if (!ModConfig.highlightPests) return;
        if (Utils.currentLocation != Location.GARDEN) return;
        if (!event.displayName.equals("Configure Plots")) return;

        List<Slot> slots = event.slots;
        for (Slot slot : slots) {
            if (slot == null || slot.getStack() == null) continue;
            for (String line : Utils.getItemLore(slot.getStack())) {
                if (line.length() <= 30) continue;

                if (line.charAt(4) == 'ൠ') {
                    RenderUtils.drawOnSlot(event.chestSize, slot.xDisplayPosition, slot.yDisplayPosition, ModConfig.highlightPestsColour.getRGB());
                    break;
                }
            }
        }
    }

}
