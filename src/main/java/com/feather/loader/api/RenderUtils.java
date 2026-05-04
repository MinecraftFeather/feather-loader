package com.feather.loader.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RenderUtils {
    public static void drawText(Object matrixStack, String text, int x, int y, int color) {
        try {
            Class<?> mcClass;
            try {
                mcClass = Class.forName("net.minecraft.client.Minecraft");
            } catch (ClassNotFoundException e) {
                mcClass = Class.forName("ekq");
            }

            Object mc = mcClass.getMethod(mcClass.getName().equals("ekq") ? "getInstance" : "getInstance").invoke(null);
            Object font;
            try {
                font = mcClass.getField("fontRenderer").get(mc);
            } catch (NoSuchFieldException e) {
                font = mcClass.getField("m").get(mc);
            }

            Object window = mcClass.getMethod(mcClass.getName().equals("ekq") ? "g" : "getMainWindow").invoke(mc);
            int width = (int) window.getClass().getMethod(window.getClass().getName().contains("p") ? "a" : "getScaledWidth").invoke(window);
            int height = (int) window.getClass().getMethod(window.getClass().getName().contains("p") ? "b" : "getScaledHeight").invoke(window);
            Method getStringWidth = font.getClass().getMethod(font.getClass().getName().contains("k") ? "a" : "getStringWidth", String.class);
            int textWidth = (int) getStringWidth.invoke(font, text);

            Method[] methods = font.getClass().getDeclaredMethods();
            for (Method m : methods) {
                if ((m.getName().equals("drawString") || m.getName().equals("a")) && m.getParameterCount() == 5) {
                    m.invoke(font, matrixStack, text, (float)(width - textWidth - 2), (float)(height - 10), 0xFFFFFF);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("[Feather] Render Error: " + e.getMessage());
        }
    }
}
