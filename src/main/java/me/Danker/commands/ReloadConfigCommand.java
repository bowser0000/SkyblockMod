package me.Danker.commands;

import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ReloadConfigCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "reloadconfig";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName();
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		final ConfigHandler cf = new ConfigHandler();
		cf.reloadConfig();
		player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Reloaded config."));
	}

}
