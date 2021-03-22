package me.Danker.gui;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.features.CustomMusic;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.StringUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class DankerGui extends GuiScreen {

	private int page;
	private List<GuiButton> allButtons = new ArrayList<>();
	private List<GuiButton> foundButtons = new ArrayList<>();
	String initSearchText;
	
	private GuiButton closeGUI;
	private GuiButton backPage;
	private GuiButton nextPage;
	private GuiButton githubLink;
	private GuiButton discordLink;
	private GuiButton editLocations;
	private GuiTextField search;

	private GuiButton changeDisplay;
	private GuiButton onlySlayer;
	private GuiButton puzzleSolvers;
	private GuiButton experimentationTableSolvers;
	private GuiButton skillTracker;
	private GuiButton highlightArachne;
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
	private GuiButton shadowFury;
	private GuiButton specialHoe;
	private GuiButton melodyTooltips;
	private GuiButton dungeonBossMusic;
	// Chat Messages
	private GuiButton lividDagger;
	private GuiButton sceptreMessages;
	private GuiButton midasStaffMessages;
	private GuiButton implosionMessages;
	private GuiButton healMessages;
	private GuiButton cooldownMessages;
	private GuiButton manaMessages;
	private GuiButton killComboMessages;
	// Dungeons
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
	private GuiButton autoSkillTracker;

	public DankerGui(int page, String searchText) {
		this.page = page;
		initSearchText = searchText;
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
		search = new GuiTextField(0, this.fontRendererObj, width - 202, 5, 200, 20);

		// Page 1
		changeDisplay = new GuiButton(0, 0, 0, "Change Display Settings");
		onlySlayer = new GuiButton(0, 0, 0, "Set Slayer Quest");
		puzzleSolvers = new GuiButton(0, 0, 0, "Toggle Dungeons Puzzle Solvers");
		experimentationTableSolvers = new GuiButton(0, 0, 0, "Toggle Experimentation Table Solvers");
		skillTracker = new GuiButton(0, 0, 0, "Toggle Skill XP/Hour Tracking");
		outlineText = new GuiButton(0, 0, 0, "Outline Displayed Text: " + Utils.getColouredBoolean(ToggleCommand.outlineTextToggled));
		pickBlock = new GuiButton(0, 0, 0, "Auto-Swap to Pick Block: " + Utils.getColouredBoolean(ToggleCommand.swapToPickBlockToggled));
		// Page 2
		coords = new GuiButton(0, 0, 0, "Coordinate/Angle Display: " + Utils.getColouredBoolean(ToggleCommand.coordsToggled));
		chatMaddox = new GuiButton(0, 0, 0, "Click On-Screen to Open Maddox: " + Utils.getColouredBoolean(ToggleCommand.chatMaddoxToggled));
		cakeTimer = new GuiButton(0, 0, 0, "Cake Timer: " + Utils.getColouredBoolean(ToggleCommand.cakeTimerToggled));
		skill50Display = new GuiButton(0, 0, 0, "Display Progress To Skill Level 50: " + Utils.getColouredBoolean(ToggleCommand.skill50DisplayToggled));
		slayerCount = new GuiButton(0, 0, 0, "Count Total 20% Drops: " + Utils.getColouredBoolean(ToggleCommand.slayerCountTotal));
		aotd = new GuiButton(0, 0, 0, "Disable AOTD Ability: " + Utils.getColouredBoolean(ToggleCommand.aotdToggled));
		lividDagger = new GuiButton(0, 0, 0, "Disable Livid Dagger Ability: " + Utils.getColouredBoolean(ToggleCommand.lividDaggerToggled));
		// Page 3
		spiritBearAlert = new GuiButton(0, 0, 0, "Spirit Bear Spawn Alerts: " + Utils.getColouredBoolean(ToggleCommand.spiritBearAlerts));
		sceptreMessages = new GuiButton(0, 0, 0, "Spirit Sceptre Messages: " + Utils.getColouredBoolean(ToggleCommand.sceptreMessages));
		midasStaffMessages = new GuiButton(0, 0, 0, "Midas Staff Messages: " + Utils.getColouredBoolean(ToggleCommand.midasStaffMessages));
		implosionMessages = new GuiButton(0, 0, 0, "Implosion Messages: " + Utils.getColouredBoolean(ToggleCommand.implosionMessages));
		healMessages = new GuiButton(0, 0, 0, "Heal Messages: " + Utils.getColouredBoolean(ToggleCommand.healMessages));
		cooldownMessages = new GuiButton(0, 0, 0, "Cooldown Messages: " + Utils.getColouredBoolean(ToggleCommand.cooldownMessages));
		manaMessages = new GuiButton(0, 0, 0, "Mana Messages: " + Utils.getColouredBoolean(ToggleCommand.manaMessages));
		// Page 4
		killComboMessages = new GuiButton(0, 0, 0, "Kill Combo Messages: " + Utils.getColouredBoolean(ToggleCommand.killComboMessages));
		goldenEnch = new GuiButton(0, 0, 0, "Golden T10/T6/T4 Enchantments: " + Utils.getColouredBoolean(ToggleCommand.goldenToggled));
		petColours = new GuiButton(0, 0, 0, "Colour Pet Backgrounds: " + Utils.getColouredBoolean(ToggleCommand.petColoursToggled));
		expertiseLore = new GuiButton(0, 0, 0, "Expertise Kills In Lore: " + Utils.getColouredBoolean(ToggleCommand.expertiseLoreToggled));
		gparty = new GuiButton(0, 0, 0, "Guild Party Notifications: " + Utils.getColouredBoolean(ToggleCommand.gpartyToggled));
		golemAlerts = new GuiButton(0, 0, 0, "Alert When Golem Spawns: " + Utils.getColouredBoolean(ToggleCommand.golemAlertToggled));
		rngesusAlert = new GuiButton(0, 0, 0, "RNGesus Alerts: " + Utils.getColouredBoolean(ToggleCommand.rngesusAlerts));
		// Page 5
		splitFishing = new GuiButton(0, 0, 0, "Split Fishing Display: " + Utils.getColouredBoolean(ToggleCommand.splitFishing));
		lowHealthNotify = new GuiButton(0, 0, 0, "Low Health Notifications: " + Utils.getColouredBoolean(ToggleCommand.lowHealthNotifyToggled));
		lividSolver = new GuiButton(0, 0, 0, "Find Correct Livid: " + Utils.getColouredBoolean(ToggleCommand.lividSolverToggled));
		dungeonTimer = new GuiButton(0, 0, 0, "Display Dungeon Timers: " + Utils.getColouredBoolean(ToggleCommand.dungeonTimerToggled));
		stopSalvageStarred = new GuiButton(0, 0, 0, "Stop Salvaging Starred Items: " + Utils.getColouredBoolean(ToggleCommand.stopSalvageStarredToggled));
		watcherReadyMessage = new GuiButton(0, 0, 0, "Display Watcher Ready Message: " + Utils.getColouredBoolean(ToggleCommand.watcherReadyToggled));
		flowerWeapons = new GuiButton(0, 0, 0, "Prevent Placing FoT/Spirit Sceptre: " + Utils.getColouredBoolean(ToggleCommand.flowerWeaponsToggled));
		// Page 6
		notifySlayerSlain = new GuiButton(0, 0, 0, "Notify when Slayer Slain: " + Utils.getColouredBoolean(ToggleCommand.notifySlayerSlainToggled));
		necronNotifications = new GuiButton(0, 0, 0, "Necron Phase Notifications: " + Utils.getColouredBoolean(ToggleCommand.necronNotificationsToggled));
		bonzoTimer = new GuiButton(0, 0, 0, "Bonzo's Mask Timer: " + Utils.getColouredBoolean(ToggleCommand.bonzoTimerToggled));
		autoSkillTracker = new GuiButton(0, 0, 0, "Auto Start/Stop Skill Tracker: " + Utils.getColouredBoolean(ToggleCommand.autoSkillTrackerToggled));
		dungeonBossMusic = new GuiButton(0, 0, 0, "Custom Dungeon Boss Music: " + Utils.getColouredBoolean(ToggleCommand.dungeonBossMusic));
		shadowFury = new GuiButton(0, 0, 0, "Block Shadow Fury ability: " + Utils.getColouredBoolean(ToggleCommand.shadowFuryToggled));
		specialHoe = new GuiButton(0, 0, 0, "Block Special Hoe right click: " + Utils.getColouredBoolean(ToggleCommand.specialHoeRightClick));
		// Page 7
		melodyTooltips = new GuiButton(0, 0, 0, "Hide tooltips in Melody's Harp: " + Utils.getColouredBoolean(ToggleCommand.melodyTooltips));
		highlightArachne = new GuiButton(0, 0, 0, "Highlight Arachne: " + Utils.getColouredBoolean(ToggleCommand.highlightArachne));

		allButtons.add(changeDisplay);
		allButtons.add(onlySlayer);
		allButtons.add(puzzleSolvers);
		allButtons.add(experimentationTableSolvers);
		allButtons.add(skillTracker);
		allButtons.add(outlineText);
		allButtons.add(pickBlock);
		allButtons.add(coords);
		allButtons.add(chatMaddox);
		allButtons.add(cakeTimer);
		allButtons.add(skill50Display);
		allButtons.add(slayerCount);
		allButtons.add(aotd);
		allButtons.add(lividDagger);
		allButtons.add(spiritBearAlert);
		allButtons.add(sceptreMessages);
		allButtons.add(midasStaffMessages);
		allButtons.add(implosionMessages);
		allButtons.add(healMessages);
		allButtons.add(cooldownMessages);
		allButtons.add(manaMessages);
		allButtons.add(killComboMessages);
		allButtons.add(goldenEnch);
		allButtons.add(petColours);
		allButtons.add(expertiseLore);
		allButtons.add(gparty);
		allButtons.add(golemAlerts);
		allButtons.add(rngesusAlert);
		allButtons.add(splitFishing);
		allButtons.add(lowHealthNotify);
		allButtons.add(lividSolver);
		allButtons.add(dungeonTimer);
		allButtons.add(stopSalvageStarred);
		allButtons.add(watcherReadyMessage);
		allButtons.add(flowerWeapons);
		allButtons.add(notifySlayerSlain);
		allButtons.add(necronNotifications);
		allButtons.add(bonzoTimer);
		allButtons.add(autoSkillTracker);
		allButtons.add(dungeonBossMusic);
		allButtons.add(shadowFury);
		allButtons.add(specialHoe);
		allButtons.add(melodyTooltips);
		allButtons.add(highlightArachne);

		search.setText(initSearchText);
		search.setVisible(true);
		search.setEnabled(true);

		reInit();
	}

	public void reInit() {
		this.buttonList.clear();
		foundButtons.clear();

		for (GuiButton button : allButtons) {
			if (search.getText().length() != 0) {
				String buttonName = StringUtils.stripControlCodes(button.displayString.toLowerCase());
				if (buttonName.contains(search.getText().toLowerCase())) {
					foundButtons.add(button);
				}
			} else {
				foundButtons.add(button);
			}
		}

		for (int i = (page - 1) * 7, iteration = 0; iteration < 7 && i < foundButtons.size(); i++, iteration++) {
			GuiButton button = foundButtons.get(i);
			button.xPosition = width / 2 - 100;
			button.yPosition = (int) (height * (0.1 * (iteration + 1)));
			this.buttonList.add(button);
		}

		if (page > 1) this.buttonList.add(backPage);
		if (page < Math.ceil(foundButtons.size() / 7D)) this.buttonList.add(nextPage);

		this.buttonList.add(githubLink);
		this.buttonList.add(discordLink);
		this.buttonList.add(closeGUI);
		this.buttonList.add(editLocations);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		String pageText = "Page: " + page + "/" + (int) Math.ceil(foundButtons.size() / 7D);
		int pageWidth = mc.fontRendererObj.getStringWidth(pageText);
		new TextRenderer(mc, pageText, width / 2 - pageWidth / 2, 10, 1D);

		search.drawTextBox();
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if (button == closeGUI) {
			Minecraft.getMinecraft().thePlayer.closeScreen();
		} else if (button == nextPage) {
			mc.displayGuiScreen(new DankerGui(page + 1, search.getText()));
		} else if (button == backPage) {
			mc.displayGuiScreen(new DankerGui(page - 1, search.getText()));
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
		} else if (button == shadowFury) {
			ToggleCommand.shadowFuryToggled = !ToggleCommand.shadowFuryToggled;
			ConfigHandler.writeBooleanConfig("toggles", "ShadowFury", ToggleCommand.shadowFuryToggled);
			shadowFury.displayString = "Block Shadow Fury ability: " + Utils.getColouredBoolean(ToggleCommand.shadowFuryToggled);
		} else if (button == specialHoe) {
			ToggleCommand.specialHoeRightClick = !ToggleCommand.specialHoeRightClick;
			ConfigHandler.writeBooleanConfig("toggles", "SpecialHoe", ToggleCommand.specialHoeRightClick);
			specialHoe.displayString = "Block Special Hoe right click: " + Utils.getColouredBoolean(ToggleCommand.specialHoeRightClick);
		} else if (button == melodyTooltips) {
			ToggleCommand.melodyTooltips = !ToggleCommand.melodyTooltips;
			ConfigHandler.writeBooleanConfig("toggles", "MelodyTooltips", ToggleCommand.melodyTooltips);
			melodyTooltips.displayString = "Hide tooltips in Melody's Harp: " + Utils.getColouredBoolean(ToggleCommand.melodyTooltips);
		} else if (button == killComboMessages) {
			ToggleCommand.killComboMessages = !ToggleCommand.killComboMessages;
			ConfigHandler.writeBooleanConfig("toggles", "KillComboMessages", ToggleCommand.killComboMessages);
			killComboMessages.displayString = "Kill Combo Messages: " + Utils.getColouredBoolean(ToggleCommand.killComboMessages);
		} else if (button == highlightArachne) {
			ToggleCommand.highlightArachne = !ToggleCommand.highlightArachne;
			ConfigHandler.writeBooleanConfig("toggles", "HighlightArachne", ToggleCommand.highlightArachne);
			highlightArachne.displayString = "Highlight Arachne: " + Utils.getColouredBoolean(ToggleCommand.highlightArachne);
		} else if (button == dungeonBossMusic) {
			ToggleCommand.dungeonBossMusic = !ToggleCommand.dungeonBossMusic;
			if (CustomMusic.dungeonboss != null) {
				if (ToggleCommand.dungeonBossMusic) {
					CustomMusic.start();
				} else {
					CustomMusic.dungeonboss.stop();
				}
			}
			ConfigHandler.writeBooleanConfig("toggles", "DungeonBossMusic", ToggleCommand.dungeonBossMusic);
			dungeonBossMusic.displayString = "Custom Dungeon Boss Music: " + Utils.getColouredBoolean(ToggleCommand.dungeonBossMusic);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		search.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		search.textboxKeyTyped(typedChar, keyCode);
		reInit();
	}
	
}
