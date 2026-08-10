$ErrorActionPreference = "Stop"

$base = "https://github.com/NeoForgeMDKs/MDK-1.21.1-ModDevGradle/raw/refs/heads/main"
$wrapperDir = Join-Path $PSScriptRoot "gradle\wrapper"
New-Item -ItemType Directory -Force -Path $wrapperDir | Out-Null

Write-Host "Downloading official NeoForge 1.21.1 Gradle wrapper..."
Invoke-WebRequest "$base/gradlew" -OutFile (Join-Path $PSScriptRoot "gradlew")
Invoke-WebRequest "$base/gradlew.bat" -OutFile (Join-Path $PSScriptRoot "gradlew.bat")
Invoke-WebRequest "$base/gradle/wrapper/gradle-wrapper.jar" -OutFile (Join-Path $wrapperDir "gradle-wrapper.jar")
Invoke-WebRequest "$base/gradle/wrapper/gradle-wrapper.properties" -OutFile (Join-Path $wrapperDir "gradle-wrapper.properties")

Write-Host "Gradle wrapper installed. Checking Java/Gradle..."
& (Join-Path $PSScriptRoot "gradlew.bat") --version

Write-Host ""
Write-Host "Setup complete. Start the dev client with:"
Write-Host "  .\gradlew.bat runClient"
