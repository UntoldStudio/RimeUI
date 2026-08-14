package top.untoldstudio.simpleui.event;

public final class MouseScrollEvent extends CanceledEvent {
    private final double xOffset;
    private final double yOffset;

    public MouseScrollEvent(double xOffset, double yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    public double getXOffset(){
        return xOffset;
    }
    public double getYOffset(){
        return yOffset;
    }
}
