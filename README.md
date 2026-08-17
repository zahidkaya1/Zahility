# Zahility

**Zahility** is a Minecraft Java Edition utility / Vanilla+ mod by Mehmet Zahid Kaya.

Its goal is to add practical gameplay-friendly items and mechanics that reduce repetitive work while keeping survival progression meaningful.

Zahility is not limited to throwable items. Future versions may include utility tools, blocks, materials, quality-of-life mechanics, terrain tools, and other balanced additions.

## Current development target

- Minecraft: **1.21.1**
- Loader: **NeoForge**
- NeoForge: **21.1.244**
- Java: **21**
- Development version: **0.1.0**
- Mod ID: **`zahility`**

## Current features

### Terraform Snowball

Converts supported natural terrain blocks into Dirt on impact.

Available variants:

- `zahility:terraform_snowball`
- `zahility:creative_terraform_snowball`

Features:

- Survival radius: **3 blocks**
- Creative radius: **8 blocks**
- Only supported natural blocks are affected
- Block filtering uses `#zahility:terraformable_blocks`
- Vanilla Snowballs are unaffected
- Snow Golem snowballs are unaffected
- Custom impact particles and sound
- Creative version uses the same texture with enchantment glint
- Creative version has no crafting recipe

Survival recipe output:

- **8 Terraform Snowballs**

### Leveling Snowball

Levels terrain to a player-selected Y coordinate.

Available variants:

- `zahility:leveling_snowball`
- `zahility:creative_leveling_snowball`

Features:

- Shift + Right Click a block to select the target Y level
- Normal area: **7×7**
- Creative area: **15×15**
- Terrain above the selected Y level is removed
- Terrain below the selected Y level is filled
- Up to **6 blocks below the target surface** are supported when necessary
- Deep caves and underground water do not interfere with leveling
- Surface water columns are protected
- Block Entity columns such as chests and furnaces are protected
- Natural vegetation can be cleared during leveling
- Creative version uses the same texture with enchantment glint
- Creative version has no crafting recipe

Survival recipe output:

- **4 Leveling Snowballs**

## Localization

Currently supported:

- English
- Turkish

## Development setup

Start the development client:

```powershell
.\gradlew.bat runClient