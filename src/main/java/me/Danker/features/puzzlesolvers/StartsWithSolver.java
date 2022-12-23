package me.Danker.features.puzzlesolvers;

import me.Danker.config.ModConfig;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StartsWithSolver {

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        String displayName = event.displayName;
        if (ModConfig.startsWith && Utils.isInDungeons() && displayName.startsWith("What starts with:")) {
            char letter = displayName.charAt(displayName.indexOf("'") + 1);
            for (Slot slot : event.slots) {
                if (slot.inventory == Minecraft.getMinecraft().thePlayer.inventory) continue;
                ItemStack item = slot.getStack();
                if (item == null) continue;
                if (item.isItemEnchanted()) continue;
                if (StringUtils.stripControlCodes(item.getDisplayName()).charAt(0) == letter) {
                    RenderUtils.drawOnSlot(event.chestSize, slot.xDisplayPosition, slot.yDisplayPosition, ModConfig.startsWithColour.getRGB());
                }
            }
        }
    }

}
