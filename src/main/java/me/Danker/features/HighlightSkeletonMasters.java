package me.Danker.features;

import me.Danker.commands.ToggleCommand;
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
    public static int SKELETON_MASTER_COLOUR;

    @SubscribeEvent
    public void onRenderEntity(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (ToggleCommand.highlightSkeletonMasters && event.entity instanceof EntitySkeleton && Utils.inDungeons) {
            ItemStack helmet = event.entity.getCurrentArmor(3);
            if (helmet != null && helmet.getDisplayName().endsWith("Skeleton Master Helmet")) {
                skeletonMasters.add(event.entity);
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (ToggleCommand.highlightSkeletonMasters) {
            for (Entity skeletonMaster : skeletonMasters) {
                if (!skeletonMaster.isDead)
                    Utils.draw3DBox(skeletonMaster.getEntityBoundingBox(), SKELETON_MASTER_COLOUR, event.partialTicks);
            }
            skeletonMasters.clear();
        }
    }

}
