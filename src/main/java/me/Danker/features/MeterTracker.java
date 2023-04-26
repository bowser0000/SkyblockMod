package me.Danker.features;

import cc.polyfrost.oneconfig.utils.TickDelay;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.events.ChestSlotClickedEvent;
import me.Danker.events.ModInitEvent;
import me.Danker.events.PostConfigInitEvent;
import me.Danker.locations.Location;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MeterTracker {

    public static JsonObject meter = new JsonObject();
    public static String configFile;

    public static JsonObject createEmpty() {
        JsonObject meter = new JsonObject();

        JsonObject floor = new JsonObject();
        floor.addProperty("drop", EnumChatFormatting.RED + "None");
        floor.addProperty("progress", 0);
        floor.addProperty("goal", 0);

        meter.add("F1", Utils.deepCopy(floor));
        meter.add("F2", Utils.deepCopy(floor));
        meter.add("F3", Utils.deepCopy(floor));
        meter.add("F4", Utils.deepCopy(floor));
        meter.add("F5", Utils.deepCopy(floor));
        meter.add("F6", Utils.deepCopy(floor));
        meter.add("F7", Utils.deepCopy(floor));
        meter.add("M1", Utils.deepCopy(floor));
        meter.add("M2", Utils.deepCopy(floor));
        meter.add("M3", Utils.deepCopy(floor));
        meter.add("M4", Utils.deepCopy(floor));
        meter.add("M5", Utils.deepCopy(floor));
        meter.add("M6", Utils.deepCopy(floor));
        meter.add("M7", Utils.deepCopy(floor));

        return meter;
    }

    @SubscribeEvent
    public void init(ModInitEvent event) {
        configFile = event.configDirectory + "/dsm/dsmmeter.json";
    }

    @SubscribeEvent
    public void postConfigInit(PostConfigInitEvent event) {
        if (meter.entrySet().isEmpty()) meter = createEmpty();
        save();
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (Utils.currentLocation != Location.CATACOMBS) return;
        if (event.type == 2) return;

        if (message.contains("    Team Score: ")) {
            String currentFloor = Utils.currentFloor.toString();

            if (meter.has(currentFloor)) {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                int score = Integer.parseInt(message.replaceAll("\\D", ""));
                if (score < 270) return;
                if (score < 300) score *= 0.7;

                JsonObject floor = meter.get(currentFloor).getAsJsonObject();
                NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);

                String drop = floor.get("drop").getAsString();
                int progress = floor.get("progress").getAsInt();
                int goal = floor.get("goal").getAsInt();

                progress += score;
                if (goal == 0) return;

                double percent = (double) progress / goal * 100D;
                percent = MathHelper.clamp_double(percent, 0D, 100D);

                String info = EnumChatFormatting.GOLD + "Selected Drop: " + drop + "\n" +
                              EnumChatFormatting.GOLD + "Progress: " + EnumChatFormatting.LIGHT_PURPLE + nf.format(progress) + EnumChatFormatting.DARK_PURPLE + "/" + EnumChatFormatting.LIGHT_PURPLE + nf.format(goal) +
                              EnumChatFormatting.DARK_GRAY + " (" + EnumChatFormatting.LIGHT_PURPLE + String.format("%.2f", percent) + EnumChatFormatting.DARK_PURPLE + "%" + EnumChatFormatting.DARK_GRAY + ")" + "\n";
                int runsLeft = 0;

                if (progress >= goal) {
                    progress -= goal;
                    info += EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "Item dropped!";
                    floor.addProperty("drop", EnumChatFormatting.RED + "None");
                    goal = 0;
                } else {
                    runsLeft = (int) Math.ceil((goal - progress) / (currentFloor.charAt(1) == '6' || currentFloor.charAt(1) == '7' ? 307D : 305D));
                    info += EnumChatFormatting.GOLD + "Minimum Runs Left: " + EnumChatFormatting.AQUA + runsLeft;
                }

                floor.addProperty("progress", progress);
                floor.addProperty("goal", goal);

                save();

                if (ModConfig.meterTracker) {
                    String finalInfo = info;
                    int finalRunsLeft = runsLeft;
                    new TickDelay(() -> {
                        player.addChatMessage(new ChatComponentText(finalInfo));
                        if (finalRunsLeft == 1) {
                            player.addChatMessage(new ChatComponentText("" + EnumChatFormatting.RED + EnumChatFormatting.BOLD + "Reset meter if you want to maintain high chances!"));
                        }
                    }, 1);
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!Utils.inSkyblock) return;

        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.thePlayer;
            if (mc.currentScreen instanceof GuiChest) {
                if (player == null) return;
                ContainerChest chest = (ContainerChest) player.openContainer;
                List<Slot> invSlots = ((GuiChest) mc.currentScreen).inventorySlots.inventorySlots;
                String chestName = chest.getLowerChestInventory().getDisplayName().getUnformattedText().trim();

                if (chestName.equals("Catacombs RNG Meter")) {
                    for (int i = 19; i <= 34; i++) {
                        ItemStack stack = invSlots.get(i).getStack();
                        if (stack == null || stack.getItem() != Items.skull) continue;

                        List<String> lore = Utils.getItemLore(stack);
                        String floor = lore.get(0);
                        floor = floor.substring(floor.indexOf("(") + 1, floor.indexOf(")"));

                        if (meter.has(floor)) {
                            String drop = EnumChatFormatting.RED + "None";
                            int progress;
                            int goal = 0;

                            if (StringUtils.stripControlCodes(lore.get(15)).equals("Selected Drop")) {
                                drop = lore.get(16);
                                String line = StringUtils.stripControlCodes(lore.get(19));
                                progress = getProgressFromLine(line);
                                goal = getGoalFromLine(line);
                            } else {
                                progress = Integer.parseInt(StringUtils.stripControlCodes(lore.get(19)).replaceAll("\\D", ""));
                            }

                            JsonObject floorMeter = meter.get(floor).getAsJsonObject();
                            floorMeter.addProperty("drop", drop);
                            floorMeter.addProperty("progress", progress);
                            floorMeter.addProperty("goal", goal);
                            save();

                            System.out.println("Updated " + floor + " meter.");
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        if (event.item == null) return;

        String inventoryName = event.inventoryName;
        if (inventoryName.startsWith("Catacombs (") && inventoryName.endsWith(") RNG Meter")) {
            String floor = inventoryName.substring(inventoryName.indexOf("(") + 1, inventoryName.indexOf(")"));

            if (meter.has(floor)) {
                JsonObject floorMeter = meter.get(floor).getAsJsonObject();
                String drop = event.item.getDisplayName();
                String currentDrop = floorMeter.get("drop").getAsString();
                if (drop.equals(currentDrop)) return;

                List<String> lore = Utils.getItemLore(event.item);
                for (String line : lore) {
                    String cleaned = StringUtils.stripControlCodes(line);
                    if (cleaned.startsWith("Dungeon Score: ")) {
                        int progress = getProgressFromLine(cleaned);
                        int goal = getGoalFromLine(cleaned);

                        floorMeter.addProperty("drop", drop);
                        floorMeter.addProperty("progress", progress);
                        floorMeter.addProperty("goal", goal);
                        save();

                        System.out.println("Switched " + floor + " meter to " + drop);
                        return;
                    }
                }
            }
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(configFile)) {
            new GsonBuilder().create().toJson(meter, writer);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static int getProgressFromLine(String line) {
        return Integer.parseInt(line.substring(0, line.indexOf("/")).replaceAll("\\D", ""));
    }

    static int getGoalFromLine(String line) {
        String goalString = line.substring(line.indexOf("/") + 1);
        if (goalString.endsWith("k")) {
            return Integer.parseInt(goalString.replaceAll("\\D", "")) * 1000;
        } else {
            return Integer.parseInt(goalString.replaceAll("\\D", ""));
        }
    }

}
