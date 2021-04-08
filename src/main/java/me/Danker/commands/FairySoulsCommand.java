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

import java.util.List;

public class FairySoulsCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "fairysouls";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName() + " [name]";
    }

    public static String usage(ICommandSender arg0) {
        return new FairySoulsCommand().getCommandUsage(arg0);
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
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = ((EntityPlayer) sender);
        // MULTI THREAD DRIFTING
        new Thread(() -> {

            // Check key
            String key = ConfigHandler.getString("api", "APIKey");
            if(key.equals("")) {
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "API key not set. Use /setkey."));
            }

            // Get UUID for Hypixel API requests
            String username;
            String uuid;
            if(args.length == 0) {
                username = player.getName();
                uuid = APIHandler.getUUID(username);
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking fairy souls of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
            } else {
                username = args[0];
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Checking fairy souls of " + DankersSkyblockMod.SECONDARY_COLOUR + username));
                uuid = APIHandler.getUUID(username);
            }

            // Find fairy souls of latest profile
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

            // Extracting the fairy souls from the json data
            System.out.println("Fetching fairy souls");
            JsonObject userObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject();

            int fairy_souls = userObject.get("fairy_souls_collected").getAsInt();
            System.out.println(fairy_souls);

            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "The player " + username + " has " + DankersSkyblockMod.VALUE_COLOUR + fairy_souls + DankersSkyblockMod.MAIN_COLOUR + "/222" + " collected"));

        }).start();

    }
}
