package me.Danker.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class WeightCommand extends CommandBase {

    ArrayList<String> t12Minions = new ArrayList<>(Arrays.asList(
            "WHEAT_12",
            "CARROT_12",
            "POTATO_12",
            "PUMPKIN_12",
            "MELON_12",
            "MUSHROOM_12",
            "COCOA_12",
            "CACTUS_12",
            "SUGAR_CANE_12",
            "NETHER_WARTS_12"
    ));

    @Override
    public String getCommandName() {
        return "weight";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("we");
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " [name] [lily/farming]";
    }

    public static String usage(ICommandSender arg0) {
        return new WeightCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return Utils.getMatchingPlayers(args[0]);
        } else if (args.length == 2) {
            return getListOfStringsMatchingLastWord(args, "lily", "farming");
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        // MULTI THREAD DRIFTING
        new Thread(() -> {
            EntityPlayer player = (EntityPlayer) arg0;

            // Check key
            String key = ConfigHandler.getString("api", "APIKey");
            if (key.equals("")) {
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "API key not set. Use /setkey."));
                return;
            }

            // Get UUID for Hypixel API requests
            String username;
            String uuid;
            if (arg1.length == 0) {
                username = player.getName();
                uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking weight of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
            } else {
                username = arg1[0];
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking weight of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
                uuid = APIHandler.getUUID(username);
            }

            if (arg1.length < 2) {
                System.out.println("Fetching weight from Senither API...");
                String weightURL = "https://hypixel-api.senither.com/v1/profiles/" + uuid + "/weight?key=" + key;
                JsonObject weightResponse = APIHandler.getResponse(weightURL, true);
                if (weightResponse.get("status").getAsInt() != 200) {
                    String reason = weightResponse.get("reason").getAsString();
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Failed with reason: " + reason));
                    return;
                }

                JsonObject data = weightResponse.get("data").getAsJsonObject();
                double weight = data.get("weight").getAsDouble();
                double overflow = data.get("weight_overflow").getAsDouble();
                double skillWeight = data.get("skills").getAsJsonObject().get("weight").getAsDouble();
                double skillOverflow = data.get("skills").getAsJsonObject().get("weight_overflow").getAsDouble();
                double slayerWeight = data.get("slayers").getAsJsonObject().get("weight").getAsDouble();
                double slayerOverflow = data.get("slayers").getAsJsonObject().get("weight_overflow").getAsDouble();
                double dungeonWeight = data.get("dungeons").getAsJsonObject().get("weight").getAsDouble();
                double dungeonOverflow = data.get("dungeons").getAsJsonObject().get("weight_overflow").getAsDouble();

                NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + "-------------------\n" +
                        EnumChatFormatting.AQUA + " " + username + "'s Weight:\n" +
                        DankersSkyblockMod.TYPE_COLOUR + " Total Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(weight + overflow) + " (" + nf.format(weight) + " + " + nf.format(overflow) + ")\n" +
                        DankersSkyblockMod.TYPE_COLOUR + " Skill Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(skillWeight + skillOverflow) + " (" + nf.format(skillWeight) + " + " + nf.format(skillOverflow) + ")\n" +
                        DankersSkyblockMod.TYPE_COLOUR + " Slayers Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(slayerWeight + slayerOverflow) + " (" + nf.format(slayerWeight) + " + " + nf.format(slayerOverflow) + ")\n" +
                        DankersSkyblockMod.TYPE_COLOUR + " Dungeons Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(dungeonWeight + dungeonOverflow) + " (" + nf.format(dungeonWeight) + " + " + nf.format(dungeonOverflow) + ")\n" +
                        DankersSkyblockMod.DELIMITER_COLOUR + " " + EnumChatFormatting.BOLD + "-------------------"));
            } else if (arg1[1].equalsIgnoreCase("lily")) {
                System.out.println("Fetching weight from Lily API...");
                String weightURL = "https://lily.antonio32a.com/" + uuid + "?key=" + key;
                JsonObject weightResponse = APIHandler.getResponse(weightURL, true);
                if (!weightResponse.get("success").getAsBoolean()) {
                    String reason = weightResponse.get("error").getAsString();
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Failed with reason: " + reason));
                    return;
                }

                JsonObject data = weightResponse.get("data").getAsJsonObject();
                double weight = data.get("total").getAsDouble();
                double skillWeight = data.get("skill").getAsJsonObject().get("base").getAsDouble();
                double skillOverflow = data.get("skill").getAsJsonObject().get("overflow").getAsDouble();
                double slayerWeight = data.get("slayer").getAsDouble();
                double catacombsXPWeight = data.get("catacombs").getAsJsonObject().get("experience").getAsDouble();
                double catacombsBaseWeight = data.get("catacombs").getAsJsonObject().get("completion").getAsJsonObject().get("base").getAsDouble();
                double catacombsMasterWeight = data.get("catacombs").getAsJsonObject().get("completion").getAsJsonObject().get("master").getAsDouble();

                NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + "-------------------\n" +
                        EnumChatFormatting.AQUA + " " + username + "'s Weight (Lily):\n" +
                        DankersSkyblockMod.TYPE_COLOUR + " Total Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(weight) + "\n" +
                        DankersSkyblockMod.TYPE_COLOUR + " Skill Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(skillWeight + skillOverflow) + " (" + nf.format(skillWeight) + " + " + nf.format(skillOverflow) + ")\n" +
                        DankersSkyblockMod.TYPE_COLOUR + " Slayers Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(slayerWeight) + "\n" +
                        DankersSkyblockMod.TYPE_COLOUR + " Catacombs XP Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(catacombsXPWeight) + "\n" +
                        DankersSkyblockMod.TYPE_COLOUR + " Catacombs Completion Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(catacombsBaseWeight) + "\n" +
                        DankersSkyblockMod.TYPE_COLOUR + " Catacombs Master Completion Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(catacombsMasterWeight) + "\n" +
                        DankersSkyblockMod.DELIMITER_COLOUR + " " + EnumChatFormatting.BOLD + "-------------------"));
            } else if (arg1[1].equalsIgnoreCase("farming")) {
                String latestProfile = APIHandler.getLatestProfileID(uuid, key);
                if (latestProfile == null) return;

                String profileURL = "https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + key;
                System.out.println("Fetching profile...");
                JsonObject profileResponse = APIHandler.getResponse(profileURL, true);
                if (!profileResponse.get("success").getAsBoolean()) {
                    String reason = profileResponse.get("cause").getAsString();
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Failed with reason: " + reason));
                    return;
                }

                JsonObject userObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject();

                if (!userObject.has("collection")) {
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + username + " does not have collection API on."));
                    return;
                }

                JsonObject multipliers = DankersSkyblockMod.data.get("farmingWeight").getAsJsonObject();
                JsonObject collections = userObject.get("collection").getAsJsonObject();
                JsonObject jacob = userObject.has("jacob2") ? userObject.get("jacob2").getAsJsonObject() : null;
                JsonArray minions = userObject.get("crafted_generators").getAsJsonArray();

                // main weight
                long wheatColl = collections.has("WHEAT") ? collections.get("WHEAT").getAsLong() < 0 ? collections.get("WHEAT").getAsLong() + 4294967294L : collections.get("WHEAT").getAsLong() : 0;
                long carrotColl = collections.has("CARROT_ITEM") ? collections.get("CARROT_ITEM").getAsLong() < 0 ? collections.get("CARROT_ITEM").getAsLong() + 4294967294L : collections.get("CARROT_ITEM").getAsLong() : 0;
                long potatoColl = collections.has("POTATO_ITEM") ? collections.get("POTATO_ITEM").getAsLong() < 0 ? collections.get("POTATO_ITEM").getAsLong() + 4294967294L : collections.get("POTATO_ITEM").getAsLong() : 0;
                long pumpkinColl = collections.has("PUMPKIN") ? collections.get("PUMPKIN").getAsLong() < 0 ? collections.get("PUMPKIN").getAsLong() + 4294967294L : collections.get("PUMPKIN").getAsLong() : 0;
                long melonColl = collections.has("MELON") ? collections.get("MELON").getAsLong() < 0 ? collections.get("MELON").getAsLong() + 4294967294L : collections.get("MELON").getAsLong() : 0;
                long mushroomColl = collections.has("MUSHROOM_COLLECTION") ? collections.get("MUSHROOM_COLLECTION").getAsLong() < 0 ? collections.get("MUSHROOM_COLLECTION").getAsLong() + 4294967294L : collections.get("MUSHROOM_COLLECTION").getAsLong() : 0;
                long cocoaColl = collections.has("INK_SACK:3") ? collections.get("INK_SACK:3").getAsLong() < 0 ? collections.get("INK_SACK:3").getAsLong() + 4294967294L : collections.get("INK_SACK:3").getAsLong() : 0;
                long cactusColl = collections.has("CACTUS") ? collections.get("CACTUS").getAsLong() < 0 ? collections.get("CACTUS").getAsLong() + 4294967294L : collections.get("CACTUS").getAsLong() : 0;
                long caneColl = collections.has("SUGAR_CANE") ? collections.get("SUGAR_CANE").getAsLong() < 0 ? collections.get("SUGAR_CANE").getAsLong() + 4294967294L : collections.get("SUGAR_CANE").getAsLong() : 0;
                long wartColl = collections.has("NETHER_STALK") ? collections.get("NETHER_STALK").getAsLong() < 0 ? collections.get("NETHER_STALK").getAsLong() + 4294967294L : collections.get("NETHER_STALK").getAsLong() : 0;

                double wheatWeight = Math.round(wheatColl / multipliers.get("wheat").getAsDouble()) / 100D;
                double carrotWeight = Math.round(carrotColl / multipliers.get("carrot").getAsDouble()) / 100D;
                double potatoWeight = Math.round(potatoColl / multipliers.get("potato").getAsDouble()) / 100D;
                double pumpkinWeight = Math.round(pumpkinColl / multipliers.get("pumpkin").getAsDouble()) / 100D;
                double melonWeight = Math.round(melonColl / multipliers.get("melon").getAsDouble()) / 100D;
                double mushroomWeight = Math.round(mushroomColl / multipliers.get("mushroom").getAsDouble()) / 100D;
                double cocoaWeight = Math.round(cocoaColl / multipliers.get("cocoa").getAsDouble()) / 100D;
                double cactusWeight = Math.round(cactusColl / multipliers.get("cactus").getAsDouble()) / 100D;
                double caneWeight = Math.round(caneColl / multipliers.get("cane").getAsDouble()) / 100D;
                double wartWeight = Math.round(wartColl / multipliers.get("wart").getAsDouble()) / 100D;

                // bonus weight
                double farmingBonus = 0;
                double anitaBonus = 0;
                double medalBonus = 0;
                double minionBonus = 0;
                if (jacob != null) {
                    // farming cap
                    double farmingXP = userObject.get("experience_skill_farming").getAsDouble();
                    int cap = jacob.get("perks").getAsJsonObject().has("farming_level_cap") ? jacob.get("perks").getAsJsonObject().get("farming_level_cap").getAsInt() : 0;
                    if (farmingXP > 111672425D && cap == 10) {
                        farmingBonus = 250D;
                    } else if (farmingXP > 55172425) {
                        farmingBonus = 100D;
                    }

                    // anita bonus
                    anitaBonus = jacob.get("perks").getAsJsonObject().has("double_drops") ? jacob.get("perks").getAsJsonObject().get("double_drops").getAsInt() * 2D : 0;

                    // gold medals
                    int totalGolds = 0;
                    List<String> contests = jacob.get("contests").getAsJsonObject().entrySet().stream()
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toCollection(ArrayList::new));

                    // get total golds
                    for (String contest : contests) {
                        JsonObject contestData = jacob.get("contests").getAsJsonObject().get(contest).getAsJsonObject();
                        if (contestData.has("claimed_position")) {
                            if (contestData.get("claimed_position").getAsInt() <= contestData.get("claimed_participants").getAsInt() * 0.05 + 1) totalGolds++;
                        }
                    }

                    medalBonus = Math.floor(totalGolds / 50D) * 50D / 2D;
                    medalBonus = MathHelper.clamp_double(medalBonus, 0D, 500D);
                }

                // t12 minions
                for (JsonElement minion : minions) {
                    String minionName = minion.getAsString();
                    if (t12Minions.contains(minionName)) minionBonus += 5;
                }

                double mainWeight = Math.floor((wheatWeight + carrotWeight + potatoWeight + pumpkinWeight + melonWeight + mushroomWeight + cocoaWeight + cactusWeight + caneWeight + wartWeight) * 100D) / 100D;
                double bonusWeight = farmingBonus + anitaBonus + medalBonus + minionBonus;

                NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + EnumChatFormatting.BOLD + "-------------------\n" +
                        EnumChatFormatting.AQUA + username + "'s Weight (Farming):\n" +
                        DankersSkyblockMod.TYPE_COLOUR + "Total Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(mainWeight + bonusWeight) + "\n" +
                        DankersSkyblockMod.TYPE_COLOUR + "Collection Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(mainWeight) + "\n" +
                        DankersSkyblockMod.TYPE_COLOUR + "Bonus Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(bonusWeight) + "\n" +
                        DankersSkyblockMod.TYPE_COLOUR + "Farming XP Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(farmingBonus) + "\n" +
                        DankersSkyblockMod.TYPE_COLOUR + "Anita Bonus Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(anitaBonus) + "\n" +
                        DankersSkyblockMod.TYPE_COLOUR + "Gold Medal Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(medalBonus) + "\n" +
                        DankersSkyblockMod.TYPE_COLOUR + "Minion Weight: " + DankersSkyblockMod.VALUE_COLOUR + nf.format(minionBonus) + "\n" +
                        DankersSkyblockMod.DELIMITER_COLOUR + EnumChatFormatting.BOLD + "-------------------"));
            } else {
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
            }
        }).start();
    }

}
