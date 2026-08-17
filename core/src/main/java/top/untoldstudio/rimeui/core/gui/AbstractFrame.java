package top.untoldstudio.rimeui.core.gui;

import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.signal.SignalType;

public abstract class AbstractFrame<T extends AbstractFrame<T>> extends GuiNode<T> {
    protected ScaleOffset position;
    protected ScaleOffset size;
    protected ScaleOffset realPosition;
    protected ScaleOffset realPositionMax;
    protected ScaleOffset realSize;
    protected RGBA color;
    protected double xAnchor = 0;
    protected double yAnchor = 0;

    public T setPosition(ScaleOffset position) {
        this.position = position;
        sendSignal(SignalType.SET_POSITION, position);
        return self;
    }
    public T setSize(ScaleOffset size) {
        this.size = size;
        sendSignal(SignalType.SET_SIZE, size);
        return self;
    }
    public T setColor(RGBA color) {
        this.color = color;
        sendSignal(SignalType.SET_COLOR, color);
        return self;
    }
    public T setXAnchor(double xAnchor) {
        this.xAnchor = xAnchor;
        sendSignal(SignalType.SET_X_ANCHOR, xAnchor);
        return self;
    }
    public T setYAnchor(double yAnchor) {
        this.yAnchor = yAnchor;
        sendSignal(SignalType.SET_Y_ANCHOR, yAnchor);
        return self;
    }
    public T setAnchor(double xAnchor, double yAnchor) {
        setXAnchor(xAnchor);
        setYAnchor(yAnchor);
        return self;
    }

    protected abstract void render(GuiRender render);

    protected ScaleOffset getRealPosition() {
        return realPosition;
    }
    protected ScaleOffset getRealSize() {
        return realSize;
    }

    protected void operationPosition(){
        if (getParent() != null && getParent() instanceof AbstractFrame<?> parentFrame){
            operationPosition(parentFrame, parentFrame.getRealPosition());
        } else {
            operationPosition(null, ScaleOffset.ZERO);
        }
    }
    protected void operationPosition(AbstractFrame<?> parentFrame, ScaleOffset parentRealPosition) {
        if (parentFrame != null) {
            realSize = ScaleOffset.fromOffset((int) Math.round(size.xScale() * parentFrame.getRealSize().getXPixel()) + size.xOffset(), (int) Math.round(size.yScale() * parentFrame.getRealSize().getYPixel()) + size.yOffset());
            realPosition = ScaleOffset.fromOffset(
                    parentRealPosition.getXPixel() + (int) Math.round(position.xScale() * parentFrame.getRealSize().getXPixel()) + position.xOffset() - (int) Math.round(realSize.getXPixel() * xAnchor),
                    parentRealPosition.getYPixel() + (int) Math.round(position.yScale() * parentFrame.getRealSize().getYPixel()) + position.yOffset() - (int) Math.round(realSize.getYPixel() * yAnchor)
            );
        } else {
            realSize = ScaleOffset.fromOffset(size.getXPixel(), size.getYPixel());
            realPosition = ScaleOffset.fromOffset(position.getXPixel() - (int) Math.round(realSize.getXPixel() * xAnchor), position.getYPixel() - (int) Math.round(realSize.getYPixel() * yAnchor));
        }
        realPositionMax = realPosition.add(realSize);
        letChildrenOperationPosition();
    }
    protected void letChildrenOperationPosition(){
        for (GuiNode<?> guiNode : children){
            if (guiNode instanceof AbstractFrame<?> frame){
                frame.operationPosition();
            }
        }
    }

    public AbstractFrame(ScaleOffset position, ScaleOffset size) {
        this.position = position;
        this.size = size;
    }
}
