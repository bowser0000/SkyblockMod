package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

import java.util.Collections;
import java.util.List;

public class StopLobbyCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "stoplobby";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("stoplb");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    public static String usage(ICommandSender arg0) {
        return new StopLobbyCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = (EntityPlayer) sender;
        if (LobbySkillsCommand.mainThread != null && LobbySkillsCommand.mainThread.isAlive()) {
            LobbySkillsCommand.mainThread.interrupt();
            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Stopped running /lobbyskills. See logs for progress."));
        }
        if (LobbyBankCommand.mainThread != null && LobbyBankCommand.mainThread.isAlive()) {
            LobbyBankCommand.mainThread.interrupt();
            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Stopped running /lobbybank. See logs for progress."));
        }
    }

}
