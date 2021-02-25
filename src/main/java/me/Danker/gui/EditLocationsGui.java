package me.Danker.gui;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.gui.buttons.LocationButton;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class EditLocationsGui extends GuiScreen {

	private String moving = null;
	private int lastMouseX = -1;
	private int lastMouseY = -1;
	
	private LocationButton display;
	private LocationButton dungeonTimer;
	private LocationButton coords;
	private LocationButton skill50;
	private LocationButton lividHP;
	private LocationButton cakeTimer;
	private LocationButton skillTracker;
	private LocationButton waterAnswer;
	private LocationButton bonzoTimer;
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		String displayText = EnumChatFormatting.GOLD + "Svens Killed:\n" +
							 EnumChatFormatting.GREEN + "Wolf Teeth:\n" +
							 EnumChatFormatting.BLUE + "Hamster Wheels:\n" +
							 EnumChatFormatting.AQUA + "Spirit Runes:\n" + 
							 EnumChatFormatting.WHITE + "Critical VI Books:\n" +
							 EnumChatFormatting.DARK_RED + "Red Claw Eggs:\n" +
							 EnumChatFormatting.GOLD + "Couture Runes:\n" +
							 EnumChatFormatting.AQUA + "Grizzly Baits:\n" +
							 EnumChatFormatting.DARK_PURPLE + "Overfluxes:\n" +
							 EnumChatFormatting.AQUA + "Time Since RNG:\n" +
							 EnumChatFormatting.AQUA + "Bosses Since RNG:";
		String displayNums = EnumChatFormatting.GOLD + "1,024" + "\n" +
							 EnumChatFormatting.GREEN + "59,719" + "\n" +
							 EnumChatFormatting.BLUE + "36" + "\n" +
							 EnumChatFormatting.AQUA + "64" + "\n" + 
							 EnumChatFormatting.WHITE + "17" + "\n" +
							 EnumChatFormatting.DARK_RED + "3" + "\n" +
							 EnumChatFormatting.GOLD + "4" + "\n" +
							 EnumChatFormatting.AQUA + "0" + "\n" +
							 EnumChatFormatting.DARK_PURPLE + "5" + "\n" +
							 EnumChatFormatting.AQUA + Utils.getTimeBetween(0, 2678400) + "\n" +
							 EnumChatFormatting.AQUA + "5,000";
		
		String dungeonTimerText = EnumChatFormatting.GRAY + "Wither Doors:\n" +
								  EnumChatFormatting.DARK_RED + "Blood Open:\n" +
								  EnumChatFormatting.RED + "Watcher Clear:\n" +
								  EnumChatFormatting.BLUE + "Boss Clear:\n" +
								  EnumChatFormatting.YELLOW + "Deaths:\n" +
								  EnumChatFormatting.YELLOW + "Puzzle Fails:";
		String dungeonTimerNums = EnumChatFormatting.GRAY + "" + 5 + "\n" +
							      EnumChatFormatting.DARK_RED + Utils.getTimeBetween(0, 33) + "\n" +
							      EnumChatFormatting.RED + Utils.getTimeBetween(0, 129) + "\n" +
							      EnumChatFormatting.BLUE + Utils.getTimeBetween(0, 169) + "\n" +
							      EnumChatFormatting.YELLOW + 2 + "\n" +
							      EnumChatFormatting.YELLOW + 1;
		
		String skillTrackerText = DankersSkyblockMod.SKILL_TRACKER_COLOUR + "Farming XP Earned: 462,425.3\n" +
								  DankersSkyblockMod.SKILL_TRACKER_COLOUR + "Time Elapsed: " + Utils.getTimeBetween(0, 3602) + "\n" +
								  DankersSkyblockMod.SKILL_TRACKER_COLOUR + "XP Per Hour: 462,168";

		String waterAnswerText = DankersSkyblockMod.MAIN_COLOUR + "The following levers must be down:\n" +
								 EnumChatFormatting.DARK_PURPLE + "Purple: " + EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay\n" +
								 EnumChatFormatting.GOLD + "Orange: " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.DARK_GRAY + "Coal\n" +
								 EnumChatFormatting.BLUE + "Blue: " + EnumChatFormatting.WHITE + "Quartz, " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.DARK_GRAY + "Coal, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay\n" +
								 EnumChatFormatting.GREEN + "Green: " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.GREEN + "Emerald\n" +
								 EnumChatFormatting.RED + "Red: " + EnumChatFormatting.YELLOW + "Gold, " + EnumChatFormatting.AQUA + "Diamond, " + EnumChatFormatting.GREEN + "Emerald, " + EnumChatFormatting.RED + "Clay";

		display = new LocationButton(0, MoveCommand.displayXY[0], MoveCommand.displayXY[1], 145 * ScaleCommand.displayScale, 102 * ScaleCommand.displayScale, ScaleCommand.displayScale, displayText, displayNums, 110);
		dungeonTimer = new LocationButton(0, MoveCommand.dungeonTimerXY[0], MoveCommand.dungeonTimerXY[1], 113 * ScaleCommand.dungeonTimerScale, 57 * ScaleCommand.dungeonTimerScale, ScaleCommand.dungeonTimerScale, dungeonTimerText, dungeonTimerNums, 80);
		coords = new LocationButton(0, MoveCommand.coordsXY[0], MoveCommand.coordsXY[1], 141 * ScaleCommand.coordsScale, 12 * ScaleCommand.coordsScale, ScaleCommand.coordsScale, DankersSkyblockMod.COORDS_COLOUR + "74 / 14 / -26 (141.1 / 6.7)", null, null);
		skill50 = new LocationButton(0, MoveCommand.skill50XY[0], MoveCommand.skill50XY[1], 233 * ScaleCommand.skill50Scale, 12 * ScaleCommand.skill50Scale, ScaleCommand.skill50Scale, DankersSkyblockMod.SKILL_50_COLOUR + "+3.5 Farming (28,882,117.7/55,172,425) 52.34%", null, null);
		lividHP = new LocationButton(0, MoveCommand.lividHpXY[0], MoveCommand.lividHpXY[1], 85 * ScaleCommand.lividHpScale, 12 * ScaleCommand.lividHpScale, ScaleCommand.lividHpScale, EnumChatFormatting.WHITE + "﴾ Livid " + EnumChatFormatting.YELLOW + "6.9M" + EnumChatFormatting.RED + "❤ " + EnumChatFormatting.WHITE + "﴿", null, null);
		cakeTimer = new LocationButton(0, MoveCommand.cakeTimerXY[0], MoveCommand.cakeTimerXY[1] + 5, 85 * ScaleCommand.cakeTimerScale, 18 * ScaleCommand.cakeTimerScale, ScaleCommand.cakeTimerScale, DankersSkyblockMod.CAKE_COLOUR + "     11h16m", null, null);
		skillTracker = new LocationButton(0, MoveCommand.skillTrackerXY[0], MoveCommand.skillTrackerXY[1], 150 * ScaleCommand.skillTrackerScale, 28 * ScaleCommand.skillTrackerScale, ScaleCommand.skillTrackerScale, skillTrackerText, null, null);
		waterAnswer = new LocationButton(0, MoveCommand.waterAnswerXY[0], MoveCommand.waterAnswerXY[1], 190 * ScaleCommand.waterAnswerScale, 54 * ScaleCommand.waterAnswerScale, ScaleCommand.waterAnswerScale, waterAnswerText, null, null);
		bonzoTimer = new LocationButton(0, MoveCommand.bonzoTimerXY[0], MoveCommand.bonzoTimerXY[1] + 5, 85 * ScaleCommand.bonzoTimerScale, 18 * ScaleCommand.bonzoTimerScale, ScaleCommand.bonzoTimerScale, DankersSkyblockMod.BONZO_COLOR + "     3m30s", null, null);


		this.buttonList.add(coords);
		this.buttonList.add(dungeonTimer);
		this.buttonList.add(lividHP);
		this.buttonList.add(cakeTimer);
		this.buttonList.add(skillTracker);
		this.buttonList.add(waterAnswer);
		this.buttonList.add(bonzoTimer);
		this.buttonList.add(display);
		this.buttonList.add(skill50);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		mouseMoved(mouseX, mouseY);
		
		double cakeTimerScale = ScaleCommand.cakeTimerScale;
		double cakeTimerScaleReset = Math.pow(cakeTimerScale, -1);
		GL11.glScaled(cakeTimerScale, cakeTimerScale, cakeTimerScale);
		mc.getTextureManager().bindTexture(DankersSkyblockMod.CAKE_ICON);
		Gui.drawModalRectWithCustomSizedTexture(MoveCommand.cakeTimerXY[0], MoveCommand.cakeTimerXY[1], 0, 0, 16, 16, 16, 16);
		GL11.glScaled(cakeTimerScaleReset, cakeTimerScaleReset, cakeTimerScaleReset);

		double bonzoTimerScale = ScaleCommand.bonzoTimerScale;
		double bonzoTimerScaleReset = Math.pow(bonzoTimerScale, -1);
		GL11.glScaled(bonzoTimerScale, bonzoTimerScale, bonzoTimerScale);
		mc.getTextureManager().bindTexture(DankersSkyblockMod.BONZO_ICON);
		Gui.drawModalRectWithCustomSizedTexture(MoveCommand.bonzoTimerXY[0], MoveCommand.bonzoTimerXY[1], 0, 0, 16, 16, 16, 16);
		GL11.glScaled(bonzoTimerScaleReset, bonzoTimerScaleReset, bonzoTimerScaleReset);


		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private void mouseMoved(int mouseX, int mouseY) {
		int xMoved = mouseX - lastMouseX;
		int yMoved = mouseY - lastMouseY;
		
		if (moving != null) {
			switch (moving) {
				case "display":
					MoveCommand.displayXY[0] += xMoved;
					MoveCommand.displayXY[1] += yMoved;
					display.xPosition = MoveCommand.displayXY[0];
					display.yPosition = MoveCommand.displayXY[1];
					break;
				case "dungeonTimer":
					MoveCommand.dungeonTimerXY[0] += xMoved;
					MoveCommand.dungeonTimerXY[1] += yMoved;
					dungeonTimer.xPosition = MoveCommand.dungeonTimerXY[0];
					dungeonTimer.yPosition = MoveCommand.dungeonTimerXY[1];
					break;
				case "coords":
					MoveCommand.coordsXY[0] += xMoved;
					MoveCommand.coordsXY[1] += yMoved;
					coords.xPosition = MoveCommand.coordsXY[0];
					coords.yPosition = MoveCommand.coordsXY[1];
					break;
				case "skill50":
					MoveCommand.skill50XY[0] += xMoved;
					MoveCommand.skill50XY[1] += yMoved;
					skill50.xPosition = MoveCommand.skill50XY[0];
					skill50.yPosition = MoveCommand.skill50XY[1];
					break;
				case "lividHP":
					MoveCommand.lividHpXY[0] += xMoved;
					MoveCommand.lividHpXY[1] += yMoved;
					lividHP.xPosition = MoveCommand.lividHpXY[0];
					lividHP.yPosition = MoveCommand.lividHpXY[1];
					break;
				case "cakeTimer":
					MoveCommand.cakeTimerXY[0] += xMoved;
					MoveCommand.cakeTimerXY[1] += yMoved;
					cakeTimer.xPosition = MoveCommand.cakeTimerXY[0];
					cakeTimer.yPosition = MoveCommand.cakeTimerXY[1];
					break;
				case "skillTracker":
					MoveCommand.skillTrackerXY[0] += xMoved;
					MoveCommand.skillTrackerXY[1] += yMoved;
					skillTracker.xPosition = MoveCommand.skillTrackerXY[0];
					skillTracker.yPosition = MoveCommand.skillTrackerXY[1];
					break;
				case "waterAnswer":
					MoveCommand.waterAnswerXY[0] += xMoved;
					MoveCommand.waterAnswerXY[1] += yMoved;
					waterAnswer.xPosition = MoveCommand.waterAnswerXY[0];
					waterAnswer.yPosition = MoveCommand.waterAnswerXY[1];
					break;
				case "bonzoTimer":
					MoveCommand.bonzoTimerXY[0] += xMoved;
					MoveCommand.bonzoTimerXY[1] += yMoved;
					bonzoTimer.xPosition = MoveCommand.bonzoTimerXY[0];
					bonzoTimer.yPosition = MoveCommand.bonzoTimerXY[1];
					break;
			}
			this.buttonList.clear();
			initGui();
		}
		
		lastMouseX = mouseX;
		lastMouseY = mouseY;
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if (button instanceof LocationButton) {
			if (button == display) {
				moving = "display";
			} else if (button == dungeonTimer) {
				moving = "dungeonTimer";
			} else if (button == coords) {
				moving = "coords";
			} else if (button == skill50) {
				moving = "skill50";
			} else if (button == lividHP) {
				moving = "lividHP";
			} else if (button == cakeTimer) {
				moving = "cakeTimer";
			} else if (button == skillTracker) {
				moving = "skillTracker";
			} else if (button == waterAnswer) {
				moving = "waterAnswer";
			} else if (button == bonzoTimer) {
				moving = "bonzoTimer";
			}
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		moving = null;
		ConfigHandler.writeIntConfig("locations", "coordsX", MoveCommand.coordsXY[0]);
		ConfigHandler.writeIntConfig("locations", "coordsY", MoveCommand.coordsXY[1]);
		ConfigHandler.writeIntConfig("locations", "displayX", MoveCommand.displayXY[0]);
		ConfigHandler.writeIntConfig("locations", "displayY", MoveCommand.displayXY[1]);
		ConfigHandler.writeIntConfig("locations", "dungeonTimerX", MoveCommand.dungeonTimerXY[0]);
		ConfigHandler.writeIntConfig("locations", "dungeonTimerY", MoveCommand.dungeonTimerXY[1]);
		ConfigHandler.writeIntConfig("locations", "skill50X", MoveCommand.skill50XY[0]);
		ConfigHandler.writeIntConfig("locations", "skill50Y", MoveCommand.skill50XY[1]);
		ConfigHandler.writeIntConfig("locations", "lividHpX", MoveCommand.lividHpXY[0]);
		ConfigHandler.writeIntConfig("locations", "lividHpY", MoveCommand.lividHpXY[1]);
		ConfigHandler.writeIntConfig("locations", "cakeTimerX", MoveCommand.cakeTimerXY[0]);
		ConfigHandler.writeIntConfig("locations", "cakeTimerY", MoveCommand.cakeTimerXY[1]);
		ConfigHandler.writeIntConfig("locations", "skillTrackerX", MoveCommand.skillTrackerXY[0]);
		ConfigHandler.writeIntConfig("locations", "skillTrackerY", MoveCommand.skillTrackerXY[1]);
		ConfigHandler.writeIntConfig("locations", "waterAnswerX", MoveCommand.waterAnswerXY[0]);
		ConfigHandler.writeIntConfig("locations", "waterAnswerY", MoveCommand.waterAnswerXY[1]);
		ConfigHandler.writeIntConfig("locations", "bonzoTimerX", MoveCommand.bonzoTimerXY[0]);
		ConfigHandler.writeIntConfig("locations", "bonzoTimerY", MoveCommand.bonzoTimerXY[1]);
	}
	
}
