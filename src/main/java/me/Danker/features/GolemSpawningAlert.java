package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GolemSpawningAlert {

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (message.contains(":")) return;

        if (ToggleCommand.golemAlertToggled) {
            if (message.contains("The ground begins to shake as an Endstone Protector rises from below!")) {
                Utils.createTitle(EnumChatFormatting.RED + "GOLEM SPAWNING!", 3);
            }
        }
    }

}
