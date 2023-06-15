package me.Danker.commands.warp;

import net.minecraftforge.client.ClientCommandHandler;

import java.util.ArrayList;
import java.util.List;

public class WarpCommandHandler {

    private List<WarpCommand> commands;

    /**
     * Constructor of the WarpCommandHandler, it will register all commands when it is created
     */
    public WarpCommandHandler() {
        this.commands = new ArrayList<WarpCommand>();
        registerCommands();
    }


    /**
     * @return List of all registered commands
     */
    public List<WarpCommand> getCommands() {
        return this.commands;
    }

    /**
     * Registers a command to the handler
     * @param warpCommand WarpCommand to register
     */
    public void registerCommand(WarpCommand warpCommand) {
        this.commands.add(warpCommand);
        ClientCommandHandler.instance.registerCommand(warpCommand);
    }

    /**
     * Get a command by its name
     * @param name Name of the command
     * @return WarpCommand with the given name
     */
    public WarpCommand getCommand(String name) {
        for (WarpCommand command : this.commands) {
            if (command.getCommandName().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return null;
    }

    /**
     * Get a command by the class it extends from
     * @param clazz Class to get the command from
     * @return WarpCommand with the given class
     */
    public WarpCommand getCommand(Class<? extends WarpCommand> clazz) {
        for (WarpCommand command : this.commands) {
            if (command.getClass() == clazz) {
                return command;
            }
        }
        return null;
    }

    /**
     * Register all commands
     */
    private void registerCommands() {
        registerCommand(new WarpCommand("deep"));
        registerCommand(new WarpCommand("nether"));
        registerCommand(new WarpCommand("isle"));
        registerCommand(new WarpCommand("crimson"));
        registerCommand(new WarpCommand("wk", "kuudra"));
        registerCommand(new WarpCommand("mines"));
        registerCommand(new WarpCommand("forge"));
        registerCommand(new WarpCommand("crystals"));
        registerCommand(new WarpCommand("gold"));
        registerCommand(new WarpCommand("desert"));
        registerCommand(new WarpCommand("spider"));
        registerCommand(new WarpCommand("barn"));
        registerCommand(new WarpCommand("end"));
        registerCommand(new WarpCommand("park"));
        registerCommand(new WarpCommand("castle"));
        registerCommand(new WarpCommand("museum"));
        registerCommand(new WarpCommand("da"));
        registerCommand(new WarpCommand("crypt"));
        registerCommand(new WarpCommand("nest"));
        registerCommand(new WarpCommand("void"));
        registerCommand(new WarpCommand("drag", "dragon"));
        registerCommand(new WarpCommand("jungle"));
        registerCommand(new WarpCommand("howl"));
        registerCommand(new WarpCommand("dun", "dungeon_hub"));
        registerCommand(new WarpCommand("garden"));
    }

}
