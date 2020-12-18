package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class RepartyCommand extends CommandBase implements ICommand {
    public static String[] players;

    @Override
    public String getCommandName() { return "reparty"; }

    @Override
    public String getCommandUsage(ICommandSender sender) { return "/" + getCommandName(); }

    @Override
    public int getRequiredPermissionLevel() { return 0; }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        final EntityPlayer player = (EntityPlayer) sender;

        if(!SetPartyCommand.set){
            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Party unset! use " + SetPartyCommand.getUsage()));
            return;
        }
        try{
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/p disband");
            Thread.sleep(210);
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/p " + String.join(" ",players));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void processCommand() {

        if(!SetPartyCommand.set){
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Party unset! use " + SetPartyCommand.getUsage()));
            return;
        }
        try{
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/p disband");
            Thread.sleep(210);
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/p " + String.join(" ",players));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
