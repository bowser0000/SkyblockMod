package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import org.apache.commons.lang3.time.StopWatch;

import java.util.List;

public class SkillTrackerCommand extends CommandBase {
	public static boolean running = false;

	@Override
	public String getCommandName() {
		return "skilltracker";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " <start/stop/reset>";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "start", "resume", "pause", "stop", "reset", "hide", "show");
		}
		return null;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		// MULTI THREAD DRIFTING
		new Thread(() -> {
			EntityPlayer player = (EntityPlayer) arg0;
			
			if (arg1.length < 1) {
				player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
				return;
			}
			
			try {
				Thread.sleep(50);

				switch (arg1[0].toLowerCase()) {
					case "start":
					case "resume":
						if (DankersSkyblockMod.skillStopwatch.isStarted() && DankersSkyblockMod.skillStopwatch.isSuspended()) {
							DankersSkyblockMod.skillStopwatch.resume();
						} else if (!DankersSkyblockMod.skillStopwatch.isStarted()) {
							DankersSkyblockMod.skillStopwatch.start();
						}
						player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill tracker started."));
						break;
					case "pause":
					case "stop":
						if (DankersSkyblockMod.skillStopwatch.isStarted() && !DankersSkyblockMod.skillStopwatch.isSuspended()) {
							DankersSkyblockMod.skillStopwatch.suspend();
						} else {
							player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill tracker paused."));
						}
						break;
					case "reset":
						DankersSkyblockMod.skillStopwatch = new StopWatch();
						DankersSkyblockMod.farmingXPGained = 0;
						DankersSkyblockMod.miningXPGained = 0;
						DankersSkyblockMod.combatXPGained = 0;
						DankersSkyblockMod.foragingXPGained = 0;
						DankersSkyblockMod.fishingXPGained = 0;
						DankersSkyblockMod.enchantingXPGained = 0;
						DankersSkyblockMod.alchemyXPGained = 0;
						player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill tracker reset."));
						break;
					case "hide":
						DankersSkyblockMod.showSkillTracker = false;
						ConfigHandler.writeBooleanConfig("misc", "showSkillTracker", false);
						player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill tracker hidden."));
						break;
					case "show":
						DankersSkyblockMod.showSkillTracker = true;
						ConfigHandler.writeBooleanConfig("misc", "showSkillTracker", true);
						player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill tracker shown."));
						break;
					default:
						player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
				}
			} catch (InterruptedException e) {
                e.printStackTrace();
			}
		}).start();
	}
}
