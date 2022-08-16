package me.Danker.features;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class HidePlayerArmour {

    List<ItemStack> armour = new ArrayList<>();

    @SubscribeEvent
    public void onRenderLivingPre(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (ToggleCommand.hideArmour && Utils.inSkyblock) {
            if (event.entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.entity;

                for (int i = 0; i < player.inventory.armorInventory.length; i++) {
                    if (player.inventory.armorInventory[i] != null) {
                        armour.add(player.inventory.armorInventory[i].copy());
                        player.inventory.armorInventory[i] = null;
                    } else {
                        armour.add(null);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderLivingPost(RenderLivingEvent.Post<EntityLivingBase> event) {
        if (ToggleCommand.hideArmour && Utils.inSkyblock) {
            if (event.entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.entity;

                for (int i = 0; i < player.inventory.armorInventory.length; i++) {
                    player.inventory.armorInventory[i] = armour.get(i);
                }

                armour.clear();
            }
        }
    }

}
