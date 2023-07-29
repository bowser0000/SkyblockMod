package me.Danker.commands.api;

import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.containers.GuiChestDynamic;
import me.Danker.handlers.APIHandler;
import me.Danker.handlers.HypixelAPIHandler;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class InventoryCommand extends CommandBase {

    public static GuiChestDynamic chest = null;

    @Override
    public String getCommandName() {
        return "inventory";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("inv");
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " [name]";
    }

    public static String usage(ICommandSender arg0) {
        return new InventoryCommand().getCommandUsage(arg0);
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
            player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking inventory of " + ModConfig.getColour(ModConfig.secondaryColour) + username));

            // Find stats of latest profile
            JsonObject profileResponse = HypixelAPIHandler.getLatestProfile(uuid);
            if (profileResponse == null) return;

            System.out.println("Fetching inventory...");
            JsonObject userObject = profileResponse.get("members").getAsJsonObject().get(uuid).getAsJsonObject();

            IInventory inventory = new InventoryBasic(username + "'s Inventory:", true, 63);

            ItemStack notEnabled = new ItemStack(Blocks.barrier, 1, 0);
            notEnabled.setStackDisplayName(EnumChatFormatting.RED + "Inventory API not enabled.");

            try {
                // Armour
                String armourBase64 = userObject.get("inv_armor").getAsJsonObject().get("data").getAsString();
                InputStream armourStream = new ByteArrayInputStream(Base64.getDecoder().decode(armourBase64));
                NBTTagCompound armour = CompressedStreamTools.readCompressed(armourStream);
                NBTTagList armourList = armour.getTagList("i", 10);

                for (int i = 0; i < armourList.tagCount(); i++) {
                    NBTTagCompound item = armourList.getCompoundTagAt(i);
                    if (item.hasNoTags()) continue;
                    inventory.setInventorySlotContents(7 - i * 2, ItemStack.loadItemStackFromNBT(item));
                }

                // Equipment
                if (userObject.has("equippment_contents")) {
                    String equipmentBase64 = userObject.get("equippment_contents").getAsJsonObject().get("data").getAsString();
                    InputStream equipmentStream = new ByteArrayInputStream(Base64.getDecoder().decode(equipmentBase64));
                    NBTTagCompound equipment = CompressedStreamTools.readCompressed(equipmentStream);
                    NBTTagList equipmentList = equipment.getTagList("i", 10);

                    for (int i = 0; i < equipmentList.tagCount(); i++) {
                        NBTTagCompound item = equipmentList.getCompoundTagAt(i);
                        if (item.hasNoTags()) continue;
                        inventory.setInventorySlotContents(16 - i * 2, ItemStack.loadItemStackFromNBT(item));
                    }
                } else {
                    for (int i = 10; i <= 16; i += 2) {
                        inventory.setInventorySlotContents(i, notEnabled);
                    }
                }

                // Border
                ItemStack glass = new ItemStack(Blocks.stained_glass_pane, 1, 15);
                glass.setStackDisplayName("");
                for (int i = 0; i < 27; i++) {
                    if (i < 8 && i % 2 == 1) continue;
                    if (i > 8 && i < 17 && i % 2 == 0) continue;
                    inventory.setInventorySlotContents(i, glass);
                }

                // Inventory
                if (userObject.has("inv_contents")) {
                    String invBase64 = userObject.get("inv_contents").getAsJsonObject().get("data").getAsString();
                    InputStream invStream = new ByteArrayInputStream(Base64.getDecoder().decode(invBase64));

                    NBTTagCompound inv = CompressedStreamTools.readCompressed(invStream);
                    NBTTagList invList = inv.getTagList("i", 10);

                    for (int i = 0; i < invList.tagCount(); i++) {
                        NBTTagCompound item = invList.getCompoundTagAt(i);
                        if (item.hasNoTags()) continue;
                        inventory.setInventorySlotContents(i < 9 ? i + 54 : i + 18, ItemStack.loadItemStackFromNBT(item));
                    }
                } else {
                    for (int i = 27; i < 63; i++) {
                        inventory.setInventorySlotContents(i, notEnabled);
                    }
                }
            } catch (IOException ex) {
                player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.errorColour) + "An error has occurred while reading inventory data. See logs for more info."));
                ex.printStackTrace();
            }

            chest = new GuiChestDynamic(player.inventory, inventory, new ResourceLocation("dsm", "textures/generic_63.png"));
            DankersSkyblockMod.guiToOpen = "inventory";
        }).start();
    }

}
