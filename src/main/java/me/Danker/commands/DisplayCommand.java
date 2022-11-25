package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class DisplayCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "display";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <zombie/spider/wolf/enderman/blaze/fishing/catacombs/mythological/ghost/auto/off> [winter/festival/spooky/ch/lava/trophy/session/f(1-7)/mm]";
	}

	public static String usage(ICommandSender arg0) {
		return new DisplayCommand().getCommandUsage(arg0);
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "wolf", "spider", "zombie", "enderman", "blaze", "fishing", "catacombs", "mythological", "ghost", "auto", "off");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("fishing")) {
			return getListOfStringsMatchingLastWord(args, "winter", "festival", "spooky", "ch", "lava", "trophy", "session");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("catacombs")) {
			return getListOfStringsMatchingLastWord(args, "f1", "floor1", "f2", "floor2", "f3", "floor3", "f4", "floor4", "f5", "floor5", "f6", "floor6", "f7", "floor7", "mm", "master");
		} else if (args.length > 1) {
			return getListOfStringsMatchingLastWord(args, "session");
		}
		return null;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: " + getCommandUsage(arg0)));
			return;
		}

		String display = "Off";
		switch (arg1[0].toLowerCase()) {
		case "wolf":
			display = "Wolf Slayer";
			break;
		case "spider":
			display = "Spider Slayer";
			break;
		case "zombie":
			display = "Zombie Slayer";
			break;
		case "enderman":
			display = "Enderman Slayer";
			break;
		case "blaze":
			display = "Blaze Slayer";
			break;
		case "fishing":
			if (arg1.length > 1) {
				switch (arg1[1].toLowerCase()) {
					case "winter":
						display = "Winter Fishing";
						break;
					case "festival":
						display = "Fishing Festival";
						break;
					case "spooky":
						display = "Spooky Fishing";
						break;
					case "ch":
						display = "Crystal Hollows Fishing";
						break;
					case "lava":
						display = "Lava Fishing";
						break;
					case "trophy":
						display = "Trophy Fishing";
						break;
					default:
						display = "Fishing";
				}
			} else {
				display = "Fishing";
			}
			break;
		case "mythological":
			display = "Mythological";
			break;
		case "catacombs":
			if (arg1.length == 1) {
				player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: /display catacombs <f1/f2/f3/f4f5/f6/f7>"));
				return;
			}
			
			switch (arg1[1].toLowerCase()) {
				case "f1":
				case "floor1":
					display = "Floor 1";
					break;
				case "f2":
				case "floor2":
					display = "Floor 2";
					break;
				case "f3":
				case "floor3":
					display = "Floor 3";
					break;
				case "f4":
				case "floor4":
					display = "Floor 4";
					break;
				case "f5":
				case "floor5":
					display = "Floor 5";
					break;
				case "f6":
				case "floor6":
					display = "Floor 6";
					break;
				case "f7":
				case "floor7":
					display = "Floor 7";
					break;
				case "mm":
				case "master":
					display = "Master Mode";
				default:
					player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: /display catacombs <f1/f2/f3/f4/f5/f6/f7/mm>"));
					return;
			}
			break;
		case "ghost":
			display = "Ghost";
			break;
		case "auto":
			ModConfig.autoDisplay = true;
			DankersSkyblockMod.config.save();
			player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Display set to " + ModConfig.getColour(ModConfig.secondaryColour) + "auto" + ModConfig.getColour(ModConfig.mainColour) + "."));
			return;
		case "off":
			display = "Off";
			break;
		default:
			player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: " + getCommandUsage(arg0)));
			return;
		}

		ModConfig.display = ModConfig.toDisplay(display);
		ModConfig.sessionDisplay = arg1[arg1.length - 1].equalsIgnoreCase("session");
		ModConfig.autoDisplay = false;
		DankersSkyblockMod.config.save();
		player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Display set to " + ModConfig.getColour(ModConfig.secondaryColour) + display + ModConfig.getColour(ModConfig.mainColour) + "."));
	}

}
