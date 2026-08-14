package top.untoldstudio.simpleui.neoforge.gui;

import net.minecraft.client.Minecraft;

public final class WindowPixel {
    public static int getWindowPixelX(){
        return Minecraft.getInstance().getWindow().getGuiScaledWidth();
    }
    public static int getWindowPixelY(){
        return Minecraft.getInstance().getWindow().getGuiScaledHeight();
    }
    public static int getWindowPixelX(double xScale){
        return (int) Math.round(Minecraft.getInstance().getWindow().getGuiScaledWidth() * xScale);
    }
    public static int getWindowPixelY(double yScale){
        return (int) Math.round(Minecraft.getInstance().getWindow().getGuiScaledHeight() * yScale);
    }
    public static int getWindowPixelX(double xScale, int xOffset){
        return getWindowPixelX(xScale) + xOffset;
    }
    public static int getWindowPixelY(double yScale, int yOffset){
        return getWindowPixelY(yScale) + yOffset;
    }
}
