package com.feather.loader.mappings;

import java.util.HashMap;
import java.util.Map;

public class MappingManager {
    private static final Map<String, String> classMap = new HashMap<>();

    public static void loadMappings() {
        classMap.put("net.minecraft.client.gui.screens.TitleScreen", "ayj");
    }

    public static String getObfuscatedName(String cleanName) {
        return classMap.getOrDefault(cleanName, cleanName).replace(".", "/");
    }
}
