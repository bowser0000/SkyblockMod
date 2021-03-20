package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.loot.LootTracker;
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
		return "/" + getCommandName() + "<zombie/spider/wolf/fishing/mythological/catacombs/confirm/cancel>";
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
			return getListOfStringsMatchingLastWord(args, "zombie", "spider", "wolf", "fishing", "mythological", "catacombs");
		}
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: /resetloot <zombie/spider/wolf/fishing/mythological/catacombs>"));
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
		LootTracker.zombieRevsSession = 0;
		LootTracker.zombieRevFleshSession = 0;
		LootTracker.zombieFoulFleshSession = 0;
		LootTracker.zombieFoulFleshDropsSession = 0;
		LootTracker.zombiePestilencesSession = 0;
		LootTracker.zombieUndeadCatasSession = 0;
		LootTracker.zombieBooksSession = 0;
		LootTracker.zombieBeheadedsSession = 0;
		LootTracker.zombieRevCatasSession = 0;
		LootTracker.zombieSnakesSession = 0;
		LootTracker.zombieScythesSession = 0;
		LootTracker.zombieTimeSession = -1;
		LootTracker.zombieBossesSession = -1;
		ConfigHandler.deleteCategory("zombie");
		ConfigHandler.reloadConfig();
	}
	
	static void resetSpider() {
		LootTracker.spiderTarantulasSession = 0;
		LootTracker.spiderWebsSession = 0;
		LootTracker.spiderTAPSession = 0;
		LootTracker.spiderTAPDropsSession = 0;
		LootTracker.spiderBitesSession = 0;
		LootTracker.spiderCatalystsSession = 0;
		LootTracker.spiderBooksSession = 0;
		LootTracker.spiderSwattersSession = 0;
		LootTracker.spiderTalismansSession = 0;
		LootTracker.spiderMosquitosSession = 0;
		LootTracker.spiderTimeSession = -1;
		LootTracker.spiderBossesSession = -1;
		ConfigHandler.deleteCategory("spider");
		ConfigHandler.reloadConfig();
	}
	
	static void resetWolf() {
		LootTracker.wolfSvensSession = 0;
		LootTracker.wolfTeethSession = 0;
		LootTracker.wolfWheelsSession = 0;
		LootTracker.wolfWheelsDropsSession = 0;
		LootTracker.wolfSpiritsSession = 0;
		LootTracker.wolfBooksSession = 0;
		LootTracker.wolfEggsSession = 0;
		LootTracker.wolfCouturesSession = 0;
		LootTracker.wolfBaitsSession = 0;
		LootTracker.wolfFluxesSession = 0;
		LootTracker.wolfTimeSession = -1;
		LootTracker.wolfBossesSession = -1;
		ConfigHandler.deleteCategory("wolf");
		ConfigHandler.reloadConfig();
	}
	
	static void resetFishing() {
		LootTracker.seaCreaturesSession = 0;
		LootTracker.goodCatchesSession = 0;
		LootTracker.greatCatchesSession = 0;
		LootTracker.squidsSession = 0;
		LootTracker.seaWalkersSession = 0;
		LootTracker.nightSquidsSession = 0;
		LootTracker.seaGuardiansSession = 0;
		LootTracker.seaWitchesSession = 0;
		LootTracker.seaArchersSession = 0;
		LootTracker.monsterOfTheDeepsSession = 0;
		LootTracker.catfishesSession = 0;
		LootTracker.carrotKingsSession = 0;
		LootTracker.seaLeechesSession = 0;
		LootTracker.guardianDefendersSession = 0;
		LootTracker.deepSeaProtectorsSession = 0;
		LootTracker.hydrasSession = 0;
		LootTracker.seaEmperorsSession = 0;
		LootTracker.empTimeSession = -1;
		LootTracker.empSCsSession = -1;
		LootTracker.fishingMilestoneSession = 0;
		LootTracker.frozenStevesSession = 0;
		LootTracker.frostyTheSnowmansSession = 0;
		LootTracker.grinchesSession = 0;
		LootTracker.yetisSession = 0;
		LootTracker.yetiTimeSession = -1;
		LootTracker.yetiSCsSession = -1;
		LootTracker.nurseSharksSession = 0;
		LootTracker.blueSharksSession = 0;
		LootTracker.tigerSharksSession = 0;
		LootTracker.greatWhiteSharksSession = 0;
		LootTracker.scarecrowsSession = 0;
		LootTracker.nightmaresSession = 0;
		LootTracker.werewolfsSession = 0;
		LootTracker.phantomFishersSession = 0;
		LootTracker.grimReapersSession = 0;
		ConfigHandler.deleteCategory("fishing");
		ConfigHandler.reloadConfig();
	}
	
	static void resetMythological() {
		LootTracker.mythCoinsSession = 0;
		LootTracker.griffinFeathersSession = 0;
		LootTracker.crownOfGreedsSession = 0;
		LootTracker.washedUpSouvenirsSession = 0;
		LootTracker.minosHuntersSession = 0;
		LootTracker.siameseLynxesSession = 0;
		LootTracker.minotaursSession = 0;
		LootTracker.gaiaConstructsSession = 0;
		LootTracker.minosChampionsSession = 0;
		LootTracker.minosInquisitorsSession = 0;
		ConfigHandler.deleteCategory("mythological");
		ConfigHandler.reloadConfig();
	}
	
	static void resetCatacombs() {
		LootTracker.recombobulatorsSession = 0;
		LootTracker.fumingPotatoBooksSession = 0;
		LootTracker.bonzoStaffsSession = 0;
		LootTracker.f1CoinsSpentSession = 0;
		LootTracker.f1TimeSpentSession = 0;
		LootTracker.scarfStudiesSession = 0;
		LootTracker.f2CoinsSpentSession = 0;
		LootTracker.f2TimeSpentSession = 0;
		LootTracker.adaptiveHelmsSession = 0;
		LootTracker.adaptiveChestsSession = 0;
		LootTracker.adaptiveLegsSession = 0;
		LootTracker.adaptiveBootsSession = 0;
		LootTracker.adaptiveSwordsSession = 0;
		LootTracker.f3CoinsSpentSession = 0;
		LootTracker.f3TimeSpentSession = 0;
		LootTracker.spiritWingsSession = 0;
		LootTracker.spiritBonesSession = 0;
		LootTracker.spiritBootsSession = 0;
		LootTracker.spiritSwordsSession = 0;
		LootTracker.epicSpiritPetsSession = 0;
		LootTracker.f4CoinsSpentSession = 0;
		LootTracker.f4TimeSpentSession = 0;
		LootTracker.warpedStonesSession = 0;
		LootTracker.shadowAssHelmsSession = 0;
		LootTracker.shadowAssChestsSession = 0;
		LootTracker.shadowAssLegsSession = 0;
		LootTracker.shadowAssBootsSession = 0;
		LootTracker.lividDaggersSession = 0;
		LootTracker.shadowFurysSession = 0;
		LootTracker.f5CoinsSpentSession = 0;
		LootTracker.f5TimeSpentSession = 0;
		LootTracker.ancientRosesSession = 0;
		LootTracker.precursorEyesSession = 0;
		LootTracker.giantsSwordsSession = 0;
		LootTracker.necroLordHelmsSession = 0;
		LootTracker.necroLordChestsSession = 0;
		LootTracker.necroLordLegsSession = 0;
		LootTracker.necroLordBootsSession = 0;
		LootTracker.necroSwordsSession = 0;
		LootTracker.f6CoinsSpentSession = 0;
		LootTracker.f6TimeSpentSession = 0;
		LootTracker.witherBloodsSession = 0;
		LootTracker.witherCloaksSession = 0;
		LootTracker.implosionsSession = 0;
		LootTracker.witherShieldsSession = 0;
		LootTracker.shadowWarpsSession = 0;
		LootTracker.necronsHandlesSession = 0;
		LootTracker.autoRecombsSession = 0;
		LootTracker.witherHelmsSession = 0;
		LootTracker.witherChestsSession = 0;
		LootTracker.witherLegsSession = 0;
		LootTracker.witherBootsSession = 0;
		LootTracker.f7CoinsSpentSession = 0;
		LootTracker.f7TimeSpentSession = 0;
		ConfigHandler.deleteCategory("catacombs");
		ConfigHandler.reloadConfig();
	}

}
