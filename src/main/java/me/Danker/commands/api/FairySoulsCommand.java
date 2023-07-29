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

            // Get UUID for Hypixel API requests
            String username;
            String uuid;
            if (args.length == 0) {
                username = player.getName();
                uuid = APIHandler.getUUID(username);
            } else {
                username = args[0];
                uuid = APIHandler.getUUID(username);
            }
            player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking fairy souls of " + ModConfig.getColour(ModConfig.secondaryColour) + username));

            // Find fairy souls of latest profile
            JsonObject profileResponse = HypixelAPIHandler.getLatestProfile(uuid);
            if (profileResponse == null) return;

            // Extracting the fairy souls from the json data
            System.out.println("Fetching fairy souls");
            JsonObject userObject = profileResponse.get("members").getAsJsonObject().get(uuid).getAsJsonObject();

            int fairy_souls = userObject.get("fairy_souls_collected").getAsInt();
            player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.secondaryColour) + username + ModConfig.getColour(ModConfig.mainColour) + " has " + ModConfig.getColour(ModConfig.valueColour) + fairy_souls + ModConfig.getColour(ModConfig.mainColour) + "/242 collected"));

        }).start();

    }
}
