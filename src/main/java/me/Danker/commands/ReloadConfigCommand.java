package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.events.PostConfigInitEvent;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

public class ReloadConfigCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "reloadconfig";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName();
	}

	public static String usage(ICommandSender arg0) {
		return new ReloadConfigCommand().getCommandUsage(arg0);
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		EntityPlayer player = (EntityPlayer)arg0;
		ConfigHandler.reloadConfig();
		MinecraftForge.EVENT_BUS.post(new PostConfigInitEvent(DankersSkyblockMod.configDirectory));
		player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Reloaded config."));
	}

}
