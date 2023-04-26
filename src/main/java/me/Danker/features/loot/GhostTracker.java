package me.Danker.features.loot;

import me.Danker.config.CfgConfig;
import me.Danker.utils.Utils;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GhostTracker {

    public static int sorrows  = 0;
    public static int bagOfCashs  = 0;
    public static int voltas  = 0;
    public static int plasmas  = 0;
    public static int ghostlyBoots = 0;

    public static int sorrowSession = 0;
    public static int bagOfCashSession = 0;
    public static int voltaSession = 0;
    public static int plasmaSession = 0;
    public static int ghostlyBootsSession = 0;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        if (message.contains("RARE DROP!")) {
            if (message.contains("Sorrow")) {
                sorrows++;
                sorrowSession++;
                CfgConfig.writeIntConfig("ghosts", "sorrow", sorrows);
            }
            if (message.contains("Volta")) {
                voltas++;
                voltaSession++;
                CfgConfig.writeIntConfig("ghosts", "volta", voltas);
            }
            if (message.contains("Plasma")) {
                plasmas++;
                plasmaSession++;
                CfgConfig.writeIntConfig("ghosts", "plasma", plasmas);
            }
            if (message.contains("Ghostly Boots")) {
                ghostlyBoots++;
                ghostlyBootsSession++;
                CfgConfig.writeIntConfig("ghosts", "ghostlyBoots", ghostlyBoots);
            }
            if (message.contains("The ghost's death materialized ")) {
                bagOfCashs++;
                bagOfCashSession++;
                CfgConfig.writeIntConfig("ghosts", "bagOfCash", bagOfCashs);
            }
        }
    }

}
