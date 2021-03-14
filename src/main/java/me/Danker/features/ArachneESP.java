package me.Danker.features;

import me.Danker.DankersSkyblockMod;
import me.Danker.commands.ToggleCommand;
import me.Danker.handlers.ScoreboardHandler;
import me.Danker.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


import java.util.List;

/**
 * @author RabbitType99
 */

public class ArachneESP {


    static Entity arachne = null;
    static boolean arachneActive = true;
    public static final int ARACHANE_COLOUR =  0x00FF00;


    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        arachne = null;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        //if (!Utils.inSkyblock) return;
        if (event.phase != TickEvent.Phase.START) return;

        World world = Minecraft.getMinecraft().theWorld;
        if (DankersSkyblockMod.tickAmount % 2 == 0 && ToggleCommand.highlightArachne) {
            if (world != null) {
                if (!arachneActive) return;
                List<Entity> entities = world.getLoadedEntityList();
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                for (Entity e : entities) {
                    System.out.println(e.getName());
                    if (e.getName().contains("Arachne")) {
                        for (String s : scoreboard) {
                            if (ScoreboardHandler.cleanSB(s).contains("Spiders Den")) {
                                arachne = e;
                            }
                        }
                    }

                }
            }
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock) return;
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (message.contains("You dare call me, the queen of the dark, to accept you. I'll accept no excuses, you shall die!") || message.contains("Hahahahaha, YOU MORTAL FOOL! You dare to summon me at my full strength to this plane, you are seeking death. With this power, I will not let a weakling survive here, this land will be a brilliant place to start a new invasion of my empire. Tremble beneath me if you wish to survive!") || message.contains("placed an Arachne Keeper Fragment! Something is awakening! (4/4)")){
            arachneActive = true;
        }
        if (message.contains("You are lucky this time, you only called out a portion of my power, if you dared to my face me at my peak, you would not survive!") || message.contains("How can this be...how could I lose to a puny being like you...I shall get my revenge") || message.contains("Arachne is bored with your feeble attempt!" ) || message.contains (" dealt the final blow")){
            arachneActive = false;
        }
    }


    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (!Utils.inSkyblock) return;
                if (arachneActive && ToggleCommand.highlightArachne) {
                    if (arachne != null) {
                        AxisAlignedBB aabb = new AxisAlignedBB(arachne.posX - 0.5, arachne.posY - 1, arachne.posZ - 0.5, arachne.posX + 0.5, arachne.posY, arachne.posZ + 0.5);
                        Utils.draw3DBox(aabb, ARACHANE_COLOUR, event.partialTicks);
                    }
        }
    }

}

