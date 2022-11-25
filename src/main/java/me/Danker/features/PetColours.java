package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.utils.RenderUtils;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Pattern;

public class PetColours {

    static Pattern petPattern = Pattern.compile("\\[Lvl [\\d]{1,3}]");

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        if (ModConfig.petColours) {
            for (Slot slot : event.slots) {
                ItemStack item = slot.getStack();
                if (item == null) continue;
                String name = item.getDisplayName();
                if (petPattern.matcher(StringUtils.stripControlCodes(name)).find()) {
                    if (name.endsWith("aHealer") || name.endsWith("aMage") || name.endsWith("aBerserk") || name.endsWith("aArcher") || name.endsWith("aTank"))
                        continue;
                    int colour;
                    int petLevel = Integer.parseInt(item.getDisplayName().substring(item.getDisplayName().indexOf(" ") + 1, item.getDisplayName().indexOf("]")));
                    if (petLevel == 100 || petLevel == 200) {
                        colour = ModConfig.pet100Colour.getRGB();
                    } else if ((petLevel < 100 && petLevel >= 90) || petLevel >= 190) {
                        colour = ModConfig.pet90To99Colour.getRGB();
                    } else if ((petLevel < 100 && petLevel >= 80) || petLevel >= 180) {
                        colour = ModConfig.pet80To89Colour.getRGB();
                    } else if ((petLevel < 100 && petLevel >= 70) || petLevel >= 170) {
                        colour = ModConfig.pet70To79Colour.getRGB();
                    } else if ((petLevel < 100 && petLevel >= 60) || petLevel >= 160) {
                        colour = ModConfig.pet60To69Colour.getRGB();
                    } else if ((petLevel < 100 && petLevel >= 50) || petLevel >= 150) {
                        colour = ModConfig.pet50To59Colour.getRGB();
                    } else if ((petLevel < 100 && petLevel >= 40) || petLevel >= 140) {
                        colour = ModConfig.pet40To49Colour.getRGB();
                    } else if ((petLevel < 100 && petLevel >= 30) || petLevel >= 130) {
                        colour = ModConfig.pet30To39Colour.getRGB();
                    } else if ((petLevel < 100 && petLevel >= 20) || petLevel >= 120) {
                        colour = ModConfig.pet20To29Colour.getRGB();
                    } else if ((petLevel < 100 && petLevel >= 10) || petLevel >= 110) {
                        colour = ModConfig.pet10To19Colour.getRGB();
                    } else {
                        colour = ModConfig.pet1To9Colour.getRGB();
                    }
                    RenderUtils.drawOnSlot(event.chestSize, slot.xDisplayPosition, slot.yDisplayPosition, colour);
                }
            }
        }
    }

}
