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
Implement the FeatherMod interface in your main class:

```java
package io.github.minecraftfeather.example;

import com.feather.loader.api.FeatherMod;

public class MyExampleMod implements FeatherMod {
    @Override
    public void onInitialize() {
        System.out.println("[Feather] Hello from my custom mod!");
    }
}
```

### 3. Register the Mod
Create the following file in your resources folder:

**src/main/resources/META-INF/services/com.feather.loader.api.FeatherMod**

Inside the file, write the full path to your class:

**io.github.minecraftfeather.example.MyExampleMod**


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

