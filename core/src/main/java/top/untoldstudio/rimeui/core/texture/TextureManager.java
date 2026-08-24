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

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import top.untoldstudio.rimeui.core.error.ResourceError;
import top.untoldstudio.rimeui.core.render.RenderBackend;
import top.untoldstudio.rimeui.core.resource.ResourceReader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

public final class TextureManager {
    private static final Map<String, Integer> imageIdMap = new HashMap<>();
    private static final IntBuffer width = BufferUtils.createIntBuffer(1);
    private static final IntBuffer height = BufferUtils.createIntBuffer(1);
    private static final IntBuffer channels = BufferUtils.createIntBuffer(1);

    static {
        STBImage.stbi_set_flip_vertically_on_load(true);
    }

    public static int loadImage(String imagePath) {
        if (imageIdMap.containsKey(imagePath)) {
            return imageIdMap.get(imagePath);
        }

        width.clear();
        height.clear();
        channels.clear();

        ByteBuffer pixels = STBImage.stbi_load(imagePath, width, height, channels, 4);

        if (pixels == null) {
            width.clear();
            height.clear();
            channels.clear();

            try {
                byte[] bytes = ResourceReader.readBytes(imagePath);
                ByteBuffer imageBuffer = BufferUtils.createByteBuffer(bytes.length);
                imageBuffer.put(bytes);
                imageBuffer.flip();

                pixels = STBImage.stbi_load_from_memory(imageBuffer, width, height, channels, 4);
            } catch (IOException e) {
                //ignore
            }
        }

        if (pixels == null) {
            throw new ResourceError("Failed to load image " + imagePath + ": " + STBImage.stbi_failure_reason());
        }

        int id = RenderBackend.getProvider().loadImage(width.get(0), height.get(0), pixels);

        imageIdMap.put(imagePath, id);
        STBImage.stbi_image_free(pixels);
        return id;
    }
    public static int getImageId(String imagePath){
        if (imageIdMap.containsKey(imagePath)) {
            return imageIdMap.get(imagePath);
        } else {
            return loadImage(imagePath);
        }
    }
}
