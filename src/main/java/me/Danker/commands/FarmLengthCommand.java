package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.EndOfFarmAlert;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class FarmLengthCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "dsmfarmlength";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " <min coords> <max coords>";
    }

    public static String usage(ICommandSender arg0) {
        return new FarmLengthCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        EntityPlayer player = (EntityPlayer) arg0;

        if (arg1.length == 0) {
            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Current Range: " + DankersSkyblockMod.SECONDARY_COLOUR + EndOfFarmAlert.min + DankersSkyblockMod.MAIN_COLOUR + " to " + DankersSkyblockMod.SECONDARY_COLOUR + EndOfFarmAlert.max));
            return;
        } else if (arg1.length < 2) {
            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
            return;
        }

        EndOfFarmAlert.min = Double.parseDouble(arg1[0]);
        EndOfFarmAlert.max = Double.parseDouble(arg1[1]);
        ConfigHandler.writeDoubleConfig("misc", "farmMin", EndOfFarmAlert.min);
        ConfigHandler.writeDoubleConfig("misc", "farmMax", EndOfFarmAlert.max);
        player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Farm length set to " + DankersSkyblockMod.SECONDARY_COLOUR + EndOfFarmAlert.min + DankersSkyblockMod.MAIN_COLOUR + " to " + DankersSkyblockMod.SECONDARY_COLOUR + EndOfFarmAlert.max));
    }

}
