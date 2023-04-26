package me.Danker.features;

import cc.polyfrost.oneconfig.libs.universal.UResolution;
import me.Danker.config.ModConfig;
import me.Danker.events.ModInitEvent;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class FilletMagmafish {

    static Map<String, Integer> fillet = new HashMap<>();
    static int total = 0;

    @SubscribeEvent
    public void init(ModInitEvent event) {
        fillet.put("SULPHUR_SKITTER_BRONZE", 40);
        fillet.put("SULPHUR_SKITTER_SILVER", 60);
        fillet.put("SULPHUR_SKITTER_GOLD", 80);
        fillet.put("SULPHUR_SKITTER_DIAMOND", 120);
        fillet.put("OBFUSCATED_FISH_1_BRONZE", 16);
        fillet.put("OBFUSCATED_FISH_1_SILVER", 24);
        fillet.put("OBFUSCATED_FISH_1_GOLD", 32);
        fillet.put("OBFUSCATED_FISH_1_DIAMOND", 48);
        fillet.put("STEAMING_HOT_FLOUNDER_BRONZE", 20);
        fillet.put("STEAMING_HOT_FLOUNDER_SILVER", 28);
        fillet.put("STEAMING_HOT_FLOUNDER_GOLD", 40);
        fillet.put("STEAMING_HOT_FLOUNDER_DIAMOND", 60);
        fillet.put("GUSHER_BRONZE", 32);
        fillet.put("GUSHER_SILVER", 48);
        fillet.put("GUSHER_GOLD", 64);
        fillet.put("GUSHER_DIAMOND", 96);
        fillet.put("BLOBFISH_BRONZE", 4);
        fillet.put("BLOBFISH_SILVER", 8);
        fillet.put("BLOBFISH_GOLD", 12);
        fillet.put("BLOBFISH_DIAMOND", 16);
        fillet.put("OBFUSCATED_FISH_2_BRONZE", 40);
        fillet.put("OBFUSCATED_FISH_2_SILVER", 60);
        fillet.put("OBFUSCATED_FISH_2_GOLD", 80);
        fillet.put("OBFUSCATED_FISH_2_DIAMOND", 120);
        fillet.put("SLUGFISH_BRONZE", 40);
        fillet.put("SLUGFISH_SILVER", 60);
        fillet.put("SLUGFISH_GOLD", 80);
        fillet.put("SLUGFISH_DIAMOND", 120);
        fillet.put("FLYFISH_BRONZE", 32);
        fillet.put("FLYFISH_SILVER", 48);
        fillet.put("FLYFISH_GOLD", 64);
        fillet.put("FLYFISH_DIAMOND", 96);
        fillet.put("OBFUSCATED_FISH_3_BRONZE", 400);
        fillet.put("OBFUSCATED_FISH_3_SILVER", 700);
        fillet.put("OBFUSCATED_FISH_3_GOLD", 1000);
        fillet.put("OBFUSCATED_FISH_3_DIAMOND", 1300);
        fillet.put("LAVA_HORSE_BRONZE", 12);
        fillet.put("LAVA_HORSE_SILVER", 16);
        fillet.put("LAVA_HORSE_GOLD", 20);
        fillet.put("LAVA_HORSE_DIAMOND", 24);
        fillet.put("MANA_RAY_BRONZE", 40);
        fillet.put("MANA_RAY_SILVER", 60);
        fillet.put("MANA_RAY_GOLD", 80);
        fillet.put("MANA_RAY_DIAMOND", 120);
        fillet.put("VOLCANIC_STONEFISH_BRONZE", 20);
        fillet.put("VOLCANIC_STONEFISH_SILVER", 28);
        fillet.put("VOLCANIC_STONEFISH_GOLD", 40);
        fillet.put("VOLCANIC_STONEFISH_DIAMOND", 60);
        fillet.put("VANILLE_BRONZE", 80);
        fillet.put("VANILLE_SILVER", 120);
        fillet.put("VANILLE_GOLD", 160);
        fillet.put("VANILLE_DIAMOND", 240);
        fillet.put("SKELETON_FISH_BRONZE", 32);
        fillet.put("SKELETON_FISH_SILVER", 48);
        fillet.put("SKELETON_FISH_GOLD", 64);
        fillet.put("SKELETON_FISH_DIAMOND", 96);
        fillet.put("MOLDFIN_BRONZE", 32);
        fillet.put("MOLDFIN_SILVER", 48);
        fillet.put("MOLDFIN_GOLD", 64);
        fillet.put("MOLDFIN_DIAMOND", 96);
        fillet.put("SOUL_FISH_BRONZE", 32);
        fillet.put("SOUL_FISH_SILVER", 48);
        fillet.put("SOUL_FISH_GOLD", 64);
        fillet.put("SOUL_FISH_DIAMOND", 96);
        fillet.put("KARATE_FISH_BRONZE", 40);
        fillet.put("KARATE_FISH_SILVER", 60);
        fillet.put("KARATE_FISH_GOLD", 80);
        fillet.put("KARATE_FISH_DIAMOND", 120);
        fillet.put("GOLDEN_FISH_BRONZE", 400);
        fillet.put("GOLDEN_FISH_SILVER", 700);
        fillet.put("GOLDEN_FISH_GOLD", 1000);
        fillet.put("GOLDEN_FISH_DIAMOND", 1300);
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (!ModConfig.showTotalMagmafish || !Utils.inSkyblock) return;

        if (event.gui instanceof GuiInventory) {
            ItemStack[] inv = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;

            for (ItemStack stack : inv) {
                if (stack == null) continue;

                String id = Utils.getSkyblockItemID(stack);
                if (id == null) continue;

                total += Optional.ofNullable(fillet.get(id)).orElse(0) * stack.stackSize;
            }
        } else {
            total = 0;
        }
    }

    @SubscribeEvent
    public void onGuiScreenRender(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (!ModConfig.showTotalMagmafish || !Utils.inSkyblock) return;

        if (event.gui instanceof GuiInventory) {
            if (total == 0) return;

            NumberFormat nf = NumberFormat.getIntegerInstance(Locale.US);
            String display = EnumChatFormatting.BLUE + "Magmafish: " + nf.format(total);

            int width = UResolution.getScaledWidth();
            int height = (UResolution.getScaledHeight() - 222) / 2 + 10;

            RenderUtils.drawCenteredText(display, width, height, 1D);
        }
    }

}
