package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.locations.Location;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class FishingSpawnAlerts {

    static boolean lastThunder = false;
    static boolean lastJawbus = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        World world = Minecraft.getMinecraft().theWorld;
        if (DankersSkyblockMod.tickAmount % 10 == 0) {
            if (ModConfig.fishingAlert && Utils.currentLocation == Location.CRIMSON_ISLE && world != null) {
                boolean thunder = false;
                boolean jawbus = false;
                List<Entity> entities = world.getLoadedEntityList();

                for (Entity entity : entities) {
                    if (entity instanceof EntityArmorStand) {
                        String name = StringUtils.stripControlCodes(entity.getName());
                        if (name.contains("Thunder")) {
                            thunder = true;
                        } else if (name.contains("Lord Jawbus")) {
                            jawbus = true;
                        }
                    }
                }

                if (thunder && !lastThunder) Utils.createTitle(EnumChatFormatting.AQUA + "THUNDER", 2);
                if (jawbus && !lastJawbus) Utils.createTitle(EnumChatFormatting.AQUA + "JAWBUS", 2);

                lastThunder = thunder;
                lastJawbus = jawbus;
            }
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        lastThunder = false;
        lastJawbus = false;
    }

}
