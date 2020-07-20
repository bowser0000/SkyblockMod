package me.Danker.commands;

import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ToggleCommand extends CommandBase implements ICommand {
	public static boolean gpartyToggled;
	public static boolean coordsToggled;

	@Override
	public String getCommandName() {
		return "toggle";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return getCommandName() + " <gparty/coords/list>";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		final ConfigHandler cf = new ConfigHandler();
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /toggle <gparty/coords/list>"));
			return;
		}
		
		if (arg1[0].equalsIgnoreCase("gparty")) {
			gpartyToggled = !gpartyToggled;
			cf.writeBooleanConfig("toggles", "GParty", gpartyToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Guild party notifications has been set to " + EnumChatFormatting.DARK_GREEN + gpartyToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("coords")) {
			coordsToggled = !coordsToggled;
			cf.writeBooleanConfig("toggles", "Coords", coordsToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Coord/Angle display has been set to " + EnumChatFormatting.DARK_GREEN + coordsToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("list")) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Guild party notifications: " + EnumChatFormatting.DARK_GREEN + gpartyToggled + "\n" +
														EnumChatFormatting.GREEN + " Coord/Angle display: " + EnumChatFormatting.DARK_GREEN + coordsToggled));
		} else {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /toggle <gparty/coords/list>"));
		}
	}
}
