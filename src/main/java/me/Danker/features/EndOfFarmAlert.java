package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
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

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (DankersSkyblockMod.tickAmount % 10 == 0) {
            if (ModConfig.endOfFarmAlert && Utils.isInScoreboard("Your Island")) {
                double x = player.posX;
                double z = player.posZ;
                double min = ModConfig.farmMinCoords;
                double max = ModConfig.farmMaxCoords;

                if (x <= min || x >= max || z <= min || z >= max) {
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
