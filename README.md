# Danker's Skyblock Mod
QOL changes that enhances your Hypixel Skyblock experience. Created to add features I couldn't find in a mod, or if the mod was paid (which is against the [Mojang TOS](https://account.mojang.com/documents/commercial_guidelines)).

Discord Server: https://discord.gg/QsEkNQS

## Incompatibilities
- Old Animations <2.6.4 - Frequent crashes
- Old Animations 2.6.4
  - Pet colors does not color slots
  - Catacombs F7 terminal solvers do not color slots
  - Enchanting solvers do not color slots

## Current features
- Guild party desktop notifications
- Coordinate and angle display
- Golden T10/T6/T4 enchant display
- Disable Spirit Sceptre messages
- Disable Midas Staff messages
- Disable Implosion messages
- Disable heal messages
- Disable ability cooldown messages
- Disable out of mana messages
- Disable kill combo messages
- Slayer item tracker
- Ghosts item tracker  
- RNGesus drop alerts
- Click anywhere on-screen to open Maddox
- Maddox menu keybind
- Block starting other slayer quests
- Slayer slain alert
- Fishing, jerry fishing, fishing festival, spooky fishing trackers
- Expertise kills in fishing rod lore
- Gemstones applied in item lore
- Catacombs trackers
- Dungeons puzzle solver (Riddle, trivia, blaze, creeper, water, tic tac toe, boulder, silverfish, ice walk)
- Dungeons timer (watcher, boss, deaths, and puzzle fails)
- Watcher ready message
- Catacombs F7 Stage 3 solvers (Starts with letter, select all colour, ignore arrows on sea lanterns)
- Find correct Livid (with graphic display of HP)
- Catacombs F6 and F7 Giant HP display
- Use custom music in supported locations
- Experimentation solvers (Ultrasequencer, Chronomatron, Superpairs)
- Hide tooltips in experiment addons
- Hide tooltips in Melody's Harp
- Pet background colors based on level
- Golem spawning alerts + 20 second timer
- Skill xp/hour tracker
- Show total skill xp instead of progress to next level
- Show time until century cakes run out
- Mythological event (Diana) tracker
- Low health alert in dungeons
- API commands
- Update checker
- Reparty command
- Auto accept reparty
- Highlight Slayer Bosses
- Highlight Arachne
- Highlight Skeleton Masters
- Show teammates in 30 block radius
- Hide pet candy in pet tooltip
- Custom name colors
- Crystal Hollows waypoints (with SkyblockExtras support)
- Ability cooldowns display
- Custom alerts based on chat

## Commands
- /dhelp - Returns this message in-game.
- /dsm - Opens the GUI for Danker's Skyblock Mod.
- /toggle <too many to list> - Toggles features. /toggle list returns values of every toggle.
- /setkey <key> - Sets API key.
- /getkey - Returns key set with /setkey and copies it to your clipboard.
- /loot <zombie/spider/wolf/enderman/fishing/catacombs/mythological/> [winter/festival/spooky/f(1-7)/session] - Returns loot received from slayer quests or fishing stats. /loot fishing winter returns winter sea creatures instead.
- /display <zombie/spider/wolf/enderman/fishing/catacombs/mythological/ghosts/auto/off> [winter/festival/spooky/f(1-7)/session] - Text display for trackers. /display fishing winter displays winter sea creatures instead. /display auto automatically displays the loot for the slayer quest you have active.
- /resetloot <zombie/spider/wolf/enderman/fishing/catacombs/mythological/confirm/cancel> -  - Resets loot for trackers. /resetloot confirm confirms the reset.
- /move <coords/display/dungeontimer/skill50/lividhp/caketimer/skilltracker/wateranswer/bonzotimer/golemtimer/teammatesinradius/gianthp/abilitycooldowns> <x> <y> - Moves text display to specified X and Y coordinates.
- /scale <coords/display/dungeontimer/skill50/lividhp/caketimer/skilltracker/wateranswer/bonzotimer/golemtimer/teammatesinradius/gianthp/abilitycooldowns> <scale (0.1 - 10)> - Scales text display to a specified multipler between 0.1x and 10x.
- /slayer [player] - Uses API to get slayer xp of a person. If no name is provided, it checks yours.
- /skill [player] - Uses API to get skill levels of a person. If no name is provided, it checks yours.
- /lobbyskills - Uses API to find the average skills of the lobby, as well the three players with the highest skill average.
- /guildof [player] - Uses API to get guild name and guild master of a person. If no name is provided, it checks yours.
- /petsof [player] - Uses API to get pets of a person. If no name is provided, it checks yours.
- /bank [player] - Uses API to get bank and purse coins of a person. If no name is provided, it checks yours.
- /armor [player] - Uses API to get armour of a person. If no name is provided, it checks yours.
- /dungeons [player] - Uses API to get dungeon levels of a person. If no name is provided, it checks yours.
- /weight [player] [lily] - Uses API to get weight of a person. If no name is provided, it checks yours. Adding lily uses lily's weight instead of Senither's.
- /importfishing - Imports your fishing stats from your latest profile to your fishing tracker using the API.
- /sbplayers - Uses API to find how many players are on each Skyblock island.
- /onlyslayer <zombie/spider/wolf/enderman> <1/2/3/4/5>
- /skilltracker <start/stop/reset> - Text display for skill xp/hour.
- /reparty - Disbands and reparties all members in the party
- /fairysouls - Check the fairysouls of a player
- /lobbybank - Uses API to find the average bank total of the lobby, as well the three players with the highest total money in the bank (and purse).
- /dsmmusic <stop/reload/volume> [dungeonboss/bloodroom/dungeon/hub/island/dungeonhub/farmingislands/goldmine/deepcaverns/dwarvenmines/crystalhollows/spidersden/blazingfortress/end/park] [1-100] - Stops, reloads or changes the volume of custom music.
- /player [player] - Uses API to find skills, slayers, coins and weight of a player.
- /reloadconfig - Reloads Danker's Skyblock Mod config.
- /reloaddsmrepo - Reloads Danker's Skyblock Mod repository.
- /dsmfarmlength <min coords> <max coords> - Sets coords to be used for end of farm alert.
- /hotmof [player] - Uses API to find total powder and HotM tree of a person. If no name is provided, it checks yours.
- /networth [player] - Uses API to find networth of a person. If no name is provided, it checks yours.

## Keybinds
- Open Maddox menu - M by default.
- Regular Ability - Numpad 4 by default.
- Start/Stop Skill Tracker - Numpad 5 by default.

## Custom Music
1. Place a music file with the given name in the `.minecraft/config/dsmmusic` folder:
  - Dungeon: `dungeon.wav`
  - Blood room: `bloodroom.wav`
  - Dungeon boss: `dungeonboss.wav`
  - Dungeon hub: `dungeonhub.wav`
  - Hub: `hub.wav`
  - Private Island: `island.wav`
  - Farming Islands: `farmingislands.wav`
  - Gold Mine: `goldmine.wav`
  - Deep Caverns: `deepcaverns.wav`
  - Dwarven Mines: `dwarvenmines.wav`
  - Crystal Hollows: `crystalhollows.wav`
  - Spider's Den: `spidersden.wav`
  - Blazing Fortress: `blazingfortress.wav`
  - The End: `end.wav`
  - The Park: `park.wav`
2. Either run `/dsmmusic reload` or restart your game.
3. Enable the custom music in `/dsm`.
4. (Optional) Change the volume of the music with `/dsmmusic volume`.

#### Shuffling
By adding numbers to the end of the file, you can have multiple music files for the same area. One of them will be randomly selected (the same song could play twice in a row). For example:
- dungeon.wav
- dungeon1.wav
- dungeon2.wav
- dungeon99.wav

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
[Zealot Counter](https://github.com/symt/zealot-counter/) | [Apache License](https://github.com/symt/zealot-counter/blob/master/LICENSE.md)
[NotEnoughUpdates](https://github.com/Moulberry/NotEnoughUpdates/) | [Creative Commons Public License](https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE)
[HyChat](https://github.com/Moulberry/Hychat) | [Creative Commons Public License](https://github.com/Moulberry/Hychat/blob/master/LICENSE)
[SkyblockCatia](https://github.com/SteveKunG/SkyBlockcatia) | [MIT License](https://github.com/SteveKunG/SkyBlockcatia/blob/1.8.9/LICENSE.md)
[MatterOverdrive (Legacy Edition)](https://bitbucket.org/hrznstudio/mo-legacy-edition/) | [GNU General Public License](https://bitbucket.org/hrznstudio/mo-legacy-edition/src/1.12.2/LICENSE.md)
