package me.Danker.features.loot;

import me.Danker.config.CfgConfig;
import me.Danker.config.ModConfig;
import me.Danker.events.SlayerLootDropEvent;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ZombieTracker {

    public static int revs;
    public static int revFlesh;
    public static int revViscera;
    public static int foulFlesh;
    public static int foulFleshDrops;
    public static int pestilences;
    public static int undeadCatas;
    public static int books;
    public static int booksT7;
    public static int beheadeds;
    public static int revCatas;
    public static int snakes;
    public static int scythes;
    public static int shards;
    public static int wardenHearts;
    public static double time;
    public static int bosses;

    public static int revsSession = 0;
    public static int revFleshSession = 0;
    public static int revVisceraSession = 0;
    public static int foulFleshSession = 0;
    public static int foulFleshDropsSession = 0;
    public static int pestilencesSession = 0;
    public static int undeadCatasSession = 0;
    public static int booksSession = 0;
    public static int booksT7Session = 0;
    public static int beheadedsSession = 0;
    public static int revCatasSession = 0;
    public static int snakesSession = 0;
    public static int scythesSession = 0;
    public static int shardsSession = 0;
    public static int wardenHeartsSession = 0;
    public static double timeSession = -1;
    public static int bossesSession = -1;

    @SubscribeEvent
    public void onLootDrop(SlayerLootDropEvent event) {
        boolean rng = false;

        switch (event.drop) {
            case "Revenant Viscera":
                revViscera += event.amount;
                revVisceraSession += event.amount;
                CfgConfig.writeIntConfig("zombie", "revViscera", revViscera);
                break;
            case "Foul Flesh":
                foulFlesh += event.amount;
                foulFleshSession += event.amount;
                foulFleshDrops++;
                foulFleshDropsSession++;
                CfgConfig.writeIntConfig("zombie", "foulFlesh", foulFlesh);
                CfgConfig.writeIntConfig("zombie", "foulFleshDrops", foulFleshDrops);
                break;
            case "Revenant Catalyst":
                revCatas += event.amount;
                revCatasSession += event.amount;
                CfgConfig.writeIntConfig("zombie", "revCatalyst", revCatas);
                break;
            case "◆ Pestilence Rune I":
                pestilences += event.amount;
                pestilencesSession += event.amount;
                CfgConfig.writeIntConfig("zombie", "pestilence", pestilences);
                break;
            case "Smite VI":
                books += event.amount;
                booksSession += event.amount;
                CfgConfig.writeIntConfig("zombie", "book", books);
                break;
            case "Smite VII":
                booksT7 += event.amount;
                booksT7Session += event.amount;
                CfgConfig.writeIntConfig("zombie", "bookT7", booksT7);
                break;
            case "Undead Catalyst":
                undeadCatas += event.amount;
                undeadCatasSession += event.amount;
                CfgConfig.writeIntConfig("zombie", "undeadCatalyst", undeadCatas);
                break;
            case "Beheaded Horror":
                rng = true;
                beheadeds += event.amount;
                beheadedsSession += event.amount;
                CfgConfig.writeIntConfig("zombie", "beheaded", beheadeds);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "BEHEADED HORROR!", 3);
                break;
            case "◆ Snake Rune I":
                rng = true;
                snakes += event.amount;
                snakesSession += event.amount;
                CfgConfig.writeIntConfig("zombie", "snake", snakes);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_GREEN + "SNAKE RUNE!", 3);
                break;
            case "Scythe Blade":
                rng = true;
                scythes += event.amount;
                scythesSession += event.amount;
                CfgConfig.writeIntConfig("zombie", "scythe", scythes);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "SCYTHE BLADE!", 5);
                break;
            case "Shard of the Shredded":
                rng = true;
                shards += event.amount;
                shardsSession += event.amount;
                CfgConfig.writeIntConfig("zombie", "shard", shards);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "SHARD OF THE SHREDDED!", 5);
                break;
            case "WARDEN HEART":
                rng = true;
                wardenHearts += event.amount;
                wardenHeartsSession += event.amount;
                CfgConfig.writeIntConfig("zombie", "heart", wardenHearts);
                if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "WARDEN HEART!", 5);
                break;
        }

        if (rng) {
            time = System.currentTimeMillis() / 1000;
            bosses = 0;
            timeSession = System.currentTimeMillis() / 1000;
            bossesSession = 0;
            CfgConfig.writeDoubleConfig("zombie", "timeRNG", time);
            CfgConfig.writeIntConfig("zombie", "bossRNG", 0);
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.contains(":")) return;

        if (message.contains("   Zombie Slayer LVL ")) {
            revs++;
            revsSession++;
            if (bosses != -1) {
                bosses++;
            }
            if (bossesSession != 1) {
                bossesSession++;
            }
            CfgConfig.writeIntConfig("zombie", "revs", revs);
            CfgConfig.writeIntConfig("zombie", "bossRNG", bosses);
        }
    }

}
