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

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;
import top.untoldstudio.rimeui.core.error.ResourceError;
import top.untoldstudio.rimeui.core.resource.ResourceReader;
import top.untoldstudio.rimeui.core.ui.MainUi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

public final class TextureManager {
    private static final Map<String, ImageData> imageMap = new HashMap<>();
    private static final IntBuffer width = MemoryUtil.memAllocInt(1);
    private static final IntBuffer height = MemoryUtil.memAllocInt(1);
    private static final IntBuffer channels = MemoryUtil.memAllocInt(1);
    public record ImageInitializationData(ByteBuffer dataBytes, int width, int height){}

    public static ImageData loadImageWithoutNiceGrid(String imagePath){
        if (imageMap.containsKey(imagePath)) {
            return imageMap.get(imagePath);
        }

        ImageInitializationData data = loadImageInitializationData(imagePath);
        int textureId = MainUi.getInstance().getRender().loadImage(data.width, data.height, data.dataBytes);
        STBImage.stbi_image_free(data.dataBytes);
        ImageData imageData = new ImageData(textureId, data.width(), data.height());
        imageMap.put(imagePath, imageData);
        return imageData;
    }
    public static ImageData loadImageWithNiceGrid(String imagePath, int left, int right, int top, int bottom){
        if (imageMap.containsKey(imagePath)) {
            return imageMap.get(imagePath);
        }

        ImageInitializationData data = loadImageInitializationData(imagePath);
        int textureId = MainUi.getInstance().getRender().loadImage(data.width, data.height, data.dataBytes);
        STBImage.stbi_image_free(data.dataBytes);
        ImageData imageData = new ImageData(textureId, data.width(), data.height(), left, right, top, bottom);
        imageMap.put(imagePath, imageData);
        return imageData;
    }

    public static ImageInitializationData loadImageInitializationData(String imagePath) {
        width.clear();
        height.clear();
        channels.clear();

        ByteBuffer pixels = STBImage.stbi_load(imagePath, width, height, channels, 4);

        if (pixels == null) {
            width.clear();
            height.clear();
            channels.clear();

            ByteBuffer imageBuffer = null;

            try {
                byte[] bytes = ResourceReader.readBytes(imagePath);
                imageBuffer = MemoryUtil.memAlloc(bytes.length);
                imageBuffer.put(bytes);
                imageBuffer.flip();

                pixels = STBImage.stbi_load_from_memory(imageBuffer, width, height, channels, 4);
            } catch (IOException e) {
                //ignore
            } finally {
                if (imageBuffer != null) {
                    MemoryUtil.memFree(imageBuffer);
                }
            }
        }

        if (pixels == null) {
            throw new ResourceError("Failed to load image " + imagePath + ": " + STBImage.stbi_failure_reason());
        }

        int widthInt = width.get(0);
        int heightInt = height.get(0);

        return new ImageInitializationData(pixels, widthInt, heightInt);
    }

    public static void cleanup() {
        MemoryUtil.memFree(width);
        MemoryUtil.memFree(height);
        MemoryUtil.memFree(channels);
    }
}
