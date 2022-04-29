package me.Danker.features.loot;

import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WolfTracker {

    public static int svens;
    public static int teeth;
    public static int wheels;
    public static int wheelsDrops;
    public static int spirits;
    public static int books;
    public static int furballs;
    public static int eggs;
    public static int coutures;
    public static int baits;
    public static int fluxes;
    public static double time;
    public static int bosses;

    public static int svensSession = 0;
    public static int teethSession = 0;
    public static int wheelsSession = 0;
    public static int wheelsDropsSession = 0;
    public static int spiritsSession = 0;
    public static int booksSession = 0;
    public static int furballsSession = 0;
    public static int eggsSession = 0;
    public static int couturesSession = 0;
    public static int baitsSession = 0;
    public static int fluxesSession = 0;
    public static double timeSession = -1;
    public static int bossesSession = -1;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        boolean rng = false;

        if (message.contains("   Wolf Slayer LVL ")) {
            svens++;
            svensSession++;
            if (bosses != -1) {
                bosses++;
            }
            if (bossesSession != -1) {
                bossesSession++;
            }
            ConfigHandler.writeIntConfig("wolf", "svens", svens);
            ConfigHandler.writeIntConfig("wolf", "bossRNG", bosses);
        } else if (message.contains("RARE DROP! (") && message.contains("Hamster Wheel)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            wheels += amount;
            wheelsSession += amount;
            wheelsDrops++;
            wheelsDropsSession++;
            ConfigHandler.writeIntConfig("wolf", "wheel", wheels);
            ConfigHandler.writeIntConfig("wolf", "wheelDrops", wheelsDrops);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains(" Spirit Rune I)")) { // Removing the unicode here *should* fix rune drops not counting
            spirits++;
            spiritsSession++;
            ConfigHandler.writeIntConfig("wolf", "spirit", spirits);
        } else if (message.contains("VERY RARE DROP!  (Critical VI)")) {
            books++;
            booksSession++;
            ConfigHandler.writeIntConfig("wolf", "book", books);
        } else if (message.contains("VERY RARE DROP!  (Furball)")) {
            furballs++;
            furballsSession++;
            ConfigHandler.writeIntConfig("wolf", "furball", furballs);
        } else if (message.contains("CRAZY RARE DROP!  (Red Claw Egg)")) {
            rng = true;
            eggs++;
            eggsSession++;
            ConfigHandler.writeIntConfig("wolf", "egg", eggs);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_RED + "RED CLAW EGG!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Couture Rune I)")) {
            rng = true;
            coutures++;
            couturesSession++;
            ConfigHandler.writeIntConfig("wolf", "couture", coutures);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "COUTURE RUNE!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Grizzly Bait)") || message.contains("CRAZY RARE DROP! (Rename Me)")) { // How did Skyblock devs even manage to make this item Rename Me
            rng = true;
            baits++;
            baitsSession++;
            ConfigHandler.writeIntConfig("wolf", "bait", baits);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.AQUA + "GRIZZLY BAIT!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Overflux Capacitor)")) {
            rng = true;
            fluxes++;
            fluxesSession++;
            ConfigHandler.writeIntConfig("wolf", "flux", fluxes);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "OVERFLUX CAPACITOR!", 5);
        }

        if (rng) {
            time = System.currentTimeMillis() / 1000;
            bosses = 0;
            timeSession = System.currentTimeMillis() / 1000;
            bossesSession = 0;
            ConfigHandler.writeDoubleConfig("wolf", "timeRNG", time);
            ConfigHandler.writeIntConfig("wolf", "bossRNG", 0);
        }
    }

}
