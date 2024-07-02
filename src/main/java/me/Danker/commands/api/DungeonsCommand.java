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
import net.minecraft.event.HoverEvent;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DungeonsCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "dungeons";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " [name]";
    }

    public static String usage(ICommandSender arg0) {
        return new DungeonsCommand().getCommandUsage(arg0);
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
            player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking dungeon stats of " + ModConfig.getColour(ModConfig.secondaryColour) + username + ModConfig.getColour(ModConfig.mainColour) + " using Polyfrost's API."));

            // Find stats of latest profile
            JsonObject profileResponse = HypixelAPIHandler.getLatestProfile(uuid);
            if (profileResponse == null) return;

            System.out.println("Fetching player data...");
            JsonObject playerResponse = HypixelAPIHandler.getJsonObjectAuth(HypixelAPIHandler.URL + "player/" + uuid);

            if (playerResponse == null) {
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Could not connect to API."));
                return;
            }
            if (!playerResponse.get("success").getAsBoolean()) {
                String reason = playerResponse.get("cause").getAsString();
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Failed with reason: " + reason));
                return;
            }
            
            System.out.println("Fetching dungeon stats...");
            JsonObject dungeonsObject = Utils.getObjectFromPath(profileResponse, "members." + uuid + ".dungeons");
            JsonObject catacombsObject = Utils.getObjectFromPath(dungeonsObject, "dungeon_types.catacombs");
            if (!catacombsObject.has("experience")) {
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "This player has not played dungeons."));
                return;
            }

            double catacombs = Utils.xpToDungeonsLevel(catacombsObject.get("experience").getAsDouble());
            double healer = getClassLevel(dungeonsObject, "healer");
            double mage = getClassLevel(dungeonsObject, "mage");
            double berserk = getClassLevel(dungeonsObject, "berserk");
            double archer = getClassLevel(dungeonsObject, "archer");
            double tank = getClassLevel(dungeonsObject, "tank");
            double classAverage = Math.round((healer + mage + berserk + archer + tank) / 5D * 100D) / 100D;
            String selectedClass = Utils.capitalizeString(dungeonsObject.get("selected_dungeon_class").getAsString());

            int secrets = 0;
            JsonObject achievementsObj = Utils.getObjectFromPath(playerResponse, "player.achievements");
            if (achievementsObj.has("skyblock_treasure_hunter")) {
                secrets = achievementsObj.get("skyblock_treasure_hunter").getAsInt();
            }

            int highestFloor = 0;
            if (catacombsObject.has("highest_tier_completed")) {
                highestFloor = catacombsObject.get("highest_tier_completed").getAsInt();
            }
            JsonObject completionObj = catacombsObject.getAsJsonObject("tier_completions");

            JsonObject catacombsMasterObject = Utils.getObjectFromPath(dungeonsObject, "dungeon_types.master_catacombs");
            boolean hasPlayedMaster = catacombsMasterObject.has("highest_tier_completed");

            int highestMasterFloor = 0;
            JsonObject completionMasterObj = null;
            if (hasPlayedMaster) {
                highestMasterFloor = catacombsMasterObject.get("highest_tier_completed").getAsInt();
                completionMasterObj = catacombsMasterObject.getAsJsonObject("tier_completions");
            }

            ChatComponentText classLevels = new ChatComponentText(EnumChatFormatting.GOLD + " Selected Class: " + selectedClass + "\n\n" +
                                                                  EnumChatFormatting.RED + " Catacombs Level: " + catacombs + "\n" +
                                                                  EnumChatFormatting.RED + " Class Average: " + classAverage + "\n\n" +
                                                                  EnumChatFormatting.YELLOW + " Healer Level: " + healer + "\n" +
                                                                  EnumChatFormatting.LIGHT_PURPLE + " Mage Level: " + mage + "\n" +
                                                                  EnumChatFormatting.RED + " Berserk Level: " + berserk + "\n" +
                                                                  EnumChatFormatting.GREEN + " Archer Level: " + archer + "\n" +
                                                                  EnumChatFormatting.BLUE + " Tank Level: " + tank + "\n\n" +
                                                                  EnumChatFormatting.WHITE + " Secrets Found: " + NumberFormat.getIntegerInstance(Locale.US).format(secrets) + "\n\n");

            StringBuilder completionsHoverString = new StringBuilder();
            for (int i = 0; i <= highestFloor; i++) {
                completionsHoverString
                    .append(EnumChatFormatting.GOLD)
                    .append(i == 0 ? "Entrance: " : "Floor " + i + ": ")
                    .append(EnumChatFormatting.RESET)
                    .append(completionObj.get(String.valueOf(i)).getAsInt())
                    .append(i < highestFloor || hasPlayedMaster ? "\n": "");
            }
            for (int i = 1; i <= highestMasterFloor; i++) {
                if (completionMasterObj != null && completionMasterObj.has(String.valueOf(i))) {
                    completionsHoverString
                        .append(EnumChatFormatting.GOLD)
                        .append("Master Floor ")
                        .append(i)
                        .append(": ")
                        .append(EnumChatFormatting.RESET)
                        .append(completionMasterObj.get(String.valueOf(i)).getAsInt())
                        .append(i < highestMasterFloor ? "\n": "");
                }
            }

            ChatComponentText completions;
            if (hasPlayedMaster) {
                completions = new ChatComponentText(EnumChatFormatting.GOLD + " Highest Floor Completed: Master " + highestMasterFloor);
            } else {
                completions = new ChatComponentText(EnumChatFormatting.GOLD + " Highest Floor Completed: " + highestFloor);
            }
            completions.setChatStyle(completions.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(completionsHoverString.toString()))));

            player.addChatMessage(
                    new ChatComponentText(ModConfig.getDelimiter())
                    .appendText("\n")
                    .appendSibling(classLevels)
                    .appendSibling(completions)
                    .appendText("\n")
                    .appendSibling(new ChatComponentText(ModConfig.getDelimiter())));
        }).start();
    }

    double getClassLevel(JsonObject obj, String dungeonClass) {
        JsonObject clazz = Utils.getObjectFromPath(obj, "player_classes." + dungeonClass);
        if (clazz != null) {
            if (clazz.has("experience")) {
                double xp = clazz.get("experience").getAsDouble();
                return MathHelper.clamp_double(Utils.xpToDungeonsLevel(xp), 0D, 50D);
            }
        }

        return 0D;
    }

}
