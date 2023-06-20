package me.Danker.features.loot;

import me.Danker.config.CfgConfig;
import me.Danker.events.SlayerLootDropEvent;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class VampireTracker {

    public static int riftstalkers;
    public static int covenSeals;
    public static int quantumBundles;
    public static int bubbaBlisters;
    public static int soultwistRunes;
    public static int chocolateChips;
    public static int luckyBlocks;
    public static int theOneBundles;
    public static int mcgrubbersBurgers;
    public static int vampireParts;

    public static int riftstalkersSession = 0;
    public static int covenSealsSession = 0;
    public static int quantumBundlesSession = 0;
    public static int bubbaBlistersSession = 0;
    public static int soultwistRunesSession = 0;
    public static int chocolateChipsSession = 0;
    public static int luckyBlocksSession = 0;
    public static int theOneBundlesSession = 0;
    public static int mcgrubbersBurgersSession = 0;
    public static int vampirePartsSession = 0;

    @SubscribeEvent
    public void onLootDrop(SlayerLootDropEvent event) {
        // no rngs
        switch (event.drop) {
            case "Bubba Blister":
                bubbaBlisters += event.amount;
                bubbaBlistersSession += event.amount;
                CfgConfig.writeIntConfig("vampire", "bubbaBlisters", bubbaBlisters);
                break;
            case "◆ Soultwist Rune I":
                soultwistRunes += event.amount;
                soultwistRunesSession += event.amount;
                CfgConfig.writeIntConfig("vampire", "soultwistRunes", soultwistRunes);
                break;
            case "Fang-tastic Chocolate Chip":
                chocolateChips += event.amount;
                chocolateChipsSession += event.amount;
                CfgConfig.writeIntConfig("vampire", "chocolateChips", chocolateChips);
                break;
            case "Guardian Lucky Block":
                luckyBlocks += event.amount;
                luckyBlocksSession += event.amount;
                CfgConfig.writeIntConfig("vampire", "luckyBlocks", luckyBlocks);
                break;
            case "McGrubber's Burger":
                mcgrubbersBurgers += event.amount;
                mcgrubbersBurgersSession += event.amount;
                CfgConfig.writeIntConfig("vampire", "mcgrubbersBurgers", mcgrubbersBurgers);
                break;
            case "Unfanged Vampire Part":
                vampireParts += event.amount;
                vampirePartsSession += event.amount;
                CfgConfig.writeIntConfig("vampire", "vampireParts", vampireParts);
                break;
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.contains(":")) return;

        if (message.contains("   Vampire Slayer LVL ")) {
            riftstalkers++;
            riftstalkersSession++;
            CfgConfig.writeIntConfig("vampire", "riftstalkers", riftstalkers);
        } else if (message.contains("(Enchanted Book Bundle)")) { // special case
            String formatted = event.message.getFormattedText();
            if (formatted.contains(EnumChatFormatting.GREEN + "Enchanted")) { // quantum
                quantumBundles++;
                quantumBundlesSession++;
                CfgConfig.writeIntConfig("vampire", "quantumBundles", quantumBundles);
            } else if (formatted.contains(EnumChatFormatting.GOLD + "Enchanted")) { // the one
                theOneBundles++;
                theOneBundlesSession++;
                CfgConfig.writeIntConfig("vampire", "theOneBundles", theOneBundles);
            }
        }
    }

}
