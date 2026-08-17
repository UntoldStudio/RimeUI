package top.untoldstudio.rimeui.core.data;

import top.untoldstudio.rimeui.core.MathTool;
import top.untoldstudio.rimeui.core.ui.MainUi;

public record ScaleOffset(double xScale, int xOffset, double yScale, int yOffset) {
    public static final ScaleOffset ZERO = new ScaleOffset(0, 0, 0, 0);

    public ScaleOffset withScale(double xScale, double yScale){
        return new ScaleOffset(xScale, xOffset, yScale, yOffset);
    }
    public ScaleOffset withOffset(int xOffset, int yOffset){
        return new ScaleOffset(xScale, xOffset, yScale, yOffset);
    }
    public ScaleOffset withXScale(double xScale){
        return withScale(xScale, yScale);
    }
    public ScaleOffset withYScale(double yScale){
        return withScale(xScale, yScale);
    }
    public ScaleOffset withXOffset(int xOffset){
        return withOffset(xOffset, yOffset);
    }
    public ScaleOffset withYOffset(int yOffset){
        return withOffset(xOffset, yOffset);
    }

    public static ScaleOffset fromScale(double xScale, double yScale) {
        return new ScaleOffset(xScale, 0, yScale, 0);
    }
    public static ScaleOffset fromOffset(int xOffset, int yOffset) {
        return new ScaleOffset(0, xOffset, 0, yOffset);
    }

    public ScaleOffset add(double xScale, int xOffset, double yScale, int yOffset){
        return new ScaleOffset(this.xScale + xScale, this.xOffset + xOffset, this.yScale + yScale, this.yOffset + yOffset);
    }
    public ScaleOffset addScale(double xScale, double yScale){
        return add(xScale, 0, yScale, 0);
    }
    public ScaleOffset addXScale(double xScale){
        return addScale(xScale, 0);
    }
    public ScaleOffset addYScale(double yScale){
        return addScale(0, yScale);
    }
    public ScaleOffset addOffset(int xOffset, int yOffset){
        return add(0, xOffset, 0, yOffset);
    }
    public ScaleOffset addXOffset(int xOffset){
        return addOffset(xOffset, 0);
    }
    public ScaleOffset addYOffset(int yOffset){
        return addOffset(0, yOffset);
    }
    public ScaleOffset add(ScaleOffset other){
        return add(other.xScale, other.xOffset, other.yScale, other.yOffset);
    }
    public ScaleOffset sub(double xScale, int xOffset, double yScale, int yOffset){
        return new ScaleOffset(this.xScale - xScale, this.xOffset - xOffset, this.yScale - yScale, this.yOffset - yOffset);
    }
    public ScaleOffset sub(ScaleOffset other){
        return sub(other.xScale, other.xOffset, other.yScale, other.yOffset);
    }

    public int getScaleXPixel(){
        return MathTool.round(MainUi.getInstance().getWindowWidth() * xScale);
    }
    public double getScaledX(){
        return (double)getXPixel() / (double) MainUi.getInstance().getWindowWidth();
    }
    public double getScaledY(){
        return (double)getYPixel() / (double)MainUi.getInstance().getWindowHeight();
    }
    public int getXPixel(){
        return getScaleXPixel() + xOffset;
    }
    public int getScaleYPixel(){
        return MathTool.round(MainUi.getInstance().getWindowHeight() * yScale);
    }
    public int getYPixel(){
        return getScaleYPixel() + yOffset;
    }
}
