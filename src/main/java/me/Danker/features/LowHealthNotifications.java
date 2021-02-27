package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class LowHealthNotifications {

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        World world = Minecraft.getMinecraft().theWorld;
        if (DankersSkyblockMod.tickAmount % 2 == 0) {
            if (ToggleCommand.lowHealthNotifyToggled && Utils.inDungeons && world != null) {
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                for (String score : scoreboard) {
                    if (score.endsWith("❤") && score.matches(".* §c\\d.*")) {
                        String name = score.substring(score.indexOf(" ") + 1);
                        Utils.createTitle(EnumChatFormatting.RED + "LOW HEALTH!\n" + name, 1);
                        break;
                    }
                }
            }
        }
    }

}
