package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.events.ChestSlotClickedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockWrongSlayer {

    static String[] names = {
        "Revenant Horror",
        "Tarantula Broodfather",
        "Sven Packmaster",
        "Voidgloom Seraph",
        "Inferno Demonlord"
    };
    static String[] tiers = {
        "I",
        "II",
        "III",
        "IV",
        "V"
    };

    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        String inventoryName = event.inventoryName;
        ItemStack item = event.item;
        if (ModConfig.blockSlayer && item != null) {
            String name = names[ModConfig.slayerName];
            String tier = tiers[ModConfig.slayerNumber];

            if (inventoryName.equals("Slayer")) {
                if (!item.getDisplayName().contains("Revenant Horror") && !item.getDisplayName().contains("Tarantula Broodfather") && !item.getDisplayName().contains("Sven Packmaster"))
                    return;
                if (!item.getDisplayName().contains(name)) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Danker's Skyblock Mod has stopped you from starting this quest (Set to " + name + " " + tier + ")"));
                    Minecraft.getMinecraft().thePlayer.playSound("note.bass", 1, (float) 0.5);
                    event.setCanceled(true);
                }
            } else if (inventoryName.equals("Revenant Horror") || inventoryName.equals("Tarantula Broodfather") || inventoryName.equals("Sven Packmaster") || inventoryName.equals("Voidgloom Seraph")) {
                if (item.getDisplayName().contains("Revenant Horror") || item.getDisplayName().contains("Tarantula Broodfather") || item.getDisplayName().contains("Sven Packmaster") || item.getDisplayName().contains("Voidgloom Seraph")) {
                    // Only check number as they passed the above check
                    String slayerNumber = item.getDisplayName().substring(item.getDisplayName().lastIndexOf(" ") + 1);
                    if (!slayerNumber.equals(tier)) {
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Danker's Skyblock Mod has stopped you from starting this quest (Set to " + name + " " + tier + ")"));
                        Minecraft.getMinecraft().thePlayer.playSound("note.bass", 1, (float) 0.5);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

}
