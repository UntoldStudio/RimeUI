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
import top.untoldstudio.rimeui.core.texture.TextureManager;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;

public final class ImageLabel extends AbstractFrame<ImageLabel> {
    private int textureId;

    @Override
    protected void render(GuiRender render){
        render.drawTexture(textureId, realPosition, realPositionMax, color);
    }

    public ImageLabel setTextureId(int textureId){
        this.textureId = textureId;
        sendSignal(SignalType.SET_TEXTURE_ID, textureId);
        return self;
    }
    public ImageLabel setTexturePath(String texturePath){
        setTextureId(TextureManager.loadImage(texturePath));
        return self;
    }

    public ImageLabel(int textureId, ScaleOffset position, ScaleOffset size) {
        super(position, size);
        this.textureId = textureId;
    }
    public ImageLabel(String imagePath, ScaleOffset position, ScaleOffset size){
        this(TextureManager.loadImage(imagePath), position, size);
    }
}
