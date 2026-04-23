# 🪶 Feather Loader

The lightest, fastest mod loader for Minecraft Java Edition. Developed by the **MinecraftFeather** organization, specifically optimized for mobile-based environments and low-end hardware.

---

## ✨ Features
* **Ultra-Lightweight:** Zero-dependency core (using only Java's native Instrumentation and ASM).
* **Bytecode Transformation:** Modify game code on-the-fly.
* **External Mappings:** Easily update for new Minecraft versions via `feather_mappings.txt`.
* **SPI Support:** Standardized mod loading using Java Service Provider Interface.

---

## 🚀 Developer Guide

### 1. Installation (Gradle)
Add the Feather repository to your `build.gradle` to start developing mods:

```groovy
repositories {
    maven {
        url = "[https://maven.pkg.github.com/MinecraftFeather/feather-loader](https://maven.pkg.github.com/MinecraftFeather/feather-loader)"
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation 'io.github.minecraftfeather:feather-loader:1.0.0-SNAPSHOT'
}
```

### 2. Create Your Mod Entry Point 
this is example main class:

```java
package com.feather.example;

public class ExampleMod {
    public static void init() {
        System.out.println("=====================================");
        System.out.println("[Feather Mod] Hello from Example Mod!");
        System.out.println("=====================================");
    }
}
```

### 3. Register the Mod
Create the following file in your resources folder:

**src/main/resources/feather.mod.json**

Inside the file, write the full path to your class:

```json
{
  "id": "example-mod",
  "version": "1.0.0",
  "main": "com.feather.example.ExampleMod",
  "name": "Feather Example Mod"
}
```

---

## ​🗺️ Mappings Configuration

​To support obfuscated versions of Minecraft, place a feather_mappings.txt file in your game directory:

```text
# Format: CleanName=ObfuscatedName
net.minecraft.client.gui.screens.TitleScreen=ayj
```

---

## 🛠️ Usage (JVM Argument) 

​To run Minecraft with Feather, add the following to your JVM arguments (e.g., in PojavLauncher):

```bash
-javaagent:feather-loader.jar
```

---

## 📱 Optimization for Mobile

Feather was built with **(2GB RAM)** in mind. It ensures the lowest possible impact on system resources during game initialization.

---

​© 2026 **MinecraftFeather** Organization

