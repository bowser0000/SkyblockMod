package me.Danker.commands.warp;

import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

import java.util.List;

public class SpidersNest extends CommandBase {
    @Override
    public String getCommandName() {
        return "nest";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    public static String usage(ICommandSender arg0) {
        return new me.Danker.commands.warp.SpidersNest().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (!Utils.inSkyblock) return;
        EntityPlayer player = ((EntityPlayer) sender);
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp nest");
    }
}
