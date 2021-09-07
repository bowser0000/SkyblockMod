package me.Danker.features;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.events.RenderOverlayEvent;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class AbilityCooldowns {

    List<Ability> cooldowns = new ArrayList<>();

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock || event.type != 2) return;

        String[] actionBarSections = StringUtils.stripControlCodes(event.message.getUnformattedText()).split(" {3,}");

        for (String section : actionBarSections) {
            if (section.charAt(0) == '-' && section.contains("(") && section.charAt(section.length() - 1) == ')') {
                String ability = section.substring(section.indexOf("(") + 1, section.length() - 1);

                for (Ability cooldown : cooldowns) {
                    if (cooldown.ability.equals(ability)) return;
                }

                cooldowns.add(new Ability(ability, Utils.getCooldownFromAbility(ability)));
            }
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlayEvent event) {
        if (Utils.inSkyblock) {
            StringBuilder sb = new StringBuilder();

            for (int i = cooldowns.size() - 1; i >= 0; i--) {
                Ability ability = cooldowns.get(i);

                if (ability.getCooldown() <= 0) {
                    cooldowns.remove(i);
                    continue;
                }

                sb.insert(0, ability.getTimer() + "\n");
            }

            new TextRenderer(Minecraft.getMinecraft(), sb.toString(), MoveCommand.abilityCooldownsXY[0], MoveCommand.abilityCooldownsXY[1], ScaleCommand.abilityCooldownsScale);
        }
    }

    public static class Ability {

        public final String ability;
        private final long cooldown;

        public Ability(String ability, int cooldown) {
            this.ability = ability;
            this.cooldown = System.currentTimeMillis() + cooldown * 1000L;
        }

        public String getTimer() {
            return EnumChatFormatting.GREEN + ability + ": " + EnumChatFormatting.YELLOW + getCooldown() + "s";
        }

        public double getCooldown() {
            return (cooldown - System.currentTimeMillis()) / 1000D;
        }

    }

}
