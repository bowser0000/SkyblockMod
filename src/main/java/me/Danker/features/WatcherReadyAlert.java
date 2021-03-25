package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WatcherReadyAlert {

    @SubscribeEvent(receiveCanceled = true)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inDungeons) return;

        if (message.contains("[BOSS] The Watcher: That will be enough for now.")) {
            if (ToggleCommand.watcherReadyToggled) Utils.createTitle(EnumChatFormatting.RED + "WATCHER READY", 2);
        }
    }
}
