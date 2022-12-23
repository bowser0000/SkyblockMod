package me.Danker.features;

import cc.polyfrost.oneconfig.config.annotations.Exclude;
import cc.polyfrost.oneconfig.hud.Hud;
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack;
import me.Danker.config.ModConfig;
import me.Danker.handlers.TextRenderer;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
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

    public static List<Ability> cooldowns = new ArrayList<>();
    static double mageReduction = 0D;

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock || !ModConfig.abilityCooldownHud.isEnabled()) return;

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

            if (Utils.isInDungeons() && message.startsWith("[Mage] Cooldown Reduction ")) {
                mageReduction = Integer.parseInt(message.substring(message.indexOf(">") + 2, message.length() - 1)) / 100D;
            }
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

            if (ModConfig.abilityCooldownHud.isEnabled() && Utils.isInDungeons() && chestName.startsWith("Catacombs - ")) {
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
            return EnumChatFormatting.GREEN + ability + ": " + EnumChatFormatting.YELLOW + String.format("%.3f", getCooldown()) + "s";
        }

        public double getCooldown() {
            return (cooldown - System.currentTimeMillis()) / 1000D;
        }

    }

    public static class AbilityCooldownHud extends Hud {

        @Exclude
        String exampleText = EnumChatFormatting.GREEN + "Spirit Glide: " + EnumChatFormatting.YELLOW + "32.734s\n" +
                EnumChatFormatting.GREEN + "Parley: " + EnumChatFormatting.YELLOW + "2.652s\n" +
                EnumChatFormatting.GREEN + "Ice Spray: " + EnumChatFormatting.YELLOW + "1.429s";

        @Override
        protected void preRender(boolean example) {
            if (enabled && Utils.inSkyblock) {
                cooldowns.removeIf(ability -> (ability.getCooldown() <= 0));
            }
        }

        @Override
        protected void draw(UMatrixStack matrices, float x, float y, float scale, boolean example) {
            if (example) {
                TextRenderer.drawHUDText(exampleText, x, y, scale);
                return;
            }

            if (enabled && Utils.inSkyblock && cooldowns.size() > 0) {
                TextRenderer.drawHUDText(getText(), x, y, scale);
            }
        }

        @Override
        protected float getWidth(float scale, boolean example) {
            return RenderUtils.getWidthFromText(example ? exampleText : getText()) * scale;
        }

        @Override
        protected float getHeight(float scale, boolean example) {
            return RenderUtils.getHeightFromText(example ? exampleText : getText()) * scale;
        }

        String getText() {
            StringBuilder sb = new StringBuilder();

            for (Ability ability : cooldowns) {
                sb.append(ability.getTimer()).append("\n");
            }

            return sb.toString();
        }

    }

}
