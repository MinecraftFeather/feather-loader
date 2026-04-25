package com.feather.loader;

import com.feather.loader.transformer.FeatherTransformer;
import com.feather.loader.mappings.MappingManager;
import com.feather.loader.core.ModLoader;
import java.lang.instrument.Instrumentation;

public class FeatherAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("---------------------------------------");
        System.out.println("  [Feather] Initializing Engine...     ");
        
        MappingManager.loadMappings();
        System.out.println("  [Feather] Mappings Loaded");

        ModLoader.loadMods(inst);
        com.feather.loader.api.FeatherEvents.postInit();

        try {
            inst.addTransformer(new FeatherTransformer());
            System.out.println("  [Feather] Bytecode Transformer Active");
        } catch (Exception e) {
            System.err.println("  [Feather] Failed to initialize Transformer");
        }

        System.out.println("  [Feather] Engine Ready to fly!       ");
        System.out.println("---------------------------------------");
    }
}
