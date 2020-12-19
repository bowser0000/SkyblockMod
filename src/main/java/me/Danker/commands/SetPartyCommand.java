package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;

public class SetPartyCommand extends CommandBase implements ICommand {

    public static boolean set = false;
    public static int delimiter = 0;
    public static boolean gettingParty;
    public static String partyResponse = "";

    @Override
    public String getCommandName() { return "setparty"; }
    public static String getName() { return "setparty"; }

    @Override
    public String getCommandUsage(ICommandSender sender) { return "/" + getCommandName() + " <Party members>"; }
    public static String getUsage(){ return "/" + getName() + " <Party members>"; }

    @Override
    public int getRequiredPermissionLevel() { return 0; }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        final EntityPlayer player = (EntityPlayer) sender;

        if(args.length == 0) {
            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(sender)));
            return;
        }

        RepartyCommand.players = args;

        String members = String.join("\n" + EnumChatFormatting.GOLD,RepartyCommand.players);
        player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + "-------------------\n" +
                EnumChatFormatting.GOLD + members + "\n" +
                DankersSkyblockMod.DELIMITER_COLOUR + "" + EnumChatFormatting.BOLD + "-------------------"));

        set = true;
    }

    public static void getParty() {
        gettingParty = true;
        partyResponse = "";
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/pl");
        (new Thread(() ->{
            try{
                Thread.sleep(500);
                RepartyCommand.players = Arrays.stream(partyResponse.split(" ")).filter(e -> !e.contains("[") && !e.contains("●")).toArray(String[]::new);
                set = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })).start();
    }
}
