package me.Danker.features;

import me.Danker.commands.MoveCommand;
import me.Danker.commands.ScaleCommand;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.RenderOverlayEvent;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class AbilityCooldowns {

    static List<Ability> cooldowns = new ArrayList<>();
    static double mageReduction = 0D;

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock || !ToggleCommand.abilityCooldowns) return;

        if (event.type == 2) {
            String[] actionBarSections = StringUtils.stripControlCodes(event.message.getUnformattedText()).split(" {3,}");

            for (String section : actionBarSections) {
                if (section.charAt(0) == '-' && section.contains("(") && section.charAt(section.length() - 1) == ')') {
                    String ability = section.substring(section.indexOf("(") + 1, section.length() - 1);

                    for (Ability cooldown : cooldowns) {
                        if (cooldown.ability.equals(ability)) return;
                    }

                    cooldowns.add(new Ability(ability, Utils.getCooldownFromAbility(ability) * (1D - mageReduction)));
                }
            }
        } else {
            String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

            if (Utils.inDungeons && message.startsWith("[Mage] Cooldown Reduction ")) {
                mageReduction = Integer.parseInt(message.substring(message.indexOf(">") + 2, message.length() - 1)) / 100D;
            }
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlayEvent event) {
        if (ToggleCommand.abilityCooldowns && Utils.inSkyblock) {
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

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (mc.currentScreen instanceof GuiChest) {
            if (player == null) return;
            ContainerChest chest = (ContainerChest) player.openContainer;
            List<Slot> invSlots = ((GuiChest) mc.currentScreen).inventorySlots.inventorySlots;
            String chestName = chest.getLowerChestInventory().getDisplayName().getUnformattedText().trim();

            if (ToggleCommand.abilityCooldowns && Utils.inDungeons && chestName.startsWith("Catacombs - ")) {
                ItemStack mage = invSlots.get(30).getStack();
                if (mage == null || mage.getDisplayName() == null) return;
                if (mage.isItemEnchanted()) {
                    String display = mage.getDisplayName();
                    mageReduction = Utils.getCooldownReductionFromLevel(Integer.parseInt(display.substring(display.indexOf(" ") + 1, display.indexOf("]"))));
                } else {
                    mageReduction = 0D;
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        cooldowns.clear();
        mageReduction = 0D;
    }

    public static class Ability {

        public final String ability;
        private final long cooldown;

        public Ability(String ability, double cooldown) {
            this.ability = ability;
            this.cooldown = (long) (System.currentTimeMillis() + cooldown * 1000L);
        }

        public String getTimer() {
            return EnumChatFormatting.GREEN + ability + ": " + EnumChatFormatting.YELLOW + getCooldown() + "s";
        }

        public double getCooldown() {
            return (cooldown - System.currentTimeMillis()) / 1000D;
        }

    }

}
