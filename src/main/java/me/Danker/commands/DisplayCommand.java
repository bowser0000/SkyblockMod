package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.loot.LootDisplay;
import me.Danker.handlers.ConfigHandler;
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
		return "/" + getCommandName() + " <zombie/spider/wolf/fishing/catacombs/mythological/ghost/auto/off> [winter/festival/spooky/session/f(1-7)]";
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
			return getListOfStringsMatchingLastWord(args, "wolf", "spider", "zombie", "fishing", "catacombs", "mythological", "ghost", "auto", "off");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("fishing")) {
			return getListOfStringsMatchingLastWord(args, "winter", "festival", "spooky", "session");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("catacombs")) {
			return getListOfStringsMatchingLastWord(args, "f1", "floor1", "f2", "floor2", "f3", "floor3", "f4", "floor4", "f5", "floor5", "f6", "floor6", "f7", "floor7");
		} else if (args.length > 1) {
			return getListOfStringsMatchingLastWord(args, "session");
		}
		return null;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		boolean showSession = false;
		
		if (arg1[arg1.length - 1].equalsIgnoreCase("session")) showSession = true;
		
		switch (arg1[0].toLowerCase()) {
		case "wolf":
			if (showSession) {
				LootDisplay.display = "wolf_session";
			} else {
				LootDisplay.display = "wolf";
			}
			break;
		case "spider":
			if (showSession) {
				LootDisplay.display = "spider_session";
			} else {
				LootDisplay.display = "spider";
			}
			break;
		case "zombie":
			if (showSession) {
				LootDisplay.display = "zombie_session";
			} else {
				LootDisplay.display = "zombie";
			}
			break;
		case "fishing":
			if (arg1.length > 1) {
				switch (arg1[1].toLowerCase()) {
					case "winter":
						if (showSession) {
							LootDisplay.display = "fishing_winter_session";
						} else {
							LootDisplay.display = "fishing_winter";
						}
						break;
					case "festival":
						if (showSession) {
							LootDisplay.display = "fishing_festival_session";
						} else {
							LootDisplay.display = "fishing_festival";
						}
						break;
					case "spooky":
						if (showSession) {
							LootDisplay.display = "fishing_spooky_session";
						} else {
							LootDisplay.display = "fishing_spooky";
						}
						break;
					default:
						if (showSession) {
							LootDisplay.display = "fishing_session";
						} else {
							LootDisplay.display = "fishing";
						}
				}
			} else {
				if (showSession) {
					LootDisplay.display = "fishing_session";
				} else {
					LootDisplay.display = "fishing";
				}
			}
			break;
		case "mythological":
			if (showSession) {
				LootDisplay.display = "mythological_session";
			} else {
				LootDisplay.display = "mythological";
			}
			break;
		case "catacombs":
			if (arg1.length == 1) {
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: /display catacombs <f1/f2/f3/f4f5/f6/f7>"));
				return;
			}
			
			switch (arg1[1].toLowerCase()) {
				case "f1":
				case "floor1":
					if (showSession) {
						LootDisplay.display = "catacombs_floor_one_session";
					} else {
						LootDisplay.display = "catacombs_floor_one";
					}
					break;
				case "f2":
				case "floor2":
					if (showSession) {
						LootDisplay.display = "catacombs_floor_two_session";
					} else {
						LootDisplay.display = "catacombs_floor_two";
					}
					break;
				case "f3":
				case "floor3":
					if (showSession) {
						LootDisplay.display = "catacombs_floor_three_session";
					} else {
						LootDisplay.display = "catacombs_floor_three";
					}
					break;
				case "f4":
				case "floor4":
					if (showSession) {
						LootDisplay.display = "catacombs_floor_four_session";
					} else {
						LootDisplay.display = "catacombs_floor_four";
					}
					break;
				case "f5":
				case "floor5":
					if (showSession) {
						LootDisplay.display = "catacombs_floor_five_session";
					} else {
						LootDisplay.display = "catacombs_floor_five";
					}
					break;
				case "f6":
				case "floor6":
					if (showSession) {
						LootDisplay.display = "catacombs_floor_six_session";
					} else {
						LootDisplay.display = "catacombs_floor_six";
					}
					break;
				case "f7":
				case "floor7":
					if (showSession) {
						LootDisplay.display = "catacombs_floor_seven_session";
					} else {
						LootDisplay.display = "catacombs_floor_seven";
					}
					break;
				default:
					player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: /display catacombs <f1/f2/f3/f4/f5/f6/f7>"));
					return;
			}
			break;
		case "ghost":
			if (showSession) {
				LootDisplay.display = "ghost_session";
			} else {
				LootDisplay.display = "ghost";
			}

		case "auto":
			LootDisplay.auto = true;
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Display set to " + DankersSkyblockMod.SECONDARY_COLOUR + "auto" + DankersSkyblockMod.MAIN_COLOUR + "."));
			ConfigHandler.writeBooleanConfig("misc", "autoDisplay", true);
			return;
		case "off":
			LootDisplay.display = "off";
			break;
		default:
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
			return;
		}

		LootDisplay.auto = false;
		player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Display set to " + DankersSkyblockMod.SECONDARY_COLOUR + LootDisplay.display + DankersSkyblockMod.MAIN_COLOUR + "."));
		ConfigHandler.writeBooleanConfig("misc", "autoDisplay", false);
		ConfigHandler.writeStringConfig("misc", "display", LootDisplay.display);
	}

}
