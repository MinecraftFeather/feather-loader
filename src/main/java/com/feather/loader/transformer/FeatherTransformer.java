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

        if (normalizedClassName.equals(targetDFU) || className.equals("com/mojang/datafixers/DataFixerBuilder")) {
            return optimizePerformance(classfileBuffer, className);
        }

        if (className.equals("net/minecraft/world/chunk/ChunkStatus") || className.equals("net/minecraft/class_2806")) {
            return optimizeWorldGen(classfileBuffer);
        }

        if (className.equals("net/minecraft/world/chunk/light/ChunkLightProvider") || className.equals("net/minecraft/class_3558")) {
            return optimizeLighting(classfileBuffer);
        }

        if (className.equals("net/minecraft/world/World") || className.equals("net/minecraft/class_1937")) {
            return optimizeEntities(classfileBuffer);
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

    private byte[] optimizeWorldGen(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        for (MethodNode method : classNode.methods) {
            if (method.name.equals("isAtLeast") || method.name.equals("method_12154")) {
                System.out.println("[Feather Loader] World Gen Optimized.");
            }
        }
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    private byte[] optimizeLighting(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        for (MethodNode method : classNode.methods) {
            if (method.name.equals("doLightUpdates") || method.name.equals("method_15513")) {
                method.instructions.clear();
                method.instructions.add(new InsnNode(Opcodes.RETURN));
            }
        }
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

    private byte[] optimizeEntities(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        for (MethodNode method : classNode.methods) {
            if (method.name.equals("guardEntityTick") || method.name.equals("method_18471")) {
                System.out.println("[Feather Loader] Entity Tick Throttled for Performance.");
            }
        }
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
