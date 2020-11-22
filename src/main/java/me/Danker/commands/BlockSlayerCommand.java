package me.Danker.commands;

import java.util.List;

import me.Danker.TheMod;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

public class BlockSlayerCommand extends CommandBase {
	
	public static String onlySlayerName = "";
	public static String onlySlayerNumber = "";

	@Override
	public String getCommandName() {
		return "onlyslayer";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <zombie/spider/wolf> <1/2/3/4>";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "zombie", "spider", "wolf");
		} else if (args.length == 2) {
			return getListOfStringsMatchingLastWord(args, "1", "2", "3", "4");
		}
		return null;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		
		if (arg1.length == 0 || (arg1.length == 1 && !arg1[0].equalsIgnoreCase("off"))) {
			player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		if (arg1[0].equalsIgnoreCase("zombie")) {
			onlySlayerName = "Revenant Horror";
		} else if (arg1[0].equalsIgnoreCase("spider")) {
			onlySlayerName = "Tarantula Broodfather";
		} else if (arg1[0].equalsIgnoreCase("wolf")) {
			onlySlayerName = "Sven Packmaster";
		} else if (arg1[0].equalsIgnoreCase("off")) {
			onlySlayerName = "";
			onlySlayerNumber = "";
			ConfigHandler.writeStringConfig("toggles", "BlockSlayer", "");
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Slayer blocking turned off."));
			return;
		} else {
			player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		int slayerNumber = Integer.parseInt(arg1[1]);
		// Just manually set to roman numeral, I don't wanna put a whole converter in here
		switch (slayerNumber) {
			case 1:
				onlySlayerNumber = "I";
				break;
			case 2:
				onlySlayerNumber = "II";
				break;
			case 3:
				onlySlayerNumber = "III";
				break;
			case 4:
				onlySlayerNumber = "IV";
				break;
			default:
				onlySlayerName = "";
				onlySlayerNumber = "";
				player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
				return;
		}
		
		ConfigHandler.writeStringConfig("toggles", "BlockSlayer", onlySlayerName + " " + onlySlayerNumber);
		player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Slayer blocking set to " + TheMod.SECONDARY_COLOUR + onlySlayerName + " " + onlySlayerNumber));
	}

}
