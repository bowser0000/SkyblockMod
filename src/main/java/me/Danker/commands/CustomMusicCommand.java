package me.Danker.commands;

import me.Danker.DankersSkyblockMod;
import me.Danker.features.CustomMusic;
import me.Danker.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CustomMusicCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "dsmmusic";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " <stop/reload/volume> [dungeonboss/bloodroom/dungeon/p2/p3/p4/p5/hub/island/dungeonhub/farmingislands/" +
                                                                "goldmine/deepcaverns/dwarvenmines/crystalhollows/spidersden/crimsonisle/end/park] [1-100]";
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
            return getListOfStringsMatchingLastWord(args, "stop", "reload", "volume");
        } else if (args.length == 2) {
            return getListOfStringsMatchingLastWord(args, "dungeonboss", "bloodroom", "dungeon", "hub", "island", "dungeonhub", "farmingislands", "goldmine",
                                                          "deepcaverns", "dwarvenmines", "crystalhollows", "spidersden", "crimsonisle", "end", "park");
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
                CustomMusic.reset();
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Stopped custom music."));
                break;
            case "reload":
                CustomMusic.init(DankersSkyblockMod.configDirectory);
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.MAIN_COLOUR + "Reloaded custom music."));
                break;
            case "volume":
                if (arg1.length < 3) {
                    player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
                    return;
                }

                int volume = Integer.parseInt(arg1[2]);

                switch (arg1[1].toLowerCase()) {
                    case "dungeonboss":
                        if (!CustomMusic.dungeonboss.setVolume(volume)) return;

                        CustomMusic.dungeonbossVolume = volume;
                        ConfigHandler.writeIntConfig("music", "DungeonBossVolume", volume);
                        break;
                    case "bloodroom":
                        if (!CustomMusic.bloodroom.setVolume(volume)) return;

                        CustomMusic.bloodroomVolume = volume;
                        ConfigHandler.writeIntConfig("music", "BloodRoomVolume", volume);
                        break;
                    case "dungeon":
                        if (!CustomMusic.dungeon.setVolume(volume)) return;

                        CustomMusic.dungeonVolume = volume;
                        ConfigHandler.writeIntConfig("music", "DungeonVolume", volume);
                        break;
                    case "p2":
                        if (!CustomMusic.phase2.setVolume(volume)) return;

                        CustomMusic.phase2Volume = volume;
                        ConfigHandler.writeIntConfig("music", "Phase2Volume", volume);
                        break;
                    case "p3":
                        if (!CustomMusic.phase3.setVolume(volume)) return;

                        CustomMusic.phase3Volume = volume;
                        ConfigHandler.writeIntConfig("music", "Phase3Volume", volume);
                        break;
                    case "p4":
                        if (!CustomMusic.phase4.setVolume(volume)) return;

                        CustomMusic.phase4Volume = volume;
                        ConfigHandler.writeIntConfig("music", "Phase4Volume", volume);
                        break;
                    case "p5":
                        if (!CustomMusic.phase5.setVolume(volume)) return;

                        CustomMusic.phase5Volume = volume;
                        ConfigHandler.writeIntConfig("music", "Phase5Volume", volume);
                        break;
                    case "hub":
                        if (!CustomMusic.hub.setVolume(volume)) return;

                        CustomMusic.hubVolume = volume;
                        ConfigHandler.writeIntConfig("music", "HubVolume", volume);
                        break;
                    case "island":
                        if (!CustomMusic.island.setVolume(volume)) return;

                        CustomMusic.islandVolume = volume;
                        ConfigHandler.writeIntConfig("music", "IslandVolume", volume);
                        break;
                    case "dungeonhub":
                        if (!CustomMusic.dungeonHub.setVolume(volume)) return;

                        CustomMusic.dungeonHubVolume = volume;
                        ConfigHandler.writeIntConfig("music", "DungeonHubVolume", volume);
                        break;
                    case "farmingislands":
                        if (!CustomMusic.farmingIslands.setVolume(volume)) return;

                        CustomMusic.farmingIslandsVolume = volume;
                        ConfigHandler.writeIntConfig("music", "FarmingIslandsVolume", volume);
                        break;
                    case "goldmine":
                        if (!CustomMusic.goldMine.setVolume(volume)) return;

                        CustomMusic.goldMineVolume = volume;
                        ConfigHandler.writeIntConfig("music", "GoldMineVolume", volume);
                        break;
                    case "deepcaverns":
                        if (!CustomMusic.deepCaverns.setVolume(volume)) return;

                        CustomMusic.deepCavernsVolume = volume;
                        ConfigHandler.writeIntConfig("music", "DeepCavernsVolume", volume);
                        break;
                    case "dwarvenmines":
                        if (!CustomMusic.dwarvenMines.setVolume(volume)) return;

                        CustomMusic.dwarvenMinesVolume = volume;
                        ConfigHandler.writeIntConfig("music", "DwarvenMinesVolume", volume);
                        break;
                    case "crystalhollows":
                        if (!CustomMusic.crystalHollows.setVolume(volume)) return;

                        CustomMusic.crystalHollowsVolume = volume;
                        ConfigHandler.writeIntConfig("music", "CrystalHollowsVolume", volume);
                        break;
                    case "spidersden":
                        if (!CustomMusic.spidersDen.setVolume(volume)) return;

                        CustomMusic.spidersDenVolume = volume;
                        ConfigHandler.writeIntConfig("music", "SpidersDenVolume", volume);
                        break;
                    case "crimsonisle":
                        if (!CustomMusic.crimsonIsle.setVolume(volume)) return;

                        CustomMusic.crimsonIsleVolume = volume;
                        ConfigHandler.writeIntConfig("music", "CrimsonIsleVolume", volume);
                        break;
                    case "end":
                        if (!CustomMusic.end.setVolume(volume)) return;

                        CustomMusic.endVolume = volume;
                        ConfigHandler.writeIntConfig("music", "EndVolume", volume);
                        break;
                    case "park":
                        if (!CustomMusic.park.setVolume(volume)) return;

                        CustomMusic.parkVolume = volume;
                        ConfigHandler.writeIntConfig("music", "ParkVolume", volume);
                        break;
                    default:
                        player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
                        return;
                }

                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.SECONDARY_COLOUR + arg1[1] + DankersSkyblockMod.MAIN_COLOUR + " was set to " + DankersSkyblockMod.SECONDARY_COLOUR + volume + "%"));
                break;
            default:
                player.addChatMessage(new ChatComponentText(DankersSkyblockMod.ERROR_COLOUR + "Usage: " + getCommandUsage(arg0)));
        }
    }

}
