package me.Danker.features.puzzlesolvers;

import me.Danker.commands.ToggleCommand;
import me.Danker.utils.Utils;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class TriviaSolver {

    static Map<String, String[]> triviaSolutions = new HashMap<>();
    static String[] triviaAnswers = null;
    public static String TRIVIA_WRONG_ANSWER_COLOUR;

    public static void init() {
        triviaSolutions.put("What is the status of The Watcher?", new String[]{"Stalker"});
        triviaSolutions.put("What is the status of Bonzo?", new String[]{"New Necromancer"});
        triviaSolutions.put("What is the status of Scarf?", new String[]{"Apprentice Necromancer"});
        triviaSolutions.put("What is the status of The Professor?", new String[]{"Professor"});
        triviaSolutions.put("What is the status of Thorn?", new String[]{"Shaman Necromancer"});
        triviaSolutions.put("What is the status of Livid?", new String[]{"Master Necromancer"});
        triviaSolutions.put("What is the status of Sadan?", new String[]{"Necromancer Lord"});
        triviaSolutions.put("What is the status of Maxor?", new String[]{"Young Wither"});
        triviaSolutions.put("What is the status of Goldor?", new String[]{"Wither Soldier"});
        triviaSolutions.put("What is the status of Storm?", new String[]{"Elementalist"});
        triviaSolutions.put("What is the status of Necron?", new String[]{"Wither Lord"});
        triviaSolutions.put("How many total Fairy Souls are there?", new String[]{"222 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Spider's Den?", new String[]{"19 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in The End?", new String[]{"12 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in The Barn?", new String[]{"7 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Mushroom Desert?", new String[]{"8 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Blazing Fortress?", new String[]{"19 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in The Park?", new String[]{"11 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Jerry's Workshop?", new String[]{"5 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Hub?", new String[]{"79 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in The Hub?", new String[]{"79 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Deep Caverns?", new String[]{"21 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Gold Mine?", new String[]{"12 Fairy Souls"});
        triviaSolutions.put("How many Fairy Souls are there in Dungeon Hub?", new String[]{"7 Fairy Souls"});
        triviaSolutions.put("Which brother is on the Spider's Den?", new String[]{"Rick"});
        triviaSolutions.put("What is the name of Rick's brother?", new String[]{"Pat"});
        triviaSolutions.put("What is the name of the Painter in the Hub?", new String[]{"Marco"});
        triviaSolutions.put("What is the name of the person that upgrades pets?", new String[]{"Kat"});
        triviaSolutions.put("What is the name of the lady of the Nether?", new String[]{"Elle"});
        triviaSolutions.put("Which villager in the Village gives you a Rogue Sword?", new String[]{"Jamie"});
        triviaSolutions.put("How many unique minions are there?", new String[]{"53 Minions"});
        triviaSolutions.put("Which of these enemies does not spawn in the Spider's Den?", new String[]{"Zombie Spider", "Cave Spider", "Wither Skeleton",
                "Dashing Spooder", "Broodfather", "Night Spider"});
        triviaSolutions.put("Which of these monsters only spawns at night?", new String[]{"Zombie Villager", "Ghast"});
        triviaSolutions.put("Which of these is not a dragon in The End?", new String[]{"Zoomer Dragon", "Weak Dragon", "Stonk Dragon", "Holy Dragon", "Boomer Dragon",
                "Booger Dragon", "Older Dragon", "Elder Dragon", "Stable Dragon", "Professor Dragon"});
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inDungeons) return;
        if (event.type == 2) return;

        if (ToggleCommand.oruoToggled) {
            if (message.contains("What SkyBlock year is it?")) {
                double currentTime = System.currentTimeMillis() /1000L;

                double diff = Math.floor(currentTime - 1560276000);

                int year = (int) (diff / 446400 + 1);
                triviaAnswers = new String[]{"Year " + year};
            } else {
                for (String question : triviaSolutions.keySet()) {
                    if (message.contains(question)) {
                        triviaAnswers = triviaSolutions.get(question);
                        break;
                    }
                }
            }

            // Set wrong answers to red and remove click events
            if (triviaAnswers != null && (message.contains("ⓐ") || message.contains("ⓑ") || message.contains("ⓒ"))) {
                boolean isSolution = false;
                for (String solution : triviaAnswers) {
                    if (message.contains(solution)) {
                        isSolution = true;
                        break;
                    }
                }
                if (!isSolution) {
                    char letter = message.charAt(5);
                    String option = message.substring(6);
                    event.message = new ChatComponentText("     " + EnumChatFormatting.GOLD + letter + TRIVIA_WRONG_ANSWER_COLOUR + option);
                }
            }
        }
    }

}
