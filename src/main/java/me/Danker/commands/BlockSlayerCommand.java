package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

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
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		switch (arg1[0].toLowerCase()) {
			case "zombie":
				onlySlayerName = "Revenant Horror";
				break;
			case "spider":
				onlySlayerName = "Tarantula Broodfather";
				break;
			case "wolf":
				onlySlayerName = "Sven Packmaster";
				break;
			case "off":
				onlySlayerName = "";
				onlySlayerNumber = "";
				ConfigHandler.writeStringConfig("toggles", "BlockSlayer", "");
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Slayer blocking turned off."));
				return;
			default:
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
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
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
				return;
		}
		
		ConfigHandler.writeStringConfig("toggles", "BlockSlayer", onlySlayerName + " " + onlySlayerNumber);
		player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Slayer blocking set to " + DankersSkyblockMod.SECONDARY_COLOUR + onlySlayerName + " " + onlySlayerNumber));
	}

}
