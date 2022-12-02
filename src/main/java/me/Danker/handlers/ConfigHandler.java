package me.Danker.handlers;

import me.Danker.DankersSkyblockMod;
import me.Danker.config.CfgConfig;
import me.Danker.config.JsonConfig;

public class ConfigHandler {

    public static void reloadConfig() {
        CfgConfig.reloadConfig();
        JsonConfig.reloadConfig();
        DankersSkyblockMod.config.reInitialize();
    }

}
