package me.Danker.features.loot;

import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlazeTracker {

    public static int demonlords;
    public static int derelictAshes;
    public static int lavatearRunes;
    public static int splashPotions;
    public static int magmaArrows;
    public static int manaDisintegrators;
    public static int scorchedBooks;
    public static int kelvinInverters;
    public static int blazeRodDistillates;
    public static int glowstoneDistillates;
    public static int magmaCreamDistillates;
    public static int netherWartDistillates;
    public static int gabagoolDistillates;
    public static int scorchedPowerCrystals;
    public static int fireAspectBooks;
    public static int fieryBurstRunes;
    public static int opalGems;
    public static int archfiendDice;
    public static int duplexBooks;
    public static int highClassArchfiendDice;
    public static int engineeringPlans;
    public static int subzeroInverters;
    public static double time;
    public static int bosses;

    public static int demonlordsSession = 0;
    public static int derelictAshesSession = 0;
    public static int lavatearRunesSession = 0;
    public static int splashPotionsSession = 0;
    public static int magmaArrowsSession = 0;
    public static int manaDisintegratorsSession = 0;
    public static int scorchedBooksSession = 0;
    public static int kelvinInvertersSession = 0;
    public static int blazeRodDistillatesSession = 0;
    public static int glowstoneDistillatesSession = 0;
    public static int magmaCreamDistillatesSession = 0;
    public static int netherWartDistillatesSession = 0;
    public static int gabagoolDistillatesSession = 0;
    public static int scorchedPowerCrystalsSession = 0;
    public static int fireAspectBooksSession = 0;
    public static int fieryBurstRunesSession = 0;
    public static int opalGemsSession = 0;
    public static int archfiendDiceSession = 0;
    public static int duplexBooksSession = 0;
    public static int highClassArchfiendDiceSession = 0;
    public static int engineeringPlansSession = 0;
    public static int subzeroInvertersSession = 0;
    public static double timeSession = 0;
    public static int bossesSession = 0;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        boolean rng = false;

        if (message.contains("   Blaze Slayer LVL ")) {
            demonlords++;
            demonlordsSession++;
            if (bosses != -1) {
                bosses++;
            }
            if (bossesSession != -1) {
                bossesSession++;
            }
            ConfigHandler.writeIntConfig("blaze", "demonlords", demonlords);
            ConfigHandler.writeIntConfig("blaze", "bossRNG", bosses);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains(" Lavatears Rune I)")) {
            lavatearRunes++;
            lavatearRunesSession++;
            ConfigHandler.writeIntConfig("blaze", "lavatearRunes", lavatearRunes);
        } else if (message.contains("VERY RARE DROP!  (Wisp's Ice-Flavored Water I Splash Potion)")) {
            splashPotions++;
            splashPotionsSession++;
            ConfigHandler.writeIntConfig("blaze", "splashPotions", splashPotions);
        } else if (message.contains("RARE DROP! (Bundle of Magma Arrows)")) {
            magmaArrows++;
            magmaArrowsSession++;
            ConfigHandler.writeIntConfig("blaze", "magmaArrows", magmaArrows);
        } else if (message.contains("VERY RARE DROP!  (Mana Disintegrator)")) {
            manaDisintegrators++;
            manaDisintegratorsSession++;
            ConfigHandler.writeIntConfig("blaze", "manaDisintegrators", manaDisintegrators);
        } else if (message.contains("VERY RARE DROP!  (Scorched Books)")) {
            scorchedBooks++;
            scorchedBooksSession++;
            ConfigHandler.writeIntConfig("blaze", "scorchedBooks", scorchedBooks);
        } else if (message.contains("VERY RARE DROP!  (Kelvin Inverter)")) {
            kelvinInverters++;
            kelvinInvertersSession++;
            ConfigHandler.writeIntConfig("blaze", "kelvinInverters", kelvinInverters);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains("Blaze Rod Distillate)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            blazeRodDistillates += amount;
            blazeRodDistillatesSession += amount;
            ConfigHandler.writeIntConfig("blaze", "blazeRodDistillates", blazeRodDistillates);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains("Glowstone Distillate)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            glowstoneDistillates += amount;
            glowstoneDistillatesSession += amount;
            ConfigHandler.writeIntConfig("blaze", "glowstoneDistillates", glowstoneDistillates);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains("Magma Cream Distillate)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            magmaCreamDistillates += amount;
            magmaCreamDistillatesSession += amount;
            ConfigHandler.writeIntConfig("blaze", "magmaCreamDistillates", magmaCreamDistillates);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains("Nether Wart Distillate)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            netherWartDistillates += amount;
            netherWartDistillatesSession += amount;
            ConfigHandler.writeIntConfig("blaze", "netherWartDistillates", netherWartDistillates);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains("Gabagool Distillate)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            gabagoolDistillates += amount;
            gabagoolDistillatesSession += amount;
            ConfigHandler.writeIntConfig("blaze", "gabagoolDistillates", gabagoolDistillates);
        } else if (message.contains("VERY RARE DROP!  (Scorched Power Crystal)")) {
            scorchedPowerCrystals++;
            scorchedPowerCrystalsSession++;
            ConfigHandler.writeIntConfig("blaze", "scorchedPowerCrystals", scorchedPowerCrystals);
        } else if (message.contains("VERY RARE DROP!  (Fire Aspect III)")) {
            fireAspectBooks++;
            fireAspectBooksSession++;
            ConfigHandler.writeIntConfig("blaze", "fireAspectBooks", fireAspectBooks);
        } else if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Fiery Burst Rune I)")) {
            rng = true;
            fieryBurstRunes++;
            fieryBurstRunesSession++;
            ConfigHandler.writeIntConfig("blaze", "fieryBurstRunes", fieryBurstRunes);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "FIERY BURST RUNE!", 5);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains(" Flawed Opal Gemstone)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            opalGems += amount;
            opalGemsSession += amount;
            ConfigHandler.writeIntConfig("blaze", "opalGems", opalGems);
        } else if (message.contains("VERY RARE DROP!  (Archfiend Dice)")) {
            archfiendDice++;
            archfiendDiceSession++;
            ConfigHandler.writeIntConfig("blaze", "archfiendDice", archfiendDice);
        } else if (message.contains("VERY RARE DROP!  (Duplex I)")) {
            duplexBooks++;
            duplexBooksSession++;
            ConfigHandler.writeIntConfig("blaze", "duplexBooks", duplexBooks);
        } else if (message.contains("CRAZY RARE DROP!  (High Class Archfiend Dice)")) {
            rng = true;
            highClassArchfiendDice++;
            highClassArchfiendDiceSession++;
            ConfigHandler.writeIntConfig("blaze", "highClassArchfiendDice", highClassArchfiendDice);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "HIGH CLASS ARCHFIEND DICE!", 5);
        } else if (message.contains("CRAZY RARE DROP!  (Wilson's Engineering Plans)")) {
            rng = true;
            engineeringPlans++;
            engineeringPlansSession++;
            ConfigHandler.writeIntConfig("blaze", "engineeringPlans", engineeringPlans);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "ENGINEERING PLANS!", 5);
        } else if (message.contains("CRAZY RARE DROP!  (Subzero Inverter)")) {
            rng = true;
            subzeroInverters++;
            subzeroInvertersSession++;
            ConfigHandler.writeIntConfig("blaze", "subzeroInverters", subzeroInverters);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "SUBZERO INVERTER!", 5);
        }

        if (rng) {
            time = System.currentTimeMillis() / 1000;
            bosses = 0;
            timeSession = System.currentTimeMillis() / 1000;
            bossesSession = 0;
            ConfigHandler.writeDoubleConfig("blaze", "timeRNG", time);
            ConfigHandler.writeIntConfig("blaze", "bossRNG", 0);
        }
    }

}
