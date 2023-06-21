package me.Danker.commands.warp;

import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class WarpCommand extends CommandBase {


    private boolean custom_command;
    public String name;
    public String destination;

    public WarpCommand(String name) {
        this(name, name, false);
    }

    /**
     * A Command blueprint extending CommandBase that uses the name and sends the destination as "/warp {@link #destination}"
     * @param name the name that the command is called as
     * @param destination the location name that is sent
     */
    public WarpCommand(String name, String destination) {
        this(name, destination, false);
    }

    /**
     * A Command blueprint extending CommandBase that uses the name and sends the destination as "/warp {@link #destination}"
     * @param name the name that the command is called as
     * @param destination the location name that is sent
     * @param custom_command is the custom command should use the /warp format or send a custom command.
     *                       Adds the "/" to the destination.
     */
    public WarpCommand(String name, String destination, boolean custom_command) {
        this.name = name;
        this.destination = destination;
        this.custom_command = custom_command;
    }

    /**
     * Returns the commands name that is set in the constructor
     */
    @Override
    public String getCommandName() {
        return name;
    }

    /**
     * Returns the command usage for the builtin help
     * @param sender the command sender
     * @return "/" + the command name
     */
    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    /**
     * The logic that is called when the command is executed
     * Sends a message as the player containing "/warp {@link #destination}"
     * @param sender the command sender
     * @param args what is written after the command
     */
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (!Utils.inSkyblock) return;
        if (custom_command) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/" + this.destination);
            return;
        }
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/warp " + this.destination);

    }

}
