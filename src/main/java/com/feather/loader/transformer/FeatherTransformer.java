package com.feather.loader.transformer;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

public class FeatherTransformer implements java.lang.instrument.ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            java.security.ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        
        if (className != null && (className.equals("net/minecraft/client/gui/screen/TitleScreen") || className.equals("djz"))) {
            return transformTitleScreen(classfileBuffer);
        }
        return classfileBuffer;
    }

    private byte[] transformTitleScreen(byte[] basicClass) {
        ClassReader reader = new ClassReader(basicClass);
        ClassNode node = new ClassNode();
        reader.accept(node, 0);

        for (MethodNode method : node.methods) {
            if (method.name.equals("render") || method.name.equals("a")) {
                InsnList insns = method.instructions;
                for (AbstractInsnNode insn : insns) {
                    if (insn.getOpcode() == Opcodes.RETURN) {
                        InsnList inject = new InsnList();
                        
                        inject.add(new VarInsnNode(Opcodes.ALOAD, 1)); 
                        inject.add(new LdcInsnNode("feather loader 1.0.0-BETA")); 
                        inject.add(new InsnNode(Opcodes.ICONST_0)); 
                        inject.add(new InsnNode(Opcodes.ICONST_0)); 
                        inject.add(new InsnNode(Opcodes.ICONST_M1)); 
                        
                        inject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/feather/loader/api/RenderUtils", "drawText", "(Ljava/lang/Object;Ljava/lang/String;III)V", false));
                        
                        insns.insertBefore(insn, inject);
                    }
                }
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        node.accept(writer);
        return writer.toByteArray();
    }
}
