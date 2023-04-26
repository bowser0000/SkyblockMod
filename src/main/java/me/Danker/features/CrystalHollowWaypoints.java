package me.Danker.features;

import cc.polyfrost.oneconfig.utils.IOUtils;
import cc.polyfrost.oneconfig.utils.Notifications;
import cc.polyfrost.oneconfig.utils.SimpleProfiler;
import com.google.gson.*;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.gui.crystalhollowwaypoints.CrystalHollowAddWaypointGui;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.locations.Location;
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
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrystalHollowWaypoints {

    public static ArrayList<Waypoint> waypoints = new ArrayList<>();
    public static Pattern dsmPattern = Pattern.compile("(?<name>.*?) @ (?<x>\\d{1,3}) (?<y>\\d{1,3}) (?<z>\\d{1,3})");
    public static Pattern skytilsPattern = Pattern.compile("(?<name>.*?): (?<x>\\d{1,3}) (?<y>\\d{1,3}) (?<z>\\d{1,3})");

    static boolean perma = false;
    static boolean khazad = false;
    static boolean fairy = false;
    static boolean temple = false;
    static boolean guardian = false;
    static boolean divan = false;
    static boolean corleone = false;
    static boolean king = false;
    static boolean queen = false;
    static boolean city = false;
    static boolean shop = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        World world = mc.theWorld;

        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            if (ModConfig.autoWaypoints && Utils.currentLocation == Location.CRYSTAL_HOLLOWS && world != null) {
                boolean found = false;
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();

                if (!perma) {
                    perma = true;
                    try {
                        if (ModConfig.autoImportWaypoints) addDSMWaypoints(ModConfig.permaWaypoints, false, true);
                    } catch (ArrayIndexOutOfBoundsException ignored) {}
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

                if (found && ModConfig.crystalHollowWaypoints) {
                    Waypoint latest = waypoints.get(waypoints.size() - 1);
                    player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Added " + latest.location + " @ " + latest.getPos()));
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
        if (ModConfig.crystalHollowWaypoints && Utils.currentLocation == Location.CRYSTAL_HOLLOWS) {
            if (!message.contains(player.getName())) {
                if (message.contains(": $DSMCHWP:") || message.contains(": $SBECHWP:")) {
                    String waypoints = message.substring(message.lastIndexOf(":") + 1);

                    if (ModConfig.autoPlayerWaypoints) {
                        addDSMWaypoints(waypoints, true, false);
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
                        player.addChatMessage(new ChatComponentText("\n" + ModConfig.getColour(ModConfig.mainColour) + "DSM/SBE Crystal Hollows waypoints found. Click to add.\n").appendSibling(add));
                    }).start();
                } else if (message.indexOf(":") != message.lastIndexOf(":")) {
                    String text = message.substring(message.indexOf(":") + 2);
                    Matcher matcher = skytilsPattern.matcher(text);

                    if (matcher.matches()) {
                        String name = matcher.group("name");
                        String x = matcher.group("x");
                        String y = matcher.group("y");
                        String z = matcher.group("z");

                        if (ModConfig.autoPlayerWaypoints) {
                            addWaypoint(name, x, y, z, true);
                            return;
                        }

                        ChatComponentText add = new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "  [ADD]\n");
                        add.setChatStyle(add.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dsmaddcrystalhollowwaypoints st " + x + " " + y + " " + z + " " + name)));

                        new Thread(() -> {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            player.addChatMessage(new ChatComponentText("\n" + ModConfig.getColour(ModConfig.mainColour) + "Skytils Crystal Hollows waypoints found. Click to add.\n").appendSibling(add));
                        }).start();
                    }
                } else if (message.contains(" @ ")) {
                    String text = message.substring(message.indexOf(":") + 2);
                    Matcher matcher = dsmPattern.matcher(text);

                    if (matcher.matches()) {
                        String name = matcher.group("name");
                        String x = matcher.group("x");
                        String y = matcher.group("y");
                        String z = matcher.group("z");

                        if (ModConfig.autoPlayerWaypoints) {
                            addWaypoint(name, x, y, z, true);
                            return;
                        }

                        ChatComponentText add = new ChatComponentText(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "  [ADD]\n");
                        add.setChatStyle(add.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dsmaddcrystalhollowwaypoints st " + x + " " + y + " " + z + " " + name)));

                        new Thread(() -> {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            player.addChatMessage(new ChatComponentText("\n" + ModConfig.getColour(ModConfig.mainColour) + "DSM Crystal Hollows waypoints found. Click to add.\n").appendSibling(add));
                        }).start();
                    }
                }
            }
        }
    }

    public static void onKey() {
        if (Utils.currentLocation != Location.CRYSTAL_HOLLOWS) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;

        mc.displayGuiScreen(new CrystalHollowAddWaypointGui((int) player.posX, (int) player.posY, (int) player.posZ));
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (ModConfig.crystalHollowWaypoints && Utils.currentLocation == Location.CRYSTAL_HOLLOWS) {
            for (Waypoint waypoint : waypoints) {
                if (waypoint.toggled) RenderUtils.draw3DWaypointString(waypoint, event.partialTicks);
            }
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        waypoints.clear();
        perma = false;
        khazad = false;
        fairy = false;
        temple = false;
        guardian = false;
        divan = false;
        corleone = false;
        king = false;
        queen = false;
        city = false;
        shop = false;
    }

    public static void addWaypoint(String name, String x, String y, String z, boolean auto) {
        if (auto) {
            for (Waypoint existing : waypoints) {
                if (existing.location.equals(name)) {
                    return;
                }
            }
        }

        BlockPos pos = new BlockPos(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
        Waypoint waypoint = new Waypoint(name, pos);
        waypoints.add(waypoint);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Added " + waypoint.location + " @ " + waypoint.getPos()));
    }

    public static void addDSMWaypoints(String list, boolean auto, boolean silent) {
        String[] waypointsList = list.split("\\\\n");

        for (String waypoint : waypointsList) {
            String[] parts = waypoint.split("@-");
            String[] coords = parts[1].split(",");

            String location = parts[0];
            BlockPos pos = new BlockPos(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
            Waypoint newWaypoint = new Waypoint(location, pos);

            if (auto) {
                boolean contains = false;
                for (Waypoint existing : waypoints) {
                    if (existing.location.equals(location)) {
                        contains = true;
                        break;
                    }
                }
                if (contains) continue;
            }

            waypoints.add(newWaypoint);
            if (!silent) Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Added " + newWaypoint.location + " @ " + newWaypoint.getPos()));
        }
    }

    public static void addSTWaypoints(String list) {
        JsonObject obj;
        try {
            obj = new Gson().fromJson(list, JsonObject.class);
        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
            return;
        }

        JsonArray categories = obj.get("categories").getAsJsonArray();

        for (JsonElement element : categories) {
            JsonObject inner = element.getAsJsonObject();

            String island = inner.get("island").getAsString();
            if (!island.equals("crystal_hollows")) return;

            JsonArray waypoints = inner.get("waypoints").getAsJsonArray();

            for (JsonElement waypointElement : waypoints) {
                JsonObject waypoint = waypointElement.getAsJsonObject();

                String name = waypoint.get("name").getAsString();
                String x = waypoint.get("x").getAsString();
                String y = waypoint.get("y").getAsString();
                String z = waypoint.get("z").getAsString();

                addWaypoint(name, x, y, z, false);
            }
        }
    }

    public static void addSoopyWaypoints(String list) {
        JsonArray arr;
        try {
            arr = new Gson().fromJson(list, JsonArray.class);
        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
            return;
        }

        for (JsonElement element : arr) {
            JsonObject waypoint = element.getAsJsonObject();

            String name = waypoint.get("options").getAsJsonObject().get("name").getAsString();
            String x = waypoint.get("x").getAsString();
            String y = waypoint.get("y").getAsString();
            String z = waypoint.get("z").getAsString();

            addWaypoint(name, x, y, z, false);
        }
    }

    public static void copyToClipboard() {
        StringBuilder sb = new StringBuilder();
        for (Waypoint waypoint : waypoints) {
            if (sb.length() > 0) sb.append("\\n");
            sb.append(waypoint.getFormattedWaypoint());
        }
        String waypoints = sb.toString();
        if (waypoints.length() > 0) {
            IOUtils.copyStringToClipboard(waypoints);
            Notifications.INSTANCE.send("Success", "Copied waypoints to clipboard.");
        }
    }

    public static void importWaypoints() {
        String clipboard = IOUtils.getStringFromClipboard();
        if (clipboard == null) {
            Notifications.INSTANCE.send("Error", "Could not find waypoints in clipboard.");
            return;
        }

        if (clipboard.startsWith("<Skytils-Waypoint-Data>(V1):")) { // ST
            try {
                String str = clipboard.substring(clipboard.indexOf(":") + 1);
                GzipCompressorInputStream gcis = new GzipCompressorInputStream(new Base64InputStream(new ByteArrayInputStream(str.getBytes())));

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                org.apache.commons.compress.utils.IOUtils.copy(gcis, out);

                addSTWaypoints(out.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
                Notifications.INSTANCE.send("Error", "Error parsing waypoints in clipboard.");
            }
        } else if (Base64.isBase64(clipboard)) { // ST
            addSTWaypoints(new String(Base64.decodeBase64(clipboard), StandardCharsets.UTF_8));
        } else if (Utils.isJson(clipboard)) { // Soopy
            addSoopyWaypoints(clipboard);
        } else { // DSM
            try {
                addDSMWaypoints(clipboard, false, false);
            } catch (Exception ex) {
                Notifications.INSTANCE.send("Error", "Error parsing waypoints in clipboard.");
            }
        }
    }

    public static void optimize() {
        SimpleProfiler.push("Coords Optimizer");
        Notifications.INSTANCE.send("Running...", "Optimizing waypoints...");

        Minecraft mc = Minecraft.getMinecraft();
        ArrayList<Waypoint> numbered = CoordsOptimizer.getNumbered(waypoints);
        double unoptimizedLength = CoordsOptimizer.getLength(numbered);

        if (numbered.size() < 2) {
            Notifications.INSTANCE.send("Error", "Requires at least 2 numbered waypoints.");
            SimpleProfiler.pop("Coords Optimizer");
            return;
        }

        CoordsOptimizer coordsOptimizer = new CoordsOptimizer(numbered);
        coordsOptimizer.solve();
        ArrayList<Waypoint> optimized = coordsOptimizer.getOptimizedPath();
        double optimizedLength = CoordsOptimizer.getLength(optimized);

        System.out.println("Coords optimizer took " + SimpleProfiler.pop("Coords Optimizer") + "ms");
        Notifications.INSTANCE.send("Finished", "Finished optimizing waypoints. See logs for more details.");

        if (optimizedLength < unoptimizedLength) {
            waypoints = optimized;
            System.out.println("Optimized waypoints from length " + unoptimizedLength + " to length " + optimizedLength);
            if (mc.thePlayer != null) {
                mc.thePlayer.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Optimized waypoints from length " + String.format("%.2f", unoptimizedLength) + " to length " + String.format("%.2f", optimizedLength) + "."));
            }
        } else {
            System.out.println("Could not find more optimized path. Found length " + optimizedLength + " but best length is " + unoptimizedLength);
            if (mc.thePlayer != null) {
                mc.thePlayer.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Could not find more optimized path. It may have failed to find one, or your route is already perfect."));
            }
        }
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
