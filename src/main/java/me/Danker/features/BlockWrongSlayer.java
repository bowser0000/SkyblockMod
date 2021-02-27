package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.events.ChestSlotClickedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockWrongSlayer {

    public static String onlySlayerName = "";
    public static String onlySlayerNumber = "";

    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        String inventoryName = event.inventoryName;
        ItemStack item = event.item;
        if (!onlySlayerName.equals("") && item != null) {
            if (inventoryName.equals("Slayer")) {
                if (!item.getDisplayName().contains("Revenant Horror") && !item.getDisplayName().contains("Tarantula Broodfather") && !item.getDisplayName().contains("Sven Packmaster"))
                    return;
                if (!item.getDisplayName().contains(onlySlayerName)) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Danker's Skyblock Mod has stopped you from starting this quest (Set to " + onlySlayerName + " " + onlySlayerNumber + ")"));
                    Minecraft.getMinecraft().thePlayer.playSound("note.bass", 1, (float) 0.5);
                    event.setCanceled(true);
                }
            } else if (inventoryName.equals("Revenant Horror") || inventoryName.equals("Tarantula Broodfather") || inventoryName.equals("Sven Packmaster")) {
                if (item.getDisplayName().contains("Revenant Horror") || item.getDisplayName().contains("Tarantula Broodfather") || item.getDisplayName().contains("Sven Packmaster")) {
                    // Only check number as they passed the above check
                    String slayerNumber = item.getDisplayName().substring(item.getDisplayName().lastIndexOf(" ") + 1);
                    if (!slayerNumber.equals(onlySlayerNumber)) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Danker's Skyblock Mod has stopped you from starting this quest (Set to " + onlySlayerName + " " + onlySlayerNumber + ")"));
                        Minecraft.getMinecraft().thePlayer.playSound("note.bass", 1, (float) 0.5);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

}
