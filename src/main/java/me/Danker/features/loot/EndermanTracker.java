package me.Danker.features.loot;

import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EndermanTracker {

    public static int endermanVoidglooms;
    public static int endermanNullSpheres;
    public static int endermanTAP;
    public static int endermanTAPDrops;
    public static int endermanEndersnakes;
    public static int endermanSummoningEyes;
    public static int endermanManaBooks;
    public static int endermanTuners;
    public static int endermanAtoms;
    public static int endermanEspressoMachines;
    public static int endermanSmartyBooks;
    public static int endermanEndRunes;
    public static int endermanChalices;
    public static int endermanDice;
    public static int endermanArtifacts;
    public static int endermanSkins;
    public static int endermanMergers;
    public static int endermanCores;
    public static int endermanEnchantRunes;
    public static int endermanEnderBooks;
    public static double endermanTime;
    public static int endermanBosses;

    public static int endermanVoidgloomsSession = 0;
    public static int endermanNullSpheresSession = 0;
    public static int endermanTAPSession = 0;
    public static int endermanTAPDropsSession = 0;
    public static int endermanEndersnakesSession = 0;
    public static int endermanSummoningEyesSession = 0;
    public static int endermanManaBooksSession = 0;
    public static int endermanTunersSession = 0;
    public static int endermanAtomsSession = 0;
    public static int endermanEspressoMachinesSession = 0;
    public static int endermanSmartyBooksSession = 0;
    public static int endermanEndRunesSession = 0;
    public static int endermanChalicesSession = 0;
    public static int endermanDiceSession = 0;
    public static int endermanArtifactsSession = 0;
    public static int endermanSkinsSession = 0;
    public static int endermanMergersSession = 0;
    public static int endermanCoresSession = 0;
    public static int endermanEnchantRunesSession = 0;
    public static int endermanEnderBooksSession = 0;
    public static double endermanTimeSession = -1;
    public static int endermanBossesSession = -1;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        boolean rng = false;

        if (message.contains("   Enderman Slayer LVL ")) {
            endermanVoidglooms++;
            endermanVoidgloomsSession++;
            if (endermanBosses != -1) {
                endermanBosses++;
            }
            if (endermanBossesSession != -1) {
                endermanBossesSession++;
            }
            ConfigHandler.writeIntConfig("enderman", "voidglooms", endermanVoidglooms);
            ConfigHandler.writeIntConfig("enderman", "bossRNG", endermanBosses);
        } else if (message.contains("RARE DROP! (") && message.contains("Twilight Arrow Poison)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            endermanTAP += amount;
            endermanTAPSession += amount;
            endermanTAPDrops++;
            endermanTAPDropsSession++;
            ConfigHandler.writeIntConfig("enderman", "tap", endermanTAP);
            ConfigHandler.writeIntConfig("enderman", "tapDrops", endermanTAPDrops);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains(" Endersnake Rune I)")) {
            endermanEndersnakes++;
            endermanEndersnakesSession++;
            ConfigHandler.writeIntConfig("enderman", "endersnakes", endermanEndersnakes);
        } else if (message.contains("VERY RARE DROP!  (Summoning Eye)")) {
            endermanSummoningEyes++;
            endermanSummoningEyesSession++;
            ConfigHandler.writeIntConfig("enderman", "summoningEyes", endermanSummoningEyes);
        } else if (message.contains("VERY RARE DROP!  (Mana Steal I)")) {
            endermanManaBooks++;
            endermanManaBooksSession++;
            ConfigHandler.writeIntConfig("enderman", "manaBooks", endermanManaBooks);
        } else if (message.contains("VERY RARE DROP!  (Transmission Tuner)")) {
            endermanTuners++;
            endermanTunersSession++;
            ConfigHandler.writeIntConfig("enderman", "tuners", endermanTuners);
        } else if (message.contains("VERY RARE DROP!  (Null Atom)")) {
            endermanAtoms++;
            endermanAtomsSession++;
            ConfigHandler.writeIntConfig("enderman", "atoms", endermanAtoms);
        } else if (message.contains("CRAZY RARE DROP!  (Pocket Espresso Machine)")) {
            rng = true;
            endermanEspressoMachines++;
            endermanEspressoMachinesSession++;
            ConfigHandler.writeIntConfig("enderman", "espressoMachines", endermanEspressoMachines);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.AQUA + "POCKET ESPRESSO MACHINE!", 3);
        } else if (message.contains("VERY RARE DROP!  (Smarty Pants I)")) {
            endermanSmartyBooks++;
            endermanSmartyBooksSession++;
            ConfigHandler.writeIntConfig("enderman", "smartyBooks", endermanSmartyBooks);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains(" End Rune I)")) {
            endermanEndRunes++;
            endermanEndRunesSession++;
            ConfigHandler.writeIntConfig("enderman", "endRunes", endermanEndRunes);
        } else if (message.contains("CRAZY RARE DROP!  (Handy Blood Chalice)")) {
            rng = true;
            endermanChalices++;
            endermanChalicesSession++;
            ConfigHandler.writeIntConfig("enderman", "chalices", endermanChalices);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "HANDY BLOOD CHALICE!", 3);
        } else if (message.contains("VERY RARE DROP!  (Sinful Dice)")) {
            endermanDice++;
            endermanDiceSession++;
            ConfigHandler.writeIntConfig("enderman", "dice", endermanDice);
        } else if (message.contains("CRAZY RARE DROP!  (Exceedingly Rare Ender Artifact Upgrader)")) {
            rng = true;
            endermanArtifacts++;
            endermanArtifactsSession++;
            ConfigHandler.writeIntConfig("enderman", "artifacts", endermanArtifacts);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "ENDER ARTIFACT UPGRADER!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Void Conqueror Enderman Skin)")) {
            rng = true;
            endermanSkins++;
            endermanSkinsSession++;
            ConfigHandler.writeIntConfig("enderman", "skins", endermanSkins);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "ENDERMAN SKIN!", 3);
        } else if (message.contains("VERY RARE DROP!  (Etherwarp Merger)")) {
            endermanMergers++;
            endermanMergersSession++;
            ConfigHandler.writeIntConfig("enderman", "mergers", endermanMergers);
        } else if (message.contains("CRAZY RARE DROP!  (Judgement Core)")) {
            rng = true;
            endermanCores++;
            endermanCoresSession++;
            ConfigHandler.writeIntConfig("enderman", "cores", endermanCores);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "JUDGEMENT CORE!", 5);
        } else if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Enchant Rune I)")) {
            rng = true;
            endermanEnchantRunes++;
            endermanEnchantRunesSession++;
            ConfigHandler.writeIntConfig("enderman", "enchantRunes", endermanEnchantRunes);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GRAY + "ENCHANT RUNE!", 3);
        } else if (message.contains("INSANE DROP!  (Ender Slayer VII)") || message.contains("CRAZY RARE DROP!  (Ender Slayer VII)")) {
            rng = true;
            endermanEnderBooks++;
            endermanEnderBooksSession++;
            ConfigHandler.writeIntConfig("enderman", "enderBooks", endermanEnderBooks);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "ENDER SLAYER VII!", 3);
        }

        if (rng) {
            endermanTime = System.currentTimeMillis() / 1000;
            endermanBosses = 0;
            endermanTimeSession = System.currentTimeMillis() / 1000;
            endermanBossesSession = 0;
            ConfigHandler.writeDoubleConfig("enderman", "timeRNG", endermanTime);
            ConfigHandler.writeIntConfig("enderman", "bossRNG", 0);
        }
    }

}
