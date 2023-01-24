package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.utils.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HidePlayerArmour {

    List<ItemStack> armour = new ArrayList<>();

    public static Pattern npcNameRegex = Pattern.compile("^[a-z0-9]{10}$");

    @SubscribeEvent
    public void onRenderLivingPre(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if (shouldHideArmor(event)) {
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

    public boolean isNPC(EntityPlayer player) {
        return player.getMaxHealth() == 20.0F && npcNameRegex.matcher(player.getName()).matches();
    }

    public boolean shouldHideArmor(RenderLivingEvent<EntityLivingBase> event) {
        return ModConfig.hideArmour
                && Utils.inSkyblock && event.entity instanceof EntityPlayer
                && (!ModConfig.hidePlayerArmourOnly || !isNPC(((EntityPlayer) event.entity)));
    }

    @SubscribeEvent
    public void onRenderLivingPost(RenderLivingEvent.Post<EntityLivingBase> event) {
        if (shouldHideArmor(event)) {
            EntityPlayer player = (EntityPlayer) event.entity;

            for (int i = 0; i < player.inventory.armorInventory.length; i++) {
                player.inventory.armorInventory[i] = armour.get(i);
            }

            armour.clear();
        }
    }

}
