# Natural Terraform Patch Kurulumu

Bu patch mevcut Minecraft 1.21.1 + NeoForge Zahility projesinin Ã¼zerine uygulanÄ±r.

## Eklenenler

- Terraform Snowball ve Creative Terraform Snowball yalnÄ±zca `#zahility:terraformable_blocks` etiketindeki doÄŸal bloklarÄ± Dirt'e dÃ¶nÃ¼ÅŸtÃ¼rÃ¼r.
- Survival yarÄ±Ã§apÄ±: 3 blok.
- Creative yarÄ±Ã§apÄ±: 8 blok.
- Normal oyuncu kar toplarÄ± ve Snow Golem kar toplarÄ± etkilenmez.
- Terraform Snowball iÃ§in ilk/prototip zor survival tarifi eklendi.
- Creative Terraform Snowball iÃ§in tarif yoktur.

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
- SonuÃ§ = 16x Terraform Snowball

Bu tarif daha sonra dengeleme aÅŸamasÄ±nda deÄŸiÅŸtirilebilir.

## Kurulum

ZIP'in iÃ§indekileri `C:\Users\mehme\Zahility` klasÃ¶rÃ¼nÃ¼n Ã¼zerine kopyala.

ArdÄ±ndan:

```powershell
cd C:\Users\mehme\Zahility
.\gradlew.bat compileJava
.\gradlew.bat runClient
```

## Test

```
/give @s zahility:terraform_snowball 16
/give @s zahility:creative_terraform_snowball 16
/give @s minecraft:snowball 16
```

Terraform toplarÄ± doÄŸal bloklarÄ± Dirt'e dÃ¶nÃ¼ÅŸtÃ¼rmeli; vanilla kar topu hiÃ§bir ÅŸeyi dÃ¶nÃ¼ÅŸtÃ¼rmemeli.
