package me.Danker.commands;

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

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PlayerCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "player";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " [name]";
    }

    public static String usage(ICommandSender arg0) {
        return new SkillsCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return Utils.getMatchingPlayers(args[0]);
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
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking stats of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
            } else {
                username = arg1[0];
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking stats of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
                uuid = APIHandler.getUUID(username);
            }

            // Find stats of latest profile
            String latestProfile = APIHandler.getLatestProfileID(uuid, key);
            if (latestProfile == null) return;

            String profileURL = "https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + key;
            System.out.println("Fetching profile...");
            JsonObject profileResponse = APIHandler.getResponse(profileURL);
            if (!profileResponse.get("success").getAsBoolean()) {
                String reason = profileResponse.get("cause").getAsString();
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Failed with reason: " + reason));
                return;
            }

            // Skills
            System.out.println("Fetching skills...");
            JsonObject userObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject();

            double farmingLevel = 0;
            double miningLevel = 0;
            double combatLevel = 0;
            double foragingLevel = 0;
            double fishingLevel = 0;
            double enchantingLevel = 0;
            double alchemyLevel = 0;
            double tamingLevel = 0;

            if (userObject.has("experience_skill_farming") || userObject.has("experience_skill_mining") || userObject.has("experience_skill_combat") || userObject.has("experience_skill_foraging") || userObject.has("experience_skill_fishing") || userObject.has("experience_skill_enchanting") || userObject.has("experience_skill_alchemy")) {
                if (userObject.has("experience_skill_farming")) {
                    farmingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_farming").getAsDouble(), 60);
                    farmingLevel = (double) Math.round(farmingLevel * 100) / 100;
                }
                if (userObject.has("experience_skill_mining")) {
                    miningLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_mining").getAsDouble(), 60);
                    miningLevel = (double) Math.round(miningLevel * 100) / 100;
                }
                if (userObject.has("experience_skill_combat")) {
                    combatLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_combat").getAsDouble(), 60);
                    combatLevel = (double) Math.round(combatLevel * 100) / 100;
                }
                if (userObject.has("experience_skill_foraging")) {
                    foragingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_foraging").getAsDouble(), 50);
                    foragingLevel = (double) Math.round(foragingLevel * 100) / 100;
                }
                if (userObject.has("experience_skill_fishing")) {
                    fishingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_fishing").getAsDouble(), 50);
                    fishingLevel = (double) Math.round(fishingLevel * 100) / 100;
                }
                if (userObject.has("experience_skill_enchanting")) {
                    enchantingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_enchanting").getAsDouble(), 60);
                    enchantingLevel = (double) Math.round(enchantingLevel * 100) / 100;
                }
                if (userObject.has("experience_skill_alchemy")) {
                    alchemyLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_alchemy").getAsDouble(), 50);
                    alchemyLevel = (double) Math.round(alchemyLevel * 100) / 100;
                }
                if (userObject.has("experience_skill_taming")) {
                    tamingLevel = Utils.xpToSkillLevel(userObject.get("experience_skill_taming").getAsDouble(), 50);
                    tamingLevel = (double) Math.round(tamingLevel * 100) / 100;
                }
            } else {
                // Get skills from achievement API, will be floored

                String playerURL = "https://api.hypixel.net/player?uuid=" + uuid + "&key=" + key;
                System.out.println("Fetching skills from achievement API");
                JsonObject playerObject = APIHandler.getResponse(playerURL);

                if (!playerObject.get("success").getAsBoolean()) {
                    String reason = profileResponse.get("cause").getAsString();
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Failed with reason: " + reason));
                    return;
                }

                JsonObject achievementObject = playerObject.get("player").getAsJsonObject().get("achievements").getAsJsonObject();
                if (achievementObject.has("skyblock_harvester")) {
                    farmingLevel = achievementObject.get("skyblock_harvester").getAsInt();
                }
                if (achievementObject.has("skyblock_excavator")) {
                    miningLevel = achievementObject.get("skyblock_excavator").getAsInt();
                }
                if (achievementObject.has("skyblock_combat")) {
                    combatLevel = achievementObject.get("skyblock_combat").getAsInt();
                }
                if (achievementObject.has("skyblock_gatherer")) {
                    foragingLevel = Math.min(achievementObject.get("skyblock_gatherer").getAsInt(), 50);
                }
                if (achievementObject.has("skyblock_angler")) {
                    fishingLevel = Math.min(achievementObject.get("skyblock_angler").getAsInt(), 50);
                }
                if (achievementObject.has("skyblock_augmentation")) {
                    enchantingLevel = achievementObject.get("skyblock_augmentation").getAsInt();
                }
                if (achievementObject.has("skyblock_concoctor")) {
                    alchemyLevel = Math.min(achievementObject.get("skyblock_concoctor").getAsInt(), 50);
                }
                if (achievementObject.has("skyblock_domesticator")) {
                    tamingLevel = Math.min(achievementObject.get("skyblock_domesticator").getAsInt(), 50);
                }
            }

            double skillAvg = (farmingLevel + miningLevel + combatLevel + foragingLevel + fishingLevel + enchantingLevel + alchemyLevel + tamingLevel) / 8;
            skillAvg = (double) Math.round(skillAvg * 100) / 100;
            double trueAvg = (Math.floor(farmingLevel) + Math.floor(miningLevel) + Math.floor(combatLevel) + Math.floor(foragingLevel) + Math.floor(fishingLevel) + Math.floor(enchantingLevel) + Math.floor(alchemyLevel) + Math.floor(tamingLevel)) / 8;

            // Slayers
            System.out.println("Fetching slayer stats...");
            JsonObject slayersObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("slayer_bosses").getAsJsonObject();
            // Zombie
            int zombieXP = 0;
            if (slayersObject.get("zombie").getAsJsonObject().has("xp")) {
                zombieXP = slayersObject.get("zombie").getAsJsonObject().get("xp").getAsInt();
            }
            // Spider
            int spiderXP = 0;
            if (slayersObject.get("spider").getAsJsonObject().has("xp")) {
                spiderXP = slayersObject.get("spider").getAsJsonObject().get("xp").getAsInt();
            }
            // Wolf
            int wolfXP = 0;
            if (slayersObject.get("wolf").getAsJsonObject().has("xp")) {
                wolfXP = slayersObject.get("wolf").getAsJsonObject().get("xp").getAsInt();
            }

            // Bank
            System.out.println("Fetching bank + purse coins...");
            double bankCoins = 0;
            double purseCoins = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("coin_purse").getAsDouble();
            purseCoins = Math.floor(purseCoins * 100.0) / 100.0;

            // Check for bank api
            if (profileResponse.get("profile").getAsJsonObject().has("banking")) {
                bankCoins = profileResponse.get("profile").getAsJsonObject().get("banking").getAsJsonObject().get("balance").getAsDouble();
                bankCoins = Math.floor(bankCoins * 100.0) / 100.0;
            }

            // Weight
            System.out.println("Fetching weight from Senither API...");
            String weightURL = "https://hypixel-api.senither.com/v1/profiles/" + uuid + "/weight?key=" + key;
            JsonObject weightResponse = APIHandler.getResponse(weightURL);
            if (weightResponse.get("status").getAsInt() != 200) {
                String reason = weightResponse.get("reason").getAsString();
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Failed with reason: " + reason));
                return;
            }

            double weight = weightResponse.get("data").getAsJsonObject().get("weight").getAsDouble();
            double overflow = weightResponse.get("data").getAsJsonObject().get("weight_overflow").getAsDouble();

            NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
            NumberFormat nfd = NumberFormat.getNumberInstance(Locale.US);
            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + "-------------------\n" +
                                                        EnumChatFormatting.AQUA + " " + username + "'s Skills:\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Farming: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + farmingLevel + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Mining: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + miningLevel + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Combat: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + combatLevel + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Foraging: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + foragingLevel + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Fishing: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + fishingLevel + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Enchanting: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + enchantingLevel + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Alchemy: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + alchemyLevel + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Taming: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + tamingLevel + "\n" +
                                                        EnumChatFormatting.AQUA + " Average Skill Level: " + DankersSkyblockMod.SKILL_AVERAGE_COLOUR + EnumChatFormatting.BOLD + skillAvg + "\n" +
                                                        EnumChatFormatting.AQUA + " True Average Skill Level: " + DankersSkyblockMod.SKILL_AVERAGE_COLOUR + EnumChatFormatting.BOLD + trueAvg + "\n\n" +
                                                        EnumChatFormatting.AQUA + " " + username + "'s Total Slayer XP: " + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + nf.format(zombieXP + spiderXP + wolfXP) + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Zombie XP: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + nf.format(zombieXP) + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Spider XP: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + nf.format(spiderXP) + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Wolf XP: " + DankersSkyblockMod.VALUE_COLOUR + EnumChatFormatting.BOLD + nf.format(wolfXP) + "\n\n" +
                                                        EnumChatFormatting.AQUA + " " + username + "'s Coins:\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Bank: " + (bankCoins == 0 ? EnumChatFormatting.RED + "Bank API disabled." : EnumChatFormatting.GOLD + nf.format(bankCoins)) + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Purse: " + EnumChatFormatting.GOLD + nf.format(purseCoins) + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Total: " + EnumChatFormatting.GOLD + nf.format(bankCoins + purseCoins) + "\n\n" +
                                                        EnumChatFormatting.AQUA + " " + username + "'s Weight:\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Total Weight: " + DankersSkyblockMod.VALUE_COLOUR + nfd.format(weight + overflow) + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Weight: " + DankersSkyblockMod.VALUE_COLOUR + nfd.format(weight) + "\n" +
                                                        DankersSkyblockMod.TYPE_COLOUR + " Overflow: " + DankersSkyblockMod.VALUE_COLOUR + nfd.format(overflow) + "\n" +
                                                        DankersSkyblockMod.DELIMITER_COLOUR + " " + EnumChatFormatting.BOLD + "-------------------"));
        }).start();
    }

}
