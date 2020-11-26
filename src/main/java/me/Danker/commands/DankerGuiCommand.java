package me.Danker.commands;

import me.Danker.TheMod;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class DankerGuiCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "dsm";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return null;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		TheMod.guiToOpen = "dankergui1";
	}

}
