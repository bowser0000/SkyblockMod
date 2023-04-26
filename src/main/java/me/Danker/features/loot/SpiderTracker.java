package me.Danker.features.loot;

import me.Danker.config.CfgConfig;
import me.Danker.config.ModConfig;
import me.Danker.events.SlayerLootDropEvent;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpiderTracker {

    public static int tarantulas;
    public static int webs;
    public static int TAP;
    public static int TAPDrops;
    public static int bites;
    public static int catalysts;
    public static int books;
    public static int swatters;
    public static int talismans;
    public static int mosquitos;
    public static double time;
    public static int bosses;

    public static int tarantulasSession = 0;
    public static int websSession = 0;
    public static int TAPSession = 0;
    public static int TAPDropsSession = 0;
    public static int bitesSession = 0;
    public static int catalystsSession = 0;
    public static int booksSession = 0;
    public static int swattersSession = 0;
    public static int talismansSession = 0;
    public static int mosquitosSession = 0;
    public static double timeSession = -1;
    public static int bossesSession = -1;

    @SubscribeEvent
    public void onLootDrop(SlayerLootDropEvent event) {
        boolean rng = false;

        switch (event.drop) {
            case "Toxic Arrow Poison":
                TAP += event.amount;
                TAPSession += event.amount;
                TAPDrops++;
                TAPDropsSession++;
                CfgConfig.writeIntConfig("spider", "tap", TAP);
                CfgConfig.writeIntConfig("spider", "tapDrops", TAPDrops);
                break;
            case "◆ Bite Rune I":
                bites += event.amount;
                bitesSession += event.amount;
                CfgConfig.writeIntConfig("spider", "bite", bites);
                break;
            case "Bane of Arthropods VI":
                books += event.amount;
                booksSession += event.amount;
                CfgConfig.writeIntConfig("spider", "book", books);
                break;
            case "Spider Catalyst":
                catalysts += event.amount;
                catalystsSession += event.amount;
                CfgConfig.writeIntConfig("spider", "catalyst", catalysts);
                break;
            case "Fly Swatter":
                rng = true;
                swatters += event.amount;
                swattersSession += event.amount;
                CfgConfig.writeIntConfig("spider", "swatter", swatters);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.LIGHT_PURPLE + "FLY SWATTER!", 3);
                break;
            case "Tarantula Talisman":
                rng = true;
                talismans += event.amount;
                talismansSession += event.amount;
                CfgConfig.writeIntConfig("spider", "talisman", talismans);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "TARANTULA TALISMAN!", 3);
                break;
            case "Digested Mosquito":
                rng = true;
                mosquitos += event.amount;
                mosquitosSession += event.amount;
                CfgConfig.writeIntConfig("spider", "mosquito", mosquitos);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "DIGESTED MOSQUITO!", 5);
                break;
        }

        if (rng) {
            time = System.currentTimeMillis() / 1000;
            bosses = 0;
            timeSession = System.currentTimeMillis() / 1000;
            bossesSession = 0;
            CfgConfig.writeDoubleConfig("spider", "timeRNG", time);
            CfgConfig.writeIntConfig("spider", "bossRNG", 0);
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.contains(":")) return;

        if (message.contains("   Spider Slayer LVL ")) {
            tarantulas++;
            tarantulasSession++;
            if (bosses != -1) {
                bosses++;
            }
            if (bossesSession != -1) {
                bossesSession++;
            }
            CfgConfig.writeIntConfig("spider", "tarantulas", tarantulas);
            CfgConfig.writeIntConfig("spider", "bossRNG", bosses);
        }
    }

}
