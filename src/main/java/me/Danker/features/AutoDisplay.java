package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.loot.LootDisplay;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.locations.Location;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class AutoDisplay {

    public static String display = "Off";

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        World world = mc.theWorld;
        EntityPlayerSP player = mc.thePlayer;
        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            if (LootDisplay.autoDisplay && world != null && player != null) {
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                boolean found = false;
                for (String s : scoreboard) {
                    String sCleaned = ScoreboardHandler.cleanSB(s);
                    if (sCleaned.contains("Sven Packmaster")) {
                        display = "Wolf Slayer";
                        found = true;
                    } else if (sCleaned.contains("Tarantula Broodfather")) {
                        display = "Spider Slayer";
                        found = true;
                    } else if (sCleaned.contains("Revenant Horror")) {
                        display = "Zombie Slayer";
                        found = true;
                    } else if (sCleaned.contains("Voidgloom Seraph")) {
                        display = "Enderman Slayer";
                        found = true;
                    } else if (sCleaned.contains("Inferno Demonlord")) {
                        display = "Blaze Slayer";
                        found = true;
                    } else if (sCleaned.contains("The Mist")){
                        display = "Ghost";
                        found = true;
                    } else if (Utils.isInDungeons()) {
                        switch (Utils.currentFloor) {
                            case F1:
                                display = "Floor 1";
                                break;
                            case F2:
                                display = "Floor 2";
                                break;
                            case F3:
                                display = "Floor 3";
                                break;
                            case F4:
                                display = "Floor 4";
                                break;
                            case F5:
                                display = "Floor 5";
                                break;
                            case F6:
                                display = "Floor 6";
                                break;
                            case F7:
                                display = "Floor 7";
                                break;
                            case M1:
                            case M2:
                            case M3:
                            case M4:
                            case M5:
                            case M6:
                            case M7:
                                display = "Master Mode";
                                break;
                        }
                        found = true;
                    }
                }
                for (int i = 0; i < 8; i++) {
                    ItemStack hotbarItem = player.inventory.getStackInSlot(i);
                    if (hotbarItem == null) continue;
                    if (hotbarItem.getDisplayName().contains("Ancestral Spade")) {
                        display = "Mythological";
                        found = true;
                        break;
                    } else if (hotbarItem.getItem() == Items.fishing_rod) {
                        List<String> lore = hotbarItem.getTooltip(player, mc.gameSettings.advancedItemTooltips);
                        for (int j = lore.size() - 1; j >= 0; j--) { // reverse
                            if (lore.get(j).contains("FISHING ROD")) {
                                if (Utils.currentLocation == Location.CRIMSON_ISLE) {
                                    display = "Lava Fishing";
                                } else if (Utils.currentLocation == Location.JERRY_WORKSHOP) {
                                    display = "Winter Fishing";
                                } else {
                                    display = "Fishing";
                                }
                                found = true;
                                break;
                            }
                        }
                    }
                }
                if (!found) display = "Off";
            }
        }
    }

}
