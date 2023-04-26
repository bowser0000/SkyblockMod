package me.Danker.features.loot;

import me.Danker.config.CfgConfig;
import me.Danker.config.ModConfig;
import me.Danker.events.SlayerLootDropEvent;
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
    public void onLootDrop(SlayerLootDropEvent event) {
        boolean rng = false;

        switch (event.drop) {
            case "◆ Lavatears Rune I":
                lavatearRunes += event.amount;
                lavatearRunesSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "lavatearRunes", lavatearRunes);
                break;
            case "Wisp's Ice-Flavored Water I Splash Potion":
                splashPotions += event.amount;
                splashPotionsSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "splashPotions", splashPotions);
                break;
            case "Bundle of Magma Arrows":
                magmaArrows += event.amount;
                magmaArrowsSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "magmaArrows", magmaArrows);
                break;
            case "Mana Disintegrator":
                manaDisintegrators += event.amount;
                manaDisintegratorsSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "manaDisintegrators", manaDisintegrators);
                break;
            case "Scorched Books":
                scorchedBooks += event.amount;
                scorchedBooksSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "scorchedBooks", scorchedBooks);
                break;
            case "Kelvin Inverter":
                kelvinInverters += event.amount;
                kelvinInvertersSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "kelvinInverters", kelvinInverters);
                break;
            case "Blaze Rod Distillate":
                blazeRodDistillates += event.amount;
                blazeRodDistillatesSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "blazeRodDistillates", blazeRodDistillates);
                break;
            case "Glowstone Distillate":
                glowstoneDistillates += event.amount;
                glowstoneDistillatesSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "glowstoneDistillates", glowstoneDistillates);
                break;
            case "Magma Cream Distillate":
                magmaCreamDistillates += event.amount;
                magmaCreamDistillatesSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "magmaCreamDistillates", magmaCreamDistillates);
                break;
            case "Nether Wart Distillate":
                netherWartDistillates += event.amount;
                netherWartDistillatesSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "netherWartDistillates", netherWartDistillates);
                break;
            case "Gabagool Distillate":
                gabagoolDistillates += event.amount;
                gabagoolDistillatesSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "gabagoolDistillates", gabagoolDistillates);
                break;
            case "Scorched Power Crystal":
                scorchedPowerCrystals += event.amount;
                scorchedPowerCrystalsSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "scorchedPowerCrystals", scorchedPowerCrystals);
                break;
            case "Fire Aspect III":
                fireAspectBooks += event.amount;
                fireAspectBooksSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "fireAspectBooks", fireAspectBooks);
                break;
            case "◆ Fiery Burst Rune I":
                rng = true;
                fieryBurstRunes += event.amount;
                fieryBurstRunesSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "fieryBurstRunes", fieryBurstRunes);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "FIERY BURST RUNE!", 3);
                break;
            case "Flawed Opal Gemstone":
                opalGems += event.amount;
                opalGemsSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "opalGems", opalGems);
                break;
            case "Archfiend Dice":
                archfiendDice += event.amount;
                archfiendDiceSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "archfiendDice", archfiendDice);
                break;
            case "Duplex I":
                duplexBooks += event.amount;
                duplexBooksSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "duplexBooks", duplexBooks);
                break;
            case "High Class Archfiend Dice":
                rng = true;
                highClassArchfiendDice += event.amount;
                highClassArchfiendDiceSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "highClassArchfiendDice", highClassArchfiendDice);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "HIGH CLASS ARCHFIEND DICE!", 5);
                break;
            case "Wilson's Engineering Plans":
                rng = true;
                engineeringPlans += event.amount;
                engineeringPlansSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "engineeringPlans", engineeringPlans);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "ENGINEERING PLANS!", 3);
                break;
            case "Subzero Inverter":
                rng = true;
                subzeroInverters += event.amount;
                subzeroInvertersSession += event.amount;
                CfgConfig.writeIntConfig("blaze", "subzeroInverters", subzeroInverters);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "SUBZERO INVERTER!", 3);
                break;
        }

        if (rng) {
            time = System.currentTimeMillis() / 1000;
            bosses = 0;
            timeSession = System.currentTimeMillis() / 1000;
            bossesSession = 0;
            CfgConfig.writeDoubleConfig("blaze", "timeRNG", time);
            CfgConfig.writeIntConfig("blaze", "bossRNG", 0);
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.contains(":")) return;

        if (message.contains("   Blaze Slayer LVL ")) {
            demonlords++;
            demonlordsSession++;
            if (bosses != -1) {
                bosses++;
            }
            if (bossesSession != -1) {
                bossesSession++;
            }
            CfgConfig.writeIntConfig("blaze", "demonlords", demonlords);
            CfgConfig.writeIntConfig("blaze", "bossRNG", bosses);
        }
    }

}
