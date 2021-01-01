package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class DankerGuiCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "dsm";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return null;
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
			debug.append("# Toggles\n");
			debug.append("[gparty][").append(ToggleCommand.gpartyToggled).append("]\n");
			debug.append("[coords][").append(ToggleCommand.coordsToggled).append("]\n");
			debug.append("[golden][").append(ToggleCommand.goldenToggled).append("]\n");
			debug.append("[slayercount][").append(ToggleCommand.slayerCountTotal).append("]\n");
			debug.append("[rngesusalerts][").append(ToggleCommand.rngesusAlerts).append("]\n");
			debug.append("[splitfishing][").append(ToggleCommand.splitFishing).append("]\n");
			debug.append("[chatmaddox][").append(ToggleCommand.chatMaddoxToggled).append("]\n");
			debug.append("[spiritbearalerts][").append(ToggleCommand.spiritBearAlerts).append("]\n");
			debug.append("[aotd][").append(ToggleCommand.aotdToggled).append("]\n");
			debug.append("[lividdagger][").append(ToggleCommand.lividDaggerToggled).append("]\n");
			debug.append("[flowerweapons][").append(ToggleCommand.flowerWeaponsToggled).append("]\n");
			debug.append("[sceptremessages][").append(ToggleCommand.sceptreMessages).append("]\n");
			debug.append("[petcolors][").append(ToggleCommand.petColoursToggled).append("]\n");
			debug.append("[dungeontimer][").append(ToggleCommand.dungeonTimerToggled).append("]\n");
			debug.append("[golemalerts][").append(ToggleCommand.golemAlertToggled).append("]\n");
			debug.append("[expertiselore][").append(ToggleCommand.expertiseLoreToggled).append("]\n");
			debug.append("[skill50display][").append(ToggleCommand.skill50DisplayToggled).append("]\n");
			debug.append("[outlinetext][").append(ToggleCommand.outlineTextToggled).append("]\n");
			debug.append("[midasstaffmessages][").append(ToggleCommand.midasStaffMessages).append("]\n");
			debug.append("[implosionmessages][").append(ToggleCommand.implosionMessages).append("]\n");
			debug.append("[healmessages][").append(ToggleCommand.healMessages).append("]\n");
			debug.append("[caketimer][").append(ToggleCommand.cakeTimerToggled).append("]\n");
			debug.append("[lowhealthnotify][").append(ToggleCommand.lowHealthNotifyToggled).append("]\n");
			debug.append("[lividsolver][").append(ToggleCommand.lividSolverToggled).append("]\n");
			debug.append("[stopsalvagestarred][").append(ToggleCommand.stopSalvageStarredToggled).append("]\n");
			debug.append("[notifyslayerslain][").append(ToggleCommand.notifySlayerSlainToggled).append("]\n");
			debug.append("[necronnotifications][").append(ToggleCommand.necronNotificationsToggled).append("]\n");
			debug.append("[bonzotimer][").append(ToggleCommand.bonzoTimerToggled).append("]\n");
			debug.append("[blockbreakingfarms][").append(ToggleCommand.blockBreakingFarmsToggled).append("]\n");
			debug.append("[autoskilltracker][").append(ToggleCommand.autoSkillTrackerToggled).append("]\n");
			debug.append("[threemanpuzzle][").append(ToggleCommand.threeManToggled).append("]\n");
			debug.append("[oruopuzzle][").append(ToggleCommand.oruoToggled).append("]\n");
			debug.append("[blazepuzzle][").append(ToggleCommand.blazeToggled).append("]\n");
			debug.append("[creeperpuzzle][").append(ToggleCommand.creeperToggled).append("]\n");
			debug.append("[waterpuzzle][").append(ToggleCommand.waterToggled).append("]\n");
			debug.append("[tictactoepuzzle][").append(ToggleCommand.ticTacToeToggled).append("]\n");
			debug.append("[watchermessage][").append(ToggleCommand.watcherReadyToggled).append("]\n");
			debug.append("[startswithterminal][").append(ToggleCommand.startsWithToggled).append("]\n");
			debug.append("[selectallterminal][").append(ToggleCommand.selectAllToggled).append("]\n");
			debug.append("[clickinorderterminal][").append(ToggleCommand.clickInOrderToggled).append("]\n");
			debug.append("[blockwrongterminalclicks][").append(ToggleCommand.blockWrongTerminalClicksToggled).append("]\n");
			debug.append("[itemframeonsealanterns][").append(ToggleCommand.itemFrameOnSeaLanternsToggled).append("]\n");
			debug.append("[ultrasequencer][").append(ToggleCommand.ultrasequencerToggled).append("]\n");
			debug.append("[chronomatron][").append(ToggleCommand.chronomatronToggled).append("]\n");
			debug.append("[superpairs][").append(ToggleCommand.superpairsToggled).append("]\n");
			debug.append("[hidetooltipsinaddons][").append(ToggleCommand.hideTooltipsInExperimentAddonsToggled).append("]\n");
			debug.append("[pickblock][").append(ToggleCommand.swapToPickBlockToggled).append("]\n");
			debug.append("# Locations\n");
			debug.append("[coords][").append(MoveCommand.coordsXY[0]).append(", ").append(MoveCommand.coordsXY[1]).append("]\n");
			debug.append("[display][").append(MoveCommand.displayXY[0]).append(", ").append(MoveCommand.displayXY[1]).append("]\n");
			debug.append("[dungeontimer][").append(MoveCommand.dungeonTimerXY[0]).append(", ").append(MoveCommand.dungeonTimerXY[1]).append("]\n");
			debug.append("[skill50][").append(MoveCommand.skill50XY[0]).append(", ").append(MoveCommand.skill50XY[1]).append("]\n");
			debug.append("[lividhp][").append(MoveCommand.lividHpXY[0]).append(", ").append(MoveCommand.lividHpXY[1]).append("]\n");
			debug.append("[caketimer][").append(MoveCommand.cakeTimerXY[0]).append(", ").append(MoveCommand.cakeTimerXY[1]).append("]\n");
			debug.append("[skilltracker][").append(MoveCommand.skillTrackerXY[0]).append(", ").append(MoveCommand.skillTrackerXY[1]).append("]\n");
			debug.append("[wateranswer][").append(MoveCommand.waterAnswerXY[0]).append(", ").append(MoveCommand.waterAnswerXY[1]).append("]\n");
			debug.append("# Other Settings\n");
			debug.append("[Current Display][").append(DisplayCommand.display).append("]\n");
			debug.append("[Auto Display][").append(DisplayCommand.auto).append("]\n");
			debug.append("[Skill Tracker Visible][").append(DankersSkyblockMod.showSkillTracker).append("]\n");
			debug.append("# Resource Packs\n");
			if (Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries().size() == 0) {
				debug.append("<None>\n");
			} else {
				for (ResourcePackRepository.Entry resource : Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries()) {
					debug.append("< ").append(StringUtils.stripControlCodes(resource.getResourcePackName())).append(" >\n");
				}
			}
			debug.append("```");
			StringSelection clipboard = new StringSelection(debug.toString());
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipboard, clipboard);
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Debug stats copied to clipboard."));
			return;
		}

		DankersSkyblockMod.guiToOpen = "dankergui1";
	}

}
