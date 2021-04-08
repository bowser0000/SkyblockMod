package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.List;

public class NotifySlayerSlain {

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (!Utils.inSkyblock || Minecraft.getMinecraft().thePlayer != event.entityPlayer) return;
        ItemStack item = event.entityPlayer.getHeldItem();
        if (item == null) return;

        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
            if (ToggleCommand.notifySlayerSlainToggled) {
                if (ScoreboardHandler.getSidebarLines().stream().anyMatch(x -> ScoreboardHandler.cleanSB(x).contains("Boss slain!"))) {
                    if (ScoreboardHandler.getSidebarLines().stream().anyMatch(x -> {
                        String line = ScoreboardHandler.cleanSB(x);
                        return Arrays.stream(new String[]{"Howling Cave", "Ruins", "Graveyard", "Coal Mine", "Spider's Den"}).anyMatch(line::contains);
                    })) {
                        if (Utils.hasRightClickAbility(item)) {
                            List<String> lore = Utils.getItemLore(item);

                            int abilityLine = -1;
                            for (int i = 0; i < lore.size(); i++) {
                                String line = StringUtils.stripControlCodes(lore.get(i));
                                if (line.startsWith("Item Ability:")) abilityLine = i;
                                if (abilityLine != -1 && i > abilityLine) {
                                    if (line.toLowerCase().contains("damage")) {
                                        Utils.createTitle(EnumChatFormatting.RED + "Boss slain!", 2);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onArrowNock(ArrowNockEvent event) {
        if (!Utils.inSkyblock || Minecraft.getMinecraft().thePlayer != event.entityPlayer) return;

        if (ToggleCommand.notifySlayerSlainToggled) {
            if (ScoreboardHandler.getSidebarLines().stream().anyMatch(x -> ScoreboardHandler.cleanSB(x).contains("Boss slain!"))) {
                if (ScoreboardHandler.getSidebarLines().stream().anyMatch(x -> {
                    String line = ScoreboardHandler.cleanSB(x);
                    return Arrays.stream(new String[]{"Howling Cave", "Ruins", "Graveyard", "Coal Mine", "Spider's Den"}).anyMatch(line::contains);
                })) {
                    Utils.createTitle(EnumChatFormatting.RED + "Boss slain!", 2);
                }
            }
        }
    }

    @SubscribeEvent
    public void onAttackingEntity(AttackEntityEvent event) {
        if (ToggleCommand.notifySlayerSlainToggled && (event.target instanceof EntityZombie || event.target instanceof EntitySpider || event.target instanceof EntityWolf)) {
            List<String> scoreboard = ScoreboardHandler.getSidebarLines();

            for (String line : scoreboard) {
                String cleanedLine = ScoreboardHandler.cleanSB(line);
                if (cleanedLine.contains("Boss slain!")) {
                    Utils.createTitle(EnumChatFormatting.RED + "Boss slain!", 2);
                    break;
                }
            }
        }
    }

}
