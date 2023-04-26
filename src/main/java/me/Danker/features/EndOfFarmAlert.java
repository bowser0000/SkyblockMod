package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.locations.Location;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EndOfFarmAlert {

    static boolean alerted = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        if (DankersSkyblockMod.tickAmount % 10 == 0) {
            if (ModConfig.endOfFarmAlert && Utils.currentLocation == Location.GARDEN) {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                if (player == null) return;

                double x = player.posX;
                double z = player.posZ;

                if ((ModConfig.farmX && (x <= ModConfig.farmMinX || x >= ModConfig.farmMaxX)) ||
                    (ModConfig.farmZ && (z <= ModConfig.farmMinZ || z >= ModConfig.farmMaxZ))) {
                    if (!alerted) {
                        alerted = true;
                        Utils.createTitle(EnumChatFormatting.RED + "END OF FARM", 1);
                    }
                } else {
                    alerted = false;
                }
            }
        }
    }

}
