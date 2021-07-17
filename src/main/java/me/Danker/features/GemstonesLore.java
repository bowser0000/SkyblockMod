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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GemstonesLore {

    static Map<String, EnumChatFormatting> gemstoneColours = new HashMap<>();

    public static void init() {
        gemstoneColours.put("Amber", EnumChatFormatting.GOLD);
        gemstoneColours.put("Sapphire", EnumChatFormatting.AQUA);
        gemstoneColours.put("Jasper", EnumChatFormatting.LIGHT_PURPLE);
        gemstoneColours.put("Amethyst", EnumChatFormatting.DARK_PURPLE);
        gemstoneColours.put("Topaz", EnumChatFormatting.YELLOW);
        gemstoneColours.put("Jade", EnumChatFormatting.GREEN);
        gemstoneColours.put("Ruby", EnumChatFormatting.RED);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onTooltip(ItemTooltipEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.toolTip == null) return;

        ItemStack item = event.itemStack;
        if (ToggleCommand.gemstoneLore) {
            if (item.hasTagCompound()) {
                NBTTagCompound tags = item.getSubCompound("ExtraAttributes", false);
                if (tags != null) {
                    if (tags.hasKey("gems")) {
                        NBTTagCompound gems = tags.getCompoundTag("gems");
                        Set<String> set = gems.getKeySet();

                        if (set.size() == 0) return;

                        int index = Minecraft.getMinecraft().gameSettings.advancedItemTooltips ? 4 : 2;

                        event.toolTip.add(event.toolTip.size() - index, "");
                        event.toolTip.add(event.toolTip.size() - index, "Gemstones Applied:");

                        for (String gem : set) {
                            char last = gem.charAt(gem.length() - 1);
                            if (!Character.isDigit(last)) continue;

                            String gemstone = "  " + Utils.capitalizeString(gems.getString(gem)) + " ";
                            if (gem.startsWith("UNIVERSAL_")) {
                                gemstone += Utils.capitalizeString(gems.getString(gem + "_gem"));
                            } else {
                                gemstone += Utils.capitalizeString(gem.substring(0, gem.indexOf("_")));
                            }

                            for (String colour : gemstoneColours.keySet()) {
                                if (gemstone.contains(colour)) {
                                    gemstone = gemstoneColours.get(colour) + gemstone;
                                }
                            }

                            event.toolTip.add(event.toolTip.size() - index, gemstone);
                        }
                    }
                }
            }
        }
    }

}
