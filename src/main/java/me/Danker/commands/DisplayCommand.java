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

public class DisplayCommand extends CommandBase {
	public static String display;
	public static boolean auto;

	@Override
	public String getCommandName() {
		return "display";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <zombie/spider/wolf/fishing/catacombs/auto/off> [winter/festival/spooky/session/f(1-7)]";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "wolf", "spider", "zombie", "fishing", "catacombs", "auto", "off");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("fishing")) {
			return getListOfStringsMatchingLastWord(args, "winter", "festival", "spooky", "session");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("catacombs")) {
			return getListOfStringsMatchingLastWord(args, "f1", "floor1", "f2", "floor2", "f3", "floor3", "f4", "floor4", "f5", "floor5", "f6", "floor6", "f7", "floor7");
		} else if (args.length > 1 || (args.length == 3 && args[0].equalsIgnoreCase("fishing") && args[1].equalsIgnoreCase("winter"))) { 
			return getListOfStringsMatchingLastWord(args, "session");
		}
		return null;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		boolean showSession = false;
		
		if (arg1[arg1.length - 1].equalsIgnoreCase("session")) showSession = true;
		
		if (arg1[0].equalsIgnoreCase("wolf")) {
			if (showSession) {
				display = "wolf_session";
			} else {
				display = "wolf";
			}
		} else if (arg1[0].equalsIgnoreCase("spider")) {
			if (showSession) {
				display = "spider_session";
			} else {
				display = "spider";
			}
		} else if (arg1[0].equalsIgnoreCase("zombie")) {
			if (showSession) {
				display = "zombie_session";
			} else {
				display = "zombie";
			}
		} else if (arg1[0].equalsIgnoreCase("fishing")) {
			if (arg1.length > 1 && arg1[1].equalsIgnoreCase("winter")) {
				if (showSession) {
					display = "fishing_winter_session";
				} else {
					display = "fishing_winter";
				}
			} else if (arg1.length > 1 && arg1[1].equalsIgnoreCase("festival")) {
				if (showSession) {
					display = "fishing_festival_session";
				} else {
					display = "fishing_festival";
				}
			} else if (arg1.length > 1 && arg1[1].equalsIgnoreCase("spooky")) { 
				if (showSession) {
					display = "fishing_spooky_session";
				} else {
					display = "fishing_spooky";
				}
			} else {
				if (showSession) {
					display = "fishing_session";
				} else {
					display = "fishing";
				}
			} 
		} else if (arg1[0].equalsIgnoreCase("catacombs")) {
			if (arg1.length == 1) {
				player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Usage: /display catacombs <f1/f2/f3/f4>"));
				return;
			}
			if (arg1[1].equalsIgnoreCase("f1") || arg1[1].equalsIgnoreCase("floor1")) {
				if (showSession) {
					display = "catacombs_floor_one_session";
				} else {
					display = "catacombs_floor_one";
				}
			} else if (arg1[1].equalsIgnoreCase("f2") || arg1[1].equalsIgnoreCase("floor2")) {
				if (showSession) {
					display = "catacombs_floor_two_session";
				} else {
					display = "catacombs_floor_two";
				}
			} else if (arg1[1].equalsIgnoreCase("f3") || arg1[1].equalsIgnoreCase("floor3")) {
				if (showSession) {
					display = "catacombs_floor_three_session";
				} else {
					display = "catacombs_floor_three";
				}
			} else if (arg1[1].equalsIgnoreCase("f4") || arg1[1].equalsIgnoreCase("floor4")) {
				if (showSession) {
					display = "catacombs_floor_four_session";
				} else {
					display = "catacombs_floor_four";
				}
			} else if (arg1[1].equalsIgnoreCase("f5") || arg1[1].equalsIgnoreCase("floor5")) {
				if (showSession) {
					display = "catacombs_floor_five_session";
				} else {
					display = "catacombs_floor_five";
				}
			} else if (arg1[1].equalsIgnoreCase("f6") || arg1[1].equalsIgnoreCase("floor6")) {
				if (showSession) {
					display = "catacombs_floor_six_session";
				} else {
					display = "catacombs_floor_six";
				}
			} else if (arg1[1].equalsIgnoreCase("f7") || arg1[1].equalsIgnoreCase("floor7")) {
				if (showSession) {
					display = "catacombs_floor_seven_session";
				} else {
					display = "catacombs_floor_seven";
				}
			} else {
				player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Usage: /display catacombs <f1/f2/f3/f4/f5/f6>"));
				return;
			}
		} else if (arg1[0].equalsIgnoreCase("auto")) {
			auto = true;
			player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Display set to " + TheMod.SECONDARY_COLOUR + "auto" + TheMod.MAIN_COLOUR + "."));
			ConfigHandler.writeBooleanConfig("misc", "autoDisplay", true);
			return;
		} else if (arg1[0].equalsIgnoreCase("off")) {
			display = "off";
		} else {
			player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		auto = false;
		player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Display set to " + TheMod.SECONDARY_COLOUR + display + TheMod.MAIN_COLOUR + "."));
		ConfigHandler.writeBooleanConfig("misc", "autoDisplay", false);
		ConfigHandler.writeStringConfig("misc", "display", display);
	}

}
