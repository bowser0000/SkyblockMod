package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.loot.LootDisplay;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.ScoreboardHandler;
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
                    } else if (sCleaned.contains("Voidgloom Seraph")) {
                        LootDisplay.display = "enderman";
                        found = true;
                    } else if (sCleaned.contains("Inferno Demonlord")) {
                        LootDisplay.display = "blaze";
                        found = true;
                    } else if (sCleaned.contains("The Mist")){
                        LootDisplay.display = "ghost";
                        found = true;
                    } else if (Utils.inDungeons) {
                        switch (Utils.currentFloor) {
                            case F1:
                                LootDisplay.display = "catacombs_floor_one";
                                break;
                            case F2:
                                LootDisplay.display = "catacombs_floor_two";
                                break;
                            case F3:
                                LootDisplay.display = "catacombs_floor_three";
                                break;
                            case F4:
                                LootDisplay.display = "catacombs_floor_four";
                                break;
                            case F5:
                                LootDisplay.display = "catacombs_floor_five";
                                break;
                            case F6:
                                LootDisplay.display = "catacombs_floor_six";
                                break;
                            case F7:
                                LootDisplay.display = "catacombs_floor_seven";
                                break;
                            case M1:
                            case M2:
                            case M3:
                            case M4:
                            case M5:
                            case M6:
                            case M7:
                                LootDisplay.display = "catacombs_master";
                                break;
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
                        break;
                    } else if (hotbarItem.getItem() == Items.fishing_rod) {
                        List<String> lore = hotbarItem.getTooltip(player, mc.gameSettings.advancedItemTooltips);
                        for (int j = lore.size() - 1; j >= 0; j--) { // reverse
                            if (lore.get(j).contains("FISHING ROD")) {
                                if (Utils.tabLocation.equals("Crimson Isle")) {
                                    LootDisplay.display = "fishing_lava";
                                } else {
                                    LootDisplay.display = "fishing";
                                }
                                found = true;
                                break;
                            }
                        }
                    }
                }
                if (!found) LootDisplay.display = "off";
                ConfigHandler.writeStringConfig("misc", "display", LootDisplay.display);
            }
        }
    }

}
