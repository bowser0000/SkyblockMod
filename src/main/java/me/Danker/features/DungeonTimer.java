package me.Danker.features;

import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.DualOption;
import cc.polyfrost.oneconfig.config.annotations.Number;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import cc.polyfrost.oneconfig.utils.TickDelay;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.events.ModInitEvent;
import me.Danker.events.PostConfigInitEvent;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DungeonTimer {

    public static JsonObject timer = new JsonObject();
    public static String configFile;

    static ActiveTimer activeTimer = null;

    static int witherDoors = 0;
    static int dungeonDeaths = 0;
    static int puzzleFails = 0;

    public static JsonObject createEmpty() {
        JsonObject timer = new JsonObject();

        JsonArray f1 = getBasicSplits(EnumChatFormatting.GOLD + "Bonzo Entry", "Bonzo");
        f1.add(createSplit(EnumChatFormatting.GOLD + "Bonzo Phase 1", "\\[BOSS\\] Bonzo: Oh noes, you got me\\.\\. what ever will I do\\?!"));
        f1.add(getEndSplit(EnumChatFormatting.GOLD + "Bonzo Phase 2"));
        timer.add("F1", f1);
        timer.add("M1", Utils.deepCopy(f1));

        JsonArray f2 = getBasicSplits(EnumChatFormatting.WHITE + "Scarf Entry", "Scarf");
        f2.add(createSplit(EnumChatFormatting.WHITE + "Scarf Phase 1", "\\[BOSS\\] Scarf: Those toys are not strong enough I see\\."));
        f2.add(getEndSplit(EnumChatFormatting.WHITE + "Scarf Phase 2"));
        timer.add("F2", f2);
        timer.add("M2", Utils.deepCopy(f2));

        JsonArray f3 = getBasicSplits(EnumChatFormatting.RED + "Professor Entry", "The Professor");
        f3.add(createSplit(EnumChatFormatting.WHITE + "Guardians", "\\[BOSS\\] The Professor: Oh\\? You found my Guardians' one weakness\\?"));
        f3.add(createSplit(EnumChatFormatting.RED + "Professor", "\\[BOSS\\] The Professor: I see\\. You have forced me to use my ultimate technique\\."));
        f3.add(getEndSplit(EnumChatFormatting.RED + "Phase 2"));
        timer.add("F3", f3);
        timer.add("M3", Utils.deepCopy(f3));

        JsonArray f4 = getBasicSplits(EnumChatFormatting.RED + "Thorn Entry", "Thorn");
        f4.add(getEndSplit(EnumChatFormatting.RED + "Thorn"));
        timer.add("F4", f4);
        timer.add("M4", Utils.deepCopy(f4));

        JsonArray f5 = getBasicSplits(EnumChatFormatting.RED + "Livid Entry", "Livid");
        f5.add(getEndSplit(EnumChatFormatting.RED + "Livid"));
        timer.add("F5", f5);
        timer.add("M5", Utils.deepCopy(f5));

        JsonArray f6 = getBasicSplits(EnumChatFormatting.RED + "Sadan Entry", "Sadan");
        f6.add(createSplit(EnumChatFormatting.LIGHT_PURPLE + "Terracottas", "\\[BOSS\\] Sadan: ENOUGH!"));
        f6.add(createSplit(EnumChatFormatting.GREEN + "Giants", "\\[BOSS\\] Sadan: You did it\\. I understand now, you have earned my respect\\."));
        f6.add(getEndSplit(EnumChatFormatting.RED + "Sadan"));
        timer.add("F6", f6);
        timer.add("M6", Utils.deepCopy(f6));

        JsonArray f7 = getBasicSplits(EnumChatFormatting.RED + "Boss Entry", "Maxor");
        f7.add(createSplit(EnumChatFormatting.AQUA + "Maxor", "\\[BOSS\\] Storm: Pathetic Maxor, just like expected\\."));
        f7.add(createSplit(EnumChatFormatting.RED + "Storm", "\\[BOSS\\] Storm: At least my son died by your hands\\."));
        f7.add(createSplit(EnumChatFormatting.GOLD + "Terminals", "The Core entrance is opening!"));
        f7.add(createSplit(EnumChatFormatting.GOLD + "Goldor", "\\[BOSS\\] Goldor: \\.\\.\\.\\."));
        f7.add(createSplit(EnumChatFormatting.DARK_RED + "Necron", "\\[BOSS\\] Necron: All this, for nothing\\.\\.\\."));
        timer.add("F7", f7);
        JsonArray m7 = Utils.deepCopy(f7);
        m7.add(getEndSplit(EnumChatFormatting.GRAY + "Wither King"));
        timer.add("M7", m7);

        return timer;
    }

    public static JsonObject createSplit(String name, String triggerRegex) {
        JsonObject split = new JsonObject();

        split.addProperty("name", name);
        split.addProperty("trigger", triggerRegex);
        split.addProperty("pb", -1);
        split.addProperty("gold", -1);

        return split;
    }

    public static JsonArray getBasicSplits(String entryName, String bossName) {
        JsonArray splits = new JsonArray();

        splits.add(createSplit(EnumChatFormatting.DARK_RED + "Blood Open", "\\[BOSS\\] The Watcher: .*"));
        splits.add(createSplit(EnumChatFormatting.RED + "Blood Clear", "\\[BOSS\\] The Watcher: You have proven yourself. You may pass."));
        splits.add(createSplit(entryName, "\\[BOSS\\] " + bossName + ":.*"));

        return splits;
    }

    public static JsonObject getEndSplit(String name) {
        return createSplit(name, ".* {3}Team Score:.*");
    }

    @SubscribeEvent
    public void init(ModInitEvent event) {
        configFile = event.configDirectory + "/dsm/dsmdungeonsplits.json";
    }

    @SubscribeEvent
    public void postConfigInit(PostConfigInitEvent event) {
        if (timer.entrySet().isEmpty()) timer = createEmpty();
        save();
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.isInDungeons()) return;
        if (event.type == 2) return;

        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (activeTimer != null && !activeTimer.isTimerDone()) {
            Matcher matcher = activeTimer.getCurrentSplit().trigger.matcher(message);
            if (matcher.matches()) {
                activeTimer.split();
            }
        }

        if (message.contains(":")) return;

        if (activeTimer != null && message.contains("Dungeon starts in 1 second.")) {
            activeTimer.getFirstSplit().startTime = System.currentTimeMillis() + 1000D;
        } else if (message.contains("PUZZLE FAIL! ") || message.contains("chose the wrong answer! I shall never forget this moment")) {
            puzzleFails++;
        } else if (message.contains(" opened a WITHER door!")) {
            witherDoors++;
        } else if (message.contains(" and became a ghost.")) {
            dungeonDeaths++;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        if (!ModConfig.dungeonTimerHud.isEnabled()) return;
        if (activeTimer != null) return;

        if (DankersSkyblockMod.tickAmount % 20 == 0) {
            String currentFloor = Utils.currentFloor.toString();

            if (timer.has(currentFloor)) {
                activeTimer = new ActiveTimer(timer.get(currentFloor).getAsJsonArray());
            }
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        activeTimer = null;
        witherDoors = 0;
        dungeonDeaths = 0;
        puzzleFails = 0;
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(configFile)) {
            new GsonBuilder().create().toJson(timer, writer);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static class ActiveTimer {

        ArrayList<Split> splits = new ArrayList<>();
        int currentIndex = 0;

        public double sumOfBest = 0;

        public ActiveTimer(JsonArray floor) {
            boolean fullRun = true;

            for (JsonElement element : floor) {
                Split split = new Split(element.getAsJsonObject());
                splits.add(split);

                if (split.goldTime < 0D) {
                    sumOfBest = -1;
                    fullRun = false;
                } else if (fullRun && split.goldTime > 0D) {
                    sumOfBest += split.goldTime;
                }
            }
        }

        public boolean isTimerDone() {
            return currentIndex >= splits.size();
        }

        public Split getCurrentSplit() {
            return splits.get(currentIndex);
        }

        public Split getFirstSplit() {
            return splits.get(0);
        }

        public void split() {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            double time = System.currentTimeMillis();

            Split curSplit = getCurrentSplit();
            curSplit.endTime = time;
            if (curSplit.goldTime < 0D || curSplit.getTime() < curSplit.goldTime) {
                curSplit.splitObj.addProperty("gold", curSplit.getTime()); // set gold
                sumOfBest -= curSplit.goldTime - curSplit.getTime();
            }

            currentIndex++;

            if (currentIndex < splits.size()) {
                getCurrentSplit().startTime = time;

                if (DungeonTimerHud.inChat) {
                    player.addChatMessage(new ChatComponentText(curSplit.getFullText()));
                }
            } else {
                double sum = 0D;
                double pbSum = 0D;

                for (Split split : splits) {
                    sum += split.getTime();
                    pbSum += split.pbTime;
                }

                if (pbSum < 0D || sum < pbSum) {
                    for (Split split : splits) {
                        split.splitObj.addProperty("pb", split.getTime()); // replace times with pb
                    }
                }

                if (DungeonTimerHud.inChat) {
                    new TickDelay(() -> {
                        for (Split split : activeTimer.splits) {
                            player.addChatMessage(new ChatComponentText(split.getFullText()));
                        }
                    }, 1);
                }
            }
            save();
        }

        public String getSumOfBest() {
            if (sumOfBest < 0D) return "";

            int minutes = (int) (sumOfBest / 60);
            double seconds = sumOfBest % 60;

            if (minutes == 0) {
                return String.format("%." + DungeonTimerHud.decimals + "f", seconds) + "s";
            } else {
                return minutes + "m" + String.format("%." + DungeonTimerHud.decimals + "f", seconds) + "s";
            }
        }

        public String getBestPossibleTime() {
            if (sumOfBest < 0D) return ""; // requires all splits to have golds

            double sum = 0D;
            for (Split split : splits) {
                if (split.startTime < 0D || (split.endTime < 0D && split.getTime() < split.goldTime)) {
                    // hasnt started or split hasnt reached gold split
                    sum += split.goldTime;
                } else {
                    sum += split.getTime();
                }
            }

            int minutes = (int) (sum / 60);
            double seconds = sum % 60;

            if (minutes == 0) {
                return String.format("%." + DungeonTimerHud.decimals + "f", seconds) + "s";
            } else {
                return minutes + "m" + String.format("%." + DungeonTimerHud.decimals + "f", seconds) + "s";
            }
        }

    }

    static class Split {

        public JsonObject splitObj;
        public String name;
        public Pattern trigger;
        public double startTime = -1;
        public double endTime = -1;
        public double pbTime;
        public double goldTime;

        public Split(JsonObject split) {
            this.splitObj = split;
            this.name = split.get("name").getAsString();
            this.trigger = Pattern.compile(split.get("trigger").getAsString());
            this.pbTime = split.get("pb").getAsDouble();
            this.goldTime = split.get("gold").getAsDouble();
        }

        public double getTime() {
            if (startTime < 0D) return 0D;
            if (endTime < 0D) return (System.currentTimeMillis() - startTime) / 1000D;
            return (endTime - startTime) / 1000D;
        }

        public String getColouredTime() {
            String time = String.format("%." + DungeonTimerHud.decimals + "f", getTime()) + "s";

            if (startTime < 0D) return ModConfig.getColour(DungeonTimerHud.timerColour) + time;
            if (endTime > 0D && (goldTime < 0D || getTime() < goldTime)) return ModConfig.getColour(DungeonTimerHud.goldColour) + time;
            if (!DungeonTimerHud.compareGold && (pbTime < 0D || getTime() < pbTime)) return ModConfig.getColour(DungeonTimerHud.greenColour) + time;
            if (DungeonTimerHud.compareGold && (goldTime < 0D || getTime() < goldTime)) return ModConfig.getColour(DungeonTimerHud.greenColour) + time;
            return ModConfig.getColour(DungeonTimerHud.redColour) + time;
        }

        public String getPBDiff() {
            if (goldTime < 0D || pbTime < 0D) return "";
            if (getTime() < goldTime && endTime < 0D) return "";
            if (DungeonTimerHud.compareGold) {
                return " (" + String.format("%+." + DungeonTimerHud.decimals + "f", getTime() - goldTime) + "s)";
            }
            return " (" + String.format("%+." + DungeonTimerHud.decimals + "f", getTime() - pbTime) + "s)";
        }

        public String getFullText() {
            String text = name + ": " + getColouredTime();
            if (DungeonTimerHud.showDiff) text += getPBDiff();
            return text;
        }

    }

    public static class DungeonTimerHud extends Hud {

        @Dropdown(
                name = "Timer Color",
                options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"}
        )
        public static int timerColour = 7;

        @Dropdown(
                name = "Green Split Color",
                options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"}
        )
        public static int greenColour = 10;

        @Dropdown(
                name = "Red Split Color",
                options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"}
        )
        public static int redColour = 12;

        @Dropdown(
                name = "Gold Split Color",
                options = {"Black", "Dark Blue", "Dark Green", "Dark Aqua", "Dark Red", "Dark Purple", "Gold", "Gray", "Dark Gray", "Blue", "Green", "Aqua", "Red", "Light Purple", "Yellow", "White"}
        )
        public static int goldColour = 6;

        @Number(
                name = "Timer Decimal Places",
                min = 0, max = 5
        )
        public static int decimals = 2;

        @Switch(
                name = "Show Difference",
                description = "Show the time difference from your personal best run."
        )
        public static boolean showDiff = false;

        @DualOption(
                name = "Compare Difference Against",
                left = "PB",
                right = "Gold"
        )
        public static boolean compareGold = false;

        @Switch(
                name = "Show Current Pace",
                description = "Shows your current pace, using your gold splits as a best possible time."
        )
        public static boolean bestPossibleTime = false;

        @Switch(
                name = "Show Sum of Best",
                description = "Show sum of gold splits in the timer."
        )
        public static boolean sumOfBest = false;

        @Switch(
                name = "Show Misc. Info",
                description = "Show miscellaneous info about the dungeon run."
        )
        public static boolean extraInfo = false;

        @Switch(
                name = "Show Split Info in Chat",
                description = "Shows split information in chat after a split and after a dungeon run."
        )
        public static boolean inChat = false;

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            if (example) {
                TextRenderer.drawHUDText(getExampleText(), x, y, scale);
                TextRenderer.drawHUDText(getExampleNums(), (int) (x + (90 * scale)), y, scale);
                return;
            }

            if (enabled && Utils.isInDungeons()) {
                if (activeTimer != null) {
                    StringBuilder dungeonTimerText = new StringBuilder();
                    StringBuilder dungeonTimers = new StringBuilder();

                    for (Split split : activeTimer.splits) {
                        dungeonTimerText.append(split.name).append(":\n");
                        dungeonTimers.append(split.getColouredTime());
                        if (showDiff) dungeonTimers.append(split.getPBDiff());
                        dungeonTimers.append("\n");
                    }

                    if (bestPossibleTime) {
                        dungeonTimerText.append(EnumChatFormatting.GOLD).append("Current Pace:\n");
                        dungeonTimers.append(EnumChatFormatting.GOLD).append(activeTimer.getBestPossibleTime()).append("\n");
                    }

                    if (sumOfBest) {
                        dungeonTimerText.append(EnumChatFormatting.GOLD).append("Sum of Best:\n");
                        dungeonTimers.append(EnumChatFormatting.GOLD).append(activeTimer.getSumOfBest()).append("\n");
                    }

                    if (extraInfo) {
                        dungeonTimerText.append(EnumChatFormatting.GRAY).append("Wither Doors:\n")
                                        .append(EnumChatFormatting.YELLOW).append("Deaths:\n")
                                        .append(EnumChatFormatting.YELLOW).append("Puzzle Fails:");
                        dungeonTimers.append(EnumChatFormatting.GRAY).append(witherDoors).append("\n")
                                     .append(EnumChatFormatting.YELLOW).append(dungeonDeaths).append("\n")
                                     .append(EnumChatFormatting.YELLOW).append(puzzleFails);
                    }

                    TextRenderer.drawHUDText(dungeonTimerText.toString(), x, y, scale);
                    TextRenderer.drawHUDText(dungeonTimers.toString(), (int) (x + (90 * scale)), y, scale);
                }
            }
        }

        @Override
        protected float getWidth(float scale, boolean example) {
            return (RenderUtils.getWidthFromText(getExampleNums()) + 90 * scale) * scale;
        }

        @Override
        protected float getHeight(float scale, boolean example) {
            return RenderUtils.getHeightFromText(getExampleNums()) * scale;
        }
        
        String getExampleText() {
            String exampleText = EnumChatFormatting.DARK_RED + "Blood Open:\n" +
                                 EnumChatFormatting.RED + "Blood Clear:\n" +
                                 EnumChatFormatting.RED + "Boss Entry:\n" +
                                 EnumChatFormatting.AQUA + "Maxor:\n" +
                                 EnumChatFormatting.RED + "Storm:\n" +
                                 EnumChatFormatting.GOLD + "Terminals:\n" +
                                 EnumChatFormatting.GOLD + "Goldor:\n" +
                                 EnumChatFormatting.DARK_RED + "Necron:\n" +
                                 EnumChatFormatting.GRAY + "Wither King:";

            if (bestPossibleTime) {
                exampleText += "\n" + EnumChatFormatting.GOLD + "Current Pace:";
            }

            if (sumOfBest) {
                exampleText += "\n" + EnumChatFormatting.GOLD + "Sum of Best:";
            }

            if (extraInfo) {
                exampleText += EnumChatFormatting.GRAY + "\nWither Doors:\n" +
                               EnumChatFormatting.YELLOW + "Deaths:\n" +
                               EnumChatFormatting.YELLOW + "Puzzle Fails:";
            }

            return exampleText;
        }

        String getExampleNums() {
            String exampleNums = ModConfig.getColour(goldColour) + "175.56s\n" +
                                 ModConfig.getColour(greenColour) + "130.63s\n" +
                                 ModConfig.getColour(greenColour) + "458.94s\n" +
                                 ModConfig.getColour(redColour) + "49.93s\n" +
                                 ModConfig.getColour(goldColour) + "73.75s\n" +
                                 ModConfig.getColour(greenColour) + "227.65s\n" +
                                 ModConfig.getColour(redColour) + "17.56s\n" +
                                 ModConfig.getColour(timerColour) + "32.19s\n" +
                                 ModConfig.getColour(timerColour) + "0.00s";

            if (showDiff) {
                exampleNums = ModConfig.getColour(goldColour) + "175.56s (-12.78s)\n" +
                              ModConfig.getColour(greenColour) + "130.63s (-2.76s)\n" +
                              ModConfig.getColour(greenColour) + "458.94s (-5.62s)\n" +
                              ModConfig.getColour(redColour) + "49.93s (+1.21s)\n" +
                              ModConfig.getColour(goldColour) + "73.75s (-1.69s)\n" +
                              ModConfig.getColour(greenColour) + "227.65s (-13.52s)\n" +
                              ModConfig.getColour(redColour) + "17.56s (+0.56s)\n" +
                              ModConfig.getColour(greenColour) + "32.19s\n" +
                              ModConfig.getColour(timerColour) + "0.00s";
            }

            if (bestPossibleTime) {
                exampleNums += "\n" + EnumChatFormatting.GOLD + "21m54.01s";
            }

            if (sumOfBest) {
                exampleNums += "\n" + EnumChatFormatting.GOLD + "6m2s.20s";
            }

            if (extraInfo) {
                exampleNums += "\n" + EnumChatFormatting.GRAY+ 5 + "\n" +
                               EnumChatFormatting.YELLOW + 2 + "\n" +
                               EnumChatFormatting.YELLOW + 1;
            }

            return exampleNums;
        }

    }

}
