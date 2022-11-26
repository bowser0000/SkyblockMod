package me.Danker.commands;

import me.Danker.features.CrystalHollowWaypoints;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;

public class CrystalHollowWaypointCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "dsmaddcrystalhollowwaypoints";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " <formatted waypoint>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        if (arg1.length == 0) return;

        if (arg1.length > 4 && arg1[0].equals("st")) {
            String name = String.join(" ", Arrays.copyOfRange(arg1, 4, arg1.length));
            CrystalHollowWaypoints.addWaypoint(name, arg1[1], arg1[2], arg1[3], false);
        } else {
            CrystalHollowWaypoints.addDSMWaypoints(String.join(" ", arg1), false);
        }
    }

}
