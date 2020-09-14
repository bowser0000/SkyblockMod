package me.Danker.commands;

import java.util.List;

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
		return getCommandName() + "<zombie/spider/wolf/fishing/catacombs/confirm/cancel>";
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
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /resetloot <zombie/spider/wolf/fishing/catacombs>"));
			return;
		}
		
		if (confirmReset) {
			if (arg1[0].equalsIgnoreCase("confirm")) {
				confirmReset = false;
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Resetting " + resetOption + " tracker..."));
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
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Reset complete."));
			} else if (arg1[0].equalsIgnoreCase("cancel")) {
				confirmReset = false;
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Reset cancelled."));
			} else {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Please confirm the reset of the " + resetOption + " tracker by using /resetloot confirm." +
															EnumChatFormatting.RED + " Cancel by using /resetloot cancel."));
			}
		} else {
			if (arg1[0].equalsIgnoreCase("zombie") || arg1[0].equalsIgnoreCase("spider") || arg1[0].equalsIgnoreCase("wolf") || arg1[0].equalsIgnoreCase("fishing") || arg1[0].equalsIgnoreCase("catacombs")) {
				resetOption = arg1[0];
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "Are you sure you want to reset the " + resetOption + " tracker?" + 
															" Confirm with " + EnumChatFormatting.GREEN + "/resetloot confirm" + EnumChatFormatting.YELLOW + "." + 
															" Cancel by using " + EnumChatFormatting.GREEN + "/resetloot cancel" + EnumChatFormatting.YELLOW + "."));
				confirmReset = true;
			} else if (arg1[0].equalsIgnoreCase("confirm") || arg1[0].equalsIgnoreCase("cancel")) {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Pick something to reset first."));
			} else {
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: /resetloot <zombie/spider/wolf/fishing/catacombs>"));
			}
		}
	}
	
	static void resetZombie() {
		LootCommand lc = new LootCommand();
		ConfigHandler cf = new ConfigHandler();
		
		lc.zombieRevsSession = 0;
		lc.zombieRevFleshSession = 0;
		lc.zombieFoulFleshSession = 0;
		lc.zombieFoulFleshDropsSession = 0;
		lc.zombiePestilencesSession = 0;
		lc.zombieUndeadCatasSession = 0;
		lc.zombieBooksSession = 0;
		lc.zombieBeheadedsSession = 0;
		lc.zombieRevCatasSession = 0;
		lc.zombieSnakesSession = 0;
		lc.zombieScythesSession = 0;
		lc.zombieTimeSession = -1;
		lc.zombieBossesSession = -1;
		cf.deleteCategory("zombie");
		cf.reloadConfig();
	}
	
	static void resetSpider() {
		LootCommand lc = new LootCommand();
		ConfigHandler cf = new ConfigHandler();
		
		lc.spiderTarantulasSession = 0;
		lc.spiderWebsSession = 0;
		lc.spiderTAPSession = 0;
		lc.spiderTAPDropsSession = 0;
		lc.spiderBitesSession = 0;
		lc.spiderCatalystsSession = 0;
		lc.spiderBooksSession = 0;
		lc.spiderSwattersSession = 0;
		lc.spiderTalismansSession = 0;
		lc.spiderMosquitosSession = 0;
		lc.spiderTimeSession = -1;
		lc.spiderBossesSession = -1;
		cf.deleteCategory("spider");
		cf.reloadConfig();
	}
	
	static void resetWolf() {
		LootCommand lc = new LootCommand();
		ConfigHandler cf = new ConfigHandler();
		lc.wolfSvensSession = 0;
		lc.wolfTeethSession = 0;
		lc.wolfWheelsSession = 0;
		lc.wolfWheelsDropsSession = 0;
		lc.wolfSpiritsSession = 0;
		lc.wolfBooksSession = 0;
		lc.wolfEggsSession = 0;
		lc.wolfCouturesSession = 0;
		lc.wolfBaitsSession = 0;
		lc.wolfFluxesSession = 0;
		lc.wolfTimeSession = -1;
		lc.wolfBossesSession = -1;
		cf.deleteCategory("wolf");
		cf.reloadConfig();
	}
	
	static void resetFishing() {
		LootCommand lc = new LootCommand();
		ConfigHandler cf = new ConfigHandler();
		lc.seaCreaturesSession = 0;
		lc.goodCatchesSession = 0;
		lc.greatCatchesSession = 0;
		lc.squidsSession = 0;
		lc.seaWalkersSession = 0;
		lc.nightSquidsSession = 0;
		lc.seaGuardiansSession = 0;
		lc.seaWitchesSession = 0;
		lc.seaArchersSession = 0;
		lc.monsterOfTheDeepsSession = 0;
		lc.catfishesSession = 0;
		lc.carrotKingsSession = 0;
		lc.seaLeechesSession = 0;
		lc.guardianDefendersSession = 0;
		lc.deepSeaProtectorsSession = 0;
		lc.hydrasSession = 0;
		lc.seaEmperorsSession = 0;
		lc.empTimeSession = -1;
		lc.empSCsSession = -1;
		lc.fishingMilestoneSession = 0;
		lc.frozenStevesSession = 0;
		lc.frostyTheSnowmansSession = 0;
		lc.grinchesSession = 0;
		lc.yetisSession = 0;
		lc.yetiTimeSession = -1;
		lc.yetiSCsSession = -1;
		lc.nurseSharksSession = 0;
		lc.blueSharksSession = 0;
		lc.tigerSharksSession = 0;
		lc.greatWhiteSharksSession = 0;
		cf.deleteCategory("fishing");
		cf.reloadConfig();
	}
	
	static void resetCatacombs() {
		LootCommand lc = new LootCommand();
		ConfigHandler cf = new ConfigHandler();
		lc.recombobulatorsSession = 0;
		lc.fumingPotatoBooksSession = 0;
		lc.bonzoStaffsSession = 0;
		lc.f1CoinsSpentSession = 0;
		lc.f1TimeSpentSession = 0;
		lc.scarfStudiesSession = 0;
		lc.f2CoinsSpentSession = 0;
		lc.f2TimeSpentSession = 0;
		lc.adaptiveHelmsSession = 0;
		lc.adaptiveChestsSession = 0;
		lc.adaptiveLegsSession = 0;
		lc.adaptiveBootsSession = 0;
		lc.adaptiveSwordsSession = 0;
		lc.f3CoinsSpentSession = 0;
		lc.f3TimeSpentSession = 0;
		lc.spiritWingsSession = 0;
		lc.spiritBonesSession = 0;
		lc.spiritBootsSession = 0;
		lc.spiritSwordsSession = 0;
		lc.epicSpiritPetsSession = 0;
		lc.f4CoinsSpentSession = 0;
		lc.f4TimeSpentSession = 0;
		cf.deleteCategory("catacombs");
		cf.reloadConfig();
	}

}
