package me.Danker.features;

import com.google.gson.GsonBuilder;
import me.Danker.commands.ToggleCommand;
import me.Danker.events.ChestSlotClickedEvent;
import me.Danker.events.ModInitEvent;
import me.Danker.events.PacketWriteEvent;
import me.Danker.utils.RenderUtils;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MinionLastCollected {

    public static List<Minion> minions = new ArrayList<>();
    public static String configFile;
    static BlockPos lastMinion = null;
    public static int LAST_COLLECTED_COLOUR;

    @SubscribeEvent
    public void init(ModInitEvent event) {
        configFile = event.configDirectory + "/dsmminions.json";
    }

    @SubscribeEvent
    public void onPacketWrite(PacketWriteEvent event) {
        if (ToggleCommand.minionLastCollected && Utils.inSkyblock && Utils.isInScoreboard("Your Island")) {
            if (event.packet instanceof C02PacketUseEntity) {
                C02PacketUseEntity packet = (C02PacketUseEntity) event.packet;
                Entity entity = packet.getEntityFromWorld(Minecraft.getMinecraft().theWorld);
                if (isAMinion(entity)) {
                    lastMinion = entity.getPosition();
                    if (getMinionFromPos(lastMinion) == null) {
                        minions.add(new Minion(lastMinion));
                        save();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onSlotClick(ChestSlotClickedEvent event) {
        if (ToggleCommand.minionLastCollected && Utils.tabLocation.equals("Private Island")) {
            String inventoryName = event.inventoryName;
            ItemStack item = event.item;
            if (inventoryName.contains(" Minion ") && item != null && lastMinion != null) {
                if (item.getDisplayName().contains("Collect All") || item.getDisplayName().contains("Hopper")) {
                    getMinionFromPos(lastMinion).collectNow();
                    save();
                } else if (item.getDisplayName().contains("Pickup Minion")) {
                    minions.remove(getMinionFromPos(lastMinion));
                    save();
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (ToggleCommand.minionLastCollected && Utils.inSkyblock && Utils.tabLocation.equals("Private Island")) {
            for (Minion minion : minions) {
                if (!minionExistsAtPos(minion.pos)) continue;
                RenderUtils.draw3DString(minion.pos.getX() + 0.5, minion.pos.getY() + 2.2, minion.pos.getZ() + 0.5, minion.getTimeCollected(), LAST_COLLECTED_COLOUR, event.partialTicks);
            }
        }
    }

    public boolean isAMinion(Entity entity) {
        if (!(entity instanceof EntityArmorStand)) return false;
        EntityArmorStand armourStand = (EntityArmorStand) entity;

        for (int i = 0; i <= 3; i++) {
            if (armourStand.getCurrentArmor(i) == null) return false;
        }

        return (Item.getIdFromItem(armourStand.getCurrentArmor(0).getItem()) == 301 &&
                Item.getIdFromItem(armourStand.getCurrentArmor(1).getItem()) == 300 &&
                Item.getIdFromItem(armourStand.getCurrentArmor(2).getItem()) == 299 &&
                Item.getIdFromItem(armourStand.getCurrentArmor(3).getItem()) == 397);
    }

    public Minion getMinionFromPos(BlockPos pos) {
        for (Minion minion : minions) {
            if (minion.pos.equals(pos)) return minion;
        }
        return null;
    }

    public boolean minionExistsAtPos(BlockPos pos) {
        AxisAlignedBB aabb = new AxisAlignedBB(pos, pos.add(1, 1, 1));
        List<EntityArmorStand> entities = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(EntityArmorStand.class, aabb);
        return entities.size() > 0; // just assume theres a minion there
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(configFile)) {
            new GsonBuilder().create().toJson(minions, writer);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static class Minion {

        public BlockPos pos;
        public double lastCollect;

        public Minion(BlockPos pos) {
            this.pos = pos;
            this.lastCollect = -1;
        }

        public String getTimeCollected() {
            String lastCollected = "Last Collected: ";
            if (lastCollect == -1) {
                return lastCollected + "Never";
            }
            return lastCollected + Utils.getTimeBetween(lastCollect, System.currentTimeMillis() / 1000) + " ago";
        }

        public void collectNow() {
            lastCollect = System.currentTimeMillis() / 1000;
        }

    }

}
