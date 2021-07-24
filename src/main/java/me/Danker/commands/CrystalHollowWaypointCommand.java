package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.CrystalHollowWaypoints;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

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
        EntityPlayer player = (EntityPlayer) arg0;

        if (arg1.length == 0) return;

        String[] waypoints = String.join(" ", arg1).split("\\\\n");

        for (String waypoint : waypoints) {
            String[] parts = waypoint.split("@");
            String[] coords = parts[1].split(",");

            String location = parts[0];
            BlockPos pos = new BlockPos(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
            CrystalHollowWaypoints.Waypoint newWaypoint = new CrystalHollowWaypoints.Waypoint(location, pos);

            CrystalHollowWaypoints.waypoints.add(newWaypoint);
            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Added " + newWaypoint.location + " @ " + newWaypoint.getPos()));
        }
    }

}
