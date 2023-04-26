package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class HighlightSkeletonMasters {

    static List<Entity> skeletonMasters = new ArrayList<>();

    @SubscribeEvent
    public void onRenderEntity(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (ModConfig.highlightSkeletonMasters && event.entity instanceof EntitySkeleton && Utils.isInDungeons()) {
            ItemStack helmet = event.entity.getCurrentArmor(3);
            if (helmet != null && helmet.getDisplayName().endsWith("Skeleton Master Helmet")) {
                skeletonMasters.add(event.entity);
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (ModConfig.highlightSkeletonMasters) {
            for (Entity skeletonMaster : skeletonMasters) {
                if (!skeletonMaster.isDead)
                    RenderUtils.draw3DBox(skeletonMaster.getEntityBoundingBox(), ModConfig.skeletonMasterBoxColour.getRGB(), event.partialTicks);
            }
            skeletonMasters.clear();
        }
    }

}
