package com.feather.loader;

import com.feather.loader.api.FeatherMod;
import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;
import java.util.ServiceLoader;

public class FeatherAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("---------------------------------------");
        System.out.println("  [Feather] Initializing...            ");
        
        File modsFolder = new File("mods");
        if (!modsFolder.exists()) modsFolder.mkdirs();

        File[] files = modsFolder.listFiles((dir, name) -> name.endsWith(".jar"));

        if (files != null) {
            for (File file : files) {
                try {
                    inst.appendToSystemClassLoaderSearch(new JarFile(file));
                } catch (Exception e) {
                    System.err.println("  [Feather] Error loading: " + file.getName());
                }
            }
        }

        try {
            ServiceLoader<FeatherMod> loader = ServiceLoader.load(FeatherMod.class);
            for (FeatherMod mod : loader) {
                mod.onInitialize();
                System.out.println("  [Feather] Mod Initialized!          ");
            }
        } catch (Exception e) {
            System.out.println("  [Feather] Note: No ServiceProvider mods found yet.");
        }

        System.out.println("  [Feather] Ready to fly!              ");
        System.out.println("---------------------------------------");
    }
}
