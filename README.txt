Zahility - Creative Terraform Snowball Rename Patch

Amaç:
- creative_terraform_snowball -> creative_terraform_snowball
- CREATIVE_TERRAFORM_SNOWBALL -> CREATIVE_TERRAFORM_SNOWBALL
- Creative Terraform Snowball -> Creative Terraform Snowball
- Creative Terraform Küresi -> Creative Terraform Kar Topu
- Model dosyası da yeni registry ID ile yeniden adlandırılır.

Kurulum:
1. Minecraft / runClient kapalı olsun.
2. rename-creative-terraform-snowball.ps1 dosyasını Zahility proje köküne koy.
3. PowerShell'de çalıştır:
   powershell -ExecutionPolicy Bypass -File .\rename-creative-terraform-snowball.ps1
4. Derle:
   .\gradlew.bat compileJava
5. Oyunu aç:
   .\gradlew.bat runClient
6. Test:
   /give @s zahility:terraform_snowball 16
   /give @s zahility:creative_terraform_snowball 16

Not:
Bu script UTF-8 BOM eklemez; önceki settings.gradle BOM sorununun tekrarlanmaması için dosyaları UTF-8 BOM'suz yazar.
