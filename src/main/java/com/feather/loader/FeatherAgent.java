package com.feather.loader;

import com.feather.loader.api.FeatherMod;
import com.feather.loader.transformer.FeatherTransformer;
import com.feather.loader.mappings.MappingManager;
import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;
import java.util.ServiceLoader;

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
                try {
                    inst.appendToSystemClassLoaderSearch(new JarFile(file));
                    System.out.println("  [Feather] Injected: " + file.getName());
                } catch (Exception e) {
                    System.err.println("  [Feather] Failed to inject: " + file.getName());
                }
            }
        }

        try {
            inst.addTransformer(new FeatherTransformer());
            System.out.println("  [Feather] Bytecode Transformer Active");
        } catch (Exception e) {
            System.err.println("  [Feather] Failed to initialize Transformer");
        }
        
        try {
            ServiceLoader<FeatherMod> loader = ServiceLoader.load(FeatherMod.class);
            for (FeatherMod mod : loader) {
                mod.onInitialize();
                System.out.println("  [Feather] Mod Initialized Successfully");
            }
        } catch (Exception e) {
            System.out.println("  [Feather] Status: No ServiceProvider mods detected.");
        }

        System.out.println("  [Feather] Engine Ready to fly!       ");
        System.out.println("---------------------------------------");
    }
}
