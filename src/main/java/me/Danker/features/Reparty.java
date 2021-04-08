package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.RepartyCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reparty {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        // Getting party
        if (RepartyCommand.gettingParty) {
            if (message.contains("-----")) {
                switch(RepartyCommand.Delimiter) {
                    case 0:
                        System.out.println("Get Party Delimiter Cancelled");
                        RepartyCommand.Delimiter++;
                        event.setCanceled(true);
                        return;
                    case 1:
                        System.out.println("Done querying party");
                        RepartyCommand.gettingParty = false;
                        RepartyCommand.Delimiter = 0;
                        event.setCanceled(true);
                        return;
                }
            }else if (message.startsWith("Party M") || message.startsWith("Party Leader")){
                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;

                Pattern party_start_pattern = Pattern.compile("^Party Members \\((\\d+)\\)$");
                Pattern leader_pattern = Pattern.compile("^Party Leader: (?:\\[.+?] )?(\\w+) ●$");
                Pattern members_pattern = Pattern.compile(" (?:\\[.+?] )?(\\w+) ●");
                Matcher party_start = party_start_pattern.matcher(message);
                Matcher leader = leader_pattern.matcher(message);
                Matcher members = members_pattern.matcher(message);

                if (party_start.matches() && Integer.parseInt(party_start.group(1)) == 1) {
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "You cannot reparty yourself."));
                    RepartyCommand.partyThread.interrupt();
                } else if (leader.matches() && !(leader.group(1).equals(player.getName()))) {
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "You are not party leader."));
                    RepartyCommand.partyThread.interrupt();
                } else {
                    while (members.find()) {
                        String partyMember = members.group(1);
                        if (!partyMember.equals(player.getName())) {
                            RepartyCommand.party.add(partyMember);
                            System.out.println(partyMember);
                        }
                    }
                }
                event.setCanceled(true);
                return;
            }
        }
        // Disbanding party
        if (RepartyCommand.disbanding) {
            if (message.contains("-----")) {
                switch (RepartyCommand.Delimiter) {
                    case 0:
                        System.out.println("Disband Delimiter Cancelled");
                        RepartyCommand.Delimiter++;
                        event.setCanceled(true);
                        return;
                    case 1:
                        System.out.println("Done disbanding");
                        RepartyCommand.disbanding = false;
                        RepartyCommand.Delimiter = 0;
                        event.setCanceled(true);
                        return;
                }
            } else if (message.endsWith("has disbanded the party!")) {
                event.setCanceled(true);
                return;
            }
        }
        // Inviting
        if (RepartyCommand.inviting) {
            if (message.contains("-----")) {
                switch (RepartyCommand.Delimiter) {
                    case 1:
                        event.setCanceled(true);
                        RepartyCommand.Delimiter = 0;
                        System.out.println("Player Invited!");
                        RepartyCommand.inviting = false;
                        return;
                    case 0:
                        RepartyCommand.Delimiter++;
                        event.setCanceled(true);
                        return;
                }
            } else if (message.endsWith(" to the party! They have 60 seconds to accept.")) {
                Pattern invitePattern = Pattern.compile("(?:(?:\\[.+?] )?(?:\\w+) invited )(?:\\[.+?] )?(\\w+)");
                Matcher invitee = invitePattern.matcher(message);
                if (invitee.find()) {
                    System.out.println("" + invitee.group(1) + ": " + RepartyCommand.repartyFailList.remove(invitee.group(1)));
                }
                event.setCanceled(true);
                return;
            } else if (message.contains("Couldn't find a player") || message.contains("You cannot invite that player")) {
                event.setCanceled(true);
                return;
            }
        }
        // Fail Inviting
        if (RepartyCommand.failInviting) {
            if (message.contains("-----")) {
                switch (RepartyCommand.Delimiter) {
                    case 1:
                        event.setCanceled(true);
                        RepartyCommand.Delimiter = 0;
                        System.out.println("Player Invited!");
                        RepartyCommand.inviting = false;
                        return;
                    case 0:
                        RepartyCommand.Delimiter++;
                        event.setCanceled(true);
                }
            } else if (message.endsWith(" to the party! They have 60 seconds to accept.")) {
                Pattern invitePattern = Pattern.compile("(?:(?:\\[.+?] )?(?:\\w+) invited )(?:\\[.+?] )?(\\w+)");
                Matcher invitee = invitePattern.matcher(message);
                if (invitee.find()) {
                    System.out.println("" + invitee.group(1) + ": " + RepartyCommand.repartyFailList.remove(invitee.group(1)));
                }
                event.setCanceled(true);
            } else if (message.contains("Couldn't find a player") || message.contains("You cannot invite that player")) {
                event.setCanceled(true);
            }
        }
    }

}
