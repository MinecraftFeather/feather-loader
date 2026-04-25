package com.feather.loader.transformer;

import org.objectweb.asm.*;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class FeatherTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        return classfileBuffer;
    }
}
