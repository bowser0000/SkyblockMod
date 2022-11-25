package me.Danker.features.loot;

import me.Danker.config.ModConfig;
import me.Danker.handlers.ConfigHandler;
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
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        boolean rng = false;

        if (message.contains("   Zombie Slayer LVL ")) { // Zombie
            revs++;
            revsSession++;
            if (bosses != -1) {
                bosses++;
            }
            if (bossesSession != 1) {
                bossesSession++;
            }
            ConfigHandler.writeIntConfig("zombie", "revs", revs);
            ConfigHandler.writeIntConfig("zombie", "bossRNG", bosses);
        } else if (message.contains("RARE DROP! (") && message.contains("Revenant Viscera)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            revViscera += amount;
            revVisceraSession += amount;
            ConfigHandler.writeIntConfig("zombie", "revViscera", revViscera);
        } else if (message.contains("RARE DROP! (") && message.contains("Foul Flesh)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            foulFlesh += amount;
            foulFleshSession += amount;
            foulFleshDrops++;
            foulFleshDropsSession++;
            ConfigHandler.writeIntConfig("zombie", "foulFlesh", foulFlesh);
            ConfigHandler.writeIntConfig("zombie", "foulFleshDrops", foulFleshDrops);
        } else if (message.contains("VERY RARE DROP!  (Revenant Catalyst)")) {
            revCatas++;
            revCatasSession++;
            ConfigHandler.writeIntConfig("zombie", "revCatalyst", revCatas);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains(" Pestilence Rune I)")) {
            pestilences++;
            pestilencesSession++;
            ConfigHandler.writeIntConfig("zombie", "pestilence", pestilences);
        } else if (message.contains("VERY RARE DROP!  (Smite VI)")) {
            books++;
            booksSession++;
            ConfigHandler.writeIntConfig("zombie", "book", books);
        } else if (message.contains("VERY RARE DROP!  (Smite VII)")) {
            booksT7++;
            booksT7Session++;
            ConfigHandler.writeIntConfig("zombie", "bookT7", booksT7);
        } else if (message.contains("VERY RARE DROP!  (Undead Catalyst)")) {
            undeadCatas++;
            undeadCatasSession++;
            ConfigHandler.writeIntConfig("zombie", "undeadCatalyst", undeadCatas);
        } else if (message.contains("CRAZY RARE DROP!  (Beheaded Horror)")) {
            rng = true;
            beheadeds++;
            beheadedsSession++;
            ConfigHandler.writeIntConfig("zombie", "beheaded", beheadeds);
            if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "BEHEADED HORROR!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Snake Rune I)")) {
            rng = true;
            snakes++;
            snakesSession++;
            ConfigHandler.writeIntConfig("zombie", "snake", snakes);
            if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_GREEN + "SNAKE RUNE!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Scythe Blade)")) {
            rng = true;
            scythes++;
            scythesSession++;
            ConfigHandler.writeIntConfig("zombie", "scythe", scythes);
            if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "SCYTHE BLADE!", 5);
        } else if (message.contains("CRAZY RARE DROP!  (Shard of the Shredded)")) {
            rng = true;
            shards++;
            shardsSession++;
            ConfigHandler.writeIntConfig("zombie", "shard", shards);
            if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "SHARD OF THE SHREDDED!", 5);
        } else if (message.contains("INSANE DROP!  (Warden Heart)") || message.contains("CRAZY RARE DROP!  (Warden Heart)")) {
            rng = true;
            wardenHearts++;
            wardenHeartsSession++;
            ConfigHandler.writeIntConfig("zombie", "heart", wardenHearts);
            if (ModConfig.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "WARDEN HEART!", 5);
        }

        if (rng) {
            time = System.currentTimeMillis() / 1000;
            bosses = 0;
            timeSession = System.currentTimeMillis() / 1000;
            bossesSession = 0;
            ConfigHandler.writeDoubleConfig("zombie", "timeRNG", time);
            ConfigHandler.writeIntConfig("zombie", "bossRNG", 0);
        }
    }

}
