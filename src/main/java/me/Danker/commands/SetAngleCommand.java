package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;


public class SetAngleCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "setangle";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName() + " [yaw] [pitch]";
    }

    public static String usage(ICommandSender arg0) {
        return new SetAngleCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) return;

        Minecraft.getMinecraft().thePlayer.rotationPitch = Float.parseFloat(args[1]);
        Minecraft.getMinecraft().thePlayer.rotationYaw = Float.parseFloat(args[0]);
        sender.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Set your rotation to yaw=" + DankersSkyblockMod.SECONDARY_COLOUR + args[0] + DankersSkyblockMod.MAIN_COLOUR + ", pitch=" + DankersSkyblockMod.SECONDARY_COLOUR +args[1]));
    }
}
