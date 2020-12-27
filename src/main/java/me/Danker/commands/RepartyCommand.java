package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RepartyCommand extends CommandBase implements ICommand {
    public static double callTime = 0;
    public static boolean inviteFailed = false;
    public static String currentMember;
    public static List<String> party = new ArrayList<>();
    public static List<String> repartyFailList = new ArrayList<>();

    @Override
    public String getCommandName() { 
        return "reparty"; 
    }

    @Override
    public String getCommandUsage(ICommandSender sender) { 
        return "/" + getCommandName(); 
    }

    @Override
    public int getRequiredPermissionLevel() { 
        return 0; 
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        party.clear();
        repartyFailList.clear();

        // MULTI THREAD DRIFTING
		new Thread(() -> {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            RepartyCommand.callTime = (double) System.currentTimeMillis() / 1000;
            
            try {
                player.sendChatMessage("/p list");
                System.out.println("Grabbing the party members...");
                Thread.sleep(550);
                if (RepartyCommand.party.size() == 0) return;
                
                player.sendChatMessage("/p disband");
                Thread.sleep(210);
                
                String members = String.join(EnumChatFormatting.WHITE + "\n- " + EnumChatFormatting.GOLD, RepartyCommand.party);
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------\n" +
                                                            DankersSkyblockMod.MAIN_COLOUR + "Repartying:" + EnumChatFormatting.WHITE + "\n- " +
                                                            EnumChatFormatting.GOLD + members + "\n" +
                                                            DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------\n"));

                for (int i = 0; i < RepartyCommand.party.size(); i++) {
                    RepartyCommand.currentMember = RepartyCommand.party.get(i);
                    player.sendChatMessage("/p " + RepartyCommand.currentMember);
                    Thread.sleep(400);
                }

                if (RepartyCommand.repartyFailList.size() > 0) {
                    Thread.sleep(300);
                    String failedMembers = String.join(EnumChatFormatting.WHITE + "\n- " + EnumChatFormatting.GOLD, RepartyCommand.repartyFailList);
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------\n" +
                                                                DankersSkyblockMod.ERROR_COLOUR + "Unable to Invite:" + EnumChatFormatting.WHITE + "\n- " +
                                                                EnumChatFormatting.GOLD + failedMembers + "\n" +
                                                                DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------\n"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}