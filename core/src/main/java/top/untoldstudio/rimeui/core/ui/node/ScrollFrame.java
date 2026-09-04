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
package top.untoldstudio.rimeui.core.ui.node;

import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.event.*;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.serialization.node.JsonScrollFrame;
import top.untoldstudio.rimeui.core.signal.SignalType;
import top.untoldstudio.rimeui.core.texture.ImageData;
import top.untoldstudio.rimeui.core.texture.TextureManager;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;
import top.untoldstudio.rimeui.core.ui.GuiNode;

public final class ScrollFrame extends AbstractFrame<ScrollFrame> implements InputBlocker<ScrollFrame> {
    private final ImageButton scrollBar;
    private boolean canScroll = true;
    private double scrollScale = 2;
    private double scrollProgress = 1;
    private double scrollSpeed = 1;
    private boolean isBlockInput = false;

    @Override
    protected void render(GuiRender render, double delta) {
        super.renderFrameDefaultBackground(render, delta);
    }

    @Override
    public JsonScrollFrame toJsonNodeTree(){
        JsonScrollFrame frame = new JsonScrollFrame();
        frame.setScrollBar(scrollBar.toJsonNodeTree());
        frame.setScrollScale(scrollScale);
        frame.setScrollProgress(scrollProgress);
        frame.setScrollSpeed(scrollSpeed);
        frame.setIsBlockInput(isBlockInput);
        super.fillParentClassJsonNode(frame);
        return frame;
    }

    @Override
    public ScrollFrame clone(){
        ScrollFrame frame = new ScrollFrame(position, size);
        super.fillFieldForClone(frame);
        frame.setCanScroll(canScroll);
        frame.setScrollScale(scrollScale);
        frame.setScrollProgress(scrollProgress);
        frame.setScrollSpeed(scrollSpeed);
        frame.setIsBlockInput(isBlockInput);
        frame.setScrollBarVisible(scrollBar.isVisible());
        frame.setScrollBarTexture(getScrollBarTexture());
        frame.setScrollBarWidth(scrollBar.getSize().getXPixelInParent());
        frame.setScrollBarColor(scrollBar.getBackgroundColor());
        return frame;
    }

    private void setScrollBarProcessState() {
        double yScale = 1 / scrollScale;
        scrollBar.setSize(scrollBar.getSize().withYScale(yScale));
        double allPosition = 1 - yScale;
        scrollBar.setPosition(scrollBar.getPosition().withYScale(allPosition - scrollProgress * allPosition));
        operationPosition();
    }

    @Override
    public void init() {
        isClipChildren = true;
        super.init();
        addChild(scrollBar);
        setScrollBarProcessState();
    }

    @Override
    protected void onMouseScrollEvent(MouseScrollEvent event) {
        if (canScroll && isMouseInRange()) {
            double value = event.getYDelta() / 5 * scrollSpeed / scrollScale;
            double tempProcess = scrollProgress + value;
            scrollProgress = Math.clamp(tempProcess, 0, 1);
            event.cancel();
            setScrollBarProcessState();
        }
    }

    @Override
    public ScaleOffset getRealPosition() {
        ScaleOffset base = super.getRealPosition();
        return base.withYOffset(base.yOffset() - (int) ((int) ((scrollScale - 1) * realSize.getYPixelInParent()) * (1 - scrollProgress)));
    }

    @Override
    protected void letChildrenOperationPosition() {
        for (GuiNode<?> guiNode : children) {
            if (guiNode == scrollBar) {
                scrollBar.operationPosition(this, super.getRealPosition());
            } else if (guiNode instanceof AbstractFrame<?> frame) {
                frame.operationPosition();
            }
        }
    }

    public ScrollFrame setScrollProgress(double progress) {
        progress = Math.clamp(progress, 0, 1);
        this.scrollProgress = progress;
        setScrollBarProcessState();
        sendSignal(SignalType.SET_SCROLL_PROGRESS, progress);
        return this;
    }

    public ScrollFrame setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
        sendSignal(SignalType.SET_CAN_SCROLL, canScroll);
        return this;
    }

    public ScrollFrame setScrollScale(double scrollScale) {
        this.scrollScale = scrollScale;
        sendSignal(SignalType.SET_SCROLL_SCALE, scrollScale);
        setScrollBarProcessState();
        return this;
    }

    public ScrollFrame setScrollBarWidth(int width) {
        ScaleOffset currentSize = scrollBar.getSize();
        if (currentSize.getXPixelInParent() != width) {
            scrollBar.setSize(new ScaleOffset(0, width, currentSize.yScale(), currentSize.yOffset()));
            sendSignal(SignalType.SET_SCROLL_BAR_WIDTH);
        }
        return this;
    }

    public ScrollFrame setScrollBarColor(RGBA color) {
        if (scrollBar.getBackgroundColor() != color) {
            scrollBar.setBackgroundColor(color);
            sendSignal(SignalType.SET_SCROLL_BAR_COLOR);
        }
        return this;
    }

    public ScrollFrame setScrollBarTexture(ImageData data) {
        scrollBar.setDefaultImage(data);
        sendSignal(SignalType.SET_SCROLL_BAR_TEXTURE, data);
        return this;
    }
    public ScrollFrame setScrollBarVisible(boolean visible) {
        scrollBar.setVisible(visible);
        sendSignal(SignalType.SET_SCROLL_BAR_VISIBLE, visible);
        return this;
    }
    public ScrollFrame setScrollSpeed(double speed) {
        this.scrollSpeed = speed;
        sendSignal(SignalType.SET_SCROLL_SPEED, speed);
        return this;
    }

    public double getScrollScale() {
        return scrollScale;
    }

    public int getScrollBarWidth() {
        return scrollBar.getPosition().getXPixelInParent();
    }

    public ImageData getScrollBarTexture() {
        return scrollBar.getDefaultImage();
    }
    public boolean isScrollBarVisible() {
        return scrollBar.isVisible();
    }
    public boolean canScroll() {
        return canScroll;
    }
    public double getScrollProgress() {
        return scrollProgress;
    }
    public double getScrollSpeed() {
        return scrollSpeed;
    }
    public RGBA getScrollBarColor() {
        return scrollBar.getBackgroundColor();
    }

    public ScrollFrame setIsBlockInput(boolean isAcceptInput) {
        this.isBlockInput = isAcceptInput;
        sendSignal(SignalType.SET_BLOCK_INPUT, isAcceptInput);
        return this;
    }
    public boolean isBlockInput() {
        return isBlockInput;
    }

    @Override
    protected void onKeyEvent(KeyEvent event){
        if (isBlockInput && isMouseInRange()) event.cancel();
    }
    @Override
    protected void onMouseButtonEvent(MouseButtonEvent event){
        if (isBlockInput && isMouseInRange()) event.cancel();
    }
    @Override
    protected void onMouseMoveEvent(MouseMoveEvent event){
        if (isBlockInput && isMouseInRange()) event.cancel();
    }

    public ScrollFrame(ScaleOffset position, ScaleOffset size) {
        super(position, size);
        scrollBar = new ImageButton(TextureManager.loadImageWithNiceGrid("/texture/default_scroll_bar.png", 0, 0, 20, 20), ScaleOffset.fromScale(1, 0), new ScaleOffset(0, 6, 1, 0)) {
            @Override
            public void onWindowSizeChangeEvent(WindowSizeChangeEvent event) {
            }
        }.setBackgroundColor(RGBA.GRAY).setXAnchor(1).setRenderLevel(Integer.MAX_VALUE);
    }

    @Override
    public ScrollFrame setIsClipChildren(boolean clipChildren) {
        return this;
    }
}
