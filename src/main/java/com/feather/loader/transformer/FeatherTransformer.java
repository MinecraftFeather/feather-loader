package com.feather.loader.transformer;

import com.feather.loader.mappings.MappingManager;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

public class FeatherTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        
        String target = MappingManager.getObfuscatedName("net.minecraft.client.gui.screens.TitleScreen");
        
        if (className != null && className.equals(target)) {
            System.out.println("[Feather] Modifying TitleScreen (" + className + ")...");
            return modifyBytecode(classfileBuffer);
        }
        
        return classfileBuffer;
    }

    private byte[] modifyBytecode(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
      
        for (MethodNode method : classNode.methods) {
            for (AbstractInsnNode insn : method.instructions) {
                if (insn instanceof LdcInsnNode ldc) {
                    if (ldc.cst instanceof String text && text.equals("Minecraft")) {
                        ldc.cst = "Minecraft (Feather)";
                    }
                }
            }
        }

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
