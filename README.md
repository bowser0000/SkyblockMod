# Danker's Skyblock Mod
QOL changes that enhances your Hypixel Skyblock experience. Created to add features I couldn't find in a mod, or if the mod was paid (which is against the [Mojang TOS](https://account.mojang.com/documents/commercial_guidelines)). If I find a free mod has a feature, I will not add the feature in this mod.

## Current features
- Guild party desktop notifications (toggleable)
- Coordinate and angle display (toggleable)
- Slayer item tracker

## Commands
- /toggle [gparty/coords/list] - Toggles features. /toggle list returns values of every toggle.
- /setkey [key] - Sets API key.
- /getkey - Returns key set with /setkey.
- /loot [zombie/spider/wolf] - Returns loot received from the slayer quest.
- /display [zombie/spider/wolf/off] - Text display for slayer tracker

### Notes
- Slayer tracker for token drops and 20% chance drops uses a 12x12x12 bounding box centered on the player to detect the drops. If you are out of the range of the item drop, it will not count on the tracker.
