package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
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
		return "/" + getCommandName() + "<zombie/spider/wolf/enderman/blaze/fishing/mythological/catacombs/confirm/cancel>";
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
			return getListOfStringsMatchingLastWord(args, "zombie", "spider", "wolf", "enderman", "blaze", "fishing", "mythological", "catacombs");
		}
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: /resetloot <zombie/spider/wolf/enderman/blaze/fishing/mythological/catacombs>"));
			return;
		}
		
		if (confirmReset) {
			switch (arg1[0].toLowerCase()) {
				case "confirm":
					confirmReset = false;
					player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Resetting " + resetOption + " tracker..."));
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
					player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Reset complete."));
					break;
				case "cancel":
					confirmReset = false;
					player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Reset cancelled."));
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
				case "fishing":
				case "mythological":
				case "catacombs":
					resetOption = arg1[0];
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Are you sure you want to reset the " + resetOption + " tracker?" + 
																" Confirm with " + DankersSkyblockMod.MAIN_COLOUR + "/resetloot confirm" + EnumChatFormatting.YELLOW + "." +
																" Cancel by using " + DankersSkyblockMod.MAIN_COLOUR + "/resetloot cancel" + EnumChatFormatting.YELLOW + "."));
					confirmReset = true;
					break;
				case "confirm":
				case "cancel":
					player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Pick something to reset first."));
					break;
				default:
					player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
			}
		}
	}
	
	static void resetZombie() {		
		ZombieTracker.zombieRevsSession = 0;
		ZombieTracker.zombieRevFleshSession = 0;
		ZombieTracker.zombieRevVisceraSession = 0;
		ZombieTracker.zombieFoulFleshSession = 0;
		ZombieTracker.zombieFoulFleshDropsSession = 0;
		ZombieTracker.zombiePestilencesSession = 0;
		ZombieTracker.zombieUndeadCatasSession = 0;
		ZombieTracker.zombieBooksSession = 0;
		ZombieTracker.zombieBooksT7Session = 0;
		ZombieTracker.zombieBeheadedsSession = 0;
		ZombieTracker.zombieRevCatasSession = 0;
		ZombieTracker.zombieSnakesSession = 0;
		ZombieTracker.zombieScythesSession = 0;
		ZombieTracker.zombieTimeSession = -1;
		ZombieTracker.zombieBossesSession = -1;
		ConfigHandler.deleteCategory("zombie");
		ConfigHandler.reloadConfig();
	}
	
	static void resetSpider() {
		SpiderTracker.spiderTarantulasSession = 0;
		SpiderTracker.spiderWebsSession = 0;
		SpiderTracker.spiderTAPSession = 0;
		SpiderTracker.spiderTAPDropsSession = 0;
		SpiderTracker.spiderBitesSession = 0;
		SpiderTracker.spiderCatalystsSession = 0;
		SpiderTracker.spiderBooksSession = 0;
		SpiderTracker.spiderSwattersSession = 0;
		SpiderTracker.spiderTalismansSession = 0;
		SpiderTracker.spiderMosquitosSession = 0;
		SpiderTracker.spiderTimeSession = -1;
		SpiderTracker.spiderBossesSession = -1;
		ConfigHandler.deleteCategory("spider");
		ConfigHandler.reloadConfig();
	}
	
	static void resetWolf() {
		WolfTracker.wolfSvensSession = 0;
		WolfTracker.wolfTeethSession = 0;
		WolfTracker.wolfWheelsSession = 0;
		WolfTracker.wolfWheelsDropsSession = 0;
		WolfTracker.wolfSpiritsSession = 0;
		WolfTracker.wolfBooksSession = 0;
		WolfTracker.wolfEggsSession = 0;
		WolfTracker.wolfCouturesSession = 0;
		WolfTracker.wolfBaitsSession = 0;
		WolfTracker.wolfFluxesSession = 0;
		WolfTracker.wolfTimeSession = -1;
		WolfTracker.wolfBossesSession = -1;
		ConfigHandler.deleteCategory("wolf");
		ConfigHandler.reloadConfig();
	}

	static void resetEnderman() {
		EndermanTracker.endermanVoidgloomsSession = 0;
		EndermanTracker.endermanNullSpheresSession = 0;
		EndermanTracker.endermanTAPSession = 0;
		EndermanTracker.endermanTAPDropsSession = 0;
		EndermanTracker.endermanEndersnakesSession = 0;
		EndermanTracker.endermanSummoningEyesSession = 0;
		EndermanTracker.endermanManaBooksSession = 0;
		EndermanTracker.endermanTunersSession = 0;
		EndermanTracker.endermanAtomsSession = 0;
		EndermanTracker.endermanEspressoMachinesSession = 0;
		EndermanTracker.endermanSmartyBooksSession = 0;
		EndermanTracker.endermanEndRunesSession = 0;
		EndermanTracker.endermanChalicesSession = 0;
		EndermanTracker.endermanDiceSession = 0;
		EndermanTracker.endermanArtifactsSession = 0;
		EndermanTracker.endermanSkinsSession = 0;
		EndermanTracker.endermanMergersSession = 0;
		EndermanTracker.endermanCoresSession = 0;
		EndermanTracker.endermanEnchantRunesSession = 0;
		EndermanTracker.endermanEnderBooksSession = 0;
		EndermanTracker.endermanTimeSession = -1;
		EndermanTracker.endermanBossesSession = -1;
		ConfigHandler.deleteCategory("enderman");
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
		BlazeTracker.timeSession = 0;
		BlazeTracker.bossesSession = 0;
		ConfigHandler.deleteCategory("blaze");
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
		FishingTracker.yetisSession = 0;
		FishingTracker.yetiTimeSession = -1;
		FishingTracker.yetiSCsSession = -1;
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
		FishingTracker.magmaSlugsSession = 0;
		FishingTracker.moogmasSession = 0;
		FishingTracker.lavaLeechesSession = 0;
		FishingTracker.pyroclasticWormsSession = 0;
		FishingTracker.lavaFlamesSession = 0;
		FishingTracker.fireEelsSession = 0;
		FishingTracker.taurusesSession = 0;
		FishingTracker.thundersSession = 0;
		FishingTracker.lordJawbusesSession = 0;
		FishingTracker.jawbusTimeSession = 0;
		FishingTracker.jawbusSCsSession = 0;
		ConfigHandler.deleteCategory("fishing");
		ConfigHandler.reloadConfig();
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
		ConfigHandler.deleteCategory("mythological");
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
		ConfigHandler.deleteCategory("catacombs");
		ConfigHandler.reloadConfig();
	}

}
