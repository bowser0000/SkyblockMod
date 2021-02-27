package me.Danker.features.puzzlesolvers;

import me.Danker.commands.ToggleCommand;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
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
        if (ToggleCommand.startsWithToggled && Utils.inDungeons && displayName.startsWith("What starts with:")) {
            char letter = displayName.charAt(displayName.indexOf("'") + 1);
            for (Slot slot : event.slots) {
                if (slot.inventory == Minecraft.getMinecraft().thePlayer.inventory) continue;
                ItemStack item = slot.getStack();
                if (item == null) continue;
                if (item.isItemEnchanted()) continue;
                if (StringUtils.stripControlCodes(item.getDisplayName()).charAt(0) == letter) {
                    Utils.drawOnSlot(event.chestSize, slot.xDisplayPosition, slot.yDisplayPosition, 0xBF40FF40);
                }
            }
        }
    }

}
