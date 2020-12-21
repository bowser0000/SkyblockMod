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
        // MULTI THREAD DRIFTING
		new Thread(() -> {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            callTime = System.currentTimeMillis() / 1000;
            
            try {
                player.sendChatMessage("/p list");
                Thread.sleep(700);

                List<String> party = DankersSkyblockMod.partyList;
                if (party.size() == 0) { 
                    DankersSkyblockMod.partyList.clear();
                    return;
                }
                
                player.sendChatMessage("/p disband");
                Thread.sleep(250);
                
                String members = String.join(EnumChatFormatting.WHITE + "\n- " + EnumChatFormatting.GOLD, party);
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------\n" +
                                                            DankersSkyblockMod.MAIN_COLOUR + "Repartying:" + EnumChatFormatting.WHITE + "\n- " +
                                                            EnumChatFormatting.GOLD + members + "\n" +
                                                            DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------\n"));
                Thread.sleep(250);

                for (int i = 0; i < party.size(); i++) {
                    player.sendChatMessage("/p " + party.get(i));
                    Thread.sleep(250);
                }

                DankersSkyblockMod.partyList.clear();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}