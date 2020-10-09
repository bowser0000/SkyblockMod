package me.Danker.gui;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;

public class EditLocationsGui extends GuiScreen {

	private int lastMouseX = -1;
	private int lastMouseY = -1;
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void initGui() {
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		
		Minecraft mc = Minecraft.getMinecraft();
		
		drawRect(MoveCommand.coordsXY[0] - 2, MoveCommand.coordsXY[1] - 2, (int) (MoveCommand.coordsXY[0] + (139 * ScaleCommand.coordsScale)), (int) (MoveCommand.coordsXY[1] + (12 * ScaleCommand.coordsScale)), 0x40D3D3D3);
		new TextRenderer(mc, "74 / 14 / -26 (141.1 / 6.7)", MoveCommand.coordsXY[0], MoveCommand.coordsXY[1], ScaleCommand.coordsScale);
		
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
		drawRect(MoveCommand.dungeonTimerXY[0] - 2, MoveCommand.dungeonTimerXY[1] - 2, (int) (MoveCommand.dungeonTimerXY[0] + (112 * ScaleCommand.dungeonTimerScale)), (int) (MoveCommand.dungeonTimerXY[1] + (56 * ScaleCommand.dungeonTimerScale)), 0x40D3D3D3);
		new TextRenderer(mc, dungeonTimerText, MoveCommand.dungeonTimerXY[0], MoveCommand.dungeonTimerXY[1], ScaleCommand.dungeonTimerScale);
		new TextRenderer(mc, dungeonTimerNums, (int) (MoveCommand.dungeonTimerXY[0] + (80 * ScaleCommand.dungeonTimerScale)), MoveCommand.dungeonTimerXY[1], ScaleCommand.dungeonTimerScale);
		
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
		drawRect(MoveCommand.displayXY[0] - 2, MoveCommand.displayXY[1] - 2, (int) (MoveCommand.displayXY[0] + (144 * ScaleCommand.displayScale)), (int) (MoveCommand.displayXY[1] + (100 * ScaleCommand.displayScale)), 0x40D3D3D3);
		new TextRenderer(mc, displayText, MoveCommand.displayXY[0], MoveCommand.displayXY[1], ScaleCommand.displayScale);
		new TextRenderer(mc, displayNums, (int) (MoveCommand.displayXY[0] + (110 * ScaleCommand.displayScale)), MoveCommand.displayXY[1], ScaleCommand.displayScale);
		
		drawRect(MoveCommand.skill50XY[0] - 2, MoveCommand.skill50XY[1] - 2, (int) (MoveCommand.skill50XY[0] + (232 * ScaleCommand.skill50Scale)), (int) (MoveCommand.skill50XY[1] + (12 * ScaleCommand.skill50Scale)), 0x40D3D3D3);
		new TextRenderer(mc, EnumChatFormatting.AQUA + "+3.5 Farming (28,882,117.7/55,172,425) 52.34%", MoveCommand.skill50XY[0], MoveCommand.skill50XY[1], ScaleCommand.skill50Scale);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	/*@Override
	public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
		
		if (lastMouseX == -1) lastMouseX = mouseX;
		if (lastMouseY == -1) lastMouseY = mouseY;
		
		int xMoved = mouseX - lastMouseX;
		int yMoved = mouseY - lastMouseY;
		
		// Display
		if (isInsideBox(MoveCommand.displayXY[0] - 2, MoveCommand.displayXY[1] - 2, (int) (MoveCommand.displayXY[0] + (144 * ScaleCommand.displayScale)), (int) (MoveCommand.displayXY[1] + (100 * ScaleCommand.displayScale)), mouseX, mouseY)) {
			System.out.println("lastMouseX: " + lastMouseX + ", mouseX: " + mouseX + ", xMoved: " + xMoved);
			MoveCommand.displayXY[0] += xMoved;
			MoveCommand.displayXY[1] += yMoved;
		}
		
		lastMouseX = mouseX;
		lastMouseY = mouseY;
	}
	
	boolean isInsideBox(int x1, int y1, int x2, int y2, int x, int y) {
		if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
			return true;
		}
		return false;
	}*/
	
}
