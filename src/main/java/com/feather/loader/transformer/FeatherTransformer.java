package com.feather.loader.transformer;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import com.feather.loader.utils.VersionManager;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class FeatherTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        
        String currentVersion = VersionManager.getMinecraftVersion();
        String targetDFU = VersionManager.getTargetClass(currentVersion);
        String normalizedClassName = className.replace("/", ".");

        if (normalizedClassName.equals(targetDFU) || 
            className.equals("com/mojang/datafixers/DataFixerBuilder")) {
            return optimizePerformance(classfileBuffer, className);
        }
        
        return classfileBuffer;
    }

    private byte[] optimizePerformance(byte[] bytes, String className) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        boolean injected = false;
        for (MethodNode method : classNode.methods) {
            if (method.name.equals("create") || method.name.equals("get")) { 
                
                InsnList insns = new InsnList();
                insns.add(new InsnNode(Opcodes.ACONST_NULL));
                insns.add(new InsnNode(Opcodes.ARETURN));
                
                method.instructions.insert(insns);
                injected = true;
            }
        }

        if (injected) {
            System.out.println("[Feather Loader] [Performance] Patched: " + className);
        }

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
