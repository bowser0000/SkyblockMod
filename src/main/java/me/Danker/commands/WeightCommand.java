package me.Danker.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.handlers.APIHandler;
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
            String key = ModConfig.apiKey;
            if (key.equals("")) {
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "API key not set. Use /setkey."));
                return;
            }

            // Get UUID for Hypixel API requests
            String username;
            String uuid;
            if (arg1.length == 0) {
                username = player.getName();
                uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking weight of " + ModConfig.getColour(ModConfig.secondaryColour) + username));
            } else {
                username = arg1[0];
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking weight of " + ModConfig.getColour(ModConfig.secondaryColour) + username));
                uuid = APIHandler.getUUID(username);
            }

            if (arg1.length < 2) {
                System.out.println("Fetching weight from SkyShiiyu API...");
                String weightURL = "https://sky.shiiyu.moe/api/v2/profile/" + username;
                JsonObject weightResponse = APIHandler.getResponse(weightURL, true);
                if (weightResponse.has("error")) {
                    String reason = weightResponse.get("error").getAsString();
                    player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Failed with reason: " + reason));
                    return;
                }

                String latestProfile = APIHandler.getLatestProfileID(uuid, key);
                if (latestProfile == null) return;

                JsonObject data = weightResponse.get("profiles").getAsJsonObject().get(latestProfile).getAsJsonObject().get("data").getAsJsonObject().get("weight").getAsJsonObject().get("senither").getAsJsonObject();

                double weight = data.get("overall").getAsDouble();

                double skillWeight = data.get("skill").getAsJsonObject().get("total").getAsDouble();
                double farmingWeight = data.get("skill").getAsJsonObject().get("skills").getAsJsonObject().get("farming").getAsDouble();
                double miningWeight = data.get("skill").getAsJsonObject().get("skills").getAsJsonObject().get("mining").getAsDouble();
                double combatWeight = data.get("skill").getAsJsonObject().get("skills").getAsJsonObject().get("combat").getAsDouble();
                double foragingWeight = data.get("skill").getAsJsonObject().get("skills").getAsJsonObject().get("foraging").getAsDouble();
                double fishingWeight = data.get("skill").getAsJsonObject().get("skills").getAsJsonObject().get("fishing").getAsDouble();
                double enchantingWeight = data.get("skill").getAsJsonObject().get("skills").getAsJsonObject().get("enchanting").getAsDouble();
                double alchemyWeight = data.get("skill").getAsJsonObject().get("skills").getAsJsonObject().get("alchemy").getAsDouble();
                double tamingWeight = data.get("skill").getAsJsonObject().get("skills").getAsJsonObject().get("taming").getAsDouble();

                double slayerWeight = data.get("slayer").getAsJsonObject().get("total").getAsDouble();
                double zombieWeight = data.get("slayer").getAsJsonObject().get("slayers").getAsJsonObject().get("zombie").getAsDouble();
                double spiderWeight = data.get("slayer").getAsJsonObject().get("slayers").getAsJsonObject().get("spider").getAsDouble();
                double wolfWeight = data.get("slayer").getAsJsonObject().get("slayers").getAsJsonObject().get("wolf").getAsDouble();
                double endermanWeight = data.get("slayer").getAsJsonObject().get("slayers").getAsJsonObject().get("enderman").getAsDouble();
                double blazeWeight = data.get("slayer").getAsJsonObject().get("slayers").getAsJsonObject().get("blaze").getAsDouble();

                double dungeonWeight = data.get("dungeon").getAsJsonObject().get("total").getAsDouble();
                double cataWeight = data.get("dungeon").getAsJsonObject().get("dungeons").getAsJsonObject().get("catacombs").getAsJsonObject().get("weight").getAsDouble();
                double healerWeight = data.get("dungeon").getAsJsonObject().get("classes").getAsJsonObject().get("healer").getAsJsonObject().get("weight").getAsDouble();
                double mageWeight = data.get("dungeon").getAsJsonObject().get("classes").getAsJsonObject().get("mage").getAsJsonObject().get("weight").getAsDouble();
                double berserkWeight = data.get("dungeon").getAsJsonObject().get("classes").getAsJsonObject().get("berserk").getAsJsonObject().get("weight").getAsDouble();
                double archerWeight = data.get("dungeon").getAsJsonObject().get("classes").getAsJsonObject().get("archer").getAsJsonObject().get("weight").getAsDouble();
                double tankWeight = data.get("dungeon").getAsJsonObject().get("classes").getAsJsonObject().get("tank").getAsJsonObject().get("weight").getAsDouble();

                NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.BOLD + ModConfig.getColour(ModConfig.delimiterColour) + "" + EnumChatFormatting.BOLD + "-------------------\n" +
                        EnumChatFormatting.AQUA + " " + username + "'s Weight:\n" +
                        ModConfig.getColour(ModConfig.typeColour) + " Total Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(weight) + "\n\n" +
                        ModConfig.getColour(ModConfig.typeColour) + " Skill Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(skillWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Farming Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(farmingWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Mining Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(miningWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Combat Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(combatWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Foraging Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(foragingWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Fishing Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(fishingWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Enchanting Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(enchantingWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Alchemy Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(alchemyWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Taming Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(tamingWeight) + "\n\n" +
                        ModConfig.getColour(ModConfig.typeColour) + " Slayers Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(slayerWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Zombie Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(zombieWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Spider Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(spiderWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Wolf Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(wolfWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Enderman Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(endermanWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Blaze Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(blazeWeight) + "\n\n" +
                        ModConfig.getColour(ModConfig.typeColour) + " Dungeons Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(dungeonWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Catacombs XP Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(cataWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Healer Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(healerWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Mage Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(mageWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Berserk Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(berserkWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Archer Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(archerWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "   Tank Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(tankWeight) + "\n" +
                        EnumChatFormatting.BOLD + ModConfig.getColour(ModConfig.delimiterColour) + " " + EnumChatFormatting.BOLD + "-------------------"));
            } else if (arg1[1].equalsIgnoreCase("lily")) {
                System.out.println("Fetching weight from SkyShiiyu API...");
                String weightURL = "https://sky.shiiyu.moe/api/v2/profile/" + username;
                JsonObject weightResponse = APIHandler.getResponse(weightURL, true);
                if (weightResponse.has("error")) {
                    String reason = weightResponse.get("error").getAsString();
                    player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Failed with reason: " + reason));
                    return;
                }

                String latestProfile = APIHandler.getLatestProfileID(uuid, key);
                if (latestProfile == null) return;

                JsonObject data = weightResponse.get("profiles").getAsJsonObject().get(latestProfile).getAsJsonObject().get("data").getAsJsonObject().get("weight").getAsJsonObject().get("lily").getAsJsonObject();

                double weight = data.get("total").getAsDouble();
                double skillWeight = data.get("skill").getAsJsonObject().get("base").getAsDouble();
                double skillOverflow = data.get("skill").getAsJsonObject().get("overflow").getAsDouble();
                double slayerWeight = data.get("slayer").getAsDouble();
                double catacombsXPWeight = data.get("catacombs").getAsJsonObject().get("experience").getAsDouble();
                double catacombsBaseWeight = data.get("catacombs").getAsJsonObject().get("completion").getAsJsonObject().get("base").getAsDouble();
                double catacombsMasterWeight = data.get("catacombs").getAsJsonObject().get("completion").getAsJsonObject().get("master").getAsDouble();

                NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.BOLD + ModConfig.getColour(ModConfig.delimiterColour) + "" + EnumChatFormatting.BOLD + "-------------------\n" +
                        EnumChatFormatting.AQUA + " " + username + "'s Weight (Lily):\n" +
                        ModConfig.getColour(ModConfig.typeColour) + " Total Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(weight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + " Skill Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(skillWeight + skillOverflow) + " (" + nf.format(skillWeight) + " + " + nf.format(skillOverflow) + ")\n" +
                        ModConfig.getColour(ModConfig.typeColour) + " Slayers Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(slayerWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + " Catacombs XP Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(catacombsXPWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + " Catacombs Completion Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(catacombsBaseWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + " Catacombs Master Completion Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(catacombsMasterWeight) + "\n" +
                        EnumChatFormatting.BOLD + ModConfig.getColour(ModConfig.delimiterColour) + " " + EnumChatFormatting.BOLD + "-------------------"));
            } else if (arg1[1].equalsIgnoreCase("farming")) {
                String latestProfile = APIHandler.getLatestProfileID(uuid, key);
                if (latestProfile == null) return;

                String profileURL = "https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + key;
                System.out.println("Fetching profile...");
                JsonObject profileResponse = APIHandler.getResponse(profileURL, true);
                if (!profileResponse.get("success").getAsBoolean()) {
                    String reason = profileResponse.get("cause").getAsString();
                    player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Failed with reason: " + reason));
                    return;
                }

                JsonObject userObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject();

                if (!userObject.has("collection")) {
                    player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + username + " does not have collection API on."));
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
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.BOLD + ModConfig.getColour(ModConfig.delimiterColour) + EnumChatFormatting.BOLD + "-------------------\n" +
                        EnumChatFormatting.AQUA + username + "'s Weight (Farming):\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "Total Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(mainWeight + bonusWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "Collection Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(mainWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "Bonus Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(bonusWeight) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "Farming XP Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(farmingBonus) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "Anita Bonus Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(anitaBonus) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "Gold Medal Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(medalBonus) + "\n" +
                        ModConfig.getColour(ModConfig.typeColour) + "Minion Weight: " + ModConfig.getColour(ModConfig.valueColour) + nf.format(minionBonus) + "\n" +
                        EnumChatFormatting.BOLD + ModConfig.getColour(ModConfig.delimiterColour) + EnumChatFormatting.BOLD + "-------------------"));
            } else {
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: " + getCommandUsage(arg0)));
            }
        }).start();
    }

}
