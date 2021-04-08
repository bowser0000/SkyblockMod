package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.loot.LootDisplay;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.ScoreboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class AutoDisplay {

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayerSP player = mc.thePlayer;
        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            if (LootDisplay.auto && world != null && player != null) {
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                boolean found = false;
                for (String s : scoreboard) {
                    String sCleaned = ScoreboardHandler.cleanSB(s);
                    if (sCleaned.contains("Sven Packmaster")) {
                        LootDisplay.display = "wolf";
                        found = true;
                    } else if (sCleaned.contains("Tarantula Broodfather")) {
                        LootDisplay.display = "spider";
                        found = true;
                    } else if (sCleaned.contains("Revenant Horror")) {
                        LootDisplay.display = "zombie";
                        found = true;
                    } else if (sCleaned.contains("The Mist")){
                        LootDisplay.display = "ghost";
                        found = true;
                    } else if (sCleaned.contains("The Catacombs (")) {
                        if (sCleaned.contains("F1")) {
                            LootDisplay.display = "catacombs_floor_one";
                        } else if (sCleaned.contains("F2")) {
                            LootDisplay.display = "catacombs_floor_two";
                        } else if (sCleaned.contains("F3")) {
                            LootDisplay.display = "catacombs_floor_three";
                        } else if (sCleaned.contains("F4")) {
                            LootDisplay.display = "catacombs_floor_four";
                        } else if (sCleaned.contains("F5")) {
                            LootDisplay.display = "catacombs_floor_five";
                        } else if (sCleaned.contains("F6")) {
                            LootDisplay.display = "catacombs_floor_six";
                        } else if (sCleaned.contains("F7")) {
                            LootDisplay.display = "catacombs_floor_seven";
                        }
                        found = true;
                    }
                }
                for (int i = 0; i < 8; i++) {
                    ItemStack hotbarItem = player.inventory.getStackInSlot(i);
                    if (hotbarItem == null) continue;
                    if (hotbarItem.getDisplayName().contains("Ancestral Spade")) {
                        LootDisplay.display = "mythological";
                        found = true;
                    }
                }
                if (!found) LootDisplay.display = "off";
                ConfigHandler.writeStringConfig("misc", "display", LootDisplay.display);
            }
        }
    }

}
