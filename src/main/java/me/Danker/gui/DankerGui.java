package me.Danker.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import me.Danker.TheMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ConfigHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class DankerGui extends GuiScreen {

	private int page;
	
	private GuiButton closeGUI;
	private GuiButton backPage;
	private GuiButton nextPage;
	private GuiButton githubLink;
	private GuiButton discordLink;
	private GuiButton changeDisplay;
	private GuiButton onlySlayer;
	private GuiButton puzzleSolvers;
	// Toggles
	private GuiButton gparty;
	private GuiButton coords;
	private GuiButton goldenEnch;
	private GuiButton slayerCount;
	private GuiButton rngesusAlert;
	private GuiButton splitFishing;
	private GuiButton chatMaddox;
	private GuiButton spiritBearAlert;
	private GuiButton aotd;
	private GuiButton lividDagger;
	private GuiButton sceptreMessages;
	private GuiButton petColours;
	private GuiButton dungeonTimer;
	private GuiButton golemAlerts;
	private GuiButton expertiseLore;
	private GuiButton skill50Display;
	private GuiButton outlineText;
	
	public DankerGui(int page) {
		this.page = page;
	}
	
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
		
		// Default button size is 200, 20
		closeGUI = new GuiButton(0, width / 2 - 100, (int) (height * 0.9), "Close");
		backPage = new GuiButton(0, width / 2 - 100, (int) (height * 0.8), 80, 20, "< Back");
		nextPage = new GuiButton(0, width / 2 + 20, (int) (height * 0.8), 80, 20, "Next >");
		githubLink = new GuiButton(0, 2, height - 50, 80, 20, "GitHub");
		discordLink = new GuiButton(0, 2, height - 30, 80, 20, "Discord");
		
		// Page 1
		changeDisplay = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Change Display Settings");
		onlySlayer = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Set Slayer Quest");
		puzzleSolvers = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Toggle Dungeons Puzzle Solvers");
		outlineText = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Outline Displayed Text: " + Utils.getColouredBoolean(ToggleCommand.outlineTextToggled));
		gparty = new GuiButton(0, width / 2 - 100, (int) (height * 0.5), "Guild Party Notifications: " + Utils.getColouredBoolean(ToggleCommand.gpartyToggled));
		coords = new GuiButton(0, width / 2 - 100, (int) (height * 0.6), "Coordinate/Angle Display: " + Utils.getColouredBoolean(ToggleCommand.coordsToggled));
		goldenEnch = new GuiButton(0, width / 2 - 100, (int) (height * 0.7), "Golden T10/T6/T4 Enchantments: " + Utils.getColouredBoolean(ToggleCommand.goldenToggled));
		// Page 2
		slayerCount = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Count Total 20% Drops: " + Utils.getColouredBoolean(ToggleCommand.slayerCountTotal));
		aotd = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Disable AOTD Ability: " + Utils.getColouredBoolean(ToggleCommand.aotdToggled));
		lividDagger = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Disable Livid Dagger Ability: " + Utils.getColouredBoolean(ToggleCommand.lividDaggerToggled));
		sceptreMessages = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Enable Spirit Sceptre Messages: " + Utils.getColouredBoolean(ToggleCommand.sceptreMessages));
		petColours = new GuiButton(0, width / 2 - 100, (int) (height * 0.5), "Colour Pet Backgrounds: " + Utils.getColouredBoolean(ToggleCommand.petColoursToggled));
		dungeonTimer = new GuiButton(0, width / 2 - 100, (int) (height * 0.6), "Display Dungeon Timers: " + Utils.getColouredBoolean(ToggleCommand.dungeonTimerToggled));
		golemAlerts = new GuiButton(0, width / 2 - 100, (int) (height * 0.7), "Alert When Golem Spawns: " + Utils.getColouredBoolean(ToggleCommand.golemAlertToggled));
		// Page 3
		expertiseLore = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Expertise Kills In Lore: " + Utils.getColouredBoolean(ToggleCommand.expertiseLoreToggled));
		skill50Display = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Display Progress To Skill Level 50: " + Utils.getColouredBoolean(ToggleCommand.skill50DisplayToggled));
		
		if (page == 1) {
			this.buttonList.add(changeDisplay);
			this.buttonList.add(onlySlayer);
			this.buttonList.add(puzzleSolvers);
			this.buttonList.add(outlineText);
			this.buttonList.add(gparty);
			this.buttonList.add(coords);
			this.buttonList.add(goldenEnch);
			this.buttonList.add(nextPage);
			this.buttonList.add(closeGUI);
		} else if (page == 2) {
			this.buttonList.add(slayerCount);
			this.buttonList.add(aotd);
			this.buttonList.add(lividDagger);
			this.buttonList.add(sceptreMessages);
			this.buttonList.add(petColours);
			this.buttonList.add(dungeonTimer);
			this.buttonList.add(golemAlerts);
			this.buttonList.add(nextPage);
			this.buttonList.add(backPage);
			this.buttonList.add(closeGUI);
		} else if (page == 3) {
			this.buttonList.add(expertiseLore);
			this.buttonList.add(skill50Display);
			this.buttonList.add(backPage);
			this.buttonList.add(closeGUI);
		}
		this.buttonList.add(githubLink);
		this.buttonList.add(discordLink);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if (button == closeGUI) {
			Minecraft.getMinecraft().thePlayer.closeScreen();
		} else if (button == nextPage) {
			TheMod.guiToOpen = "dankergui" + (page + 1);
		} else if (button == backPage) {
			TheMod.guiToOpen = "dankergui" + (page - 1);
		} else if (button == githubLink) {
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/bowser0000/SkyblockMod"));
			} catch (IOException | URISyntaxException ex) {
				System.err.println(ex);
			}
		} else if (button == discordLink) {
			try {
				Desktop.getDesktop().browse(new URI("https://discord.gg/QsEkNQS"));
			} catch (IOException | URISyntaxException ex) {
				System.err.println(ex);
			}
		} else if (button == changeDisplay) {
			TheMod.guiToOpen = "displaygui";
		} else if (button == onlySlayer) {
			TheMod.guiToOpen = "onlyslayergui";
		} else if (button == puzzleSolvers) { 
			TheMod.guiToOpen = "puzzlesolvers";
		} else if (button == outlineText) {
			ToggleCommand.outlineTextToggled = !ToggleCommand.outlineTextToggled;
			ConfigHandler.writeBooleanConfig("toggles", "OutlineText", ToggleCommand.outlineTextToggled);
			outlineText.displayString = "Outline Displayed Text: " + Utils.getColouredBoolean(ToggleCommand.outlineTextToggled);
		} else if (button == gparty) {
			ToggleCommand.gpartyToggled = !ToggleCommand.gpartyToggled;
			ConfigHandler.writeBooleanConfig("toggles", "GParty", ToggleCommand.gpartyToggled);
			gparty.displayString = "Guild Party Notifications: " + Utils.getColouredBoolean(ToggleCommand.gpartyToggled);
		} else if (button == coords) {
			ToggleCommand.coordsToggled = !ToggleCommand.coordsToggled;
			ConfigHandler.writeBooleanConfig("toggles", "Coords", ToggleCommand.coordsToggled);
			coords.displayString = "Coordinate/Angle Display: " + Utils.getColouredBoolean(ToggleCommand.coordsToggled);
		} else if (button == goldenEnch) {
			ToggleCommand.goldenToggled = !ToggleCommand.goldenToggled;
			ConfigHandler.writeBooleanConfig("toggles", "Golden", ToggleCommand.goldenToggled);
			goldenEnch.displayString = "Golden T10/T6/T4 Enchantments: " + Utils.getColouredBoolean(ToggleCommand.goldenToggled);
		} else if (button == slayerCount) {
			ToggleCommand.slayerCountTotal = !ToggleCommand.slayerCountTotal;
			ConfigHandler.writeBooleanConfig("toggles", "SlayerCount", ToggleCommand.slayerCountTotal);
			slayerCount.displayString = "Count Total 20% Drops: " + Utils.getColouredBoolean(ToggleCommand.slayerCountTotal);
		} else if (button == aotd) {
			ToggleCommand.aotdToggled = !ToggleCommand.aotdToggled;
			ConfigHandler.writeBooleanConfig("toggles", "AOTD", ToggleCommand.aotdToggled);
			aotd.displayString = "Disable AOTD Ability: " + Utils.getColouredBoolean(ToggleCommand.aotdToggled);
		} else if (button == lividDagger) {
			ToggleCommand.lividDaggerToggled = !ToggleCommand.lividDaggerToggled;
			ConfigHandler.writeBooleanConfig("toggles", "LividDagger", ToggleCommand.lividDaggerToggled);
			lividDagger.displayString = "Disable Livid Dagger Ability: " + Utils.getColouredBoolean(ToggleCommand.lividDaggerToggled);
		} else if (button == sceptreMessages) {
			ToggleCommand.sceptreMessages = !ToggleCommand.sceptreMessages;
			ConfigHandler.writeBooleanConfig("toggles", "SceptreMessages", ToggleCommand.sceptreMessages);
			sceptreMessages.displayString = "Enable Spirit Sceptre Messages: " + Utils.getColouredBoolean(ToggleCommand.sceptreMessages);
		} else if (button == petColours) {
			ToggleCommand.petColoursToggled = !ToggleCommand.petColoursToggled;
			ConfigHandler.writeBooleanConfig("toggles", "PetColors", ToggleCommand.petColoursToggled);
			petColours.displayString = "Colour Pet Backgrounds: " + Utils.getColouredBoolean(ToggleCommand.petColoursToggled);
		} else if (button == dungeonTimer) {
			ToggleCommand.dungeonTimerToggled = !ToggleCommand.dungeonTimerToggled;
			ConfigHandler.writeBooleanConfig("toggles", "DungeonTimer", ToggleCommand.dungeonTimerToggled);
			dungeonTimer.displayString = "Display Dungeon Timers: " + Utils.getColouredBoolean(ToggleCommand.dungeonTimerToggled);
		} else if (button == golemAlerts) {
			ToggleCommand.golemAlertToggled = !ToggleCommand.golemAlertToggled;
			ConfigHandler.writeBooleanConfig("toggles", "GolemAlerts", ToggleCommand.golemAlertToggled);
			golemAlerts.displayString = "Alert When Golem Spawns: " + Utils.getColouredBoolean(ToggleCommand.golemAlertToggled);
		} else if (button == expertiseLore) {
			ToggleCommand.expertiseLoreToggled = !ToggleCommand.expertiseLoreToggled;
			ConfigHandler.writeBooleanConfig("toggles", "ExpertiseLore", ToggleCommand.expertiseLoreToggled);
			expertiseLore.displayString = "Expertise Kills In Lore: " + Utils.getColouredBoolean(ToggleCommand.expertiseLoreToggled);
		} else if (button == skill50Display) {
			ToggleCommand.skill50DisplayToggled = !ToggleCommand.skill50DisplayToggled;
			ConfigHandler.writeBooleanConfig("toggles", "Skill50Display", ToggleCommand.skill50DisplayToggled);
			skill50Display.displayString = "Display Progress To Skill Level 50: " + Utils.getColouredBoolean(ToggleCommand.skill50DisplayToggled);
		}
	}
	
}
