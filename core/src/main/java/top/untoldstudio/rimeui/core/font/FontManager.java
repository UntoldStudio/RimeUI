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
package top.untoldstudio.rimeui.core.font;

import static org.lwjgl.util.freetype.FreeType.*;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.freetype.FT_Face;
import top.untoldstudio.rimeui.core.error.ResourceError;
import top.untoldstudio.rimeui.core.resource.ResourceReader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public final class FontManager {
    private static long ftLibrary;
    private static final PointerBuffer faceBuffer = MemoryUtil.memAllocPointer(1);
    private static final Map<String, Font> FONT_MAP = new HashMap<>();

    public static void init() {
        faceBuffer.clear();
        int error = FT_Init_FreeType(faceBuffer);
        if (error != 0) {
            throw new ResourceError("Failed to initialize FreeType library");
        }
        ftLibrary = faceBuffer.get(0);
    }

    public static Font loadFont(String fontPath) {
        Font cached = FONT_MAP.get(fontPath);
        if (cached != null) return cached;

        faceBuffer.clear();
        int error = FT_New_Face(ftLibrary, fontPath, 0, faceBuffer);
        long facePointer = -1;
        ByteBuffer fontBuffer = null;

        if (error != 0) {
            byte[] data;
            try {
                data = ResourceReader.readBytes(fontPath);
            } catch (IOException e) {
                throw new ResourceError("Cannot load font file: " + fontPath);
            }
            fontBuffer = MemoryUtil.memAlloc(data.length);
            fontBuffer.put(data).flip();
            faceBuffer.clear();
            error = FT_New_Memory_Face(ftLibrary, fontBuffer, 0, faceBuffer);
            if (error == 0) {
                facePointer = faceBuffer.get(0);
            }
        } else {
            facePointer = faceBuffer.get(0);
        }

        if (facePointer == -1) {
            throw new ResourceError("Failed to create font face for: " + fontPath);
        }

        FT_Face face = FT_Face.create(facePointer);
        Font font = new Font(face, fontPath, fontBuffer);
        FONT_MAP.put(fontPath, font);
        return font;
    }
}