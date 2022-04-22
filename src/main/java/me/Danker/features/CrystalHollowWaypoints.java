package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.CrystalHollowWaypointCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class CrystalHollowWaypoints {

    public static List<Waypoint> waypoints = new ArrayList<>();
    
    static boolean khazad = false;
    static boolean fairy = false;
    static boolean temple = false;
    static boolean guardian = false;
    static boolean divan = false;
    static boolean corleone = false;
    static boolean king = false;
    static boolean queen = false;
    static boolean city = false;
    static boolean nucleus = false;
    static boolean shop = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        World world = mc.theWorld;

        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            if (ToggleCommand.crystalAutoWaypoints && Utils.tabLocation.equals("Crystal Hollows") && world != null) {
                boolean found = false;
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();

                if (!nucleus) {
                    nucleus = true;
                    waypoints.add(new Waypoint("Crystal Nucleus", new BlockPos(512, 110, 512)));
                }

                for (String s : scoreboard) {
                    String sCleaned = ScoreboardHandler.cleanSB(s);
                    if (!khazad && sCleaned.contains("Khazad-d")) {
                        khazad = found = true;
                        waypoints.add(new Waypoint("Khazad-dûm", player.getPosition()));
                    } else if (!fairy && sCleaned.contains("Fairy Grotto")) {
                        fairy = found = true;
                        waypoints.add(new Waypoint("Fairy Grotto", player.getPosition()));
                    } else if (!temple && sCleaned.contains("Jungle Temple")) {
                        temple = found = true;
                        waypoints.add(new Waypoint("Jungle Temple", player.getPosition()));
                    } else if (!divan && sCleaned.contains("Mines of Divan")) {
                        divan = found = true;
                        waypoints.add(new Waypoint("Mines of Divan", player.getPosition()));
                    } else if (!queen && sCleaned.contains("Goblin Queen's Den")) {
                        queen = found = true;
                        waypoints.add(new Waypoint("Goblin Queen's Den", player.getPosition()));
                    } else if (!city && sCleaned.contains("Lost Precursor City")) {
                        city = found = true;
                        waypoints.add(new Waypoint("Lost Precursor City", player.getPosition()));
                    }

                    if (found) break;
                }

                if (!found) {
                    AxisAlignedBB scan = new AxisAlignedBB(player.getPosition().add(-15, -15, -15), player.getPosition().add(15, 15, 15));
                    List<EntityArmorStand> entities = world.getEntitiesWithinAABB(EntityArmorStand.class, scan);

                    for (EntityArmorStand entity : entities) {
                        if (entity.hasCustomName()) {
                            if (!king && entity.getCustomNameTag().endsWith("King Yolkar")) {
                                king = found = true;
                                waypoints.add(new Waypoint("King Yolkar", entity.getPosition()));
                            } else if (!corleone && entity.getCustomNameTag().contains("Boss Corleone")) {
                                corleone = found = true;
                                waypoints.add(new Waypoint("Boss Corleone", entity.getPosition()));
                            } else if (!guardian && entity.getCustomNameTag().contains("Key Guardian")) {
                                guardian = found = true;
                                waypoints.add(new Waypoint("Key Guardian", entity.getPosition()));
                            } else if (!shop && entity.getCustomNameTag().contains("Odawa")) {
                                shop = found = true;
                                waypoints.add(new Waypoint("Odawa", entity.getPosition()));
                            }
                        }
                    }
                }

                if (found && ToggleCommand.crystalHollowWaypoints) {
                    Waypoint latest = waypoints.get(waypoints.size() - 1);
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Added " + latest.location + " @ " + latest.getPos()));
                }
            }
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if (player == null) return;

        /* examples
        $SBECHWP:Mines of Divan@-673,117,426
        $SBECHWP:Khazad-dûm@-292,63,281\nFairy Grotto@-216,110,400\njungle temple@-525,110,395\nJungle Temple@-493,101,425\nMines of Divan@-673,117,426
        */
        if (ToggleCommand.crystalHollowWaypoints && Utils.tabLocation.equals("Crystal Hollows")) {
            if (!message.contains(player.getName()) && (message.contains(": $DSMCHWP:") || message.contains(": $SBECHWP:"))) {
                String waypoints = message.substring(message.lastIndexOf(":") + 1);

                if (ToggleCommand.crystalAutoPlayerWaypoints) {
                    CrystalHollowWaypointCommand.addWaypoints(waypoints, true);
                    return;
                }

                ChatComponentText add = new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "  [ADD]\n");
                add.setChatStyle(add.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dsmaddcrystalhollowwaypoints " + waypoints)));

                new Thread(() -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    player.addChatMessage(new ChatComponentText("\n" + DankersSkyblockMod.MAIN_COLOUR + "DSM/SBE Crystal Hollows waypoints found. Click to add.\n").appendSibling(add));
                }).start();
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (ToggleCommand.crystalHollowWaypoints && Utils.tabLocation.equals("Crystal Hollows")) {
            for (Waypoint waypoint : waypoints) {
                if (waypoint.toggled) RenderUtils.draw3DWaypointString(waypoint, event.partialTicks);
            }
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        waypoints.clear();
        khazad = false;
        fairy = false;
        temple = false;
        guardian = false;
        divan = false;
        corleone = false;
        king = false;
        queen = false;
        city = false;
        nucleus = false;
        shop = false;
    }

    public static class Waypoint {

        public String location;
        public BlockPos pos;
        public boolean toggled;

        public Waypoint(String location, BlockPos pos) {
            this.location = location;
            this.pos = pos;
            this.toggled = true;
        }

        public String getFormattedWaypoint() {
            return location + "@-" + pos.getX() + "," + pos.getY() + "," + pos.getZ();
        }

        public String getDistance(EntityPlayer player) {
            return Math.round(player.getDistance(pos.getX(), pos.getY(), pos.getZ())) + "m";
        }

        public String getPos() {
            return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
        }

        public void toggle() {
            toggled = !toggled;
        }

    }

}
