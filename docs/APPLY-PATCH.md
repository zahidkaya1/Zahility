# Natural Terraform Patch Kurulumu

Bu patch mevcut Minecraft 1.21.1 + NeoForge Special Snowballs projesinin üzerine uygulanır.

## Eklenenler

- Terraform Snowball ve Creative Terraform Orb yalnızca `#specialsnowballs:terraformable_blocks` etiketindeki doğal blokları Dirt'e dönüştürür.
- Survival yarıçapı: 3 blok.
- Creative yarıçapı: 8 blok.
- Normal oyuncu kar topları ve Snow Golem kar topları etkilenmez.
- Terraform Snowball için ilk/prototip zor survival tarifi eklendi.
- Creative Terraform Orb için tarif yoktur.

## Prototip tarif

```
E D E
D S D
E N E
```

- E = Echo Shard (4)
- D = Dragon's Breath (3)
- S = Snowball (1)
- N = Nether Star (1)
- Sonuç = 16x Terraform Snowball

Bu tarif daha sonra dengeleme aşamasında değiştirilebilir.

## Kurulum

ZIP'in içindekileri `C:\Users\mehme\Special-Snowballs` klasörünün üzerine kopyala.

Ardından:

```powershell
cd C:\Users\mehme\Special-Snowballs
.\gradlew.bat compileJava
.\gradlew.bat runClient
```

## Test

```
/give @s specialsnowballs:terraform_snowball 16
/give @s specialsnowballs:creative_terraform_orb 16
/give @s minecraft:snowball 16
```

Terraform topları doğal blokları Dirt'e dönüştürmeli; vanilla kar topu hiçbir şeyi dönüştürmemeli.
