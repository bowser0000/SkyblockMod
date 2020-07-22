# Danker's Skyblock Mod
QOL changes that enhances your Hypixel Skyblock experience. Created to add features I couldn't find in a mod, or if the mod was paid (which is against the [Mojang TOS](https://account.mojang.com/documents/commercial_guidelines)).

## Current features
- Guild party desktop notifications (toggleable)
- Coordinate and angle display (toggleable)
- Slayer item tracker (with GUI display)
- API commands

## Commands
- /dhelp - Returns this message in-game.
- /toggle <gparty/coords/list> - Toggles features. /toggle list returns values of every toggle.
- /setkey <key> - Sets API key.
- /getkey - Returns key set with /setkey.
- /loot <zombie/spider/wolf> - Returns loot received from slayer quests.
- /display <zombie/spider/wolf/off> - Text display for slayer tracker.
- /move <coords/display> <x> <y> - Moves text display to specified X and Y coordinates.
- /slayer [name] - Uses API to get slayer xp of a person. If no name is provided, it checks yours.
- /skills [name] - Uses API to get skill levels of a person. If no name is provided, it checks yours.
- /guildof [name] - Uses API to get guild name and guild master of a person. If no name is provided, it checks yours.
 - /pets [name] - Uses API to get pets of a person. If no name is provided, it checks yours.

### Notes
- Slayer tracker for token drops and 20% chance drops uses a 12x12x12 bounding box centered on the player to detect the drops. If you are out of the range of the item drop, it will not count on the tracker.
- API commands may take a while depending on your internet connection.
- If you use too many API commands too fast, you can and will get rate-limited.
- An incorrect API key will result in an HTTP error code of 402.
