package com.feather.loader.utils;

import java.util.HashMap;
import java.util.Map;

public class VersionManager {
    private static final Map<String, String> DFU_MAPPINGS = new HashMap<>();

    static {
        DFU_MAPPINGS.put("1.16.5", "net/minecraft/util/datafixers/DataFixers");
        DFU_MAPPINGS.put("1.12.2", "net/minecraft/util/datafixers/DataFixers"); 
    }

    public static String getTargetClass(String currentVersion) {
        return DFU_MAPPINGS.getOrDefault(currentVersion, "net/minecraft/util/datafixers/DataFixers");
    }
    public static String getMinecraftVersion() {
        String version = System.getProperty("minecraft.version");
        return (version != null) ? version : "1.16.5";
    }
}
