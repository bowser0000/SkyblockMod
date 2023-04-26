package me.Danker.features.loot;

import me.Danker.config.CfgConfig;
import me.Danker.config.ModConfig;
import me.Danker.events.SlayerLootDropEvent;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EndermanTracker {

    public static int voidglooms;
    public static int nullSpheres;
    public static int TAP;
    public static int TAPDrops;
    public static int endersnakes;
    public static int summoningEyes;
    public static int manaBooks;
    public static int tuners;
    public static int atoms;
    public static int hazmats;
    public static int espressoMachines;
    public static int smartyBooks;
    public static int endRunes;
    public static int chalices;
    public static int dice;
    public static int artifacts;
    public static int skins;
    public static int mergers;
    public static int cores;
    public static int enchantRunes;
    public static int enderBooks;
    public static double time;
    public static int bosses;

    public static int voidgloomsSession = 0;
    public static int nullSpheresSession = 0;
    public static int TAPSession = 0;
    public static int TAPDropsSession = 0;
    public static int endersnakesSession = 0;
    public static int summoningEyesSession = 0;
    public static int manaBooksSession = 0;
    public static int tunersSession = 0;
    public static int atomsSession = 0;
    public static int hazmatsSession = 0;
    public static int espressoMachinesSession = 0;
    public static int smartyBooksSession = 0;
    public static int endRunesSession = 0;
    public static int chalicesSession = 0;
    public static int diceSession = 0;
    public static int artifactsSession = 0;
    public static int skinsSession = 0;
    public static int mergersSession = 0;
    public static int coresSession = 0;
    public static int enchantRunesSession = 0;
    public static int enderBooksSession = 0;
    public static double timeSession = -1;
    public static int bossesSession = -1;

    @SubscribeEvent
    public void onLootDrop(SlayerLootDropEvent event) {
        boolean rng = false;

        switch (event.drop) {
            case "Twilight Arrow Poison":
                TAP += event.amount;
                TAPSession += event.amount;
                TAPDrops++;
                TAPDropsSession++;
                CfgConfig.writeIntConfig("enderman", "tap", TAP);
                CfgConfig.writeIntConfig("enderman", "tapDrops", TAPDrops);
                break;
            case "◆ Endersnake Rune I":
                endersnakes += event.amount;
                endersnakesSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "endersnakes", endersnakes);
                break;
            case "Summoning Eye":
                summoningEyes += event.amount;
                summoningEyesSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "summoningEyes", summoningEyes);
                break;
            case "Mana Steal I":
                manaBooks += event.amount;
                manaBooksSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "manaBooks", manaBooks);
                break;
            case "Transmission Tuner":
                tuners += event.amount;
                tunersSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "tuners", tuners);
                break;
            case "Null Atom":
                atoms += event.amount;
                atomsSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "atoms", atoms);
                break;
            case "Hazmat Enderman":
                hazmats += event.amount;
                hazmatsSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "hazmats", hazmats);
                break;
            case "Pocket Espresso Machine":
                rng = true;
                espressoMachines += event.amount;
                espressoMachinesSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "espressoMachines", espressoMachines);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.AQUA + "POCKET ESPRESSO MACHINE!", 3);
                break;
            case "Smarty Pants I":
                smartyBooks += event.amount;
                smartyBooksSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "smartyBooks", smartyBooks);
                break;
            case "◆ End Rune I":
                endRunes += event.amount;
                endRunesSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "endRunes", endRunes);
                break;
            case "Handy Blood Chalice":
                rng = true;
                chalices += event.amount;
                chalicesSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "chalices", chalices);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "HANDY BLOOD CHALICE!", 3);
                break;
            case "Sinful Dice":
                dice += event.amount;
                diceSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "dice", dice);
                break;
            case "Exceedingly Rare Ender Artifact Upgrader":
                rng = true;
                artifacts += event.amount;
                artifactsSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "artifacts", artifacts);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "ENDER ARTIFACT UPGRADER!", 3);
                break;
            case "Void Conqueror Enderman Skin":
                rng = true;
                skins += event.amount;
                skinsSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "skins", skins);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "ENDERMAN SKIN!", 3);
                break;
            case "Etherwarp Merger":
                mergers += event.amount;
                mergersSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "mergers", mergers);
                break;
            case "Judgement Core":
                rng = true;
                cores += event.amount;
                coresSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "cores", cores);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "JUDGEMENT CORE!", 5);
                break;
            case "◆ Enchant Rune I":
                rng = true;
                enchantRunes += event.amount;
                enchantRunesSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "enchantRunes", enchantRunes);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GRAY + "ENCHANT RUNE!", 3);
                break;
            case "Ender Slayer VII":
                rng = true;
                enderBooks += event.amount;
                enderBooksSession += event.amount;
                CfgConfig.writeIntConfig("enderman", "enderBooks", enderBooks);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "ENDER SLAYER VII!", 3);
                break;
        }

        if (rng) {
            time = System.currentTimeMillis() / 1000;
            bosses = 0;
            timeSession = System.currentTimeMillis() / 1000;
            bossesSession = 0;
            CfgConfig.writeDoubleConfig("enderman", "timeRNG", time);
            CfgConfig.writeIntConfig("enderman", "bossRNG", 0);
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.contains(":")) return;

        if (message.contains("   Enderman Slayer LVL ")) {
            voidglooms++;
            voidgloomsSession++;
            if (bosses != -1) {
                bosses++;
            }
            if (bossesSession != -1) {
                bossesSession++;
            }
            CfgConfig.writeIntConfig("enderman", "voidglooms", voidglooms);
            CfgConfig.writeIntConfig("enderman", "bossRNG", bosses);
        }
    }

}
