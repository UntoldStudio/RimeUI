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
package top.untoldstudio.rimeui.core.texture;

import top.untoldstudio.rimeui.core.serialization.JsonImageData;

public record ImageData(boolean isNiceGridTexture, int textureId, int width, int height, int left, int right, int top, int bottom, String path) {
    public ImageData(int textureId, int width, int height, String path){
        this(false, textureId, width, height, 0, 0, 0, 0, path);
    }
    public ImageData(int textureId, int width, int height, int left, int right, int top, int bottom, String path) {
        this(true, textureId, width, height, left, right, top, bottom, path);
    }

    public JsonImageData toJsonImageData(){
        return new JsonImageData(isNiceGridTexture, left, right, top, bottom, path);
    }
}
