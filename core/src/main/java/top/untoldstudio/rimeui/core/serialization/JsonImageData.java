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
package top.untoldstudio.rimeui.core.serialization;

import com.google.gson.annotations.SerializedName;
import top.untoldstudio.rimeui.core.texture.ImageData;
import top.untoldstudio.rimeui.core.texture.TextureManager;

public record JsonImageData(@SerializedName("is_nice_grid_texture") boolean isNiceGridTexture, int left, int right, int top, int bottom, String path) {
    public JsonImageData(String path){
        this(false, 0, 0, 0, 0, path);
    }
    public JsonImageData(String path, int left, int right, int top, int bottom){
        this(true, left, right, top, bottom, path);
    }

    public ImageData toImageData() {
        if (isNiceGridTexture){
            return TextureManager.loadImageWithNiceGrid(path, left, right, top, bottom);
        } else {
            return TextureManager.loadImageWithoutNiceGrid(path);
        }
    }
}
