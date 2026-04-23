package com.feather.loader.mappings;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MappingManager {
    private static final Map<String, String> classMap = new HashMap<>();

    public static void loadMappings() {
        File mappingFile = new File("feather_mappings.txt");
        
        if (!mappingFile.exists()) {
            System.out.println("  [Feather] No mapping file found. Using defaults.");
            classMap.put("net.minecraft.client.gui.screens.TitleScreen", "net/minecraft/client/gui/screens/TitleScreen");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(mappingFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue;
                
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    classMap.put(parts[0].trim(), parts[1].trim());
                }
            }
            System.out.println("  [Feather] Loaded " + classMap.size() + " mappings from file.");
        } catch (IOException e) {
            System.err.println("  [Feather] Failed to read mappings: " + e.getMessage());
        }
    }

    public static String getObfuscatedName(String cleanName) {
        return classMap.getOrDefault(cleanName, cleanName).replace(".", "/");
    }
}
