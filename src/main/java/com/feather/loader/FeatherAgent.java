package com.feather.loader;

import java.lang.instrument.Instrumentation;

public class FeatherAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("---------------------------------------");
        System.out.println("  [Feather] Initializing Loader...     ");
        System.out.println("  [Feather] Scanning for mods...       ");
        System.out.println("  [Feather] Status: Light & Ready      ");
        System.out.println("---------------------------------------");
    }
}
