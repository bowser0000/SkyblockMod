package me.Danker.features.loot;

import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WolfTracker {

    public static int wolfSvens;
    public static int wolfTeeth;
    public static int wolfWheels;
    public static int wolfWheelsDrops;
    public static int wolfSpirits;
    public static int wolfBooks;
    public static int wolfEggs;
    public static int wolfCoutures;
    public static int wolfBaits;
    public static int wolfFluxes;
    public static double wolfTime;
    public static int wolfBosses;

    public static int wolfSvensSession = 0;
    public static int wolfTeethSession = 0;
    public static int wolfWheelsSession = 0;
    public static int wolfWheelsDropsSession = 0;
    public static int wolfSpiritsSession = 0;
    public static int wolfBooksSession = 0;
    public static int wolfEggsSession = 0;
    public static int wolfCouturesSession = 0;
    public static int wolfBaitsSession = 0;
    public static int wolfFluxesSession = 0;
    public static double wolfTimeSession = -1;
    public static int wolfBossesSession = -1;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        boolean rng = false;

        if (message.contains("VERY RARE DROP!  (Enchanted Book)") || message.contains("CRAZY RARE DROP!  (Enchanted Book)")) {
            if (Utils.isInScoreboard("Sven Packmaster")) {
                wolfBooks++;
                wolfBooksSession++;
                ConfigHandler.writeIntConfig("wolf", "book", wolfBooks);
            }
        }

        if (message.contains("   Wolf Slayer LVL ")) {
            wolfSvens++;
            wolfSvensSession++;
            if (wolfBosses != -1) {
                wolfBosses++;
            }
            if (wolfBossesSession != -1) {
                wolfBossesSession++;
            }
            ConfigHandler.writeIntConfig("wolf", "svens", wolfSvens);
            ConfigHandler.writeIntConfig("wolf", "bossRNG", wolfBosses);
        } else if (message.contains("RARE DROP! (") && message.contains("Hamster Wheel)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            wolfWheels += amount;
            wolfWheelsSession += amount;
            wolfWheelsDrops++;
            wolfWheelsDropsSession++;
            ConfigHandler.writeIntConfig("wolf", "wheel", wolfWheels);
            ConfigHandler.writeIntConfig("wolf", "wheelDrops", wolfWheelsDrops);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains(" Spirit Rune I)")) { // Removing the unicode here *should* fix rune drops not counting
            wolfSpirits++;
            wolfSpiritsSession++;
            ConfigHandler.writeIntConfig("wolf", "spirit", wolfSpirits);
        } else if (message.contains("CRAZY RARE DROP!  (Red Claw Egg)")) {
            rng = true;
            wolfEggs++;
            wolfEggsSession++;
            ConfigHandler.writeIntConfig("wolf", "egg", wolfEggs);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_RED + "RED CLAW EGG!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Couture Rune I)")) {
            rng = true;
            wolfCoutures++;
            wolfCouturesSession++;
            ConfigHandler.writeIntConfig("wolf", "couture", wolfCoutures);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "COUTURE RUNE!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Grizzly Bait)") || message.contains("CRAZY RARE DROP! (Rename Me)")) { // How did Skyblock devs even manage to make this item Rename Me
            rng = true;
            wolfBaits++;
            wolfBaitsSession++;
            ConfigHandler.writeIntConfig("wolf", "bait", wolfBaits);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.AQUA + "GRIZZLY BAIT!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Overflux Capacitor)")) {
            rng = true;
            wolfFluxes++;
            wolfFluxesSession++;
            ConfigHandler.writeIntConfig("wolf", "flux", wolfFluxes);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "OVERFLUX CAPACITOR!", 5);
        }

        if (rng) {
            wolfTime = System.currentTimeMillis() / 1000;
            wolfBosses = 0;
            wolfTimeSession = System.currentTimeMillis() / 1000;
            wolfBossesSession = 0;
            ConfigHandler.writeDoubleConfig("wolf", "timeRNG", wolfTime);
            ConfigHandler.writeIntConfig("wolf", "bossRNG", 0);
        }
    }

}
