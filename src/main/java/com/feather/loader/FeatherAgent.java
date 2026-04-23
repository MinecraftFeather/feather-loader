package com.feather.loader;

import com.feather.loader.transformer.FeatherTransformer;
import com.feather.loader.mappings.MappingManager;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.util.Scanner;

public class FeatherAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("---------------------------------------");
        System.out.println("  [Feather] Initializing Engine...     ");
        
        MappingManager.loadMappings();
        System.out.println("  [Feather] Mappings Loaded");

        File modsFolder = new File("mods");
        if (!modsFolder.exists()) {
            modsFolder.mkdirs();
        }

        File[] files = modsFolder.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files != null) {
            for (File file : files) {
                loadMod(file, inst);
            }
        }

        try {
            inst.addTransformer(new FeatherTransformer());
            System.out.println("  [Feather] Bytecode Transformer Active");
        } catch (Exception e) {
            System.err.println("  [Feather] Failed to initialize Transformer");
        }

        System.out.println("  [Feather] Engine Ready to fly!       ");
        System.out.println("---------------------------------------");
    }

    private static void loadMod(File file, Instrumentation inst) {
        try {
            JarFile jarFile = new JarFile(file);
            inst.appendToSystemClassLoaderSearch(jarFile);
            
            JarEntry entry = jarFile.getJarEntry("feather.mod.json");
            if (entry != null) {
                Scanner s = new Scanner(jarFile.getInputStream(entry)).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                String mainClass = parseJsonField(result, "main");
                String modName = parseJsonField(result, "name");

                if (mainClass != null) {
                    Class<?> clazz = Class.forName(mainClass);
                    Object modInstance = clazz.getDeclaredConstructor().newInstance();
                    clazz.getMethod("init").invoke(modInstance);
                    
                    System.out.println("  [Feather] Mod [" + modName + "] Initialized");
                }
            }
            jarFile.close();
        } catch (Exception e) {
            System.err.println("  [Feather] Error loading mod " + file.getName() + ": " + e.getMessage());
        }
    }
    private static String parseJsonField(String json, String field) {
        try {
            String key = "\"" + field + "\":";
            int start = json.indexOf(key) + key.length();
            int firstQuote = json.indexOf("\"", start) + 1;
            int lastQuote = json.indexOf("\"", firstQuote);
            return json.substring(firstQuote, lastQuote);
        } catch (Exception e) {
            return null;
        }
    }
}
