package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

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
            Utils.refreshRepo();
            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Refreshed Danker's Skyblock Mod repo."));
        }).start();
    }

}
