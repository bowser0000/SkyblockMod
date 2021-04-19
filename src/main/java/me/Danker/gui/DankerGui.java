package me.Danker.gui;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.gui.buttons.FeatureButton;
import me.Danker.handlers.ConfigHandler;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
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
	private GuiButton puzzleSolvers;
	private GuiButton experimentationTableSolvers;
	private GuiButton skillTracker;
	private GuiButton customMusic;
	// Toggles
	private GuiButton gparty;
	private GuiButton coords;
	private GuiButton goldenEnch;
	private GuiButton slayerCount;
	private GuiButton rngesusAlert;
	private GuiButton splitFishing;
	private GuiButton chatMaddox;
	private GuiButton spiritBearAlert;
	private GuiButton petColours;
	private GuiButton golemAlerts;
	private GuiButton expertiseLore;
	private GuiButton skill50Display;
	private GuiButton outlineText;
	private GuiButton cakeTimer;
	private GuiButton pickBlock;
	private GuiButton notifySlayerSlain;
	private GuiButton melodyTooltips;
	private GuiButton autoSkillTracker;
	private GuiButton highlightArachne;
	private GuiButton highlightSlayer;
	private GuiButton highlightSkeletonMasters;
	private GuiButton teammatesInRadius;
	// Chat Messages
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
	private GuiButton necronNotifications;
	private GuiButton bonzoTimer;

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

		changeDisplay = new GuiButton(0, 0, 0, "Change Display Settings");
		puzzleSolvers = new GuiButton(0, 0, 0, "Toggle Dungeons Puzzle Solvers");
		experimentationTableSolvers = new GuiButton(0, 0, 0, "Toggle Experimentation Table Solvers");
		skillTracker = new GuiButton(0, 0, 0, "Toggle Skill XP/Hour Tracking");
		customMusic = new GuiButton(0, 0, 0, "Custom Music");
		outlineText = new FeatureButton("Outline Displayed Text: " + Utils.getColouredBoolean(ToggleCommand.outlineTextToggled), "Adds bold outline to on-screen text.");
		pickBlock = new FeatureButton("Auto-Swap to Pick Block: " + Utils.getColouredBoolean(ToggleCommand.swapToPickBlockToggled), "Automatically changes left clicks to middle clicks.\nHelpful when lagging.");
		coords = new FeatureButton("Coordinate/Angle Display: " + Utils.getColouredBoolean(ToggleCommand.coordsToggled), "Displays coordinates and angle.");
		chatMaddox = new FeatureButton("Click On-Screen to Open Maddox: " + Utils.getColouredBoolean(ToggleCommand.chatMaddoxToggled), "Open chat then click anywhere after calling Maddox to open the menu.");
		cakeTimer = new FeatureButton("Cake Timer: " + Utils.getColouredBoolean(ToggleCommand.cakeTimerToggled), "Displays time until century cake buffs run out.");
		skill50Display = new FeatureButton("Display Progress To Skill Level 50: " + Utils.getColouredBoolean(ToggleCommand.skill50DisplayToggled), "Display total progress to max skill level.");
		slayerCount = new FeatureButton("Count Total 20% Drops: " + Utils.getColouredBoolean(ToggleCommand.slayerCountTotal), "Counts times dropped instead of amount dropped.\nE.x. Hamster Wheels: 40 -> Hamster Wheels: 10 times.");
		spiritBearAlert = new FeatureButton("Spirit Bear Spawn Alerts: " + Utils.getColouredBoolean(ToggleCommand.spiritBearAlerts), "Alert when Spirit Bear spawns.");
		sceptreMessages = new FeatureButton("Spirit Sceptre Messages: " + Utils.getColouredBoolean(ToggleCommand.sceptreMessages), "Turn " + EnumChatFormatting.RED + "off" + EnumChatFormatting.RESET + " to hide Spirit Sceptre messages.");
		midasStaffMessages = new FeatureButton("Midas Staff Messages: " + Utils.getColouredBoolean(ToggleCommand.midasStaffMessages), "Turn " + EnumChatFormatting.RED + "off" + EnumChatFormatting.RESET + " to hide Midas Staff messages.");
		implosionMessages = new FeatureButton("Implosion Messages: " + Utils.getColouredBoolean(ToggleCommand.implosionMessages), "Turn " + EnumChatFormatting.RED + "off" + EnumChatFormatting.RESET + " to hide Implosion messages.");
		healMessages = new FeatureButton("Heal Messages: " + Utils.getColouredBoolean(ToggleCommand.healMessages), "Turn " + EnumChatFormatting.RED + "off" + EnumChatFormatting.RESET + " to hide healing messages.");
		cooldownMessages = new FeatureButton("Cooldown Messages: " + Utils.getColouredBoolean(ToggleCommand.cooldownMessages), "Turn " + EnumChatFormatting.RED + "off" + EnumChatFormatting.RESET + " to hide cooldown messages.");
		manaMessages = new FeatureButton("Mana Messages: " + Utils.getColouredBoolean(ToggleCommand.manaMessages), "Turn " + EnumChatFormatting.RED + "off" + EnumChatFormatting.RESET + " to hide out of mana messages.");
		killComboMessages = new FeatureButton("Kill Combo Messages: " + Utils.getColouredBoolean(ToggleCommand.killComboMessages), "Turn " + EnumChatFormatting.RED + "off" + EnumChatFormatting.RESET + " to hide kill combo messages.");
		goldenEnch = new FeatureButton("Golden T10/T6/T4 Enchantments: " + Utils.getColouredBoolean(ToggleCommand.goldenToggled), "Turns expensive enchants golden in tooltips.");
		petColours = new FeatureButton("Colour Pet Backgrounds: " + Utils.getColouredBoolean(ToggleCommand.petColoursToggled), "Colors pets based on their level.");
		expertiseLore = new FeatureButton("Expertise Kills In Lore: " + Utils.getColouredBoolean(ToggleCommand.expertiseLoreToggled), "Adds expertise kills to fishing rod tooltip.");
		gparty = new FeatureButton("Guild Party Notifications: " + Utils.getColouredBoolean(ToggleCommand.gpartyToggled), "Creates desktop notification on guild party.");
		golemAlerts = new FeatureButton("Golem Spawn Alert And Timer: " + Utils.getColouredBoolean(ToggleCommand.golemAlertToggled), "Creates alert with 20s countdown when golem is spawning.");
		rngesusAlert = new FeatureButton("RNGesus Alerts: " + Utils.getColouredBoolean(ToggleCommand.rngesusAlerts), "Alerts when an RNGesus item is dropped.");
		splitFishing = new FeatureButton("Split Fishing Display: " + Utils.getColouredBoolean(ToggleCommand.splitFishing), "Splits fishing display in half to save vertical space.");
		lowHealthNotify = new FeatureButton("Low Health Notifications: " + Utils.getColouredBoolean(ToggleCommand.lowHealthNotifyToggled), "Alerts when dungeon teammate has low health.");
		lividSolver = new FeatureButton("Find Correct Livid: " + Utils.getColouredBoolean(ToggleCommand.lividSolverToggled), "Shows health and color of correct Livid.");
		dungeonTimer = new FeatureButton("Display Dungeon Timers: " + Utils.getColouredBoolean(ToggleCommand.dungeonTimerToggled), "Displays timing of certain dungeon objectives and other information.");
		stopSalvageStarred = new FeatureButton("Stop Salvaging Starred Items: " + Utils.getColouredBoolean(ToggleCommand.stopSalvageStarredToggled), "Blocks salvaging starred items.");
		watcherReadyMessage = new FeatureButton("Display Watcher Ready Message: " + Utils.getColouredBoolean(ToggleCommand.watcherReadyToggled), "Alerts when Watcher finishes spawning mobs.");
		notifySlayerSlain = new FeatureButton("Notify when Slayer Slain: " + Utils.getColouredBoolean(ToggleCommand.notifySlayerSlainToggled), "Alerts when slayer boss has been slain.");
		necronNotifications = new FeatureButton("Necron Phase Notifications: " + Utils.getColouredBoolean(ToggleCommand.necronNotificationsToggled), "Creates alert on different phases of the Necron fight.");
		bonzoTimer = new FeatureButton("Bonzo's Mask Timer: " + Utils.getColouredBoolean(ToggleCommand.bonzoTimerToggled), "Displays cooldown of Bonzo Mask ability.");
		autoSkillTracker = new FeatureButton("Auto Start/Stop Skill Tracker: " + Utils.getColouredBoolean(ToggleCommand.autoSkillTrackerToggled), "Automatically pauses skill tracker when opening a gui.");
		melodyTooltips = new FeatureButton("Hide tooltips in Melody's Harp: " + Utils.getColouredBoolean(ToggleCommand.melodyTooltips), "Hides tooltips in Melody's Harp.");
		highlightArachne = new FeatureButton("Highlight Arachne: " + Utils.getColouredBoolean(ToggleCommand.highlightArachne), "Highlights Arachne bosses.");
		highlightSlayer = new FeatureButton("Highlight Slayer: " + Utils.getColouredBoolean(ToggleCommand.highlightSlayers), "Highlights Slayer bosses.");
		highlightSkeletonMasters = new FeatureButton("Highlight Skeleton Masters: " + Utils.getColouredBoolean(ToggleCommand.highlightSkeletonMasters), "Highlights Skeleton Masters.");
		teammatesInRadius = new FeatureButton("Display Players in 30 Block Radius: " + Utils.getColouredBoolean(ToggleCommand.teammatesInRadius), "Displays dungeon teammates in 30 block radius for tether and diversion.");

		allButtons.clear();
		allButtons.add(changeDisplay);
		allButtons.add(puzzleSolvers);
		allButtons.add(experimentationTableSolvers);
		allButtons.add(skillTracker);
		allButtons.add(customMusic);
		allButtons.add(outlineText);
		allButtons.add(pickBlock);
		allButtons.add(coords);
		allButtons.add(chatMaddox);
		allButtons.add(cakeTimer);
		allButtons.add(skill50Display);
		allButtons.add(slayerCount);
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
		allButtons.add(notifySlayerSlain);
		allButtons.add(necronNotifications);
		allButtons.add(bonzoTimer);
		allButtons.add(autoSkillTracker);
		allButtons.add(melodyTooltips);
		allButtons.add(highlightArachne);
		allButtons.add(highlightSlayer);
		allButtons.add(highlightSkeletonMasters);
		allButtons.add(teammatesInRadius);

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

		for (GuiButton button : this.buttonList) {
			if (button instanceof FeatureButton && button.isMouseOver()) {
				FeatureButton feature = (FeatureButton) button;
				drawHoveringText(feature.hoverText, mouseX - 5, mouseY);
			}
		}

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
		} else if (button == puzzleSolvers) {
			DankersSkyblockMod.guiToOpen = "puzzlesolvers";
		} else if (button == experimentationTableSolvers) {
			DankersSkyblockMod.guiToOpen = "experimentsolvers";
		} else if (button == skillTracker) {
			DankersSkyblockMod.guiToOpen = "skilltracker";
		} else if (button == customMusic) {
			DankersSkyblockMod.guiToOpen = "custommusic";
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
			golemAlerts.displayString = "Golem Spawn Alert And Timer: " + Utils.getColouredBoolean(ToggleCommand.golemAlertToggled);
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
		} else if (button == autoSkillTracker) {
			ToggleCommand.autoSkillTrackerToggled = !ToggleCommand.autoSkillTrackerToggled;
			ConfigHandler.writeBooleanConfig("toggles", "AutoSkillTracker", ToggleCommand.autoSkillTrackerToggled);
			autoSkillTracker.displayString = "Auto Start/Stop Skill Tracker: " + Utils.getColouredBoolean(ToggleCommand.autoSkillTrackerToggled);
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
		} else if (button == highlightSlayer) {
			ToggleCommand.highlightSlayers = !ToggleCommand.highlightSlayers;
			ConfigHandler.writeBooleanConfig("toggles", "HighlightSlayers", ToggleCommand.highlightSlayers);
			highlightSlayer.displayString = "Highlight Slayer: " + Utils.getColouredBoolean(ToggleCommand.highlightSlayers);
		} else if (button == highlightSkeletonMasters) {
			ToggleCommand.highlightSkeletonMasters = !ToggleCommand.highlightSkeletonMasters;
			ConfigHandler.writeBooleanConfig("toggles", "HighlightSkeletonMasters", ToggleCommand.highlightSkeletonMasters);
			highlightSkeletonMasters.displayString = "Highlight Skeleton Masters: " + Utils.getColouredBoolean(ToggleCommand.highlightSkeletonMasters);
		} else if (button == teammatesInRadius) {
			ToggleCommand.teammatesInRadius = !ToggleCommand.teammatesInRadius;
			ConfigHandler.writeBooleanConfig("toggles", "TeammatesInRadius", ToggleCommand.teammatesInRadius);
			teammatesInRadius.displayString = "Display Players in 30 Block Radius: " + Utils.getColouredBoolean(ToggleCommand.teammatesInRadius);
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
		initSearchText = search.getText();
		reInit();
	}
	
}
