/*
 * Copyright 2026 Untold Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.untoldstudio.rimeui.core.ui;

import top.untoldstudio.rimeui.core.MathTool;
import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.event.WindowSizeChangeEvent;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.signal.SignalType;

public abstract class AbstractFrame<T extends AbstractFrame<T>> extends GuiNode<T> {
    protected ScaleOffset position;
    protected ScaleOffset size;
    protected ScaleOffset realPosition;
    protected ScaleOffset realPositionMax;
    protected ScaleOffset realSize;
    protected RGBA color = RGBA.WHITE;
    protected double xAnchor = 0;
    protected double yAnchor = 0;

    public T setPosition(ScaleOffset position) {
        this.position = position;
        operationPosition();
        sendSignal(SignalType.SET_POSITION, position);
        return self;
    }
    public T setSize(ScaleOffset size) {
        this.size = size;
        operationPosition();
        sendSignal(SignalType.SET_SIZE, size);
        return self;
    }
    public T setColor(RGBA color) {
        this.color = color;
        operationPosition();
        sendSignal(SignalType.SET_COLOR, color);
        return self;
    }
    public T setTransparency(double transparency){
        setColor(color.withAlpha(MathTool.round((1 - transparency) * 255)));
        return self;
    }
    public T setXAnchor(double xAnchor) {
        this.xAnchor = xAnchor;
        operationPosition();
        sendSignal(SignalType.SET_X_ANCHOR, xAnchor);
        return self;
    }
    public T setYAnchor(double yAnchor) {
        this.yAnchor = yAnchor;
        operationPosition();
        sendSignal(SignalType.SET_Y_ANCHOR, yAnchor);
        return self;
    }
    public T setAnchor(double xAnchor, double yAnchor) {
        setXAnchor(xAnchor);
        setYAnchor(yAnchor);
        return self;
    }
    public double getTransparency(){
        return (double)(255 - color.alpha()) / (double)255;
    }
    public double getXAnchor(){
        return xAnchor;
    }
    public double getYAnchor(){
        return yAnchor;
    }
    public RGBA getColor(){
        return color;
    }
    public ScaleOffset getPosition(){
        return position;
    }
    public ScaleOffset getSize(){
        return size;
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
    @Override
    protected void onWindowSizeChangeEvent(WindowSizeChangeEvent event){
        operationPosition();
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
        operationPosition();
    }
}
