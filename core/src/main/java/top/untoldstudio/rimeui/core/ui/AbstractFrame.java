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
import top.untoldstudio.rimeui.core.event.*;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.serialization.JsonAbstractFrame;
import top.untoldstudio.rimeui.core.signal.SignalType;

public abstract class AbstractFrame<T extends AbstractFrame<T>> extends GuiNode<T> {
    protected ScaleOffset position;
    protected ScaleOffset size;
    protected ScaleOffset realPosition;
    protected ScaleOffset realPositionMax;
    protected ScaleOffset realSize;
    protected RGBA backgroundColor = RGBA.WHITE;
    protected double xAnchor = 0;
    protected double yAnchor = 0;
    protected boolean isClipChildren = false;
    private int layoutOrder = 1;

    @Override
    public T fillFieldForClone(T other){
        super.fillFieldForClone(other);
        other.setPosition(position);
        other.setSize(size);
        other.setBackgroundColor(backgroundColor);
        other.setYAnchor(yAnchor);
        other.setXAnchor(xAnchor);
        other.setIsClipChildren(isClipChildren);
        other.setLayoutOrder(layoutOrder);
        return other;
    }

    @Override
    public void renderWithChildren(GuiRender render, double delta){
        if (isClipChildren) {
            render.enableScissor(realPosition, realSize);
        }
        super.renderWithChildren(render, delta);
        if (isClipChildren) {
            render.disableScissor();
        }
    }

    public boolean isMouseInRange(){
        return MainGui.getInstance().isMouseInRange(realPosition, realPositionMax);
    }

    public void renderFrameDefaultBackground(GuiRender render, double delta){
        render.drawSquare(realPosition, realPositionMax, backgroundColor);
    }

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
    public T setBackgroundColor(RGBA backgroundColor) {
        this.backgroundColor = backgroundColor;
        operationPosition();
        sendSignal(SignalType.SET_BACKGROUND_COLOR, backgroundColor);
        return self;
    }
    public T setTransparency(double transparency){
        setBackgroundColor(backgroundColor.withAlpha(MathTool.round((1 - transparency) * 255)));
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
    public T setIsClipChildren(boolean clipChildren) {
        isClipChildren = clipChildren;
        return self;
    }
    public double getTransparency(){
        return (double)(255 - backgroundColor.alpha()) / (double)255;
    }
    public double getXAnchor(){
        return xAnchor;
    }
    public double getYAnchor(){
        return yAnchor;
    }
    public RGBA getBackgroundColor(){
        return backgroundColor;
    }
    public ScaleOffset getPosition(){
        return position;
    }
    public ScaleOffset getSize(){
        return size;
    }
    public ScaleOffset getRealSize(){
        return realSize;
    }
    public ScaleOffset getRealPosition(){
        return realPosition;
    }
    public ScaleOffset getRealPositionMax(){
        return realPositionMax;
    }
    public boolean isClipChildren(){
        return isClipChildren;
    }

    @Override
    protected abstract void render(GuiRender render, double delta);

    public void operationPosition(){
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
    public void operationPosition(AbstractFrame<?> parentFrame, ScaleOffset parentRealPosition) {
        if (parentFrame != null) {
            realSize = ScaleOffset.fromOffset((int) Math.round(size.xScale() * parentFrame.getRealSize().getXPixelInParent()) + size.xOffset(), (int) Math.round(size.yScale() * parentFrame.getRealSize().getYPixelInParent()) + size.yOffset());
            realPosition = ScaleOffset.fromOffset(
                    parentRealPosition.getXPixelInParent() + (int) Math.round(position.xScale() * parentFrame.getRealSize().getXPixelInParent()) + position.xOffset() - (int) Math.round(realSize.getXPixelInParent() * xAnchor),
                    parentRealPosition.getYPixelInParent() + (int) Math.round(position.yScale() * parentFrame.getRealSize().getYPixelInParent()) + position.yOffset() - (int) Math.round(realSize.getYPixelInParent() * yAnchor)
            );
        } else {
            realSize = ScaleOffset.fromOffset(size.getXPixelInParent(), size.getYPixelInParent());
            realPosition = ScaleOffset.fromOffset(position.getXPixelInParent() - (int) Math.round(realSize.getXPixelInParent() * xAnchor), position.getYPixelInParent() - (int) Math.round(realSize.getYPixelInParent() * yAnchor));
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

    public T setLayoutOrder(int layoutOrder) {
        this.layoutOrder = layoutOrder;
        sendSignal(SignalType.SET_LAYOUT_ORDER, layoutOrder);
        return self;
    }
    public int getLayoutOrder() {
        return layoutOrder;
    }

    @Override
    protected void init(){
        if (parent != null && !parent.isInit){
            parent.init();
        }
        operationPosition();
    }

    public final JsonAbstractFrame fillParentClassJsonNode(JsonAbstractFrame frame){
        frame.setBackgroundColor(backgroundColor);
        frame.setPosition(position);
        frame.setSize(size);
        frame.setBackgroundColor(backgroundColor);
        frame.setXAnchor(xAnchor);
        frame.setYAnchor(yAnchor);
        frame.setClipChildren(isClipChildren);
        frame.setLayoutOrder(layoutOrder);
        super.fillParentClassJsonNode(frame);
        return frame;
    }

    public AbstractFrame(ScaleOffset position, ScaleOffset size) {
        this.position = position;
        this.size = size;
    }
}
