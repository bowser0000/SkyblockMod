package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WatcherReadyAlert {

    @SubscribeEvent(receiveCanceled = true)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!ModConfig.watcherReady) return;
        if (!Utils.isInDungeons()) return;

        if (message.contains("[BOSS] The Watcher: That will be enough for now.")) {
            Utils.createTitle(EnumChatFormatting.RED + "WATCHER READY", 2);
        }
    }
}
