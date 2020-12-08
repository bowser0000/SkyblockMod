package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
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
		return "/" + getCommandName() + "<zombie/spider/wolf/fishing/catacombs/confirm/cancel>";
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
		LootCommand.zombieRevsSession = 0;
		LootCommand.zombieRevFleshSession = 0;
		LootCommand.zombieFoulFleshSession = 0;
		LootCommand.zombieFoulFleshDropsSession = 0;
		LootCommand.zombiePestilencesSession = 0;
		LootCommand.zombieUndeadCatasSession = 0;
		LootCommand.zombieBooksSession = 0;
		LootCommand.zombieBeheadedsSession = 0;
		LootCommand.zombieRevCatasSession = 0;
		LootCommand.zombieSnakesSession = 0;
		LootCommand.zombieScythesSession = 0;
		LootCommand.zombieTimeSession = -1;
		LootCommand.zombieBossesSession = -1;
		ConfigHandler.deleteCategory("zombie");
		ConfigHandler.reloadConfig();
	}
	
	static void resetSpider() {
		LootCommand.spiderTarantulasSession = 0;
		LootCommand.spiderWebsSession = 0;
		LootCommand.spiderTAPSession = 0;
		LootCommand.spiderTAPDropsSession = 0;
		LootCommand.spiderBitesSession = 0;
		LootCommand.spiderCatalystsSession = 0;
		LootCommand.spiderBooksSession = 0;
		LootCommand.spiderSwattersSession = 0;
		LootCommand.spiderTalismansSession = 0;
		LootCommand.spiderMosquitosSession = 0;
		LootCommand.spiderTimeSession = -1;
		LootCommand.spiderBossesSession = -1;
		ConfigHandler.deleteCategory("spider");
		ConfigHandler.reloadConfig();
	}
	
	static void resetWolf() {
		LootCommand.wolfSvensSession = 0;
		LootCommand.wolfTeethSession = 0;
		LootCommand.wolfWheelsSession = 0;
		LootCommand.wolfWheelsDropsSession = 0;
		LootCommand.wolfSpiritsSession = 0;
		LootCommand.wolfBooksSession = 0;
		LootCommand.wolfEggsSession = 0;
		LootCommand.wolfCouturesSession = 0;
		LootCommand.wolfBaitsSession = 0;
		LootCommand.wolfFluxesSession = 0;
		LootCommand.wolfTimeSession = -1;
		LootCommand.wolfBossesSession = -1;
		ConfigHandler.deleteCategory("wolf");
		ConfigHandler.reloadConfig();
	}
	
	static void resetFishing() {
		LootCommand.seaCreaturesSession = 0;
		LootCommand.goodCatchesSession = 0;
		LootCommand.greatCatchesSession = 0;
		LootCommand.squidsSession = 0;
		LootCommand.seaWalkersSession = 0;
		LootCommand.nightSquidsSession = 0;
		LootCommand.seaGuardiansSession = 0;
		LootCommand.seaWitchesSession = 0;
		LootCommand.seaArchersSession = 0;
		LootCommand.monsterOfTheDeepsSession = 0;
		LootCommand.catfishesSession = 0;
		LootCommand.carrotKingsSession = 0;
		LootCommand.seaLeechesSession = 0;
		LootCommand.guardianDefendersSession = 0;
		LootCommand.deepSeaProtectorsSession = 0;
		LootCommand.hydrasSession = 0;
		LootCommand.seaEmperorsSession = 0;
		LootCommand.empTimeSession = -1;
		LootCommand.empSCsSession = -1;
		LootCommand.fishingMilestoneSession = 0;
		LootCommand.frozenStevesSession = 0;
		LootCommand.frostyTheSnowmansSession = 0;
		LootCommand.grinchesSession = 0;
		LootCommand.yetisSession = 0;
		LootCommand.yetiTimeSession = -1;
		LootCommand.yetiSCsSession = -1;
		LootCommand.nurseSharksSession = 0;
		LootCommand.blueSharksSession = 0;
		LootCommand.tigerSharksSession = 0;
		LootCommand.greatWhiteSharksSession = 0;
		LootCommand.scarecrowsSession = 0;
		LootCommand.nightmaresSession = 0;
		LootCommand.werewolfsSession = 0;
		LootCommand.phantomFishersSession = 0;
		LootCommand.grimReapersSession = 0;
		ConfigHandler.deleteCategory("fishing");
		ConfigHandler.reloadConfig();
	}
	
	static void resetMythological() {
		LootCommand.mythCoinsSession = 0;
		LootCommand.griffinFeathersSession = 0;
		LootCommand.crownOfGreedsSession = 0;
		LootCommand.washedUpSouvenirsSession = 0;
		LootCommand.minosHuntersSession = 0;
		LootCommand.siameseLynxesSession = 0;
		LootCommand.minotaursSession = 0;
		LootCommand.gaiaConstructsSession = 0;
		LootCommand.minosChampionsSession = 0;
		LootCommand.minosInquisitorsSession = 0;
		ConfigHandler.deleteCategory("mythological");
		ConfigHandler.reloadConfig();
	}
	
	static void resetCatacombs() {
		LootCommand.recombobulatorsSession = 0;
		LootCommand.fumingPotatoBooksSession = 0;
		LootCommand.bonzoStaffsSession = 0;
		LootCommand.f1CoinsSpentSession = 0;
		LootCommand.f1TimeSpentSession = 0;
		LootCommand.scarfStudiesSession = 0;
		LootCommand.f2CoinsSpentSession = 0;
		LootCommand.f2TimeSpentSession = 0;
		LootCommand.adaptiveHelmsSession = 0;
		LootCommand.adaptiveChestsSession = 0;
		LootCommand.adaptiveLegsSession = 0;
		LootCommand.adaptiveBootsSession = 0;
		LootCommand.adaptiveSwordsSession = 0;
		LootCommand.f3CoinsSpentSession = 0;
		LootCommand.f3TimeSpentSession = 0;
		LootCommand.spiritWingsSession = 0;
		LootCommand.spiritBonesSession = 0;
		LootCommand.spiritBootsSession = 0;
		LootCommand.spiritSwordsSession = 0;
		LootCommand.epicSpiritPetsSession = 0;
		LootCommand.f4CoinsSpentSession = 0;
		LootCommand.f4TimeSpentSession = 0;
		LootCommand.warpedStonesSession = 0;
		LootCommand.shadowAssHelmsSession = 0;
		LootCommand.shadowAssChestsSession = 0;
		LootCommand.shadowAssLegsSession = 0;
		LootCommand.shadowAssBootsSession = 0;
		LootCommand.lividDaggersSession = 0;
		LootCommand.shadowFurysSession = 0;
		LootCommand.f5CoinsSpentSession = 0;
		LootCommand.f5TimeSpentSession = 0;
		LootCommand.ancientRosesSession = 0;
		LootCommand.precursorEyesSession = 0;
		LootCommand.giantsSwordsSession = 0;
		LootCommand.necroLordHelmsSession = 0;
		LootCommand.necroLordChestsSession = 0;
		LootCommand.necroLordLegsSession = 0;
		LootCommand.necroLordBootsSession = 0;
		LootCommand.necroSwordsSession = 0;
		LootCommand.f6CoinsSpentSession = 0;
		LootCommand.f6TimeSpentSession = 0;
		LootCommand.witherBloodsSession = 0;
		LootCommand.witherCloaksSession = 0;
		LootCommand.implosionsSession = 0;
		LootCommand.witherShieldsSession = 0;
		LootCommand.shadowWarpsSession = 0;
		LootCommand.necronsHandlesSession = 0;
		LootCommand.autoRecombsSession = 0;
		LootCommand.witherHelmsSession = 0;
		LootCommand.witherChestsSession = 0;
		LootCommand.witherLegsSession = 0;
		LootCommand.witherBootsSession = 0;
		LootCommand.f7CoinsSpentSession = 0;
		LootCommand.f7TimeSpentSession = 0;
		ConfigHandler.deleteCategory("catacombs");
		ConfigHandler.reloadConfig();
	}

}
