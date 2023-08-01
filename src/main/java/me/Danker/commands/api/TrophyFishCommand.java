package me.Danker.commands.api;

import com.google.gson.JsonObject;
import me.Danker.config.ModConfig;
import me.Danker.features.loot.TrophyFishTracker;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.HypixelAPIHandler;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class TrophyFishCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "trophyfish";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName();
    }

    public static String usage(ICommandSender arg0) {
        return new TrophyFishCommand().getCommandUsage(arg0);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return Utils.getMatchingPlayers(args[0]);
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        // MULTI THREAD DRIFTING
        new Thread(() -> {
            EntityPlayer player = (EntityPlayer) arg0;

            // Get UUID for Hypixel API requests
            String username;
            String uuid;
            if (arg1.length == 0) {
                username = player.getName();
                uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");
            } else {
                username = arg1[0];
                uuid = APIHandler.getUUID(username);
            }
            player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking trophy fish of " + ModConfig.getColour(ModConfig.secondaryColour) + username + ModConfig.getColour(ModConfig.mainColour) + " using Polyfrost's API."));

            // Find stats of latest profile
            JsonObject profileResponse = HypixelAPIHandler.getLatestProfile(uuid);
            if (profileResponse == null) return;

            System.out.println("Fetching trophy fish...");
            JsonObject trophyObject = profileResponse.get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("trophy_fish").getAsJsonObject();

            if (!trophyObject.has("total_caught")) {
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "This player has not fished a trophy fish."));
                return;
            }

            JsonObject fish = new JsonObject();
            fish.add("Sulphur Skitter", Utils.getTrophyFromAPI(trophyObject, "sulphur_skitter"));
            fish.add("Obfuscated 1", Utils.getTrophyFromAPI(trophyObject, "obfuscated_fish_1"));
            fish.add("Steaming-Hot Flounder", Utils.getTrophyFromAPI(trophyObject, "steaming_hot_flounder"));
            fish.add("Obfuscated 2", Utils.getTrophyFromAPI(trophyObject, "obfuscated_fish_2"));
            fish.add("Gusher", Utils.getTrophyFromAPI(trophyObject, "gusher"));
            fish.add("Blobfish", Utils.getTrophyFromAPI(trophyObject, "blobfish"));
            fish.add("Slugfish", Utils.getTrophyFromAPI(trophyObject, "slugfish"));
            fish.add("Obfuscated 3", Utils.getTrophyFromAPI(trophyObject, "obfuscated_fish_3"));
            fish.add("Flyfish", Utils.getTrophyFromAPI(trophyObject, "flyfish"));
            fish.add("Lavahorse", Utils.getTrophyFromAPI(trophyObject, "lava_horse"));
            fish.add("Mana Ray", Utils.getTrophyFromAPI(trophyObject, "mana_ray"));
            fish.add("Volcanic Stonefish", Utils.getTrophyFromAPI(trophyObject, "volcanic_stonefish"));
            fish.add("Vanille", Utils.getTrophyFromAPI(trophyObject, "vanille"));
            fish.add("Skeleton Fish", Utils.getTrophyFromAPI(trophyObject, "skeleton_fish"));
            fish.add("Moldfin", Utils.getTrophyFromAPI(trophyObject, "moldfin"));
            fish.add("Soul Fish", Utils.getTrophyFromAPI(trophyObject, "soul_fish"));
            fish.add("Karate Fish", Utils.getTrophyFromAPI(trophyObject, "karate_fish"));
            fish.add("Golden Fish", Utils.getTrophyFromAPI(trophyObject, "golden_fish"));

            String tier = EnumChatFormatting.RED + "None";
            if (trophyObject.has("rewards")) {
                switch (trophyObject.get("rewards").getAsJsonArray().size()) {
                    case 1:
                        tier = EnumChatFormatting.DARK_GRAY + "Novice";
                        break;
                    case 2:
                        tier = EnumChatFormatting.GRAY + "Adept";
                        break;
                    case 3:
                        tier = EnumChatFormatting.GOLD + "Expert";
                        break;
                    case 4:
                        tier = EnumChatFormatting.AQUA + "Master";
                        break;
                }
            }

            player.addChatMessage(new ChatComponentText(ModConfig.getDelimiter() + "\n" +
                    EnumChatFormatting.GOLD + " Trophy Fish Rank: " + tier + "\n" +
                    EnumChatFormatting.WHITE + "  Sulphur Skitter " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Sulphur Skitter") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(fish, "Sulphur Skitter") + "\n" +
                    EnumChatFormatting.WHITE + "  Obfuscated 1 " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Obfuscated 1") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(fish, "Obfuscated 1") + "\n" +
                    EnumChatFormatting.WHITE + "  Steaminghot Flounder " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Steaming-Hot Flounder") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(fish, "Steaming-Hot Flounder") + "\n" +
                    EnumChatFormatting.WHITE + "  Gusher " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Gusher") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(fish, "Gusher") + "\n" +
                    EnumChatFormatting.WHITE + "  Blobfish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Blobfish") + ")" + EnumChatFormatting.WHITE + ": " + TrophyFishTracker.getTierCount(fish, "Blobfish") + "\n" +
                    EnumChatFormatting.GREEN + "  Obfuscated 2 " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Obfuscated 2") + ")" + EnumChatFormatting.GREEN + ": " + TrophyFishTracker.getTierCount(fish, "Obfuscated 2") + "\n" +
                    EnumChatFormatting.GREEN + "  Slugfish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Slugfish") + ")" + EnumChatFormatting.GREEN + ": " + TrophyFishTracker.getTierCount(fish, "Slugfish") + "\n" +
                    EnumChatFormatting.GREEN + "  Flyfish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Flyfish") + ")" + EnumChatFormatting.GREEN + ": " + TrophyFishTracker.getTierCount(fish, "Flyfish") + "\n" +
                    EnumChatFormatting.BLUE + "  Obfuscated 3 " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Obfuscated 3") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(fish, "Obfuscated 3") + "\n" +
                    EnumChatFormatting.BLUE + "  Lavahorse " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Lavahorse") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(fish, "Lavahorse") + "\n" +
                    EnumChatFormatting.BLUE + "  Mana Ray " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Mana Ray") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(fish, "Mana Ray") + "\n" +
                    EnumChatFormatting.BLUE + "  Volcanic Stonefish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Volcanic Stonefish") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(fish, "Volcanic Stonefish") + "\n" +
                    EnumChatFormatting.BLUE + "  Vanille " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Vanille") + ")" + EnumChatFormatting.BLUE + ": " + TrophyFishTracker.getTierCount(fish, "Vanille") + "\n" +
                    EnumChatFormatting.DARK_PURPLE + "  Skeleton Fish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Skeleton Fish") + ")" + EnumChatFormatting.DARK_PURPLE + ": " + TrophyFishTracker.getTierCount(fish, "Skeleton Fish") + "\n" +
                    EnumChatFormatting.DARK_PURPLE + "  Moldfin " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Moldfin") + ")" + EnumChatFormatting.DARK_PURPLE + ": " + TrophyFishTracker.getTierCount(fish, "Moldfin") + "\n" +
                    EnumChatFormatting.DARK_PURPLE + "  Soul Fish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Soul Fish") + ")" + EnumChatFormatting.DARK_PURPLE + ": " + TrophyFishTracker.getTierCount(fish, "Soul Fish") + "\n" +
                    EnumChatFormatting.DARK_PURPLE + "  Karate Fish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Karate Fish") + ")" + EnumChatFormatting.DARK_PURPLE + ": " + TrophyFishTracker.getTierCount(fish, "Karate Fish") + "\n" +
                    EnumChatFormatting.GOLD + "  Golden Fish " + EnumChatFormatting.DARK_GRAY + "(" + TrophyFishTracker.getSum(fish, "Golden Fish") + ")" + EnumChatFormatting.GOLD + ": " + TrophyFishTracker.getTierCount(fish, "Golden Fish") + "\n" +
                    ModConfig.getDelimiter()));
        }).start();
    }

}
