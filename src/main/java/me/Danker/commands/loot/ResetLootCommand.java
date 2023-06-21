package me.Danker.commands.loot;

import me.Danker.config.CfgConfig;
import me.Danker.config.ModConfig;
import me.Danker.features.loot.*;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class ResetLootCommand extends CommandBase {

	static String resetOption;
	static boolean confirmReset = false;
	
	@Override
	public String getCommandName() {
		return "resetloot";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + "<zombie/spider/wolf/enderman/blaze/vampire/fishing/mythological/catacombs/confirm/cancel>";
	}

	public static String usage(ICommandSender arg0) {
		return new ResetLootCommand().getCommandUsage(arg0);
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length != 1) return null;
		
		if (confirmReset) {
			return getListOfStringsMatchingLastWord(args, "confirm", "cancel");
		} else {
			return getListOfStringsMatchingLastWord(args, "zombie", "spider", "wolf", "enderman", "blaze", "vampire", "fishing", "mythological", "catacombs");
		}
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: /resetloot <zombie/spider/wolf/enderman/blaze/vampire/fishing/mythological/catacombs>"));
			return;
		}
		
		if (confirmReset) {
			switch (arg1[0].toLowerCase()) {
				case "confirm":
					confirmReset = false;
					player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Resetting " + resetOption + " tracker..."));
					switch (resetOption.toLowerCase()) {
						case "zombie":
							resetZombie();
							break;
						case "spider":
							resetSpider();
							break;
						case "wolf":
							resetWolf();
							break;
						case "enderman":
							resetEnderman();
							break;
						case "blaze":
							resetBlaze();
							break;
						case "vampire":
							resetVampire();
							break;
						case "fishing":
							resetFishing();
							break;
						case "mythological":
							resetMythological();
						case "catacombs":
							resetCatacombs();
						default:
							System.err.println("Resetting unknown tracker.");
							return;
					}
					player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Reset complete."));
					break;
				case "cancel":
					confirmReset = false;
					player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Reset cancelled."));
					break;
				default:
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Please confirm the reset of the " + resetOption + " tracker by using /resetloot confirm." +
																EnumChatFormatting.RED + " Cancel by using /resetloot cancel."));
			}
		} else {
			switch (arg1[0].toLowerCase()) {
				case "zombie":
				case "spider":
				case "wolf":
				case "enderman":
				case "blaze":
				case "vampire":
				case "fishing":
				case "mythological":
				case "catacombs":
					resetOption = arg1[0];
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Are you sure you want to reset the " + resetOption + " tracker?" + 
																" Confirm with " + ModConfig.getColour(ModConfig.mainColour) + "/resetloot confirm" + EnumChatFormatting.YELLOW + "." +
																" Cancel by using " + ModConfig.getColour(ModConfig.mainColour) + "/resetloot cancel" + EnumChatFormatting.YELLOW + "."));
					confirmReset = true;
					break;
				case "confirm":
				case "cancel":
					player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Pick something to reset first."));
					break;
				default:
					player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: " + getCommandUsage(arg0)));
			}
		}
	}
	
	static void resetZombie() {		
		ZombieTracker.revsSession = 0;
		ZombieTracker.revFleshSession = 0;
		ZombieTracker.revVisceraSession = 0;
		ZombieTracker.foulFleshSession = 0;
		ZombieTracker.foulFleshDropsSession = 0;
		ZombieTracker.pestilencesSession = 0;
		ZombieTracker.undeadCatasSession = 0;
		ZombieTracker.booksSession = 0;
		ZombieTracker.booksT7Session = 0;
		ZombieTracker.beheadedsSession = 0;
		ZombieTracker.revCatasSession = 0;
		ZombieTracker.snakesSession = 0;
		ZombieTracker.scythesSession = 0;
		ZombieTracker.timeSession = -1;
		ZombieTracker.bossesSession = -1;
		CfgConfig.deleteCategory("zombie");
		ConfigHandler.reloadConfig();
	}
	
	static void resetSpider() {
		SpiderTracker.tarantulasSession = 0;
		SpiderTracker.websSession = 0;
		SpiderTracker.TAPSession = 0;
		SpiderTracker.TAPDropsSession = 0;
		SpiderTracker.bitesSession = 0;
		SpiderTracker.catalystsSession = 0;
		SpiderTracker.booksSession = 0;
		SpiderTracker.swattersSession = 0;
		SpiderTracker.talismansSession = 0;
		SpiderTracker.mosquitosSession = 0;
		SpiderTracker.timeSession = -1;
		SpiderTracker.bossesSession = -1;
		CfgConfig.deleteCategory("spider");
		ConfigHandler.reloadConfig();
	}
	
	static void resetWolf() {
		WolfTracker.svensSession = 0;
		WolfTracker.teethSession = 0;
		WolfTracker.wheelsSession = 0;
		WolfTracker.wheelsDropsSession = 0;
		WolfTracker.spiritsSession = 0;
		WolfTracker.booksSession = 0;
		WolfTracker.furballsSession = 0;
		WolfTracker.eggsSession = 0;
		WolfTracker.couturesSession = 0;
		WolfTracker.baitsSession = 0;
		WolfTracker.fluxesSession = 0;
		WolfTracker.timeSession = -1;
		WolfTracker.bossesSession = -1;
		CfgConfig.deleteCategory("wolf");
		ConfigHandler.reloadConfig();
	}

	static void resetEnderman() {
		EndermanTracker.voidgloomsSession = 0;
		EndermanTracker.nullSpheresSession = 0;
		EndermanTracker.TAPSession = 0;
		EndermanTracker.TAPDropsSession = 0;
		EndermanTracker.endersnakesSession = 0;
		EndermanTracker.summoningEyesSession = 0;
		EndermanTracker.manaBooksSession = 0;
		EndermanTracker.tunersSession = 0;
		EndermanTracker.atomsSession = 0;
		EndermanTracker.hazmatsSession = 0;
		EndermanTracker.espressoMachinesSession = 0;
		EndermanTracker.smartyBooksSession = 0;
		EndermanTracker.endRunesSession = 0;
		EndermanTracker.chalicesSession = 0;
		EndermanTracker.diceSession = 0;
		EndermanTracker.artifactsSession = 0;
		EndermanTracker.skinsSession = 0;
		EndermanTracker.mergersSession = 0;
		EndermanTracker.coresSession = 0;
		EndermanTracker.enchantRunesSession = 0;
		EndermanTracker.enderBooksSession = 0;
		EndermanTracker.timeSession = -1;
		EndermanTracker.bossesSession = -1;
		CfgConfig.deleteCategory("enderman");
		ConfigHandler.reloadConfig();
	}

	static void resetBlaze() {
		BlazeTracker.demonlordsSession = 0;
		BlazeTracker.derelictAshesSession = 0;
		BlazeTracker.lavatearRunesSession = 0;
		BlazeTracker.splashPotionsSession = 0;
		BlazeTracker.magmaArrowsSession = 0;
		BlazeTracker.manaDisintegratorsSession = 0;
		BlazeTracker.scorchedBooksSession = 0;
		BlazeTracker.kelvinInvertersSession = 0;
		BlazeTracker.blazeRodDistillatesSession = 0;
		BlazeTracker.glowstoneDistillatesSession = 0;
		BlazeTracker.magmaCreamDistillatesSession = 0;
		BlazeTracker.netherWartDistillatesSession = 0;
		BlazeTracker.gabagoolDistillatesSession = 0;
		BlazeTracker.scorchedPowerCrystalsSession = 0;
		BlazeTracker.fireAspectBooksSession = 0;
		BlazeTracker.fieryBurstRunesSession = 0;
		BlazeTracker.opalGemsSession = 0;
		BlazeTracker.archfiendDiceSession = 0;
		BlazeTracker.duplexBooksSession = 0;
		BlazeTracker.highClassArchfiendDiceSession = 0;
		BlazeTracker.engineeringPlansSession = 0;
		BlazeTracker.subzeroInvertersSession = 0;
		BlazeTracker.timeSession = -1;
		BlazeTracker.bossesSession = -1;
		CfgConfig.deleteCategory("blaze");
		ConfigHandler.reloadConfig();
	}

	static void resetVampire() {
		VampireTracker.riftstalkersSession = 0;
		VampireTracker.covenSealsSession = 0;
		VampireTracker.quantumBundlesSession = 0;
		VampireTracker.bubbaBlistersSession = 0;
		VampireTracker.soultwistRunesSession = 0;
		VampireTracker.chocolateChipsSession = 0;
		VampireTracker.luckyBlocksSession = 0;
		VampireTracker.theOneBundlesSession = 0;
		VampireTracker.mcgrubbersBurgersSession = 0;
		VampireTracker.vampirePartsSession = 0;
		CfgConfig.deleteCategory("vampire");
		ConfigHandler.reloadConfig();
	}

	static void resetFishing() {
		FishingTracker.seaCreaturesSession = 0;
		FishingTracker.goodCatchesSession = 0;
		FishingTracker.greatCatchesSession = 0;
		FishingTracker.squidsSession = 0;
		FishingTracker.seaWalkersSession = 0;
		FishingTracker.nightSquidsSession = 0;
		FishingTracker.seaGuardiansSession = 0;
		FishingTracker.seaWitchesSession = 0;
		FishingTracker.seaArchersSession = 0;
		FishingTracker.monsterOfTheDeepsSession = 0;
		FishingTracker.catfishesSession = 0;
		FishingTracker.carrotKingsSession = 0;
		FishingTracker.seaLeechesSession = 0;
		FishingTracker.guardianDefendersSession = 0;
		FishingTracker.deepSeaProtectorsSession = 0;
		FishingTracker.hydrasSession = 0;
		FishingTracker.seaEmperorsSession = 0;
		FishingTracker.empTimeSession = -1;
		FishingTracker.empSCsSession = -1;
		FishingTracker.fishingMilestoneSession = 0;
		FishingTracker.frozenStevesSession = 0;
		FishingTracker.frostyTheSnowmansSession = 0;
		FishingTracker.grinchesSession = 0;
		FishingTracker.nutcrackersSession = 0;
		FishingTracker.yetisSession = 0;
		FishingTracker.reindrakesSession = 0;
		FishingTracker.reindrakeTimeSession = -1;
		FishingTracker.reindrakeSCsSession = -1;
		FishingTracker.nurseSharksSession = 0;
		FishingTracker.blueSharksSession = 0;
		FishingTracker.tigerSharksSession = 0;
		FishingTracker.greatWhiteSharksSession = 0;
		FishingTracker.scarecrowsSession = 0;
		FishingTracker.nightmaresSession = 0;
		FishingTracker.werewolfsSession = 0;
		FishingTracker.phantomFishersSession = 0;
		FishingTracker.grimReapersSession = 0;
		FishingTracker.waterWormsSession = 0;
		FishingTracker.poisonedWaterWormsSession = 0;
		FishingTracker.flamingWormsSession = 0;
		FishingTracker.lavaBlazesSession = 0;
		FishingTracker.lavaPigmenSession = 0;
		FishingTracker.zombieMinersSession = 0;
		FishingTracker.plhlegblastsSession = 0;
		FishingTracker.magmaSlugsSession = 0;
		FishingTracker.moogmasSession = 0;
		FishingTracker.lavaLeechesSession = 0;
		FishingTracker.pyroclasticWormsSession = 0;
		FishingTracker.lavaFlamesSession = 0;
		FishingTracker.fireEelsSession = 0;
		FishingTracker.taurusesSession = 0;
		FishingTracker.thundersSession = 0;
		FishingTracker.lordJawbusesSession = 0;
		FishingTracker.jawbusTimeSession = -1;
		FishingTracker.jawbusSCsSession = -1;
		CfgConfig.deleteCategory("fishing");
		ConfigHandler.reloadConfig();

		TrophyFishTracker.fish = TrophyFishTracker.createEmpty();
		TrophyFishTracker.fishSession = TrophyFishTracker.createEmpty();
		TrophyFishTracker.save();
	}
	
	static void resetMythological() {
		MythologicalTracker.mythCoinsSession = 0;
		MythologicalTracker.griffinFeathersSession = 0;
		MythologicalTracker.crownOfGreedsSession = 0;
		MythologicalTracker.washedUpSouvenirsSession = 0;
		MythologicalTracker.minosHuntersSession = 0;
		MythologicalTracker.siameseLynxesSession = 0;
		MythologicalTracker.minotaursSession = 0;
		MythologicalTracker.gaiaConstructsSession = 0;
		MythologicalTracker.minosChampionsSession = 0;
		MythologicalTracker.minosInquisitorsSession = 0;
		CfgConfig.deleteCategory("mythological");
		ConfigHandler.reloadConfig();
	}
	
	static void resetCatacombs() {
		CatacombsTracker.recombobulatorsSession = 0;
		CatacombsTracker.fumingPotatoBooksSession = 0;
		CatacombsTracker.f1SPlusSession = 0;
		CatacombsTracker.bonzoStaffsSession = 0;
		CatacombsTracker.f1CoinsSpentSession = 0;
		CatacombsTracker.f1TimeSpentSession = 0;
		CatacombsTracker.f2SPlusSession = 0;
		CatacombsTracker.scarfStudiesSession = 0;
		CatacombsTracker.f2CoinsSpentSession = 0;
		CatacombsTracker.f2TimeSpentSession = 0;
		CatacombsTracker.f3SPlusSession = 0;
		CatacombsTracker.adaptiveHelmsSession = 0;
		CatacombsTracker.adaptiveChestsSession = 0;
		CatacombsTracker.adaptiveLegsSession = 0;
		CatacombsTracker.adaptiveBootsSession = 0;
		CatacombsTracker.adaptiveSwordsSession = 0;
		CatacombsTracker.f3CoinsSpentSession = 0;
		CatacombsTracker.f3TimeSpentSession = 0;
		CatacombsTracker.f4SPlusSession = 0;
		CatacombsTracker.spiritWingsSession = 0;
		CatacombsTracker.spiritBonesSession = 0;
		CatacombsTracker.spiritBootsSession = 0;
		CatacombsTracker.spiritSwordsSession = 0;
		CatacombsTracker.epicSpiritPetsSession = 0;
		CatacombsTracker.f4CoinsSpentSession = 0;
		CatacombsTracker.f4TimeSpentSession = 0;
		CatacombsTracker.f5SPlusSession = 0;
		CatacombsTracker.warpedStonesSession = 0;
		CatacombsTracker.shadowAssHelmsSession = 0;
		CatacombsTracker.shadowAssChestsSession = 0;
		CatacombsTracker.shadowAssLegsSession = 0;
		CatacombsTracker.shadowAssBootsSession = 0;
		CatacombsTracker.lividDaggersSession = 0;
		CatacombsTracker.shadowFurysSession = 0;
		CatacombsTracker.f5CoinsSpentSession = 0;
		CatacombsTracker.f5TimeSpentSession = 0;
		CatacombsTracker.f6SPlusSession = 0;
		CatacombsTracker.ancientRosesSession = 0;
		CatacombsTracker.precursorEyesSession = 0;
		CatacombsTracker.giantsSwordsSession = 0;
		CatacombsTracker.necroLordHelmsSession = 0;
		CatacombsTracker.necroLordChestsSession = 0;
		CatacombsTracker.necroLordLegsSession = 0;
		CatacombsTracker.necroLordBootsSession = 0;
		CatacombsTracker.necroSwordsSession = 0;
		CatacombsTracker.f6RerollsSession = 0;
		CatacombsTracker.f6CoinsSpentSession = 0;
		CatacombsTracker.f6TimeSpentSession = 0;
		CatacombsTracker.f7SPlusSession = 0;
		CatacombsTracker.witherBloodsSession = 0;
		CatacombsTracker.witherCloaksSession = 0;
		CatacombsTracker.implosionsSession = 0;
		CatacombsTracker.witherShieldsSession = 0;
		CatacombsTracker.shadowWarpsSession = 0;
		CatacombsTracker.necronsHandlesSession = 0;
		CatacombsTracker.autoRecombsSession = 0;
		CatacombsTracker.witherHelmsSession = 0;
		CatacombsTracker.witherChestsSession = 0;
		CatacombsTracker.witherLegsSession = 0;
		CatacombsTracker.witherBootsSession = 0;
		CatacombsTracker.f7RerollsSession = 0;
		CatacombsTracker.f7CoinsSpentSession = 0;
		CatacombsTracker.f7TimeSpentSession = 0;
		CatacombsTracker.m1SSession = 0;
		CatacombsTracker.m1SPlusSession = 0;
		CatacombsTracker.m2SSession = 0;
		CatacombsTracker.m2SPlusSession = 0;
		CatacombsTracker.m3SSession = 0;
		CatacombsTracker.m3SPlusSession = 0;
		CatacombsTracker.m4SSession = 0;
		CatacombsTracker.m4SPlusSession = 0;
		CatacombsTracker.m5SSession = 0;
		CatacombsTracker.m5SPlusSession = 0;
		CatacombsTracker.m6SSession = 0;
		CatacombsTracker.m6SPlusSession = 0;
		CatacombsTracker.m7SSession = 0;
		CatacombsTracker.m7SPlusSession = 0;
		CatacombsTracker.firstStarsSession = 0;
		CatacombsTracker.secondStarsSession = 0;
		CatacombsTracker.thirdStarsSession = 0;
		CatacombsTracker.fourthStarsSession = 0;
		CatacombsTracker.fifthStarsSession = 0;
		CatacombsTracker.darkClaymoresSession = 0;
		CatacombsTracker.masterRerollsSession = 0;
		CatacombsTracker.masterCoinsSpentSession = 0;
		CatacombsTracker.masterTimeSpentSession = 0;
		CfgConfig.deleteCategory("catacombs");
		ConfigHandler.reloadConfig();
	}

}
