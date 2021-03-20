package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.SkillTracker;
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

	public static String usage(ICommandSender arg0) {
		return new SkillTrackerCommand().getCommandUsage(arg0);
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
						if (SkillTracker.skillStopwatch.isStarted() && SkillTracker.skillStopwatch.isSuspended()) {
							SkillTracker.skillStopwatch.resume();
						} else if (!SkillTracker.skillStopwatch.isStarted()) {
							SkillTracker.skillStopwatch.start();
						}
						player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill tracker started."));
						break;
					case "pause":
					case "stop":
						if (SkillTracker.skillStopwatch.isStarted() && !SkillTracker.skillStopwatch.isSuspended()) {
							SkillTracker.skillStopwatch.suspend();
						} else {
							player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill tracker paused."));
						}
						break;
					case "reset":
						SkillTracker.skillStopwatch = new StopWatch();
						SkillTracker.farmingXPGained = 0;
						SkillTracker.miningXPGained = 0;
						SkillTracker.combatXPGained = 0;
						SkillTracker.foragingXPGained = 0;
						SkillTracker.fishingXPGained = 0;
						SkillTracker.enchantingXPGained = 0;
						SkillTracker.alchemyXPGained = 0;
						player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill tracker reset."));
						break;
					case "hide":
						SkillTracker.showSkillTracker = false;
						ConfigHandler.writeBooleanConfig("misc", "showSkillTracker", false);
						player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Skill tracker hidden."));
						break;
					case "show":
						SkillTracker.showSkillTracker = true;
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
