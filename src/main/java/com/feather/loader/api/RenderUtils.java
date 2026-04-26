package com.feather.loader.api;

import java.lang.reflect.Method;

public class RenderUtils {
    public static void drawText(Object matrixStack, String text, int x, int y, int color) {
        try {
            Class<?> mcClass = Class.forName("net.minecraft.client.Minecraft");
            Object mc = mcClass.getMethod("getInstance").invoke(null);
            
            Object fontRenderer = mcClass.getField("fontRenderer").get(mc);
            Object window = mcClass.getMethod("getMainWindow").invoke(mc);
            
            int width = (int) window.getClass().getMethod("getScaledWidth").invoke(window);
            int height = (int) window.getClass().getMethod("getScaledHeight").invoke(window);
            
            Method getStringWidth = fontRenderer.getClass().getMethod("getStringWidth", String.class);
            int textWidth = (int) getStringWidth.invoke(fontRenderer, text);
            Method drawString = fontRenderer.getClass().getMethod("drawString", 
                Class.forName("com.mojang.blaze3d.matrix.MatrixStack"), String.class, float.class, float.class, int.class);
            
            drawString.invoke(fontRenderer, matrixStack, text, (float)(width - textWidth - 2), (float)(height - 10), 0xFFFFFF);
            
        } catch (Exception e) {
        }
    }
}
