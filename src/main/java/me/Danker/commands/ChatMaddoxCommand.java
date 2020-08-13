package me.Danker.commands;

import me.Danker.TheMod;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class ChatMaddoxCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "dmodopenmaddoxmenu";
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
		if (!ToggleCommand.chatMaddoxToggled) return;
		Minecraft.getMinecraft().thePlayer.sendChatMessage(TheMod.lastMaddoxCommand);
	}

}
