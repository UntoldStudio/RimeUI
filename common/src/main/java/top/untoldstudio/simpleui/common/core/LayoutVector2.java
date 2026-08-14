package top.untoldstudio.simpleui.common.core;

import top.untoldstudio.simpleui.common.Platform;

import java.util.Objects;

/**
 * 这个对象不是正常的Vector2,注意了
 * @param xScale 相对于屏幕的X比例
 * @param xOffset X偏移
 * @param yScale 相对于屏幕的Y比例
 * @param yOffset Y偏移
 */
public record LayoutVector2(double xScale, int xOffset, double yScale, int yOffset) {
    public static final LayoutVector2 ZERO = new LayoutVector2(0, 0, 0, 0);

    public LayoutVector2 add(double xScale, int xOffset, double yScale, int yOffset) {
        return new LayoutVector2(this.xScale + xScale, this.xOffset + xOffset, this.yScale + yScale, this.yOffset + yOffset);
    }
    public LayoutVector2 add(LayoutVector2 other){
        return new LayoutVector2(xScale + other.xScale, this.xOffset + other.xOffset, this.yScale + other.yScale, this.yOffset + other.yOffset);
    }
    public LayoutVector2 sub(double xScale, int xOffset, double yScale, int yOffset) {
        return new LayoutVector2(this.xScale - xScale, this.xOffset - xOffset, this.yScale - yScale, this.yOffset - yOffset);
    }
    public LayoutVector2 sub(LayoutVector2 other){
        return new LayoutVector2(xScale - other.xScale, this.xOffset - other.xOffset, this.yScale - other.yScale, this.yOffset - other.yOffset);
    }
    public LayoutVector2 withX(double xScale){
        return new LayoutVector2(xScale, 0, yScale, yOffset);
    }
    public LayoutVector2 withX(int xOffset){
        return new LayoutVector2(0, xOffset, yScale, yOffset);
    }
    public LayoutVector2 withY(double yScale){
        return new LayoutVector2(xScale, xOffset, yScale, 0);
    }
    public LayoutVector2 withY(int yOffset){
        return new LayoutVector2(xScale, xOffset, 0, yOffset);
    }
    public LayoutVector2 withXScale(double xScale){
        return new LayoutVector2(xScale, xOffset, yScale, yOffset);
    }
    public LayoutVector2 withXOffset(int xOffset){
        return new LayoutVector2(xScale, xOffset, yScale, yOffset);
    }
    public LayoutVector2 withYScale(double yScale){
        return new LayoutVector2(xScale, xOffset, yScale, yOffset);
    }
    public LayoutVector2 withYOffset(int yOffset){
        return new LayoutVector2(xScale, xOffset, yScale, yOffset);
    }

    public int getXAllPixel(){
        return (int) Math.round(Platform.getPlatformProvider().getWindowWidth() * xScale) + xOffset;
    }
    public int getYAllPixel(){
        return (int) Math.round(Platform.getPlatformProvider().getWindowHeight() * yScale) + yOffset;
    }
    public double getAllScaleX(){
        return (double) getXAllPixel() / (double)Platform.getPlatformProvider().getWindowWidth();
    }
    public double getAllScaleY(){
        return (double) getYAllPixel() / (double)Platform.getPlatformProvider().getWindowHeight();
    }
    public double getXScale(){
        return xScale;
    }
    public double getYScale(){
        return yScale;
    }
    public int getXOffset(){
        return xOffset;
    }
    public int getYOffset(){
        return yOffset;
    }

    public static LayoutVector2 fromScale(double xScale, double yScale){
        return new LayoutVector2(xScale, 0, yScale, 0);
    }
    public static LayoutVector2 fromOffset(int xOffset, int yOffset){
        return new LayoutVector2(0, xOffset, 0, yOffset);
    }

    @Override
    public boolean equals(Object other){
        if (other instanceof LayoutVector2 vector2){
            return vector2.getXScale() == getXScale() && vector2.getYScale() == getYScale() && vector2.getXOffset() == getXOffset() && vector2.getYOffset() == getYOffset();
        } else {
            return false;
        }
    }
    @Override
    public int hashCode(){
        return Objects.hash(getXScale(), getYScale(), getXOffset(), getYOffset());
    }
}
