package me.Danker.gui;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DankerGui extends GuiScreen {

	private int page;
	
	private GuiButton closeGUI;
	private GuiButton backPage;
	private GuiButton nextPage;
	private GuiButton githubLink;
	private GuiButton discordLink;
	private GuiButton editLocations;
	private GuiButton changeDisplay;
	private GuiButton onlySlayer;
	private GuiButton puzzleSolvers;
	private GuiButton experimentationTableSolvers;
	private GuiButton skillTracker;
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
	private GuiButton petColours;
	private GuiButton golemAlerts;
	private GuiButton expertiseLore;
	private GuiButton skill50Display;
	private GuiButton outlineText;
	private GuiButton cakeTimer;
	// Chat Messages
	private GuiButton lividDagger;
	private GuiButton sceptreMessages;
	private GuiButton midasStaffMessages;
	private GuiButton implosionMessages;
	private GuiButton healMessages;
	private GuiButton cooldownMessages;
	private GuiButton manaMessages;
	//Dungeons
	private GuiButton dungeonTimer;
	private GuiButton lowHealthNotify;
	private GuiButton lividSolver;
	private GuiButton stopSalvageStarred;
	private GuiButton watcherReadyMessage;
	private GuiButton flowerWeapons;
	private GuiButton pickBlock;
	private GuiButton notifySlayerSlain;
	private GuiButton necronNotifications;
	private GuiButton bonzoTimer;
	private GuiButton blockBreakingFarms;
	private GuiButton autoSkillTracker;
	
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
		editLocations = new GuiButton(0, 2, 5, 100, 20, "Edit Locations");
		
		// Page 1
		changeDisplay = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Change Display Settings");
		onlySlayer = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Set Slayer Quest");
		puzzleSolvers = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Toggle Dungeons Puzzle Solvers");
		experimentationTableSolvers = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Toggle Experimentation Table Solvers");
		skillTracker = new GuiButton(0, width / 2 - 100, (int) (height * 0.5), "Toggle Skill XP/Hour Tracking");
		outlineText = new GuiButton(0, width / 2 - 100, (int) (height * 0.6), "Outline Displayed Text: " + Utils.getColouredBoolean(ToggleCommand.outlineTextToggled));
		pickBlock = new GuiButton(0, width / 2 - 100, (int) (height * 0.7), "Auto-Swap to Pick Block: " + Utils.getColouredBoolean(ToggleCommand.swapToPickBlockToggled));
		// Page 2
		coords = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Coordinate/Angle Display: " + Utils.getColouredBoolean(ToggleCommand.coordsToggled));
		chatMaddox = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Click On-Screen to Open Maddox: " + Utils.getColouredBoolean(ToggleCommand.chatMaddoxToggled));
		cakeTimer = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Cake Timer: " + Utils.getColouredBoolean(ToggleCommand.cakeTimerToggled));
		skill50Display = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Display Progress To Skill Level 50: " + Utils.getColouredBoolean(ToggleCommand.skill50DisplayToggled));
		slayerCount = new GuiButton(0, width / 2 - 100, (int) (height * 0.5), "Count Total 20% Drops: " + Utils.getColouredBoolean(ToggleCommand.slayerCountTotal));
		aotd = new GuiButton(0, width / 2 - 100, (int) (height * 0.6), "Disable AOTD Ability: " + Utils.getColouredBoolean(ToggleCommand.aotdToggled));
		lividDagger = new GuiButton(0, width / 2 - 100, (int) (height * 0.7), "Disable Livid Dagger Ability: " + Utils.getColouredBoolean(ToggleCommand.lividDaggerToggled));
		// Page 3
		spiritBearAlert = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Spirit Bear Spawn Alerts: " + Utils.getColouredBoolean(ToggleCommand.spiritBearAlerts));
		sceptreMessages = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Spirit Sceptre Messages: " + Utils.getColouredBoolean(ToggleCommand.sceptreMessages));
		midasStaffMessages = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Midas Staff Messages: " + Utils.getColouredBoolean(ToggleCommand.midasStaffMessages));
		implosionMessages = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Implosion Messages: " + Utils.getColouredBoolean(ToggleCommand.implosionMessages));
		healMessages = new GuiButton(0, width / 2 - 100, (int) (height * 0.5), "Heal Messages: " + Utils.getColouredBoolean(ToggleCommand.healMessages));
		cooldownMessages = new GuiButton(0, width / 2 - 100, (int) (height * 0.6), "Cooldown Messages: " + Utils.getColouredBoolean(ToggleCommand.cooldownMessages));
		manaMessages = new GuiButton(0, width / 2 - 100, (int) (height * 0.7), "Mana Messages: " + Utils.getColouredBoolean((ToggleCommand.manaMessages)));
		// Page 4
		goldenEnch = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Golden T10/T6/T4 Enchantments: " + Utils.getColouredBoolean(ToggleCommand.goldenToggled));
		petColours = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Colour Pet Backgrounds: " + Utils.getColouredBoolean(ToggleCommand.petColoursToggled));
		expertiseLore = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Expertise Kills In Lore: " + Utils.getColouredBoolean(ToggleCommand.expertiseLoreToggled));
		gparty = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Guild Party Notifications: " + Utils.getColouredBoolean(ToggleCommand.gpartyToggled));
		golemAlerts = new GuiButton(0, width / 2 - 100, (int) (height * 0.5), "Alert When Golem Spawns: " + Utils.getColouredBoolean(ToggleCommand.golemAlertToggled));
		rngesusAlert = new GuiButton(0, width / 2 - 100, (int) (height * 0.6), "RNGesus Alerts: " + Utils.getColouredBoolean(ToggleCommand.rngesusAlerts));
		splitFishing = new GuiButton(0, width / 2 - 100, (int) (height * 0.7), "Split Fishing Display: " + Utils.getColouredBoolean(ToggleCommand.splitFishing));
		// Page 5
    	lowHealthNotify = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Low Health Notifications: " + Utils.getColouredBoolean(ToggleCommand.lowHealthNotifyToggled));
		lividSolver = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Find Correct Livid: " + Utils.getColouredBoolean(ToggleCommand.lividSolverToggled));
		dungeonTimer = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Display Dungeon Timers: " + Utils.getColouredBoolean(ToggleCommand.dungeonTimerToggled));
		stopSalvageStarred = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Stop Salvaging Starred Items: " + Utils.getColouredBoolean(ToggleCommand.stopSalvageStarredToggled));
		watcherReadyMessage = new GuiButton(0, width / 2 - 100, (int) (height * 0.5), "Display Watcher Ready Message: " + Utils.getColouredBoolean(ToggleCommand.watcherReadyToggled));
		flowerWeapons = new GuiButton(0, width / 2 - 100, (int) (height * 0.6), "Prevent Placing FoT/Spirit Sceptre: " + Utils.getColouredBoolean(ToggleCommand.flowerWeaponsToggled));
		notifySlayerSlain = new GuiButton(0, width / 2 - 100, (int) (height * 0.7), "Notify when Slayer Slain: " + Utils.getColouredBoolean(ToggleCommand.notifySlayerSlainToggled));
		//Page 6
		necronNotifications = new GuiButton(0, width / 2 - 100, (int) (height * 0.1), "Necron Phase Notifications: " + Utils.getColouredBoolean(ToggleCommand.necronNotificationsToggled));
		bonzoTimer = new GuiButton(0, width / 2 - 100, (int) (height * 0.2), "Bonzo's Mask Timer: " + Utils.getColouredBoolean(ToggleCommand.bonzoTimerToggled));
		blockBreakingFarms = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Prevent Breaking Farms: " + Utils.getColouredBoolean(ToggleCommand.blockBreakingFarmsToggled));
    autoSkillTracker = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Auto Start/Stop Skill Tracker: " + Utils.getColouredBoolean(ToggleCommand.autoSkillTrackerToggled));

		switch (page) {
			case 1:
				this.buttonList.add(changeDisplay);
				this.buttonList.add(onlySlayer);
				this.buttonList.add(puzzleSolvers);
				this.buttonList.add(experimentationTableSolvers);
				this.buttonList.add(skillTracker);
				this.buttonList.add(outlineText);
				this.buttonList.add(pickBlock);
				this.buttonList.add(nextPage);
				break;
			case 2:
				this.buttonList.add(coords);
				this.buttonList.add(chatMaddox);
				this.buttonList.add(cakeTimer);
				this.buttonList.add(skill50Display);
				this.buttonList.add(slayerCount);
				this.buttonList.add(aotd);
				this.buttonList.add(lividDagger);
				this.buttonList.add(nextPage);
				this.buttonList.add(backPage);
				break;
			case 3:
				this.buttonList.add(spiritBearAlert);
				this.buttonList.add(sceptreMessages);
				this.buttonList.add(midasStaffMessages);
				this.buttonList.add(implosionMessages);
				this.buttonList.add(healMessages);
				this.buttonList.add(cooldownMessages);
				this.buttonList.add(manaMessages);
				this.buttonList.add(nextPage);
				this.buttonList.add(backPage);
				break;
			case 4:
				this.buttonList.add(goldenEnch);
				this.buttonList.add(petColours);
				this.buttonList.add(expertiseLore);
				this.buttonList.add(gparty);
				this.buttonList.add(golemAlerts);
				this.buttonList.add(rngesusAlert);
				this.buttonList.add(splitFishing);
				this.buttonList.add(nextPage);
				this.buttonList.add(backPage);
				break;
			case 5:
				this.buttonList.add(lowHealthNotify);
				this.buttonList.add(lividSolver);
				this.buttonList.add(dungeonTimer);
				this.buttonList.add(stopSalvageStarred);
				this.buttonList.add(watcherReadyMessage);
				this.buttonList.add(flowerWeapons);
				this.buttonList.add(notifySlayerSlain);
				this.buttonList.add(nextPage);
				this.buttonList.add(backPage);
				break;
			case 6:
        this.buttonList.add(necronNotifications);
				this.buttonList.add(bonzoTimer);
				this.buttonList.add(blockBreakingFarms);
				this.buttonList.add(autoSkillTracker);
				this.buttonList.add(backPage);
				break;
		}
		
		this.buttonList.add(githubLink);
		this.buttonList.add(discordLink);
		this.buttonList.add(closeGUI);
		this.buttonList.add(editLocations);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		String pageText = "Page: " + page + "/6";
		int pageWidth = mc.fontRendererObj.getStringWidth(pageText);
		new TextRenderer(mc, pageText, width / 2 - pageWidth / 2, 10, 1D);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if (button == closeGUI) {
			Minecraft.getMinecraft().thePlayer.closeScreen();
		} else if (button == nextPage) {
			DankersSkyblockMod.guiToOpen = "dankergui" + (page + 1);
		} else if (button == backPage) {
			DankersSkyblockMod.guiToOpen = "dankergui" + (page - 1);
		} else if (button == editLocations) {
			DankersSkyblockMod.guiToOpen = "editlocations";
		} else if (button == githubLink) {
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/bowser0000/SkyblockMod"));
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
		} else if (button == discordLink) {
			try {
				Desktop.getDesktop().browse(new URI("https://discord.gg/QsEkNQS"));
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
		} else if (button == changeDisplay) {
			DankersSkyblockMod.guiToOpen = "displaygui";
		} else if (button == onlySlayer) {
			DankersSkyblockMod.guiToOpen = "onlyslayergui";
		} else if (button == puzzleSolvers) {
			DankersSkyblockMod.guiToOpen = "puzzlesolvers";
		} else if (button == experimentationTableSolvers) {
			DankersSkyblockMod.guiToOpen = "experimentsolvers";
		} else if (button == skillTracker) {
			DankersSkyblockMod.guiToOpen = "skilltracker";
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
			sceptreMessages.displayString = "Spirit Sceptre Messages: " + Utils.getColouredBoolean(ToggleCommand.sceptreMessages);
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
		} else if (button == splitFishing) {
			ToggleCommand.splitFishing = !ToggleCommand.splitFishing;
			ConfigHandler.writeBooleanConfig("toggles", "SplitFishing", ToggleCommand.splitFishing);
			splitFishing.displayString = "Split Fishing Display: " + Utils.getColouredBoolean(ToggleCommand.splitFishing);
		} else if (button == chatMaddox) {
			ToggleCommand.chatMaddoxToggled = !ToggleCommand.chatMaddoxToggled;
			ConfigHandler.writeBooleanConfig("toggles", "ChatMaddox", ToggleCommand.chatMaddoxToggled);
			chatMaddox.displayString = "Click On-Screen to Open Maddox: " + Utils.getColouredBoolean(ToggleCommand.chatMaddoxToggled);
		} else if (button == spiritBearAlert) {
			ToggleCommand.spiritBearAlerts = !ToggleCommand.spiritBearAlerts;
			ConfigHandler.writeBooleanConfig("toggles", "SpiritBearAlerts", ToggleCommand.spiritBearAlerts);
			spiritBearAlert.displayString = "Spirit Bear Spawn Alerts: " + Utils.getColouredBoolean(ToggleCommand.spiritBearAlerts);
		} else if (button == midasStaffMessages) {
			ToggleCommand.midasStaffMessages = !ToggleCommand.midasStaffMessages;
			ConfigHandler.writeBooleanConfig("toggles", "MidasStaffMessages", ToggleCommand.midasStaffMessages);
			midasStaffMessages.displayString = "Midas Staff Messages: " + Utils.getColouredBoolean(ToggleCommand.midasStaffMessages);
		} else if (button == lividSolver) {
			ToggleCommand.lividSolverToggled = !ToggleCommand.lividSolverToggled;
			ConfigHandler.writeBooleanConfig("toggles", "LividSolver", ToggleCommand.lividSolverToggled);
			lividSolver.displayString = "Find Correct Livid: " + Utils.getColouredBoolean(ToggleCommand.lividSolverToggled);
		} else if (button == rngesusAlert) {
			ToggleCommand.rngesusAlerts = !ToggleCommand.rngesusAlerts;
			ConfigHandler.writeBooleanConfig("toggles", "RNGesusAlerts", ToggleCommand.rngesusAlerts);
			rngesusAlert.displayString = "RNGesus Alerts: " + Utils.getColouredBoolean(ToggleCommand.rngesusAlerts);
		} else if (button == cakeTimer) {
			ToggleCommand.cakeTimerToggled = !ToggleCommand.cakeTimerToggled;
			ConfigHandler.writeBooleanConfig("toggles", "CakeTimer", ToggleCommand.cakeTimerToggled);
			cakeTimer.displayString = "Cake Timer: " + Utils.getColouredBoolean(ToggleCommand.cakeTimerToggled);
		} else if (button == healMessages) {
			ToggleCommand.healMessages = !ToggleCommand.healMessages;
			ConfigHandler.writeBooleanConfig("toggles", "HealMessages", ToggleCommand.healMessages);
			healMessages.displayString = "Heal Messages: " + Utils.getColouredBoolean(ToggleCommand.healMessages);
		} else if (button == cooldownMessages) {
			ToggleCommand.cooldownMessages = !ToggleCommand.cooldownMessages;
			ConfigHandler.writeBooleanConfig("toggles", "CooldownMessages", ToggleCommand.cooldownMessages);
			cooldownMessages.displayString = "Cooldown Messages: " + Utils.getColouredBoolean(ToggleCommand.cooldownMessages);
		} else if (button == manaMessages) {
			ToggleCommand.manaMessages = !ToggleCommand.manaMessages;
			ConfigHandler.writeBooleanConfig("toggles", "ManaMessages", ToggleCommand.manaMessages);
			manaMessages.displayString = "Mana Messages: " + Utils.getColouredBoolean(ToggleCommand.manaMessages);
		} else if (button == lowHealthNotify) {
			ToggleCommand.lowHealthNotifyToggled = !ToggleCommand.lowHealthNotifyToggled;
			ConfigHandler.writeBooleanConfig("toggles", "LowHealthNotify", ToggleCommand.lowHealthNotifyToggled);
			lowHealthNotify.displayString = "Low Health Notifications: " + Utils.getColouredBoolean(ToggleCommand.lowHealthNotifyToggled);
		} else if (button == implosionMessages) {
			ToggleCommand.implosionMessages = !ToggleCommand.implosionMessages;
			ConfigHandler.writeBooleanConfig("toggles", "ImplosionMessages", ToggleCommand.implosionMessages);
			implosionMessages.displayString = "Implosion Messages: " + Utils.getColouredBoolean(ToggleCommand.implosionMessages);
		} else if(button == stopSalvageStarred) {
			ToggleCommand.stopSalvageStarredToggled = !ToggleCommand.stopSalvageStarredToggled;
			ConfigHandler.writeBooleanConfig("toggles", "StopSalvageStarred", ToggleCommand.stopSalvageStarredToggled);
			stopSalvageStarred.displayString = "Stop Salvaging Starred Items: " + Utils.getColouredBoolean(ToggleCommand.stopSalvageStarredToggled);
		} else if (button == watcherReadyMessage) {
			ToggleCommand.watcherReadyToggled = !ToggleCommand.watcherReadyToggled;
			ConfigHandler.writeBooleanConfig("toggles", "WatcherReadyMessage", ToggleCommand.watcherReadyToggled);
			watcherReadyMessage.displayString = "Display Watcher Ready Message: " + Utils.getColouredBoolean(ToggleCommand.watcherReadyToggled);
		} else if (button == notifySlayerSlain) {
			ToggleCommand.notifySlayerSlainToggled = !ToggleCommand.notifySlayerSlainToggled;
			ConfigHandler.writeBooleanConfig("toggles", "NotifySlayerSlain", ToggleCommand.notifySlayerSlainToggled);
			notifySlayerSlain.displayString = "Notify when Slayer Slain: " + Utils.getColouredBoolean(ToggleCommand.notifySlayerSlainToggled);
		} else if (button == necronNotifications) {
			ToggleCommand.necronNotificationsToggled = !ToggleCommand.necronNotificationsToggled;
			ConfigHandler.writeBooleanConfig("toggles", "NecronNotifications", ToggleCommand.necronNotificationsToggled);
			necronNotifications.displayString = "Necron Phase Notifications: " + Utils.getColouredBoolean(ToggleCommand.necronNotificationsToggled);
		} else if (button == bonzoTimer) {
			ToggleCommand.bonzoTimerToggled = !ToggleCommand.bonzoTimerToggled;
			ConfigHandler.writeBooleanConfig("toggles", "BonzoTimer", ToggleCommand.bonzoTimerToggled);
			bonzoTimer.displayString = "Bonzo's Mask Timer: " + Utils.getColouredBoolean(ToggleCommand.bonzoTimerToggled);
		} else if (button == blockBreakingFarms) {
			ToggleCommand.blockBreakingFarmsToggled = !ToggleCommand.blockBreakingFarmsToggled;
			ConfigHandler.writeBooleanConfig("toggles", "BlockBreakingFarms", ToggleCommand.blockBreakingFarmsToggled);
			blockBreakingFarms.displayString = "Prevent Breaking Farms: " + Utils.getColouredBoolean(ToggleCommand.blockBreakingFarmsToggled);
		} else if (button == pickBlock) {
			ToggleCommand.swapToPickBlockToggled = !ToggleCommand.swapToPickBlockToggled;
			ConfigHandler.writeBooleanConfig("toggles", "PickBlock", ToggleCommand.swapToPickBlockToggled);
			pickBlock.displayString = "Auto-Swap to Pick Block: " + Utils.getColouredBoolean(ToggleCommand.swapToPickBlockToggled);
		} else if (button == flowerWeapons) {
			ToggleCommand.flowerWeaponsToggled = !ToggleCommand.flowerWeaponsToggled;
			ConfigHandler.writeBooleanConfig("toggles", "FlowerWeapons", ToggleCommand.flowerWeaponsToggled);
			flowerWeapons.displayString = "Prevent Placing FoT/Spirit Sceptre: " + Utils.getColouredBoolean(ToggleCommand.flowerWeaponsToggled);
		} else if (button == autoSkillTracker) {
			ToggleCommand.autoSkillTrackerToggled = !ToggleCommand.autoSkillTrackerToggled;
			ConfigHandler.writeBooleanConfig("toggles", "AutoSkillTracker", ToggleCommand.autoSkillTrackerToggled);
			autoSkillTracker.displayString = "Auto Start/Stop Skill Tracker: " + Utils.getColouredBoolean(ToggleCommand.autoSkillTrackerToggled);
		}
	}
	
}
