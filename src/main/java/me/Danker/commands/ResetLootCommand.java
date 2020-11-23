package me.Danker.commands;

import java.util.List;

import me.Danker.TheMod;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

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
			return getListOfStringsMatchingLastWord(args, "zombie", "spider", "wolf", "fishing", "catacombs");
		}
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer) arg0;
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Usage: /resetloot <zombie/spider/wolf/fishing/catacombs>"));
			return;
		}
		
		if (confirmReset) {
			if (arg1[0].equalsIgnoreCase("confirm")) {
				confirmReset = false;
				player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Resetting " + resetOption + " tracker..."));
				if (resetOption.equalsIgnoreCase("zombie")) {
					resetZombie();
				} else if (resetOption.equalsIgnoreCase("spider")) {
					resetSpider();
				} else if (resetOption.equalsIgnoreCase("wolf")) {
					resetWolf();
				} else if (resetOption.equalsIgnoreCase("fishing")) {
					resetFishing();
				} else if (resetOption.equalsIgnoreCase("catacombs")) {
					resetCatacombs();
				}
				player.addChatMessage(new ChatComponentText(TheMod.MAIN_COLOUR + "Reset complete."));
			} else if (arg1[0].equalsIgnoreCase("cancel")) {
				confirmReset = false;
				player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Reset cancelled."));
			} else {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Please confirm the reset of the " + resetOption + " tracker by using /resetloot confirm." +
															EnumChatFormatting.RED + " Cancel by using /resetloot cancel."));
			}
		} else {
			if (arg1[0].equalsIgnoreCase("zombie") || arg1[0].equalsIgnoreCase("spider") || arg1[0].equalsIgnoreCase("wolf") || arg1[0].equalsIgnoreCase("fishing") || arg1[0].equalsIgnoreCase("catacombs")) {
				resetOption = arg1[0];
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Are you sure you want to reset the " + resetOption + " tracker?" + 
															" Confirm with " + TheMod.MAIN_COLOUR + "/resetloot confirm" + EnumChatFormatting.YELLOW + "." + 
															" Cancel by using " + TheMod.MAIN_COLOUR + "/resetloot cancel" + EnumChatFormatting.YELLOW + "."));
				confirmReset = true;
			} else if (arg1[0].equalsIgnoreCase("confirm") || arg1[0].equalsIgnoreCase("cancel")) {
				player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Pick something to reset first."));
			} else {
				player.addChatMessage(new ChatComponentText(TheMod.ERROR_COLOUR + "Usage: /resetloot <zombie/spider/wolf/fishing/catacombs>"));
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
