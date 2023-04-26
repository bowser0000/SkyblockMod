package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.utils.Utils;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HidePetCandy {

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.toolTip == null) return;

        if (ModConfig.hidePetCandy) {
            for (int i = 0; i < event.toolTip.size(); i++) {
                if (event.toolTip.get(i).endsWith("Pet Candy Used")) {
                    event.toolTip.remove(i);
                    event.toolTip.remove(i);
                    break;
                }
            }
        }
    }

}
