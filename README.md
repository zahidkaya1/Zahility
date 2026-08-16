# Zahility

**Zahility** is a Minecraft Java Edition utility/Vanilla+ mod by Mehmet Zahid Kaya. Its goal is to add practical, gameplay-friendly items and mechanics that reduce repetitive work while keeping survival progression meaningful.

The first feature family is Terraform tools, beginning with the Terraform Snowball and a stronger Creative Terraform Snowball. Zahility is intentionally broader than throwable items: future releases can add utility tools, blocks, materials, quality-of-life mechanics, and other balanced additions.

## Current development target

- Minecraft: **1.21.1**
- Loader: **NeoForge**
- NeoForge: **21.1.244**
- Java: **21**
- Development version: **0.1.0**
- Mod ID: **`zahility`**

## v0.1.0 current status

Implemented:

- `zahility:terraform_snowball`
- `zahility:creative_terraform_snowball`
- Dedicated **Zahility** creative tab
- Turkish and English localization
- Terraform impact detection
- Natural-block-only terraform filtering through `#zahility:terraformable_blocks`
- Survival radius: **3 blocks**
- Creative radius: **8 blocks**
- Vanilla player snowballs and Snow Golem snowballs remain unaffected
- Survival crafting recipe: Dirt + Snowballs + Ender Pearl -> **8 Terraform Snowballs**
- Creative Terraform Snowball has no recipe

The current item textures are temporary vanilla placeholders and will be replaced with Zahility textures later.

## Development setup

Start the development client:

```powershell
.\gradlew.bat runClient
```

Compile Java:

```powershell
.\gradlew.bat compileJava
```

Build the mod:

```powershell
.\gradlew.bat build
```

The generated JAR is placed under `build/libs/`.

## Planned loader/version support

Primary development starts on **Minecraft 1.21.1 NeoForge**. Planned ports include Minecraft 1.20.1 Forge and newer NeoForge lines. Fabric support is planned after the primary codebase is stable.
- Terraform impact particles and sound
