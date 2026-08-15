package top.untoldstudio.simpleui.common.gui;

import top.untoldstudio.simpleui.common.Platform;

public final class WindowPixel {
    public static int getWindowPixelX(){
        return Platform.getPlatformProvider().getWindowWidth();
    }
    public static int getWindowPixelY(){
        return Platform.getPlatformProvider().getWindowHeight();
    }
    public static int getWindowPixelX(double xScale){
        return (int) Math.round(getWindowPixelX() * xScale);
    }
    public static int getWindowPixelY(double yScale){
        return (int) Math.round(getWindowPixelY() * yScale);
    }
    public static int getWindowPixelX(double xScale, int xOffset){
        return getWindowPixelX(xScale) + xOffset;
    }
    public static int getWindowPixelY(double yScale, int yOffset){
        return getWindowPixelY(yScale) + yOffset;
    }
}
