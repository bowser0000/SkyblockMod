package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class GoldenEnchants {

    public static Map<String, String> t6Enchants = new HashMap<>();
    public static Pattern t6EnchantPattern = Pattern.compile("");

    public static void init() {
        t6Enchants.put("9Angler VI", "6Angler VI");
        t6Enchants.put("9Bane of Arthropods VI", "6Bane of Arthropods VI");
        t6Enchants.put("9Caster VI", "6Caster VI");
        t6Enchants.put("9Compact X", "6Compact X");
        t6Enchants.put("9Critical VI", "6Critical VI");
        t6Enchants.put("9Dragon Hunter V", "6Dragon Hunter V");
        t6Enchants.put("9Efficiency VI", "6Efficiency VI");
        t6Enchants.put("9Ender Slayer VI", "6Ender Slayer VI");
        t6Enchants.put("9Experience IV", "6Experience IV");
        t6Enchants.put("9Expertise X", "6Expertise X");
        t6Enchants.put("9Feather Falling X", "6Feather Falling X");
        t6Enchants.put("9Frail VI", "6Frail VI");
        t6Enchants.put("9Giant Killer VI", "6Giant Killer VI");
        t6Enchants.put("9Growth VI", "6Growth VI");
        t6Enchants.put("9Infinite Quiver X", "6Infinite Quiver X");
        t6Enchants.put("9Lethality VI", "6Lethality VI");
        t6Enchants.put("9Life Steal IV", "6Life Steal IV");
        t6Enchants.put("9Looting IV", "6Looting IV");
        t6Enchants.put("9Luck VI", "6Luck VI");
        t6Enchants.put("9Luck of the Sea VI", "6Luck of the Sea VI");
        t6Enchants.put("9Lure VI", "6Lure VI");
        t6Enchants.put("9Magnet VI", "6Magnet VI");
        t6Enchants.put("9Overload V", "6Overload V");
        t6Enchants.put("9Power VI", "6Power VI");
        t6Enchants.put("9Protection VI", "6Protection VI");
        t6Enchants.put("9Scavenger IV", "6Scavenger IV");
        t6Enchants.put("9Scavenger V", "6Scavenger V");
        t6Enchants.put("9Sharpness VI", "6Sharpness VI");
        t6Enchants.put("9Smite VI", "6Smite VI");
        t6Enchants.put("9Spiked Hook VI", "6Spiked Hook VI");
        t6Enchants.put("9Thunderlord VI", "6Thunderlord VI");
        t6Enchants.put("9Vampirism VI", "6Vampirism VI");

        String patternString = "(" + String.join("|", t6Enchants.keySet()) + ")";
        t6EnchantPattern = Pattern.compile(patternString);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onTooltip(ItemTooltipEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.toolTip == null) return;

        if (ToggleCommand.goldenToggled) {
            for (int i = 0; i < event.toolTip.size(); i++) {
                event.toolTip.set(i, Utils.returnGoldenEnchants(event.toolTip.get(i)));
            }
        }
    }

}
