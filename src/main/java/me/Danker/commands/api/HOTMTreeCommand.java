package me.Danker.commands.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.Danker.DankersSkyblockMod;
import me.Danker.config.ModConfig;
import me.Danker.containers.GuiChestDynamic;
import me.Danker.handlers.APIHandler;
import me.Danker.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class HOTMTreeCommand extends CommandBase {

    public static GuiChestDynamic chest = null;

    @Override
    public String getCommandName() {
        return "hotmtree";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return null;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        // MULTI THREAD DRIFTING
        new Thread(() -> {
            EntityPlayer player = (EntityPlayer) arg0;

            player.addChatMessage(new ChatComponentText(ModConfig.getColour(ModConfig.mainColour) + "Checking HotM tree of " + ModConfig.getColour(ModConfig.secondaryColour) + arg1[0]));

            System.out.println("Fetching profile...");
            String profileURL = "https://sky.shiiyu.moe/api/v2/profile/" + arg1[0];
            JsonObject profileResponse = APIHandler.getResponse(profileURL, true);
            if (profileResponse.has("error")) {
                String reason = profileResponse.get("error").getAsString();
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Failed with reason: " + reason));
                return;
            }

            System.out.println("Fetching HotM tree...");
            JsonArray tree = profileResponse.get("profiles").getAsJsonObject().get(arg1[1]).getAsJsonObject().get("items").getAsJsonObject().get("hotm").getAsJsonArray();

            IInventory inventory = new InventoryBasic(arg1[0] + "'s HotM Tree:", true, 63);

            for (JsonElement e : tree) {
                JsonObject node = e.getAsJsonObject();

                if (!node.has("tag")) continue;

                ItemStack item = new ItemStack(Item.getItemById(node.get("id").getAsInt()), node.get("Count").getAsInt(), node.get("Damage").getAsInt());

                NBTTagCompound nbt = new NBTTagCompound();
                try {
                    nbt = JsonToNBT.getTagFromJson(node.get("tag").toString());
                    removeDoubleQuotes(nbt);
                    nbt.getCompoundTag("display").getTagList("Lore", Constants.NBT.TAG_STRING).removeTag(0);
                } catch (NBTException ex) {
                    ex.printStackTrace();
                    continue;
                }

                if (node.get("glowing").getAsBoolean()) {
                    nbt.setTag("HideFlags", new NBTTagShort((short) 1));
                }

                if (node.has("texture_path")) {
                    String path = node.get("texture_path").getAsString();
                    NBTTagCompound skullOwner = new NBTTagCompound();
                    NBTTagCompound properties = new NBTTagCompound();
                    NBTTagList textures = new NBTTagList();
                    NBTTagCompound value = new NBTTagCompound();
                    String texture = "{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + path.substring(path.lastIndexOf("/") + 1) + "\"}}}";
                    value.setTag("Value", new NBTTagString(Base64.getEncoder().encodeToString(texture.getBytes())));
                    textures.appendTag(value);
                    properties.setTag("textures", textures);
                    skullOwner.setTag("Properties", properties);
                    skullOwner.setTag("Id", new NBTTagString(UUID.randomUUID().toString()));
                    nbt.setTag("SkullOwner", skullOwner);
                }

                item.setTagCompound(nbt);

                if (node.get("glowing").getAsBoolean()) {
                    item.addEnchantment(Enchantment.protection, 1);
                }
                item.setStackDisplayName(Utils.removeBold(item.getDisplayName()));

                inventory.setInventorySlotContents(node.get("position").getAsInt() - 1, item);
            }

            chest = new GuiChestDynamic(player.inventory, inventory, new ResourceLocation("dsm", "textures/generic_63.png"));
            DankersSkyblockMod.guiToOpen = "hotminventory";
        }).start();
    }

    // https://bitbucket.org/hrznstudio/mo-legacy-edition/src/4cc47b2a792cc2ef19eb7e5db0169706ea2e48dd/src/main/java/matteroverdrive/util/MOJsonHelper.java#lines-164:179
    public static void removeDoubleQuotes(NBTTagCompound tagCompound) {
        List<String> cachedKeyList = new ArrayList<>();
        cachedKeyList.addAll(tagCompound.getKeySet());
        for (String key : cachedKeyList) {
            NBTBase base = tagCompound.getTag(key);
            tagCompound.removeTag(key);

            key = key.replace("\"", "");
            if (base instanceof NBTTagCompound) {
                removeDoubleQuotes((NBTTagCompound) base);
            } else if (base instanceof NBTTagList) {
                removeDoubleQuotes((NBTTagList) base);
            }
            tagCompound.setTag(key, base);
        }
    }

    // https://bitbucket.org/hrznstudio/mo-legacy-edition/src/4cc47b2a792cc2ef19eb7e5db0169706ea2e48dd/src/main/java/matteroverdrive/util/MOJsonHelper.java#lines-181:189
    public static void removeDoubleQuotes(NBTTagList tagList) {
        for (int i = 0; i < tagList.tagCount(); i++) {
            if (tagList.get(i) instanceof NBTTagCompound) {
                removeDoubleQuotes((NBTTagCompound) tagList.get(i));
            } else if (tagList.get(i) instanceof NBTTagList) {
                removeDoubleQuotes((NBTTagList) tagList.get(i));
            }
        }
    }

}
