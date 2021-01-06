package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RepartyCommand extends CommandBase implements ICommand {
    public static double callTime = 0;
    public static boolean gettingParty = false;
    public static int Delimiter = 0;
    public static boolean disbanding = false;
    public static boolean inviting = false;
    public static boolean failInviting = false;
    public static String currentMember;
    public static List<String> party = new ArrayList<>();
    public static List<String> repartyFailList = new ArrayList<>();
    public static List<String> joinList = new ArrayList<>();
    public static Thread partyThread = null;

    @Override
    public String getCommandName() { 
        return "reparty"; 
    }

    @Override
    public String getCommandUsage(ICommandSender sender) { 
        return "/" + getCommandName(); 
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("rp");
    }

    @Override
    public int getRequiredPermissionLevel() { 
        return 0; 
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0 && (args[0].startsWith("fail") || args[0].equals("f"))) {
            partyThread = new Thread(() -> {
                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
                RepartyCommand.callTime = (double) System.currentTimeMillis() / 1000;
                GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();


                try {
                    player.sendChatMessage("/p " + String.join(" ", repartyFailList));
                    String members = String.join(EnumChatFormatting.WHITE + "\n- " + EnumChatFormatting.YELLOW, repartyFailList);
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------\n" +
                            DankersSkyblockMod.MAIN_COLOUR + "Partying:" + EnumChatFormatting.WHITE + "\n- " +
                            EnumChatFormatting.YELLOW + members + "\n" +
                            DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------"));
                    failInviting = true;
                    while (failInviting) {
                        Thread.sleep(10);
                    }
                    if (repartyFailList.size() > 0) {
                        String repartyFails = String.join("\n- " + EnumChatFormatting.RED, repartyFailList);
                        player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------\n" +
                                DankersSkyblockMod.MAIN_COLOUR + "Failed to invite:" + EnumChatFormatting.WHITE + "\n- " +
                                EnumChatFormatting.RED + repartyFails + "\n" +
                                DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------"));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            partyThread.start();

            return;
        }


        party.clear();
        repartyFailList.clear();

        // MULTI THREAD DRIFTING
        partyThread = new Thread(() -> {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            RepartyCommand.callTime = (double) System.currentTimeMillis() / 1000;
            GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();


            try {
                player.sendChatMessage("/pl");
                gettingParty = true;
                while (gettingParty) {
                    Thread.sleep(10);
                }
                if (party.size() == 0) return;
                player.sendChatMessage("/p disband");
                disbanding = true;
                while (disbanding) {
                    Thread.sleep(10);
                }
                player.sendChatMessage("/p " + String.join(" ", party));
                String members = String.join(EnumChatFormatting.WHITE + "\n- " + EnumChatFormatting.YELLOW, RepartyCommand.party);
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------\n" +
                        DankersSkyblockMod.MAIN_COLOUR + "Repartying:" + EnumChatFormatting.WHITE + "\n- " +
                        EnumChatFormatting.YELLOW + members + "\n" +
                        DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------"));
                inviting = true;
                repartyFailList = new ArrayList<>(party);
                while (inviting) {
                    Thread.sleep(10);
                }
                if (repartyFailList.size() > 0) {
                    String repartyFails = String.join("\n- " + EnumChatFormatting.RED, repartyFailList);
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------\n" +
                            DankersSkyblockMod.MAIN_COLOUR + "Failed to invite:" + EnumChatFormatting.WHITE + "\n- " +
                            EnumChatFormatting.RED + repartyFails + "\n" +
                            DankersSkyblockMod.DELIMITER_COLOUR + "-----------------------------"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

		partyThread.start();
    }
}