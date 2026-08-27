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
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.signal.SignalType;
import top.untoldstudio.rimeui.core.texture.ImageData;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;

public final class ImageLabel extends AbstractFrame<ImageLabel> {
    private ImageData data;

    @Override
    protected void render(GuiRender render, double delta){
        if (data.isNiceGridTexture()){
            render.drawNiceGridTexture(data.textureId(), realPosition, realSize, data.width(), data.height(), data.left(), data.right(), data.top(), data.bottom(), backgroundColor);
        } else {
            render.drawTexture(data.textureId(), realPosition, realPositionMax, backgroundColor);
        }
    }

    public ImageLabel setTexture(ImageData data){
        this.data = data;
        sendSignal(SignalType.SET_TEXTURE_ID, data);
        return self;
    }

    public ImageLabel(ImageData data, ScaleOffset position, ScaleOffset size) {
        super(position, size);
        this.data = data;
    }
}
