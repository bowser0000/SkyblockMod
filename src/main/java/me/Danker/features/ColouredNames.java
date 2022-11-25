package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.events.PacketReadEvent;
import me.Danker.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColouredNames {

    public static List<String> users = new ArrayList<>();
    public static Map<String, String> nametags = new HashMap<>();
    public static final EnumChatFormatting[] RAINBOW_COLOURS = new EnumChatFormatting[]{EnumChatFormatting.RED, EnumChatFormatting.GOLD, EnumChatFormatting.YELLOW, EnumChatFormatting.GREEN, EnumChatFormatting.AQUA, EnumChatFormatting.BLUE, EnumChatFormatting.DARK_PURPLE};

    static Field messageField = ReflectionHelper.findField(S45PacketTitle.class, "message", "field_179810_b", "b");

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!ModConfig.customColouredNames || !Utils.inSkyblock) return;
        if (event.type == 2) return;

        String text = event.message.getFormattedText();
        for (String user : users) {
            if (text.contains(user)) {
                event.message = replaceChat(event.message, user);
            }
        }
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (!ModConfig.customColouredNames || !Utils.inSkyblock) return;

        for (int i = 0; i < event.toolTip.size(); i++) {
            String line = StringUtils.stripControlCodes(event.toolTip.get(i));
            for (String user : users) {
                if (line.contains(user)) {
                    event.toolTip.set(i, replaceName(event.toolTip.get(i), user, getColourFromName(user)));
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderNametag(PlayerEvent.NameFormat event) {
        if (!ModConfig.customColouredNames || !Utils.inSkyblock) return;

        if (users.contains(event.username)) {
            event.displayname = replaceName(event.displayname, event.username, getColourFromName(event.username));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRenderLiving(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
        if (!ModConfig.customColouredNames || !ModConfig.customNametags || !Utils.inSkyblock) return;

        Entity entity = event.entity;
        if (entity instanceof EntityArmorStand && !entity.isDead && entity.hasCustomName()) {
            String name = entity.getCustomNameTag();
            if (name.charAt(name.length() - 1) == '❤') return;

            String id = entity.getUniqueID().toString();
            String nametag = nametags.get(id);

            if (name.equals(nametag)) {
                entity.setCustomNameTag(nametag);
            } else {
                for (String user : users) {
                    if (name.contains(user)) {
                        name = replaceName(name, user, getColourFromName(user));
                    }
                }

                nametags.put(id, name);
                entity.setCustomNameTag(name);
            }
        }
    }

    @SubscribeEvent
    public void onPacketRead(PacketReadEvent event) {
        if (!ModConfig.customColouredNames || !Utils.inSkyblock) return;

        if (event.packet instanceof S45PacketTitle) {
            S45PacketTitle packet = (S45PacketTitle) event.packet;

            if (packet.getMessage() == null) return;

            String message = packet.getMessage().getUnformattedText();
            for (String user : users) {
                if (message.contains(user)) {
                    try {
                        messageField.setAccessible(true);
                        messageField.set(packet, replaceChat(packet.getMessage(), user));
                        event.packet = packet;
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        nametags.clear();
    }

    // https://github.com/SteveKunG/SkyBlockcatia/blob/1.8.9/src/main/java/com/stevekung/skyblockcatia/utils/SupporterUtils.java#L53
    public static String replaceName(String text, String name, String colour) {
        String namePattern = "(?:(?:\\u00a7[0-9a-fbr])\\B(?:" + name + ")\\b)|(?:\\u00a7[rb]" + name + "\\u00a7r)|\\b" + name + "\\b";
        Matcher prevColourMat = Pattern.compile("(?:.*(?:(?<colour>\\u00a7[0-9a-fbr])" + name + ")\\b.*)").matcher(text);

        if (prevColourMat.matches()) {
            if (colour.equals("§z")) {
                StringBuilder rainbowName = new StringBuilder();
                for (int i = 0; i < name.length(); i++) {
                    rainbowName.append(RAINBOW_COLOURS[i % 7]).append(name.charAt(i));
                }
                return text.replaceAll(namePattern, rainbowName + prevColourMat.group("colour"));
            }
            return text.replaceAll(namePattern, colour + name + prevColourMat.group("colour"));
        }

        if (colour.equals("§z")) {
            StringBuilder rainbowName = new StringBuilder();
            for (int i = 0; i < name.length(); i++) {
                rainbowName.append(RAINBOW_COLOURS[i % 7]).append(name.charAt(i));
            }
            return text.replaceAll(namePattern, rainbowName.toString() + EnumChatFormatting.WHITE);
        }
        return text.replaceAll(namePattern, colour + name + EnumChatFormatting.WHITE);
    }

    // https://github.com/Moulberry/Hychat/blob/master/src/main/java/io/github/moulberry/hychat/util/TextProcessing.java#L23
    public static IChatComponent replaceChat(IChatComponent component, String user) {
        IChatComponent newComponent;
        ChatComponentText text = (ChatComponentText) component;

        newComponent = new ChatComponentText(replaceName(text.getUnformattedTextForChat(), user, getColourFromName(user)));
        newComponent.setChatStyle(text.getChatStyle().createShallowCopy());

        for (IChatComponent sibling : text.getSiblings()) {
            newComponent.appendSibling(replaceChat(sibling, user));
        }

        return newComponent;
    }

    public static String getColourFromName(String name) {
        try {
            return "§" + DankersSkyblockMod.data.get("colourednames").getAsJsonObject().get(name).getAsString();
        } catch (NullPointerException ex) {
            return EnumChatFormatting.WHITE.toString();
        }
    }

}
