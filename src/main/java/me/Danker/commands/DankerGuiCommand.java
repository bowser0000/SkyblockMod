package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Collections;
import java.util.List;

public class DankerGuiCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "dsm";
	}

	@Override
	public List<String> getCommandAliases() {
		return Collections.singletonList("dankersskyblockmod");
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName();
	}

	public static String usage(ICommandSender arg0) {
		return new DankerGuiCommand().getCommandUsage(arg0);
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		if (arg1.length > 0 && arg1[0].equalsIgnoreCase("debug")) {
			StringBuilder debug = new StringBuilder();
			debug.append("```md\n");
			debug.append("# Locations\n");
			debug.append("[coords][").append(MoveCommand.coordsXY[0]).append(", ").append(MoveCommand.coordsXY[1]).append("]\n");
			debug.append("[display][").append(MoveCommand.displayXY[0]).append(", ").append(MoveCommand.displayXY[1]).append("]\n");
			debug.append("[dungeontimer][").append(MoveCommand.dungeonTimerXY[0]).append(", ").append(MoveCommand.dungeonTimerXY[1]).append("]\n");
			debug.append("[skill50][").append(MoveCommand.skill50XY[0]).append(", ").append(MoveCommand.skill50XY[1]).append("]\n");
			debug.append("[lividhp][").append(MoveCommand.lividHpXY[0]).append(", ").append(MoveCommand.lividHpXY[1]).append("]\n");
			debug.append("[caketimer][").append(MoveCommand.cakeTimerXY[0]).append(", ").append(MoveCommand.cakeTimerXY[1]).append("]\n");
			debug.append("[skilltracker][").append(MoveCommand.skillTrackerXY[0]).append(", ").append(MoveCommand.skillTrackerXY[1]).append("]\n");
			debug.append("[wateranswer][").append(MoveCommand.waterAnswerXY[0]).append(", ").append(MoveCommand.waterAnswerXY[1]).append("]\n");
			debug.append("[bonzotimer][").append(MoveCommand.bonzoTimerXY[0]).append(", ").append(MoveCommand.bonzoTimerXY[1]).append("]\n");
			debug.append("[golemtimer][").append(MoveCommand.golemTimerXY[0]).append(", ").append(MoveCommand.golemTimerXY[1]).append("]\n");
			debug.append("[teammatesinradius][").append(MoveCommand.teammatesInRadiusXY[0]).append(", ").append(MoveCommand.teammatesInRadiusXY[1]).append("]\n");
			debug.append("[gianthp][").append(MoveCommand.giantHPXY[0]).append(", ").append(MoveCommand.giantHPXY[1]).append("]\n");
			debug.append("[abilitycooldowns][").append(MoveCommand.abilityCooldownsXY[0]).append(", ").append(MoveCommand.abilityCooldownsXY[1]).append("]\n");
			debug.append("[dungeonscore][").append(MoveCommand.dungeonScoreXY[0]).append(", ").append(MoveCommand.dungeonScoreXY[1]).append("]\n");
			debug.append("[firepillar][").append(MoveCommand.firePillarXY[0]).append(", ").append(MoveCommand.firePillarXY[1]).append("]\n");
			debug.append("# Other Settings\n");
			debug.append("[Current Display][").append(ModConfig.getDisplay()).append("]\n");
			debug.append("[Auto Display][").append(ModConfig.autoDisplay).append("]\n");
			debug.append("[Skill Tracker Visible][").append(ModConfig.showSkillTracker).append("]\n");
			debug.append("[Farm Length][").append(ModConfig.farmMinCoords).append(" to ").append(ModConfig.farmMaxCoords).append("]\n");
			debug.append("# Problematic Mods\n");
			debug.append("[LabyMod][").append(DankersSkyblockMod.usingLabymod).append("]\n");
			debug.append("[OAM][").append(DankersSkyblockMod.usingOAM).append("]\n");
			debug.append("# Resource Packs\n");
			if (Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries().size() == 0) {
				debug.append("<None>\n");
			} else {
				for (ResourcePackRepository.Entry resource : Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries()) {
					debug.append("<").append(StringUtils.stripControlCodes(resource.getResourcePackName())).append(">\n");
				}
			}
			debug.append("```");
			StringSelection clipboard = new StringSelection(debug.toString());
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipboard, clipboard);
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Debug stats copied to clipboard."));
			return;
		}

		DankersSkyblockMod.config.openGui();
	}

}
