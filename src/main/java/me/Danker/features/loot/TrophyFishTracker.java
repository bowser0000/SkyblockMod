package me.Danker.features.loot;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.Danker.events.ModInitEvent;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrophyFishTracker {

    public static JsonObject fish = new JsonObject();
    public static JsonObject fishSession = new JsonObject();
    public static Pattern fishPattern = Pattern.compile("TROPHY FISH! You caught a (?<fish>.*) (?<tier>.*).");
    public static String configFile;

    public static JsonObject createEmpty() {
        JsonObject fish = new JsonObject();

        JsonObject tiers = new JsonObject();
        tiers.addProperty("BRONZE", 0);
        tiers.addProperty("SILVER", 0);
        tiers.addProperty("GOLD", 0);
        tiers.addProperty("DIAMOND", 0);

        fish.add("Sulpher Skitter", Utils.deepCopy(tiers));
        fish.add("Obfuscated 1", Utils.deepCopy(tiers));
        fish.add("Steaming-Hot Flounder", Utils.deepCopy(tiers));
        fish.add("Obfuscated 2", Utils.deepCopy(tiers));
        fish.add("Gusher", Utils.deepCopy(tiers));
        fish.add("Blobfish", Utils.deepCopy(tiers));
        fish.add("Slugfish", Utils.deepCopy(tiers));
        fish.add("Obfuscated 3", Utils.deepCopy(tiers));
        fish.add("Flyfish", Utils.deepCopy(tiers));
        fish.add("Lavahorse", Utils.deepCopy(tiers));
        fish.add("Mana Ray", Utils.deepCopy(tiers));
        fish.add("Volcanic Stonefish", Utils.deepCopy(tiers));
        fish.add("Vanille", Utils.deepCopy(tiers));
        fish.add("Skeleton Fish", Utils.deepCopy(tiers));
        fish.add("Moldfin", Utils.deepCopy(tiers));
        fish.add("Soul Fish", Utils.deepCopy(tiers));
        fish.add("Karate Fish", Utils.deepCopy(tiers));
        fish.add("Golden Fish", Utils.deepCopy(tiers));

        return fish;
    }

    @SubscribeEvent
    public void init(ModInitEvent event) {
        configFile = event.configDirectory + "/dsmtrophyfish.json";
        if (fish.entrySet().isEmpty()) fish = createEmpty();
        fishSession = createEmpty();
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (!Utils.tabLocation.equals("Crimson Isle")) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        Matcher matcher = fishPattern.matcher(message);

        if (matcher.matches()) {
            String fishName = matcher.group("fish");
            String tier = matcher.group("tier");

            JsonObject fishObj = fish.get(fishName).getAsJsonObject();
            int amount = fishObj.get(tier).getAsInt();
            fishObj.addProperty(tier, amount + 1);

            JsonObject fishSessionObj = fishSession.get(fishName).getAsJsonObject();
            int amountSession = fishSessionObj.get(tier).getAsInt();
            fishSessionObj.addProperty(tier, amountSession + 1);

            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(configFile)) {
            new GsonBuilder().create().toJson(fish, writer);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getTierCount(JsonObject obj, String name) {
        JsonObject type = obj.get(name).getAsJsonObject();

        int bronze = type.get("BRONZE").getAsInt();
        int silver = type.get("SILVER").getAsInt();
        int gold = type.get("GOLD").getAsInt();
        int diamond = type.get("DIAMOND").getAsInt();

        return EnumChatFormatting.DARK_GRAY + "" + bronze + EnumChatFormatting.WHITE + "-" +
               EnumChatFormatting.GRAY + silver + EnumChatFormatting.WHITE + "-" +
               EnumChatFormatting.GOLD + gold + EnumChatFormatting.WHITE + "-" +
               EnumChatFormatting.AQUA + diamond;
    }

    public static int getSum(JsonObject obj, String name) {
        JsonObject type = obj.get(name).getAsJsonObject();
        return type.get("BRONZE").getAsInt() +
               type.get("SILVER").getAsInt() +
               type.get("GOLD").getAsInt() +
               type.get("DIAMOND").getAsInt();
    }

}
