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
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.signal.SignalType;
import top.untoldstudio.rimeui.core.texture.ImageData;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;

public final class ImageLabel extends AbstractFrame<ImageLabel> implements ImageBase, InputBlocker<ImageLabel> {
    private ImageData data;
    private boolean isBlockInput = false;

    @Override
    protected void render(GuiRender render, double delta){
        renderImage(render, data, realPosition, realPositionMax, realSize, backgroundColor);
    }

    public ImageLabel setTexture(ImageData data){
        this.data = data;
        sendSignal(SignalType.SET_TEXTURE_ID, data);
        return self;
    }

    public ImageLabel setBlockInput(boolean isAcceptInput) {
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
    @Override
    protected void onMouseScrollEvent(MouseScrollEvent event){
        if (isBlockInput && isMouseInRange()) event.cancel();
    }

    public ImageLabel(ImageData data, ScaleOffset position, ScaleOffset size) {
        super(position, size);
        this.data = data;
    }
}
