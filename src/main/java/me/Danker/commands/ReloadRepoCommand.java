package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.ColouredNames;
import me.Danker.handlers.APIHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class ReloadRepoCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "reloaddsmrepo";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName();
    }

    public static String usage(ICommandSender arg0) {
        return new ReloadRepoCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        // MULTI THREAD DRIFTING
        new Thread(() -> {
            EntityPlayer player = (EntityPlayer) arg0;

            DankersSkyblockMod.data = APIHandler.getResponse("https://raw.githubusercontent.com/bowser0000/SkyblockMod-REPO/main/data.json");
            System.out.println("Loaded data from GitHub?: " + (DankersSkyblockMod.data != null && DankersSkyblockMod.data.has("trivia")));
            ColouredNames.users = DankersSkyblockMod.data.get("colourednames").getAsJsonObject().entrySet().stream()
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toCollection(ArrayList::new));

            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Refreshed Danker's Skyblock Mod repo."));
        }).start();
    }

}
