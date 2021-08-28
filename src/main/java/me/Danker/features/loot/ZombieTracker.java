package me.Danker.features.loot;

import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ZombieTracker {

    public static int zombieRevs;
    public static int zombieRevFlesh;
    public static int zombieRevViscera;
    public static int zombieFoulFlesh;
    public static int zombieFoulFleshDrops;
    public static int zombiePestilences;
    public static int zombieUndeadCatas;
    public static int zombieBooks;
    public static int zombieBeheadeds;
    public static int zombieRevCatas;
    public static int zombieSnakes;
    public static int zombieScythes;
    public static int zombieShards;
    public static int zombieWardenHearts;
    public static double zombieTime;
    public static int zombieBosses;

    public static int zombieRevsSession = 0;
    public static int zombieRevFleshSession = 0;
    public static int zombieRevVisceraSession = 0;
    public static int zombieFoulFleshSession = 0;
    public static int zombieFoulFleshDropsSession = 0;
    public static int zombiePestilencesSession = 0;
    public static int zombieUndeadCatasSession = 0;
    public static int zombieBooksSession = 0;
    public static int zombieBeheadedsSession = 0;
    public static int zombieRevCatasSession = 0;
    public static int zombieSnakesSession = 0;
    public static int zombieScythesSession = 0;
    public static int zombieShardsSession = 0;
    public static int zombieWardenHeartsSession = 0;
    public static double zombieTimeSession = -1;
    public static int zombieBossesSession = -1;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        boolean rng = false;

        if (message.contains("VERY RARE DROP!  (Enchanted Book)") || message.contains("CRAZY RARE DROP!  (Enchanted Book)")) {
            if (Utils.isInScoreboard("Revenant Horror")) {
                zombieBooks++;
                zombieBooksSession++;
                ConfigHandler.writeIntConfig("zombie", "book", zombieBooks);
            }
        }

        if (message.contains("   Zombie Slayer LVL ")) { // Zombie
            zombieRevs++;
            zombieRevsSession++;
            if (zombieBosses != -1) {
                zombieBosses++;
            }
            if (zombieBossesSession != 1) {
                zombieBossesSession++;
            }
            ConfigHandler.writeIntConfig("zombie", "revs", zombieRevs);
            ConfigHandler.writeIntConfig("zombie", "bossRNG", zombieBosses);
        } else if (message.contains("RARE DROP! (") && message.contains("Revenant Viscera)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            zombieRevViscera += amount;
            zombieRevVisceraSession += amount;
            ConfigHandler.writeIntConfig("zombie", "revViscera", zombieRevViscera);
        } else if (message.contains("RARE DROP! (") && message.contains("Foul Flesh)")) {
            int amount = LootTracker.getAmountfromMessage(message);
            zombieFoulFlesh += amount;
            zombieFoulFleshSession += amount;
            zombieFoulFleshDrops++;
            zombieFoulFleshDropsSession++;
            ConfigHandler.writeIntConfig("zombie", "foulFlesh", zombieFoulFlesh);
            ConfigHandler.writeIntConfig("zombie", "foulFleshDrops", zombieFoulFleshDrops);
        } else if (message.contains("VERY RARE DROP!  (Revenant Catalyst)")) {
            zombieRevCatas++;
            zombieRevCatasSession++;
            ConfigHandler.writeIntConfig("zombie", "revCatalyst", zombieRevCatas);
        } else if (message.contains("VERY RARE DROP!  (") && message.contains(" Pestilence Rune I)")) {
            zombiePestilences++;
            zombiePestilencesSession++;
            ConfigHandler.writeIntConfig("zombie", "pestilence", zombiePestilences);
        } else if (message.contains("VERY RARE DROP!  (Undead Catalyst)")) {
            zombieUndeadCatas++;
            zombieUndeadCatasSession++;
            ConfigHandler.writeIntConfig("zombie", "undeadCatalyst", zombieUndeadCatas);
        } else if (message.contains("CRAZY RARE DROP!  (Beheaded Horror)")) {
            rng = true;
            zombieBeheadeds++;
            zombieBeheadedsSession++;
            ConfigHandler.writeIntConfig("zombie", "beheaded", zombieBeheadeds);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "BEHEADED HORROR!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (") && message.contains(" Snake Rune I)")) {
            rng = true;
            zombieSnakes++;
            zombieSnakesSession++;
            ConfigHandler.writeIntConfig("zombie", "snake", zombieSnakes);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.DARK_GREEN + "SNAKE RUNE!", 3);
        } else if (message.contains("CRAZY RARE DROP!  (Scythe Blade)")) {
            rng = true;
            zombieScythes++;
            zombieScythesSession++;
            ConfigHandler.writeIntConfig("zombie", "scythe", zombieScythes);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.GOLD + "SCYTHE BLADE!", 5);
        } else if (message.contains("CRAZY RARE DROP!  (Shard of the Shredded)")) {
            rng = true;
            zombieShards++;
            zombieShardsSession++;
            ConfigHandler.writeIntConfig("zombie", "shard", zombieShards);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "SHARD OF THE SHREDDED!", 5);
        } else if (message.contains("INSANE DROP!  (Warden Heart)") || message.contains("CRAZY RARE DROP!  (Warden Heart)")) {
            rng = true;
            zombieWardenHearts++;
            zombieWardenHeartsSession++;
            ConfigHandler.writeIntConfig("zombie", "heart", zombieWardenHearts);
            if (ToggleCommand.rngesusAlerts) Utils.createTitle(EnumChatFormatting.RED + "WARDEN HEART!", 5);
        }

        if (rng) {
            zombieTime = System.currentTimeMillis() / 1000;
            zombieBosses = 0;
            zombieTimeSession = System.currentTimeMillis() / 1000;
            zombieBossesSession = 0;
            ConfigHandler.writeDoubleConfig("zombie", "timeRNG", zombieTime);
            ConfigHandler.writeIntConfig("zombie", "bossRNG", 0);
        }
    }

}
