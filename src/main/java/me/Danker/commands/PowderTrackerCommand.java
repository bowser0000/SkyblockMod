package me.Danker.commands;

import me.Danker.config.ModConfig;
import me.Danker.features.PowderTracker;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class PowderTrackerCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "powdertracker";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " <start/stop/reset/show/hide>";
    }

    public static String usage(ICommandSender arg0) {
        return new PowderTrackerCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "start", "resume", "pause", "stop", "reset", "hide", "show");
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        EntityPlayer player = (EntityPlayer) arg0;

        if (arg1.length < 1) {
            player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: " + getCommandUsage(arg0)));
            return;
        }

        switch (arg1[0].toLowerCase()) {
            case "start":
            case "resume":
                if (PowderTracker.powderStopwatch.isStarted() && PowderTracker.powderStopwatch.isSuspended()) {
                    PowderTracker.powderStopwatch.resume();
                } else if (!PowderTracker.powderStopwatch.isStarted()) {
                    PowderTracker.powderStopwatch.start();
                }
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Powder tracker started."));
                break;
            case "pause":
            case "stop":
                if (PowderTracker.powderStopwatch.isStarted() && !PowderTracker.powderStopwatch.isSuspended()) {
                    PowderTracker.powderStopwatch.suspend();
                } else {
                    player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Powder tracker paused."));
                }
                break;
            case "reset":
                PowderTracker.reset();
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Powder tracker reset."));
                break;
            case "hide":
                ModConfig.showPowderTracker = false;
                ConfigHandler.writeBooleanConfig("misc", "showPowderTracker", false);
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Powder tracker hidden."));
                break;
            case "show":
                ModConfig.showPowderTracker = true;
                ConfigHandler.writeBooleanConfig("misc", "showPowderTracker", true);
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Powder tracker shown."));
                break;
            default:
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "Usage: " + getCommandUsage(arg0)));
        }
    }

}
