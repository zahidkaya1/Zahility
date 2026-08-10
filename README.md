# Special Snowballs

A Minecraft Java Edition mod focused on special throwable snowballs/orbs with unique gameplay and world-interaction effects.

## Current development target

- Minecraft: **1.21.1**
- Loader: **NeoForge**
- NeoForge: **21.1.244**
- Java: **21**
- Development version: **0.1.0**

## v0.1.0 prototype status

Implemented foundation:

- `specialsnowballs:terraform_snowball`
- `specialsnowballs:creative_terraform_orb`
- Dedicated **Special Snowballs** creative tab
- Turkish and English localization
- Separate item model IDs

The two items currently use temporary vanilla textures and vanilla snowball throwing behavior. The next milestone replaces the projectile layer with mod-owned projectile entities so normal snowballs and Snow Golem projectiles can never trigger Terraform behavior.

## Next milestone

1. Register a dedicated Terraform projectile entity.
2. Spawn it only from `terraform_snowball`.
3. Convert End Stone to Dirt on impact.
4. Add a stronger radius for the Creative Terraform Orb.
5. Verify vanilla player snowballs and Snow Golem snowballs remain untouched.

## Development setup

On Windows, run the included bootstrap script once to download the official NeoForge 1.21.1 Gradle wrapper:

```powershell
powershell -ExecutionPolicy Bypass -File .\bootstrap-gradle.ps1
```

Then start the development client:

```bat
gradlew.bat runClient
```

To build:

```bat
gradlew.bat build
```

The generated mod JAR will be under `build/libs/`.
