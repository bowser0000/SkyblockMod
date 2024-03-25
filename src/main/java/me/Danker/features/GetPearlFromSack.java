package me.Danker.features;

import me.Danker.config.ModConfig;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;

public class GetPearlFromSack {

    public static void onKey() {
        if (!Utils.inSkyblock) return;

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        ItemStack[] inv = player.inventory.mainInventory;
        int amountOfPearls = 0;

        for (ItemStack stack : inv) {
            if (stack == null) continue;

            String id = Utils.getSkyblockItemID(stack);
            if (id == null) continue;

            if (id.equals("ENDER_PEARL")) amountOfPearls += stack.stackSize;
        }

        if (amountOfPearls >= ModConfig.pearlAmount) return;
        player.sendChatMessage("/gfs Ender Pearl " + (ModConfig.pearlAmount - amountOfPearls));
    }

}
