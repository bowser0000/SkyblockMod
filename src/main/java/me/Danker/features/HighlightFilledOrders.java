package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class HighlightFilledOrders {

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        if (!Utils.inSkyblock) return;
        if (!ModConfig.highlightOrders) return;
        if (!event.displayName.equals("Your Bazaar Orders")) return;

        List<Slot> slots = event.slots;
        for (Slot slot : slots) {
            if (slot != null && slot.getStack() == null) continue;
            for (String line : Utils.getItemLore(slot.getStack())) {
                if (line.endsWith("100%!")) {
                    RenderUtils.drawOnSlot(event.chestSize, slot.xDisplayPosition, slot.yDisplayPosition, ModConfig.highlightOrdersColour.getRGB());
                    break;
                }
            }
        }
    }

}
