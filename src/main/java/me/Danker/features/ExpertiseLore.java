package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ExpertiseLore {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onTooltip(ItemTooltipEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.toolTip == null) return;

        ItemStack item = event.itemStack;
        if (ToggleCommand.expertiseLoreToggled) {
            if (item.hasTagCompound()) {
                NBTTagCompound tags = item.getSubCompound("ExtraAttributes", false);
                if (tags != null) {
                    if (tags.hasKey("expertise_kills")) {
                        int index = 4;
                        if (!Minecraft.getMinecraft().gameSettings.advancedItemTooltips) index -= 2;

                        event.toolTip.add(event.toolTip.size() - index, "");
                        event.toolTip.add(event.toolTip.size() - index, "Expertise Kills: " + EnumChatFormatting.RED + tags.getInteger("expertise_kills"));
                        if (Utils.expertiseKillsLeft(tags.getInteger("expertise_kills")) != -1) {
                            event.toolTip.add(event.toolTip.size() - index, Utils.expertiseKillsLeft(tags.getInteger("expertise_kills")) + " kills to tier up!");
                        }
                    }
                }
            }
        }
    }

}
