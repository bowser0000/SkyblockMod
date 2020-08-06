# Danker's Skyblock Mod
QOL changes that enhances your Hypixel Skyblock experience. Created to add features I couldn't find in a mod, or if the mod was paid (which is against the [Mojang TOS](https://account.mojang.com/documents/commercial_guidelines)).

## Current features
- Guild party desktop notifications (toggleable)
- Coordinate and angle display (toggleable)
- Golden T6/T4 enchant display (toggleable)
- Slayer item tracker (with graphic display)
- Fishing tracker (with graphic display)
- API commands
- Update checker

## Commands
- /dhelp - Returns this message in-game.
- /toggle <gparty/coords/golden/list> - Toggles features. /toggle list returns values of every toggle.
- /setkey <key> - Sets API key.
- /getkey - Returns key set with /setkey.
- /loot <zombie/spider/wolf/fishing> [winter/session] - Returns loot received from slayer quests or fishing stats. /loot fishing winter returns winter sea creatures instead.
- /display <zombie/spider/wolf/fishing/off> [winter/session] - Text display for trackers. /display fishing winter displays winter sea creatures instead.
- /move <coords/display> <x> <y> - Moves text display to specified X and Y coordinates.
- /resetloot <zombie/spider/wolf/fishing/confirm/cancel> -  - Resets loot for trackers. /resetloot confirm confirms the reset.
- /slayer [player] - Uses API to get slayer xp of a person. If no name is provided, it checks yours.
- /skills [player] - Uses API to get skill levels of a person. If no name is provided, it checks yours.
- /guildof [player] - Uses API to get guild name and guild master of a person. If no name is provided, it checks yours.
- /petsof [player] - Uses API to get pets of a person. If no name is provided, it checks yours.
- /bank [player] - Uses API to get bank and purse coins of a person. If no name is provided, it checks yours.
- /armor [player] - Uses API to get armour of a person. If no name is provided, it checks yours.
- /importfishing - Imports your fishing stats from your latest profile to your fishing tracker using the API.

### Notes
- Slayer tracker for token drops and 20% chance drops uses a 12x12x12 bounding box centered on the player to detect the drops. If you are out of the range of the item drop, it will not count on the tracker.
- API commands may take a while depending on your internet connection. The API may also go down.
- If you use too many API commands too fast, you can and will get rate-limited.
- An incorrect API key will result in an HTTP error code of 402.
- Importing fishing uses your sea creature kills, which may not always be exactly correct (e.x. someone else kills your sea creature).
