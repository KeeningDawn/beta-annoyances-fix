# Modern Beta Annoyances Fix

## Purpose

Playing on Modern Beta (a server that emulates Beta 1.7.3 mechanics) with a modern-version client has some leftover annoyances that can't be properly fixed without a mod. These are gaps Modern Beta's own server-side enforcement doesn't cover, so the client and server disagree, causing wasted actions, phantom placements, and other desyncs. Some of these are hard to notice unless you're using other mods (e.g. ClientSort), but they're annoying enough to warrant fixes anyway.

*Not affiliated with or endorsed by Modern Beta LLC. Modern Beta and related trademarks are the property of their respective owners.*

## Fixes

All fixes are individually toggleable in the config screen if Mod Menu is installed.

### Stack Sizes

- Cookies stack to 8.
- All other food (Bread, Raw Fish, Cooked Fish, Raw Porkchop, Cooked Porkchop) stack to 1.
- Eggs stack to 16.
- Miscellaneous items (Door, Sign) stack to 1.

### Placement & Interaction

- Logs...
    - cannot be stripped
    - can only be placed upright
- Trapdoors can only be placed on the bottom half against a solid block (not freestanding, not on slabs/stairs.)
- Slabs can only be placed on the bottom half of a block.
- Stairs can only be placed upright, and never form corners.
- Fences can only connect to other fences.
- Grass cannot be flattened into paths.
- Items can no longer be moved to the off-hand slot.