package me.Danker.features.loot;

import me.Danker.config.CfgConfig;
import me.Danker.config.ModConfig;
import me.Danker.events.SlayerLootDropEvent;
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
    public void onLootDrop(SlayerLootDropEvent event) {
        boolean rng = false;

        switch (event.drop) {
            case "Hamster Wheel":
                wheels += event.amount;
                wheelsSession += event.amount;
                wheelsDrops++;
                wheelsDropsSession++;
                CfgConfig.writeIntConfig("wolf", "wheel", wheels);
                CfgConfig.writeIntConfig("wolf", "wheelDrops", wheelsDrops);
                break;
            case "◆ Spirit Rune I":
                spirits += event.amount;
                spiritsSession += event.amount;
                CfgConfig.writeIntConfig("wolf", "spirit", spirits);
                break;
            case "Critical VI":
                books += event.amount;
                booksSession += event.amount;
                CfgConfig.writeIntConfig("wolf", "book", books);
                break;
            case "Furball":
                furballs += event.amount;
                furballsSession += event.amount;
                CfgConfig.writeIntConfig("wolf", "furball", furballs);
                break;
            case "Red Claw Egg":
                rng = true;
                eggs += event.amount;
                eggsSession += event.amount;
                CfgConfig.writeIntConfig("wolf", "egg", eggs);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_RED + "RED CLAW EGG!", 3);
                break;
            case "◆ Couture Rune I":
                rng = true;
                coutures += event.amount;
                couturesSession += event.amount;
                CfgConfig.writeIntConfig("wolf", "couture", coutures);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "COUTURE RUNE!", 3);
                break;
            case "Grizzly Bait":
            case "Rename Me":
                rng = true;
                baits += event.amount;
                baitsSession += event.amount;
                CfgConfig.writeIntConfig("wolf", "bait", baits);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.AQUA + "GRIZZLY BAIT!", 3);
                break;
            case "Overflux Capacitor":
                rng = true;
                fluxes += event.amount;
                fluxesSession += event.amount;
                CfgConfig.writeIntConfig("wolf", "flux", fluxes);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "OVERFLUX CAPACITOR!", 5);
                break;
        }

        if (rng) {
            time = System.currentTimeMillis() / 1000;
            bosses = 0;
            timeSession = System.currentTimeMillis() / 1000;
            bossesSession = 0;
            CfgConfig.writeDoubleConfig("wolf", "timeRNG", time);
            CfgConfig.writeIntConfig("wolf", "bossRNG", 0);
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.contains(":")) return;

        if (message.contains("   Wolf Slayer LVL ")) {
            svens++;
            svensSession++;
            if (bosses != -1) {
                bosses++;
            }
            if (bossesSession != -1) {
                bossesSession++;
            }
            CfgConfig.writeIntConfig("wolf", "svens", svens);
            CfgConfig.writeIntConfig("wolf", "bossRNG", bosses);
        }
    }

}
