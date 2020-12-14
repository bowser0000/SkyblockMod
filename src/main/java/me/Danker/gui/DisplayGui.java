package me.Danker.gui;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.DisplayCommand;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class DisplayGui extends GuiScreen {

	private boolean addSession = false;
	
	private GuiButton goBack;
	private GuiButton off;
	private GuiButton showSession;
	private GuiButton zombie;
	private GuiButton spider;
	private GuiButton wolf;
	private GuiButton auto;
	private GuiButton fishing;
	private GuiButton fishingWinter;
	private GuiButton fishingFestival;
	private GuiButton fishingSpooky;
	private GuiButton mythological;
	private GuiButton catacombsF1;
	private GuiButton catacombsF2;
	private GuiButton catacombsF3;
	private GuiButton catacombsF4;
	private GuiButton catacombsF5;
	private GuiButton catacombsF6;
	private GuiButton catacombsF7;
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int height = sr.getScaledHeight();
		int width = sr.getScaledWidth();
		
		goBack = new GuiButton(0, 2, height - 30, 100, 20, "Go Back");
		showSession = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Current Session Only: " + Utils.getColouredBoolean(addSession));
		off = new GuiButton(0, width / 2 - 210, (int) (height * 0.2), "Off");
		auto = new GuiButton(0, width / 2 + 10, (int) (height * 0.2), "Auto");
		zombie = new GuiButton(0, width / 2 - 190, (int) (height * 0.3), 110, 20, "Zombie");
		spider = new GuiButton(0, width / 2 - 55, (int) (height * 0.3), 110, 20, "Spider");
		wolf = new GuiButton(0, width / 2 + 75, (int) (height * 0.3), 110, 20, "Wolf");
		fishing = new GuiButton(0, width / 2 - 230, (int) (height * 0.4), 100, 20, "Fishing");
		fishingWinter = new GuiButton(0, width / 2 - 110, (int) (height * 0.4), 100, 20, "Fishing Winter");
		fishingFestival = new GuiButton(0, width / 2 + 10, (int) (height * 0.4), 100, 20, "Fishing Festival");
		fishingSpooky = new GuiButton(0, width / 2 + 130, (int) (height * 0.4), 100, 20, "Fishing Spooky");
		mythological = new GuiButton(0, width / 2 - 100, (int) (height * 0.5), 200, 20, "Mythological");
		catacombsF1 = new GuiButton(0, width / 2 - 205, (int) (height * 0.65), 50, 20, "F1");
		catacombsF2 = new GuiButton(0, width / 2 - 145, (int) (height * 0.65), 50, 20, "F2");
		catacombsF3 = new GuiButton(0, width / 2 - 85, (int) (height * 0.65), 50, 20, "F3");
		catacombsF4 = new GuiButton(0, width / 2 - 25, (int) (height * 0.65), 50, 20, "F4");
		catacombsF5 = new GuiButton(0, width / 2 + 35, (int) (height * 0.65), 50, 20, "F5");
		catacombsF6 = new GuiButton(0, width / 2 + 95, (int) (height * 0.65), 50, 20, "F6");
		catacombsF7 = new GuiButton(0, width / 2 + 155, (int) (height * 0.65), 50, 20, "F7");
		
		this.buttonList.add(showSession);
		this.buttonList.add(off);
		this.buttonList.add(auto);
		this.buttonList.add(zombie);
		this.buttonList.add(spider);
		this.buttonList.add(wolf);
		this.buttonList.add(fishing);
		this.buttonList.add(fishingWinter);
		this.buttonList.add(fishingFestival);
		this.buttonList.add(fishingSpooky);
		this.buttonList.add(mythological);
		this.buttonList.add(catacombsF1);
		this.buttonList.add(catacombsF2);
		this.buttonList.add(catacombsF3);
		this.buttonList.add(catacombsF4);
		this.buttonList.add(catacombsF5);
		this.buttonList.add(catacombsF6);
		this.buttonList.add(catacombsF7);
		this.buttonList.add(goBack);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		Minecraft mc = Minecraft.getMinecraft();
		
		String displayText;
		if (DisplayCommand.auto) {
			displayText = "Current Display: auto";
		} else {
			displayText = "Current Display: " + DisplayCommand.display;
		}
		int displayWidth = mc.fontRendererObj.getStringWidth(displayText);
		new TextRenderer(mc, displayText, width / 2 - displayWidth / 2, 10, 1D);
		
		String catacombsTitleText = "Catacombs Dungeon";
		int catacombsTitleWidth = mc.fontRendererObj.getStringWidth(catacombsTitleText);
		new TextRenderer(mc, catacombsTitleText, width / 2 - catacombsTitleWidth / 2, (int) (height * 0.6), 1D);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if (button == goBack) {
			DankersSkyblockMod.guiToOpen = "dankergui1";
		} else if (button == showSession) {
			addSession = !addSession;
			showSession.displayString = "Current Session Only: " + Utils.getColouredBoolean(addSession);
		} else if (button == off) {
			setDisplay("off", true);
		} else if (button == zombie) {
			setDisplay("zombie", false);
		} else if (button == spider) {
			setDisplay("spider", false);
		} else if (button == wolf) {
			setDisplay("wolf", false);
		} else if (button == auto) {
			DisplayCommand.auto = true;
			ConfigHandler.writeBooleanConfig("misc", "autoDisplay", true);
		} else if (button == fishing) {
			setDisplay("fishing", false);
		} else if (button == fishingWinter) {
			setDisplay("fishing_winter", false);
		} else if (button == fishingFestival) {
			setDisplay("fishing_festival", false);
		} else if (button == fishingSpooky) {
			setDisplay("fishing_spooky", false);
		} else if (button == mythological) {
			setDisplay("mythological", false);
		} else if (button == catacombsF1) {
			setDisplay("catacombs_floor_one", false);
		} else if (button == catacombsF2) {
			setDisplay("catacombs_floor_two", false);
		} else if (button == catacombsF3) {
			setDisplay("catacombs_floor_three", false);
		} else if (button == catacombsF4) {
			setDisplay("catacombs_floor_four", false);
		} else if (button == catacombsF5) {
			setDisplay("catacombs_floor_five", false);
		} else if (button == catacombsF6) {
			setDisplay("catacombs_floor_six", false);
		} else if (button == catacombsF7) {
			setDisplay("catacombs_floor_seven", false);
		}
	}
	
	public void setDisplay(String display, boolean forceNoSession) {
		if (!forceNoSession && addSession) display += "_session";
		DisplayCommand.auto = false;
		DisplayCommand.display = display;
		ConfigHandler.writeBooleanConfig("misc", "autoDisplay", false);
		ConfigHandler.writeStringConfig("misc", "display", display);
	}
	
}
