package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.CustomMusic;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

public class CustomMusicCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "dsmmusic";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " <stop/reload>";
    }

    public static String usage(ICommandSender arg0) {
        return new CustomMusicCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "stop", "reload");
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        final EntityPlayer player = (EntityPlayer)arg0;

        if (arg1.length == 0) {
            player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
            return;
        }

        switch (arg1[0].toLowerCase()) {
            case "stop":
                if (CustomMusic.dungeonboss != null) CustomMusic.dungeonboss.stop();
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Stopped custom music."));
                break;
            case "reload":
                try {
                    CustomMusic.init(DankersSkyblockMod.configDirectory);
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Reloaded custom music."));
                } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "An error occurred while trying to reload music."));
                    e.printStackTrace();
                }
                break;
            default:
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
        }
    }

}
