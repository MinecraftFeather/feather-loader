package com.feather.loader.api;

import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.matrix.MatrixStack;

public class RenderUtils {
    public static void drawText(MatrixStack matrixStack, String text, int x, int y, int color) {
        Minecraft mc = Minecraft.getInstance();
        int width = mc.getMainWindow().getScaledWidth();
        int height = mc.getMainWindow().getScaledHeight();
        int textWidth = mc.fontRenderer.getStringWidth(text);
        
        mc.fontRenderer.drawString(matrixStack, text, width - textWidth - 2, height - 10, 0xFFFFFF);
    }
}
