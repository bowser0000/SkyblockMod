# Danker's Skyblock Mod
QOL changes that enhances your Hypixel Skyblock experience. Created to add features I couldn't find in a mod, or if the mod was paid (which is against the [Mojang TOS](https://account.mojang.com/documents/commercial_guidelines)).

Discord Server: https://discord.gg/QsEkNQS

## Incompatibilities
- Old Animations <2.6.4 - Frequent crashes
- Old Animations 2.6.4 - Pet colors does not work

## Current features
- Guild party desktop notifications (toggleable)
- Coordinate and angle display (toggleable, graphic display) (scalable)
- Golden T10/T6/T4 enchant display (toggleable)
- Block AOTD ability (toggleable)
- Disable Spirit Sceptre messages (toggleable)
- Slayer item tracker (with graphic display) (scalable)
- RNGesus drop alerts (toggleable)
- Click in chat to open Maddox (toggleable)
- Maddox Menu keybind
- Block starting other slayer quests
- Fishing tracker (with graphic display) (scalable)
- Expertise kills in fishing rod lore
- Dungeons tracker (with graphic display) (scalable)
- Pet background colors based on level
- Golem spawning alerts (toggleable)
- API commands
- Update checker

## Commands
- /dhelp - Returns this message in-game.
- /toggle <gparty/coords/golden/slayercount/rngesusalerts/splitfishing/chatmaddox/spiritbearalerts/aotd/sceptremessages/petcolors/dungeontimer/golemalerts/expertiselore/list> - Toggles features. /toggle list returns values of every toggle.
- /setkey <key> - Sets API key.
- /getkey - Returns key set with /setkey and copies it to your clipboard.
- /loot <zombie/spider/wolf/fishing/catacombs> [winter/f(1-5)/session] - Returns loot received from slayer quests or fishing stats. /loot fishing winter returns winter sea creatures instead.
- /display <zombie/spider/wolf/fishing/catacombs> [winter/f(1-5)/session] - Text display for trackers. /display fishing winter displays winter sea creatures instead.
- /resetloot <zombie/spider/wolf/fishing/catacombs/confirm/cancel> -  - Resets loot for trackers. /resetloot confirm confirms the reset.
- /move <coords/display/dungeontimer> <x> <y> - Moves text display to specified X and Y coordinates.
- /scale <coords/display/dungeontimer> <scale (0.1 - 10)> - Scales text display to a specified multipler between 0.1x and 10x.
- /slayer [player] - Uses API to get slayer xp of a person. If no name is provided, it checks yours.
- /skills [player] - Uses API to get skill levels of a person. If no name is provided, it checks yours.
- /guildof [player] - Uses API to get guild name and guild master of a person. If no name is provided, it checks yours.
- /petsof [player] - Uses API to get pets of a person. If no name is provided, it checks yours.
- /bank [player] - Uses API to get bank and purse coins of a person. If no name is provided, it checks yours.
- /armor [player] - Uses API to get armour of a person. If no name is provided, it checks yours.
- /dungeons [player] - Uses API to get dungeon levels of a person. If no name is provided, it checks yours.
- /importfishing - Imports your fishing stats from your latest profile to your fishing tracker using the API.
- /sbplayers - Uses API to find how many players are on each Skyblock island.
- /blockslayer <zombie/spider/wolf> <1/2/3/4> - Stops you from starting a slayer quest other than the one specified.

## Keybinds
- Open Maddox menu - M by default.

### Notes
- Slayer tracker for token drops and 20% chance drops uses a 12x12x12 bounding box centered on the player to detect the drops. If you are out of the range of the item drop, it will not count on the tracker.
- API commands may take a while depending on your internet connection. The API may also go down.
- If you use too many API commands too fast, you can and will get rate-limited.
- Importing fishing uses your sea creature kills, which may not always be exactly correct (e.x. someone else kills your sea creature).
