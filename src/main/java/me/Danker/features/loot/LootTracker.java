package me.Danker.features.loot;

import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.utils.Utils;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class LootTracker {

    public static long itemsChecked = 0;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onSound(PlaySoundEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.name.equals("note.pling")) {
            // Don't check twice within 3 seconds
            long checkItemsNow = System.currentTimeMillis() / 1000;
            if (checkItemsNow - itemsChecked < 3) return;

            List<String> scoreboard = ScoreboardHandler.getSidebarLines();

            for (String line : scoreboard) {
                String cleanedLine = ScoreboardHandler.cleanSB(line);
                // If Hypixel lags and scoreboard doesn't update
                if (cleanedLine.contains("Boss slain!") || cleanedLine.contains("Slay the boss!")) {
                    int itemTeeth = Utils.getItems("Wolf Tooth");
                    int itemWebs = Utils.getItems("Tarantula Web");
                    int itemRev = Utils.getItems("Revenant Flesh");
                    int itemNullSphere = Utils.getItems("Null Sphere");

                    // If no items, are detected, allow check again. Should fix items not being found
                    if (itemTeeth + itemWebs + itemRev + itemNullSphere > 0) {
                        itemsChecked = System.currentTimeMillis() / 1000;
                        WolfTracker.wolfTeeth += itemTeeth;
                        SpiderTracker.spiderWebs += itemWebs;
                        ZombieTracker.zombieRevFlesh += itemRev;
                        EndermanTracker.endermanNullSpheres += itemNullSphere;
                        WolfTracker.wolfTeethSession += itemTeeth;
                        SpiderTracker.spiderWebsSession += itemWebs;
                        ZombieTracker.zombieRevFleshSession += itemRev;
                        EndermanTracker.endermanNullSpheresSession += itemNullSphere;

                        ConfigHandler.writeIntConfig("wolf", "teeth", WolfTracker.wolfTeeth);
                        ConfigHandler.writeIntConfig("spider", "web", SpiderTracker.spiderWebs);
                        ConfigHandler.writeIntConfig("zombie", "revFlesh", ZombieTracker.zombieRevFlesh);
                        ConfigHandler.writeIntConfig("enderman", "nullSpheres", EndermanTracker.endermanNullSpheres);
                    }
                }
            }
        }
    }

    public static int getAmountfromMessage(String message) {
        if (message.charAt(message.indexOf("(") + 2) == 'x') {
            return Integer.parseInt(message.substring(message.indexOf("(") + 1, message.indexOf("x")));
        } else {
            return 1;
        }
    }
    
}
