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

import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.event.KeyEvent;
import top.untoldstudio.rimeui.core.event.MouseButtonEvent;
import top.untoldstudio.rimeui.core.event.MouseMoveEvent;
import top.untoldstudio.rimeui.core.event.MouseScrollEvent;
import top.untoldstudio.rimeui.core.serialization.node.JsonFrame;
import top.untoldstudio.rimeui.core.signal.SignalType;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;
import top.untoldstudio.rimeui.core.render.GuiRender;

public final class Frame extends AbstractFrame<Frame> implements InputBlocker<Frame> {
    private boolean isBlockInput = false;

    @Override
    public void render(GuiRender render, double delta) {
        super.renderFrameDefaultBackground(render, delta);
    }

    @Override
    public Frame clone(){
        Frame frame = new Frame(position, size);
        super.fillFieldForClone(frame);
        frame.setIsBlockInput(isBlockInput);
        return frame;
    }

    public Frame setIsBlockInput(boolean isAcceptInput) {
        this.isBlockInput = isAcceptInput;
        sendSignal(SignalType.SET_BLOCK_INPUT, isAcceptInput);
        return this;
    }
    public boolean isBlockInput() {
        return isBlockInput;
    }

    public Frame(ScaleOffset position, ScaleOffset size) {
        super(position, size);
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

    @Override
    public JsonFrame toJsonNodeTree(){
        JsonFrame frame = new JsonFrame();
        frame.setIsBlockInput(isBlockInput);
        super.fillParentClassJsonNode(frame);
        return frame;
    }
}
