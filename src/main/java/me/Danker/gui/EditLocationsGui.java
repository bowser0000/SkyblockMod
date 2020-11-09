package me.Danker.gui;

import me.Danker.TheMod;
import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.gui.buttons.LocationButton;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class EditLocationsGui extends GuiScreen {

	private String moving = null;
	private int lastMouseX = -1;
	private int lastMouseY = -1;
	
	private LocationButton display;
	private LocationButton dungeonTimer;
	private LocationButton coords;
	private LocationButton skill50;
	private LocationButton lividHP;
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		// Ease of typing
		MoveCommand moc = new MoveCommand();
		ScaleCommand sc = new ScaleCommand();
		
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
		
		display = new LocationButton(0, moc.displayXY[0], moc.displayXY[1], 145 * sc.displayScale, 102 * sc.displayScale, sc.displayScale, displayText, displayNums, 110);
		dungeonTimer = new LocationButton(0, moc.dungeonTimerXY[0], moc.dungeonTimerXY[1], 113 * sc.dungeonTimerScale, 57 * sc.dungeonTimerScale, sc.dungeonTimerScale, dungeonTimerText, dungeonTimerNums, 80);
		coords = new LocationButton(0, moc.coordsXY[0], moc.coordsXY[1], 141 * sc.coordsScale, 12 * sc.coordsScale, sc.coordsScale, TheMod.COORDS_COLOUR + "74 / 14 / -26 (141.1 / 6.7)", null, null);
		skill50 = new LocationButton(0, moc.skill50XY[0], moc.skill50XY[1], 233 * sc.skill50Scale, 12 * sc.skill50Scale, sc.skill50Scale, TheMod.SKILL_50_COLOUR + "+3.5 Farming (28,882,117.7/55,172,425) 52.34%", null, null);
		lividHP = new LocationButton(0, moc.lividHpXY[0], moc.lividHpXY[1], 85 * sc.lividHpScale, 12 * sc.lividHpScale, sc.lividHpScale, EnumChatFormatting.WHITE + "﴾ Livid " + EnumChatFormatting.YELLOW + "6.9M" + EnumChatFormatting.RED + "❤ " + EnumChatFormatting.WHITE + "﴿", null, null);
		
		this.buttonList.add(coords);
		this.buttonList.add(dungeonTimer);
		this.buttonList.add(lividHP);
		this.buttonList.add(display);
		this.buttonList.add(skill50);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		mouseMoved(mouseX, mouseY);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private void mouseMoved(int mouseX, int mouseY) {
		int xMoved = mouseX - lastMouseX;
		int yMoved = mouseY - lastMouseY;
		
		if (moving != null) {
			if (moving.equals("display")) {
				MoveCommand.displayXY[0] += xMoved;
				MoveCommand.displayXY[1] += yMoved;
				display.xPosition = MoveCommand.displayXY[0];
				display.yPosition = MoveCommand.displayXY[1];
			} else if (moving.equals("dungeonTimer")) {
				MoveCommand.dungeonTimerXY[0] += xMoved;
				MoveCommand.dungeonTimerXY[1] += yMoved;
				dungeonTimer.xPosition = MoveCommand.dungeonTimerXY[0];
				dungeonTimer.yPosition = MoveCommand.dungeonTimerXY[1];
			} else if (moving.equals("coords")) {
				MoveCommand.coordsXY[0] += xMoved;
				MoveCommand.coordsXY[1] += yMoved;
				coords.xPosition = MoveCommand.coordsXY[0];
				coords.yPosition = MoveCommand.coordsXY[1];
			} else if (moving.equals("skill50")) {
				MoveCommand.skill50XY[0] += xMoved;
				MoveCommand.skill50XY[1] += yMoved;
				skill50.xPosition = MoveCommand.skill50XY[0];
				skill50.yPosition = MoveCommand.skill50XY[1];
			} else if (moving.equals("lividHP")) {
				MoveCommand.lividHpXY[0] += xMoved;
				MoveCommand.lividHpXY[1] += yMoved;
				lividHP.xPosition = MoveCommand.lividHpXY[0];
				lividHP.yPosition = MoveCommand.lividHpXY[1];
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
	}
	
}
