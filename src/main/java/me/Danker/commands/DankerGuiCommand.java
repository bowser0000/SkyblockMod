package me.Danker.commands;

import cc.polyfrost.oneconfig.utils.IOUtils;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.features.loot.LootDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;

import java.util.Collections;
import java.util.List;

public class DankerGuiCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "dsm";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("dankersskyblockmod");
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName();
    }

    public static String usage(ICommandSender arg0) {
        return new DankerGuiCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        if (arg1.length > 0 && arg1[0].equalsIgnoreCase("debug")) {
            StringBuilder debug = new StringBuilder();
            debug.append("```md\n");
            debug.append("# Other Settings\n");
            debug.append("[Current Display][").append(ModConfig.getDisplay()).append("]\n");
            debug.append("[Auto Display][").append(LootDisplay.autoDisplay).append("]\n");
            debug.append("[Skill Tracker Visible][").append(ModConfig.skillTrackerHud.isEnabled()).append("]\n");
            debug.append("[Farm Length X][").append(ModConfig.farmMinX).append(" to ").append(ModConfig.farmMaxX).append("]\n");
            debug.append("[Farm Length Z][").append(ModConfig.farmMinZ).append(" to ").append(ModConfig.farmMaxZ).append("]\n");
            debug.append("# Problematic Mods\n");
            debug.append("[LabyMod][").append(DankersSkyblockMod.usingLabymod).append("]\n");
            debug.append("[OAM][").append(DankersSkyblockMod.usingOAM).append("]\n");
            debug.append("# Resource Packs\n");
            if (Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries().size() == 0) {
                debug.append("<None>\n");
            } else {
                for (ResourcePackRepository.Entry resource : Minecraft.getMinecraft().getResourcePackRepository().getRepositoryEntries()) {
                    debug.append("<").append(StringUtils.stripControlCodes(resource.getResourcePackName())).append(">\n");
                }
            }
            debug.append("```");
            IOUtils.copyStringToClipboard(debug.toString());
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Debug stats copied to clipboard."));
            return;
        }

        DankersSkyblockMod.config.openGui();
    }

}
