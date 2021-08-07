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

public class WeightCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "weight";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " [name] [lily]";
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
            } else {
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
            }
        }).start();
    }

}
