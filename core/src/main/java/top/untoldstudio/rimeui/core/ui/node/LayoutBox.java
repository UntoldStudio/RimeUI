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

import org.jetbrains.annotations.NotNull;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.event.KeyEvent;
import top.untoldstudio.rimeui.core.event.MouseButtonEvent;
import top.untoldstudio.rimeui.core.event.MouseMoveEvent;
import top.untoldstudio.rimeui.core.event.MouseScrollEvent;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.serialization.node.JsonLayoutBox;
import top.untoldstudio.rimeui.core.signal.SignalType;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;
import top.untoldstudio.rimeui.core.ui.GuiNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class LayoutBox<T extends LayoutBox<T>> extends AbstractFrame<T> implements InputBlocker<T> {
    protected int frameStartSpacing = 0;
    protected int frameSpacing = 0;
    protected List<AbstractFrame<?>> sortedFrameList = new ArrayList<>();
    protected boolean isBlockInput = false;

    protected abstract void render(GuiRender render, double delta);
    protected abstract void sortFrame();

    public final JsonLayoutBox fillParentClassJsonNode(JsonLayoutBox box){
        box.setFrameStartSpacing(frameStartSpacing);
        box.setFrameSpacing(frameSpacing);
        box.setIsBlockInput(isBlockInput);
        super.fillParentClassJsonNode(box);
        return box;
    }

    public int getFrameStartSpacing() {
        return frameStartSpacing;
    }

    public int getFrameSpacing() {
        return frameSpacing;
    }

    public T setFrameSpacing(int frameSpacing) {
        this.frameSpacing = frameSpacing;
        sortFrame();
        sendSignal(SignalType.SET_FRAME_SPACING, frameSpacing);
        return self;
    }

    public T setFrameStartSpacing(int frameStartSpacing) {
        this.frameStartSpacing = frameStartSpacing;
        sortFrame();
        sendSignal(SignalType.SET_FRAME_START_SPACING, frameStartSpacing);
        return self;
    }

    @Override
    public T addChild(@NotNull GuiNode<?> child) {
        super.addChild(child);
        if (child instanceof AbstractFrame<?>) {
            sortList();
        }
        return self;
    }

    @Override
    public T removeChild(@NotNull GuiNode<?> child) {
        super.removeChild(child);
        if (child instanceof AbstractFrame<?>) {
            sortList();
        }
        return self;
    }

    @Override
    public void operationPosition(AbstractFrame<?> parentFrame, ScaleOffset parentRealPosition){
        super.operationPosition(parentFrame, parentRealPosition);
        sortFrame();
    }

    private void sortList() {
        List<AbstractFrame<?>> frames = new ArrayList<>();
        for (GuiNode<?> node : children) {
            if (node instanceof AbstractFrame<?> frame) {
                frames.add(frame);
            }
        }
        frames.sort(Comparator.comparing(AbstractFrame<?>::getLayoutOrder));
        sortedFrameList = frames;
        sortFrame();
    }

    public T setIsBlockInput(boolean isAcceptInput) {
        this.isBlockInput = isAcceptInput;
        sendSignal(SignalType.SET_BLOCK_INPUT, isAcceptInput);
        return self;
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
    @Override
    protected void onMouseScrollEvent(MouseScrollEvent event){
        if (isBlockInput && isMouseInRange()) event.cancel();
    }

    public LayoutBox(ScaleOffset position, ScaleOffset size) {
        super(position, size);
    }
}
