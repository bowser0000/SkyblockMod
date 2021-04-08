package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpamHider {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (message.contains(":")) return;

        // Spirit Sceptre
        if (!ToggleCommand.sceptreMessages && message.contains("Your Spirit Sceptre hit ")) {
            event.setCanceled(true);
            return;
        }
        // Midas Staff
        if (!ToggleCommand.midasStaffMessages && message.contains("Your Molten Wave hit ")) {
            event.setCanceled(true);
            return;
        }
        // Heals
        if (!ToggleCommand.healMessages && message.contains(" health!") && (message.contains("You healed ") || message.contains(" healed you for "))) {
            event.setCanceled(true);
            return;
        }
        // Ability Cooldown
        if (!ToggleCommand.cooldownMessages && message.contains("This ability is on cooldown for ")) {
            event.setCanceled(true);
            return;
        }
        // Out of mana messages
        if (!ToggleCommand.manaMessages && message.contains("You do not have enough mana to do this!")) {
            event.setCanceled(true);
            return;
        }
        // Implosion
        if (!ToggleCommand.implosionMessages) {
            if (message.contains("Your Implosion hit ") || message.contains("There are blocks in the way")) {
                event.setCanceled(true);
                return;
            }
        }
        // Kill Combo
        if (!ToggleCommand.killComboMessages) {
            if ((message.contains("+") && message.contains(" Kill Combo ")) || message.contains("Your Kill Combo has expired!")) {
                event.setCanceled(true);
            }
        }
    }

}
