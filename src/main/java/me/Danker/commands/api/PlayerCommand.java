package me.Danker.commands.api;

import com.google.gson.JsonObject;
import me.Danker.config.ModConfig;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.HypixelAPIHandler;
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

            // Get UUID for Hypixel API requests
            String username;
            String uuid;
            if (arg1.length == 0) {
                username = player.getName();
                uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");
            } else {
                username = arg1[0];
                uuid = APIHandler.getUUID(username);
            }
            player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking stats of " + ModConfig.getColour(ModConfig.secondaryColour) + username + ModConfig.getColour(ModConfig.mainColour) + " using Polyfrost's API."));

            // Find stats of latest profile
            JsonObject profileResponse = HypixelAPIHandler.getLatestProfile(uuid);
            if (profileResponse == null) return;

            String latestProfileID = HypixelAPIHandler.getLatestProfileID(uuid);
            if (latestProfileID == null) return;

            // Skills
            System.out.println("Fetching skills...");
            JsonObject userObject = Utils.getObjectFromPath(profileResponse, "members." + uuid);
            JsonObject experienceObj = Utils.getObjectFromPath(userObject, "player_data.experience");

            double farmingLevel = 0;
            double miningLevel = 0;
            double combatLevel = 0;
            double foragingLevel = 0;
            double fishingLevel = 0;
            double enchantingLevel = 0;
            double alchemyLevel = 0;
            double tamingLevel = 0;
            double carpentryLevel = 0;

            if (experienceObj.has("SKILL_FARMING") || experienceObj.has("SKILL_MINING") || experienceObj.has("SKILL_COMBAT") || experienceObj.has("SKILL_FORAGING") || experienceObj.has("SKILL_FISHING") || experienceObj.has("SKILL_ENCHANTING") || experienceObj.has("SKILL_ALCHEMY")) {
                if (experienceObj.has("SKILL_FARMING")) {
                    farmingLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_FARMING").getAsDouble(), 60);
                    farmingLevel = (double) Math.round(farmingLevel * 100) / 100;
                }
                if (experienceObj.has("SKILL_MINING")) {
                    miningLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_MINING").getAsDouble(), 60);
                    miningLevel = (double) Math.round(miningLevel * 100) / 100;
                }
                if (experienceObj.has("SKILL_COMBAT")) {
                    combatLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_COMBAT").getAsDouble(), 60);
                    combatLevel = (double) Math.round(combatLevel * 100) / 100;
                }
                if (experienceObj.has("SKILL_FORAGING")) {
                    foragingLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_FORAGING").getAsDouble(), 50);
                    foragingLevel = (double) Math.round(foragingLevel * 100) / 100;
                }
                if (experienceObj.has("SKILL_FISHING")) {
                    fishingLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_FISHING").getAsDouble(), 50);
                    fishingLevel = (double) Math.round(fishingLevel * 100) / 100;
                }
                if (experienceObj.has("SKILL_ENCHANTING")) {
                    enchantingLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_ENCHANTING").getAsDouble(), 60);
                    enchantingLevel = (double) Math.round(enchantingLevel * 100) / 100;
                }
                if (experienceObj.has("SKILL_ALCHEMY")) {
                    alchemyLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_ALCHEMY").getAsDouble(), 50);
                    alchemyLevel = (double) Math.round(alchemyLevel * 100) / 100;
                }
                if (experienceObj.has("SKILL_TAMING")) {
                    tamingLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_TAMING").getAsDouble(), 50);
                    tamingLevel = (double) Math.round(tamingLevel * 100) / 100;
                }
                if (experienceObj.has("SKILL_CARPENTRY")) {
                    carpentryLevel = Utils.xpToSkillLevel(experienceObj.get("SKILL_CARPENTRY").getAsDouble(), 50);
                    carpentryLevel = (double) Math.round(carpentryLevel * 100) / 100;
                }
            } else {
                // Get skills from achievement API, will be floored

                System.out.println("Fetching skills from achievement API");
                JsonObject playerObject = HypixelAPIHandler.getJsonObjectAuth(HypixelAPIHandler.URL + "player/" + uuid);

                if (playerObject == null) {
                    player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Could not connect to API."));
                    return;
                }
                if (!playerObject.get("success").getAsBoolean()) {
                    String reason = profileResponse.get("cause").getAsString();
                    player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Failed with reason: " + reason));
                    return;
                }

                JsonObject achievementObject = Utils.getObjectFromPath(playerObject, "player.achievements");
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

            double skillAvg = (farmingLevel + miningLevel + combatLevel + foragingLevel + fishingLevel + enchantingLevel + alchemyLevel + tamingLevel + carpentryLevel) / 9;
            skillAvg = (double) Math.round(skillAvg * 100) / 100;
            double trueAvg = (Math.floor(farmingLevel) + Math.floor(miningLevel) + Math.floor(combatLevel) + Math.floor(foragingLevel) + Math.floor(fishingLevel) + Math.floor(enchantingLevel) + Math.floor(alchemyLevel) + Math.floor(tamingLevel) + Math.floor(carpentryLevel)) / 9;
            trueAvg = (double) Math.round(trueAvg * 100) / 100;

            // Slayers
            System.out.println("Fetching slayer stats...");
            JsonObject slayersObject = Utils.getObjectFromPath(userObject, "slayer.slayer_bosses");
            // Zombie
            int zombieXP = 0;
            if (slayersObject.getAsJsonObject("zombie").has("xp")) {
                zombieXP = slayersObject.getAsJsonObject("zombie").get("xp").getAsInt();
            }
            // Spider
            int spiderXP = 0;
            if (slayersObject.getAsJsonObject("spider").has("xp")) {
                spiderXP = slayersObject.getAsJsonObject("spider").get("xp").getAsInt();
            }
            // Wolf
            int wolfXP = 0;
            if (slayersObject.getAsJsonObject("wolf").has("xp")) {
                wolfXP = slayersObject.getAsJsonObject("wolf").get("xp").getAsInt();
            }
            // Enderman
            int endermanXP = 0;
            if (slayersObject.getAsJsonObject("enderman").has("xp")) {
                endermanXP = slayersObject.getAsJsonObject("enderman").get("xp").getAsInt();
            }
            // Blaze
            int blazeXP = 0;
            if (slayersObject.getAsJsonObject("blaze").has("xp")) {
                blazeXP = slayersObject.getAsJsonObject("blaze").get("xp").getAsInt();
            }
            // Vampire
            int vampireXP = 0;
            if (slayersObject.getAsJsonObject("vampire").has("xp")) {
                vampireXP = slayersObject.getAsJsonObject("vampire").get("xp").getAsInt();
            }

            int totalXP = zombieXP + spiderXP + wolfXP + endermanXP + blazeXP + vampireXP;

            // Bank
            System.out.println("Fetching bank + purse coins...");
            double bankCoins = 0;
            double purseCoins = userObject.getAsJsonObject("currencies").get("coin_purse").getAsDouble();
            purseCoins = Math.floor(purseCoins * 100.0) / 100.0;

            // Check for bank api
            if (profileResponse.has("banking")) {
                bankCoins = profileResponse.getAsJsonObject("banking").get("balance").getAsDouble();
                bankCoins = Math.floor(bankCoins * 100.0) / 100.0;
            }

            // Weight
            System.out.println("Fetching weight from SkyShiiyu API...");
            String weightURL = "https://sky.shiiyu.moe/api/v2/profile/" + username;
            JsonObject weightResponse = APIHandler.getResponse(weightURL, true);
            if (weightResponse.has("error")) {
                String reason = weightResponse.get("error").getAsString();
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Failed with reason: " + reason));
                return;
            }

            double weight = Utils.getObjectFromPath(weightResponse, "profiles." + latestProfileID + ".data.weight.senither").get("overall").getAsDouble();

            NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
            NumberFormat nfd = NumberFormat.getNumberInstance(Locale.US);
            player.addChatMessage(new ChatComponentText(ModConfig.getDelimiter() + "\n" +
                                                        EnumChatFormatting.AQUA + " " + username + "'s Skills:\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Farming: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + farmingLevel + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Mining: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + miningLevel + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Combat: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + combatLevel + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Foraging: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + foragingLevel + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Fishing: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + fishingLevel + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Enchanting: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + enchantingLevel + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Alchemy: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + alchemyLevel + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Taming: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + tamingLevel + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Carpentry: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + carpentryLevel + "\n" +
                                                        EnumChatFormatting.AQUA + " Average Skill Level: " + ModConfig.getColour(ModConfig.skillAverageColour) + EnumChatFormatting.BOLD + skillAvg + "\n" +
                                                        EnumChatFormatting.AQUA + " True Average Skill Level: " + ModConfig.getColour(ModConfig.skillAverageColour) + EnumChatFormatting.BOLD + trueAvg + "\n\n" +
                                                        EnumChatFormatting.AQUA + " " + username + "'s Total Slayer XP: " + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + nf.format(totalXP) + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Zombie XP: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + nf.format(zombieXP) + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Spider XP: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + nf.format(spiderXP) + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Wolf XP: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + nf.format(wolfXP) + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Enderman XP: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + nf.format(endermanXP) + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Blaze XP: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + nf.format(blazeXP) + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Vampire XP: " + ModConfig.getColour(ModConfig.valueColour) + EnumChatFormatting.BOLD + nf.format(vampireXP) + "\n\n" +
                                                        EnumChatFormatting.AQUA + " " + username + "'s Coins:\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Bank: " + (bankCoins == 0 ? EnumChatFormatting.RED + "Bank API disabled." : EnumChatFormatting.GOLD + nf.format(bankCoins)) + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Purse: " + EnumChatFormatting.GOLD + nf.format(purseCoins) + "\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Total: " + EnumChatFormatting.GOLD + nf.format(bankCoins + purseCoins) + "\n\n" +
                                                        EnumChatFormatting.AQUA + " " + username + "'s Weight:\n" +
                                                        ModConfig.getColour(ModConfig.typeColour) + " Total Weight: " + ModConfig.getColour(ModConfig.valueColour) + nfd.format(weight) + "\n" +
                                                        ModConfig.getDelimiter()));
        }).start();
    }

}
