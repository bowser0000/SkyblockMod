package me.Danker;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ToggleCommand extends CommandBase implements ICommand {
	private static boolean gpartyToggled = true;
	private static boolean coordsToggled = true;
	
	public boolean getToggle(String type) {
		if (type.equals("gparty")) {
			return gpartyToggled;
		} else if (type.equals("coords")) {
			return coordsToggled;
		}
		return true;
	}

	@Override
	public String getCommandName() {
		return "toggle";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName() + " [gparty/coords]";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText("Usage: /toggle [gparty/coords]"));
			return;
		}
		
		if (arg1[0].equalsIgnoreCase("gparty")) {
			gpartyToggled = !gpartyToggled;
			player.addChatMessage(new ChatComponentText("Guild party notifications has been set to " + gpartyToggled + "."));
		} else if (arg1[0].equalsIgnoreCase("coords")) {
			coordsToggled = !coordsToggled;
			player.addChatMessage(new ChatComponentText("Coord/Angle display has been set to " + coordsToggled + "."));
		}
	}
}
