# Danker's Skyblock Mod
QOL changes that enhances your Hypixel Skyblock experience. Created to add features I couldn't find in a mod, or if the mod was paid (which is against the [Mojang TOS](https://account.mojang.com/documents/commercial_guidelines)).

Discord Server: https://discord.gg/QsEkNQS

## Incompatibilities
- Old Animations <2.6.4 - Frequent crashes
- Old Animations 2.6.4
  - Pet colors does not color slots
  - Catacombs F7 terminal solvers do not color slots
  - Ultrasequencer solver does not color slots

## Current features
- Guild party desktop notifications
- Coordinate and angle display
- Golden T10/T6/T4 enchant display
- Keybind to cycle Bonemerangs
- Block AOTD ability
- Block Livid Dagger ability
- Block placing Flower of Truth/Spirit Sceptre
- Block breaking "farm blocks"
- Disable Spirit Sceptre messages
- Disable Midas Staff messages
- Disable Implosion messages
- Disable heal messages
- Slayer item tracker
- RNGesus drop alerts
- Click anywhere on-screen to open Maddox
- Maddox menu keybind
- Block starting other slayer quests
- Fishing, jerry fishing, fishing festival, spooky fishing trackers
- Expertise kills in fishing rod lore
- Catacombs trackers
- Dungeons regular ability beybind
- Dungeons puzzle solver (Riddle, trivia, blaze, creeper, water, tic tac toe)
- Dungeons timer (watcher, boss, deaths, and puzzle fails)
- Watcher ready message
- Bonzo's Mask Cooldown Timer
- Catacombs F7 Stage 3 solvers (Starts with letter, select all colour, click in order, simon says, ignore arrows on sea lanterns)
- Find correct Livid (with graphic display of HP)
- Notify when Necron is stunned
- Experimentation solvers (Ultrasequencer, Chronomatron, Superpairs)
- Hide tooltips in experiment addons
- Pet background colors based on level
- Golem spawning alerts
- Skill xp/hour tracker
- Show total skill xp instead of progress to next level
- Show time until century cakes run out
- Mythological event (Diana) tracker
- Low health alert in dungeons
- API commands
- Update checker

## Commands
- /dhelp - Returns this message in-game.
- /dsm - Opens the GUI for Danker's Skyblock Mod.
- /toggle <gparty/coords/golden/slayercount/rngesusalerts/splitfishing/chatmaddox/spiritbearalerts/aotd/lividdagger/flowerweapons/sceptremessages/midasstaffmessages/implosionmessages/healmessages/petcolors/dungeontimer/golemalerts/expertiselore/skill50display/outlinetext/caketimer/lowhealthnotify/lividsolver/stopsalvagestarred/notifyslayerslain/necronnotifications/bonzotimer/blockbreakingfarms/bossbarfix/hideimplosionparticles/zombiesword/autoacceptreparty/hiddenjerryalert/threemanpuzzle/oruopuzzle/blazepuzzle/creeperpuzzle/waterpuzzle/tictactoepuzzle/watchermessage/startswithterminal/selectallterminal/clickinorderterminal/blockwrongterminalclicks/itemframeonsealanterns/ultrasequencer/chronomatron/superpairs/hidetooltipsinaddons/pickblock/list>- /setkey <key> - Sets API key.
- /getkey - Returns key set with /setkey and copies it to your clipboard.
- /loot <zombie/spider/wolf/fishing/catacombs> [winter/spooky/f(1-7)/session] - Returns loot received from slayer quests or fishing stats. /loot fishing winter returns winter sea creatures instead.
- /display <zombie/spider/wolf/fishing/catacombs/auto/off> [winter/spooky/f(1-7)/session] - Text display for trackers. /display fishing winter displays winter sea creatures instead. /display auto automatically displays the loot for the slayer quest you have active.
- /resetloot <zombie/spider/wolf/fishing/catacombs/confirm/cancel> -  - Resets loot for trackers. /resetloot confirm confirms the reset.
- /move <coords/display/dungeontimer/skill50/lividhp/caketimer/skilltracker/wateranswer> <x> <y> - Moves text display to specified X and Y coordinates.
- /scale <coords/display/dungeontimer/skill50/lividhp/caketimer/skilltracker/wateranswer> <scale (0.1 - 10)> - Scales text display to a specified multipler between 0.1x and 10x.
- /slayer [player] - Uses API to get slayer xp of a person. If no name is provided, it checks yours.
- /skills [player] - Uses API to get skill levels of a person. If no name is provided, it checks yours.
- /lobbyskills - Uses API to find the average skills of the lobby, as well the three players with the highest skill average.
- /guildof [player] - Uses API to get guild name and guild master of a person. If no name is provided, it checks yours.
- /petsof [player] - Uses API to get pets of a person. If no name is provided, it checks yours.
- /bank [player] - Uses API to get bank and purse coins of a person. If no name is provided, it checks yours.
- /armor [player] - Uses API to get armour of a person. If no name is provided, it checks yours.
- /dungeons [player] - Uses API to get dungeon levels of a person. If no name is provided, it checks yours.
- /importfishing - Imports your fishing stats from your latest profile to your fishing tracker using the API.
- /sbplayers - Uses API to find how many players are on each Skyblock island.
- /onlyslayer <zombie/spider/wolf> <1/2/3/4> - Stops you from starting a slayer quest other than the one specified.
- /skilltracker <start/stop/reset> - Text display for skill xp/hour.
- /reparty - Disbands and reparties all members in the party

## Keybinds
- Open Maddox menu - M by default.
- Start/Stop Skill Tracker - Numpad 5 by default.

### Notes
- Slayer tracker for token drops and 20% chance drops uses a 12x12x12 bounding box centered on the player to detect the drops. If you are out of the range of the item drop, it will not count on the tracker.
- API commands may take a while depending on your internet connection. The API may also go down.
- If you use too many API commands too fast, you can and will get rate-limited.
- Importing fishing uses your sea creature kills, which may not always be exactly correct (e.x. someone else kills your sea creature).

### Credits to Open Source Software
Credit to all the following open source software used in this mod.

Software | License
------------ | -------------
[SkyblockAddons](https://github.com/BiscuitDevelopment/SkyblockAddons/) | [MIT License](https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE)
[Zealot Counter](https://github.com/symt/zealot-counter/) | No License
[NotEnoughUpdates](https://github.com/Moulberry/NotEnoughUpdates/) | [Creative Commons Public License](https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE)
[ForgeHax](https://github.com/fr1kin/ForgeHax) | [MIT License](https://github.com/fr1kin/ForgeHax/blob/1.16/LICENSE)