package me.Danker.features;

import me.Danker.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class DisableMovement {

    static boolean disableMouse = false;
    static float sens = 0.5F;

    static boolean disableWS = false;
    static boolean disableAD = false;

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        GameSettings gs = mc.gameSettings;
        if (disableWS) {
            if (gs.keyBindForward.isPressed() || gs.keyBindBack.isPressed()) {
                KeyBinding.setKeyBindState(gs.keyBindForward.getKeyCode(), false);
                KeyBinding.setKeyBindState(gs.keyBindBack.getKeyCode(), false);
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Movement blocked. Press " + ModConfig.disableWS.getDisplay() + " to re-enable."));
            }
        }
        if (disableAD) {
            if (gs.keyBindLeft.isPressed() || gs.keyBindRight.isPressed()) {
                KeyBinding.setKeyBindState(gs.keyBindLeft.getKeyCode(), false);
                KeyBinding.setKeyBindState(gs.keyBindRight.getKeyCode(), false);
                mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Movement blocked. Press " + ModConfig.disableAD.getDisplay() + " to re-enable."));
            }
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        if (disableMouse) {
            Minecraft.getMinecraft().gameSettings.mouseSensitivity = sens;
        }

        disableMouse = false;
        disableWS = false;
        disableAD = false;
    }

    public static void onDisableMouse() {
        disableMouse = !disableMouse;

        Minecraft mc = Minecraft.getMinecraft();
        GameSettings gs = mc.gameSettings;
        EntityPlayer player = mc.thePlayer;

        if (disableMouse) {
            sens = gs.mouseSensitivity;
            gs.mouseSensitivity = -1/3F;
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Disabled moving mouse."));
        } else {
            gs.mouseSensitivity = sens;
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Enabled moving mouse."));
        }
    }

    public static void onDisableWS() {
        disableWS = !disableWS;

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (disableWS) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Disabled moving forwards/back."));
        } else {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Enabled moving forwards/back."));
        }
    }

    public static void onDisableAD() {
        disableAD = !disableAD;

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (disableAD) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Disabled moving left/right."));
        } else {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Enabled moving left/right."));
        }
    }

}
