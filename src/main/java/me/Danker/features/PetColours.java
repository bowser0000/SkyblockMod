package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.events.GuiChestBackgroundDrawnEvent;
import me.Danker.utils.RenderUtils;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Pattern;

public class PetColours {

    static Pattern petPattern = Pattern.compile("\\[Lvl [\\d]{1,3}]");
    public static int PET_1_TO_9;
    public static int PET_10_TO_19;
    public static int PET_20_TO_29;
    public static int PET_30_TO_39;
    public static int PET_40_TO_49;
    public static int PET_50_TO_59;
    public static int PET_60_TO_69;
    public static int PET_70_TO_79;
    public static int PET_80_TO_89;
    public static int PET_90_TO_99;
    public static int PET_100;

    @SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
        if (ToggleCommand.petColoursToggled) {
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
                        colour = PET_100;
                    } else if ((petLevel < 100 && petLevel >= 90) || petLevel >= 190) {
                        colour = PET_90_TO_99;
                    } else if ((petLevel < 100 && petLevel >= 80) || petLevel >= 180) {
                        colour = PET_80_TO_89;
                    } else if ((petLevel < 100 && petLevel >= 70) || petLevel >= 170) {
                        colour = PET_70_TO_79;
                    } else if ((petLevel < 100 && petLevel >= 60) || petLevel >= 160) {
                        colour = PET_60_TO_69;
                    } else if ((petLevel < 100 && petLevel >= 50) || petLevel >= 150) {
                        colour = PET_50_TO_59;
                    } else if ((petLevel < 100 && petLevel >= 40) || petLevel >= 140) {
                        colour = PET_40_TO_49;
                    } else if ((petLevel < 100 && petLevel >= 30) || petLevel >= 130) {
                        colour = PET_30_TO_39;
                    } else if ((petLevel < 100 && petLevel >= 20) || petLevel >= 120) {
                        colour = PET_20_TO_29;
                    } else if ((petLevel < 100 && petLevel >= 10) || petLevel >= 110) {
                        colour = PET_10_TO_19;
                    } else {
                        colour = PET_1_TO_9;
                    }
                    RenderUtils.drawOnSlot(event.chestSize, slot.xDisplayPosition, slot.yDisplayPosition, colour + 0xBF000000);
                }
            }
        }
    }

}
