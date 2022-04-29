package me.Danker.features.loot;

import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ConfigHandler;
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
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        boolean rng = false;

        if (message.contains("   Spider Slayer LVL ")) { // Spider
            tarantulas++;
            tarantulasSession++;
            if (bosses != -1) {
                bosses++;
            }
            if (bossesSession != -1) {
                bossesSession++;
            }
            ConfigHandler.writeIntConfig("spider", "tarantulas", tarantulas);
            ConfigHandler.writeIntConfig("spider", "bossRNG", bosses);
        } else if (message.contains("RARE DROP! (") && message.contains("Toxic Arrow Poison)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            TAP += amount;
            TAPSession += amount;
            TAPDrops++;
            TAPDropsSession++;
            ConfigHandler.writeIntConfig("spider", "tap", TAP);
            ConfigHandler.writeIntConfig("spider", "tapDrops", TAPDrops);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains(" Bite Rune I)")) {
            bites++;
            bitesSession++;
            ConfigHandler.writeIntConfig("spider", "bite", bites);
        } else if (message.contains("VERY RARE DROP!  (Bane of Arthropods VI)")) {
            books++;
            booksSession++;
            ConfigHandler.writeIntConfig("spider", "book", books);
        } else if (message.contains("VERY RARE DROP!  (Spider Catalyst)")) {
            catalysts++;
            catalystsSession++;
            ConfigHandler.writeIntConfig("spider", "catalyst", catalysts);
        } else if (message.contains("CRAZY RARE DROP!  (Fly Swatter)")) {
            rng = true;
            swatters++;
            swattersSession++;
            ConfigHandler.writeIntConfig("spider", "swatter", swatters);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.LIGHT_PURPLE + "FLY SWATTER!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Tarantula Talisman")) {
            rng = true;
            talismans++;
            talismansSession++;
            ConfigHandler.writeIntConfig("spider", "talisman", talismans);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "TARANTULA TALISMAN!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Digested Mosquito)")) {
            rng = true;
            mosquitos++;
            mosquitosSession++;
            ConfigHandler.writeIntConfig("spider", "mosquito", mosquitos);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "DIGESTED MOSQUITO!", 5);
        }

        if (rng) {
            time = System.currentTimeMillis() / 1000;
            bosses = 0;
            timeSession = System.currentTimeMillis() / 1000;
            bossesSession = 0;
            ConfigHandler.writeDoubleConfig("spider", "timeRNG", time);
            ConfigHandler.writeIntConfig("spider", "bossRNG", 0);
        }
    }

}
