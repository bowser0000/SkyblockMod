package me.Danker.gui;

import me.Danker.features.loot.LootDisplay;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
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
	private GuiButton enderman;
	private GuiButton blaze;
	private GuiButton auto;
	private GuiButton fishing;
	private GuiButton fishingWinter;
	private GuiButton fishingFestival;
	private GuiButton fishingSpooky;
	private GuiButton fishingCH;
	private GuiButton fishingLava;
	private GuiButton fishingTrophy;
	private GuiButton catacombsF1;
	private GuiButton catacombsF2;
	private GuiButton catacombsF3;
	private GuiButton catacombsF4;
	private GuiButton catacombsF5;
	private GuiButton catacombsF6;
	private GuiButton catacombsF7;
	private GuiButton catacombsMM;
	private GuiButton mythological;
	private GuiButton ghost;
	
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
		zombie = new GuiButton(0, width / 2 - 270, (int) (height * 0.35), 100, 20, "Zombie");
		spider = new GuiButton(0, width / 2 - 160, (int) (height * 0.35), 100, 20, "Spider");
		wolf = new GuiButton(0, width / 2 - 50, (int) (height * 0.35), 100, 20, "Wolf");
		enderman = new GuiButton(0, width / 2 + 60, (int) (height * 0.35), 100, 20, "Enderman");
		blaze = new GuiButton(0, width / 2 + 170, (int) (height * 0.35), 100, 20, "Blaze");
		fishing = new GuiButton(0, width / 2 - 310, (int) (height * 0.5), 80, 20, "Fishing");
		fishingWinter = new GuiButton(0, width / 2 - 220, (int) (height * 0.5), 80, 20, "Fishing Winter");
		fishingFestival = new GuiButton(0, width / 2 - 130, (int) (height * 0.5), 80, 20, "Fishing Festival");
		fishingSpooky = new GuiButton(0, width / 2 - 40, (int) (height * 0.5), 80, 20, "Fishing Spooky");
		fishingCH = new GuiButton(0, width / 2 + 50, (int) (height * 0.5), 80, 20, "CH Fishing");
		fishingLava = new GuiButton(0, width / 2 + 140, (int) (height * 0.5), 80, 20, "Lava Fishing");
		fishingTrophy = new GuiButton(0, width / 2 + 230, (int) (height * 0.5), 80, 20, "Fishing Trophy");
		catacombsF1 = new GuiButton(0, width / 2 - 235, (int) (height * 0.65), 50, 20, "F1");
		catacombsF2 = new GuiButton(0, width / 2 - 175, (int) (height * 0.65), 50, 20, "F2");
		catacombsF3 = new GuiButton(0, width / 2 - 115, (int) (height * 0.65), 50, 20, "F3");
		catacombsF4 = new GuiButton(0, width / 2 - 55, (int) (height * 0.65), 50, 20, "F4");
		catacombsF5 = new GuiButton(0, width / 2 + 5, (int) (height * 0.65), 50, 20, "F5");
		catacombsF6 = new GuiButton(0, width / 2 + 65, (int) (height * 0.65), 50, 20, "F6");
		catacombsF7 = new GuiButton(0, width / 2 + 125, (int) (height * 0.65), 50, 20, "F7");
		catacombsMM = new GuiButton(0, width / 2 + 185, (int) (height * 0.65), 50, 20, "MM");
		mythological = new GuiButton(0, width / 2 - 100, (int) (height * 0.8), 95, 20, "Mythological");
		ghost = new GuiButton(0, width / 2 + 5, (int) (height * 0.8), 95, 20, "Ghost");

		this.buttonList.add(showSession);
		this.buttonList.add(off);
		this.buttonList.add(auto);
		this.buttonList.add(zombie);
		this.buttonList.add(spider);
		this.buttonList.add(wolf);
		this.buttonList.add(enderman);
		this.buttonList.add(blaze);
		this.buttonList.add(fishing);
		this.buttonList.add(fishingWinter);
		this.buttonList.add(fishingFestival);
		this.buttonList.add(fishingSpooky);
		this.buttonList.add(fishingCH);
		this.buttonList.add(fishingLava);
		this.buttonList.add(fishingTrophy);
		this.buttonList.add(catacombsF1);
		this.buttonList.add(catacombsF2);
		this.buttonList.add(catacombsF3);
		this.buttonList.add(catacombsF4);
		this.buttonList.add(catacombsF5);
		this.buttonList.add(catacombsF6);
		this.buttonList.add(catacombsF7);
		this.buttonList.add(catacombsMM);
		this.buttonList.add(mythological);
		this.buttonList.add(ghost);
		this.buttonList.add(goBack);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		String displayText = LootDisplay.auto ? "Current Display: auto" : "Current Display: " + LootDisplay.display;
		RenderUtils.drawCenteredText(displayText, width, 10, 1D);
		RenderUtils.drawCenteredText("Slayer", width, (int) (height * 0.3), 1D);
		RenderUtils.drawCenteredText("Fishing", width, (int) (height * 0.45), 1D);
		RenderUtils.drawCenteredText("Catacombs Dungeon", width, (int) (height * 0.6), 1D);
		RenderUtils.drawCenteredText("Misc", width, (int) (height * 0.75), 1D);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if (button == goBack) {
			mc.displayGuiScreen(new DankerGui(1, ""));
		} else if (button == showSession) {
			addSession = !addSession;
			showSession.displayString = "Current Session Only: " + Utils.getColouredBoolean(addSession);
		} else if (button == off) {
			setDisplay("off", true);
		} else if (button == zombie) {
			setDisplay("zombie");
		} else if (button == spider) {
			setDisplay("spider");
		} else if (button == wolf) {
			setDisplay("wolf");
		} else if (button == enderman) {
			setDisplay("enderman");
		} else if (button == blaze) {
			setDisplay("blaze");
		} else if (button == auto) {
			LootDisplay.auto = true;
			ConfigHandler.writeBooleanConfig("misc", "autoDisplay", true);
		} else if (button == fishing) {
			setDisplay("fishing");
		} else if (button == fishingWinter) {
			setDisplay("fishing_winter");
		} else if (button == fishingFestival) {
			setDisplay("fishing_festival");
		} else if (button == fishingSpooky) {
			setDisplay("fishing_spooky");
		} else if (button == fishingCH) {
			setDisplay("fishing_ch");
		} else if (button == fishingLava) {
			setDisplay("fishing_lava");
		} else if (button == fishingTrophy) {
			setDisplay("fishing_trophy");
		} else if (button == mythological) {
			setDisplay("mythological");
		} else if (button == catacombsF1) {
			setDisplay("catacombs_floor_one");
		} else if (button == catacombsF2) {
			setDisplay("catacombs_floor_two");
		} else if (button == catacombsF3) {
			setDisplay("catacombs_floor_three");
		} else if (button == catacombsF4) {
			setDisplay("catacombs_floor_four");
		} else if (button == catacombsF5) {
			setDisplay("catacombs_floor_five");
		} else if (button == catacombsF6) {
			setDisplay("catacombs_floor_six");
		} else if (button == catacombsF7) {
			setDisplay("catacombs_floor_seven");
		} else if (button == catacombsMM) {
			setDisplay("catacombs_master");
		} else if (button == ghost) {
			setDisplay("ghost");
		}
	}

	public void setDisplay(String display, boolean forceNoSession) {
		if (!forceNoSession && addSession) display += "_session";
		LootDisplay.auto = false;
		LootDisplay.display = display;
		ConfigHandler.writeBooleanConfig("misc", "autoDisplay", false);
		ConfigHandler.writeStringConfig("misc", "display", display);
	}

	public void setDisplay(String display) {
		setDisplay(display, false);
	}
	
}
