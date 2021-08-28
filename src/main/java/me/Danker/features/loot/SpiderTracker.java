package me.Danker.features.loot;

import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpiderTracker {

    public static int spiderTarantulas;
    public static int spiderWebs;
    public static int spiderTAP;
    public static int spiderTAPDrops;
    public static int spiderBites;
    public static int spiderCatalysts;
    public static int spiderBooks;
    public static int spiderSwatters;
    public static int spiderTalismans;
    public static int spiderMosquitos;
    public static double spiderTime;
    public static int spiderBosses;

    public static int spiderTarantulasSession = 0;
    public static int spiderWebsSession = 0;
    public static int spiderTAPSession = 0;
    public static int spiderTAPDropsSession = 0;
    public static int spiderBitesSession = 0;
    public static int spiderCatalystsSession = 0;
    public static int spiderBooksSession = 0;
    public static int spiderSwattersSession = 0;
    public static int spiderTalismansSession = 0;
    public static int spiderMosquitosSession = 0;
    public static double spiderTimeSession = -1;
    public static int spiderBossesSession = -1;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        boolean rng = false;

        if (message.contains("VERY RARE DROP!  (Enchanted Book)") || message.contains("CRAZY RARE DROP!  (Enchanted Book)")) {
            if (Utils.isInScoreboard("Tarantula Broodfather")) {
                spiderBooks++;
                spiderBooksSession++;
                ConfigHandler.writeIntConfig("spider", "book", spiderBooks);
            }
        }

        if (message.contains("   Spider Slayer LVL ")) { // Spider
            spiderTarantulas++;
            spiderTarantulasSession++;
            if (spiderBosses != -1) {
                spiderBosses++;
            }
            if (spiderBossesSession != -1) {
                spiderBossesSession++;
            }
            ConfigHandler.writeIntConfig("spider", "tarantulas", spiderTarantulas);
            ConfigHandler.writeIntConfig("spider", "bossRNG", spiderBosses);
        } else if (message.contains("RARE DROP! (") && message.contains("Toxic Arrow Poison)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            spiderTAP += amount;
            spiderTAPSession += amount;
            spiderTAPDrops++;
            spiderTAPDropsSession++;
            ConfigHandler.writeIntConfig("spider", "tap", spiderTAP);
            ConfigHandler.writeIntConfig("spider", "tapDrops", spiderTAPDrops);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains(" Bite Rune I)")) {
            spiderBites++;
            spiderBitesSession++;
            ConfigHandler.writeIntConfig("spider", "bite", spiderBites);
        } else if (message.contains("VERY RARE DROP!  (Spider Catalyst)")) {
            spiderCatalysts++;
            spiderCatalystsSession++;
            ConfigHandler.writeIntConfig("spider", "catalyst", spiderCatalysts);
        } else if (message.contains("CRAZY RARE DROP!  (Fly Swatter)")) {
            rng = true;
            spiderSwatters++;
            spiderSwattersSession++;
            ConfigHandler.writeIntConfig("spider", "swatter", spiderSwatters);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.LIGHT_PURPLE + "FLY SWATTER!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Tarantula Talisman")) {
            rng = true;
            spiderTalismans++;
            spiderTalismansSession++;
            ConfigHandler.writeIntConfig("spider", "talisman", spiderTalismans);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "TARANTULA TALISMAN!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Digested Mosquito)")) {
            rng = true;
            spiderMosquitos++;
            spiderMosquitosSession++;
            ConfigHandler.writeIntConfig("spider", "mosquito", spiderMosquitos);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "DIGESTED MOSQUITO!", 5);
        }

        if (rng) {
            spiderTime = System.currentTimeMillis() / 1000;
            spiderBosses = 0;
            spiderTimeSession = System.currentTimeMillis() / 1000;
            spiderBossesSession = 0;
            ConfigHandler.writeDoubleConfig("spider", "timeRNG", spiderTime);
            ConfigHandler.writeIntConfig("spider", "bossRNG", 0);
        }
    }

}
