package com.feather.loader.transformer;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class FeatherTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if (className.equals("net/minecraft/client/gui/Font") || className.equals("net/minecraft/class_327")) {
            return injectArabicSupport(classfileBuffer);
        }
        return classfileBuffer;
    }

    private byte[] injectArabicSupport(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        for (MethodNode method : classNode.methods) {
            if (method.name.equals("draw") || method.name.equals("method_30744")) { 
                
                InsnList insns = new InsnList();
                insns.add(new VarInsnNode(Opcodes.ALOAD, 1)); 
                insns.add(new MethodInsnNode(Opcodes.INVOKESTATIC, 
                        "com/feather/loader/utils/ArabicUtils", 
                        "fixArabic", 
                        "(Ljava/lang/String;)Ljava/lang/String;", 
                        false));
                
                insns.add(new VarInsnNode(Opcodes.ASTORE, 1));

                method.instructions.insert(insns);
                System.out.println("[Feather] Successfully injected Arabic Fix into: " + method.name);
            }
        }

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
