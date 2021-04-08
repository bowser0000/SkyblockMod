package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.ChestSlotClickedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StopSalvagingStarredItems {

    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        ItemStack item = event.item;
        if (ToggleCommand.stopSalvageStarredToggled && event.inventoryName.startsWith("Salvage")) {
            if (item == null) return;
            boolean inSalvageGui = false;
            if (item.getDisplayName().contains("Salvage") || item.getDisplayName().contains("Essence")) {
                ItemStack salvageItem = event.inventory.getStackInSlot(13);
                if (salvageItem == null) return;
                item = salvageItem;
                inSalvageGui = true;
            }
            if (item.getDisplayName().contains("✪") && (event.slot.slotNumber > 53 || inSalvageGui)) {
                Minecraft.getMinecraft().thePlayer.playSound("note.bass", 1, 0.5f);
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Danker's Skyblock Mod has stopped you from salvaging that item!"));
                event.setCanceled(true);
                return;
            }
        }
    }

}
