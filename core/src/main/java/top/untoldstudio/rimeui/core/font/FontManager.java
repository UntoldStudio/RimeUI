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
import static org.lwjgl.util.harfbuzz.HarfBuzz.*;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.freetype.FT_Face;
import org.lwjgl.util.freetype.FreeType;
import top.untoldstudio.rimeui.core.error.ResourceError;
import top.untoldstudio.rimeui.core.resource.ResourceReader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public final class FontManager {
    private static long ftPointer;
    private static final PointerBuffer buffer = MemoryUtil.memAllocPointer(1);
    private static final Map<String, Font> FONT_MAP = new HashMap<>();

    public static void init(){
        Configuration.HARFBUZZ_LIBRARY_NAME.set(FreeType.getLibrary());
        buffer.clear();
        int error = FT_Init_FreeType(buffer);
        if (error != 0){
            throw new ResourceError("Cannot initialize FreeType");
        }
        ftPointer = buffer.get(0);
    }

    public static Font loadFont(String fontPath){
        if (FONT_MAP.containsKey(fontPath)){
            return FONT_MAP.get(fontPath);
        }

        buffer.clear();
        int error = FT_New_Face(ftPointer, fontPath, 0, buffer);
        long facePointer = -1;
        ByteBuffer fontBuffer = null;
        if (error != 0){
            byte[] data;
            try {
                data = ResourceReader.readBytes(fontPath);
            } catch (IOException e){
                throw new ResourceError(fontPath);
            }
            fontBuffer = MemoryUtil.memAlloc(data.length);
            fontBuffer.put(data);
            fontBuffer.flip();
            buffer.clear();
            error = FT_New_Memory_Face(ftPointer, fontBuffer, 0, buffer);
            if (error == 0){
                facePointer = buffer.get(0);
            }
        } else {
            facePointer = buffer.get(0);
        }
        if (facePointer == -1){
            throw new ResourceError("Cannot load font: " + fontPath);
        }
        FT_Face face = FT_Face.create(facePointer);
        long bufferPointer = hb_buffer_create();
        long hbFontPointer = hb_ft_font_create_referenced(facePointer);
        Font font = new Font(face, hbFontPointer, bufferPointer, fontPath, fontBuffer);
        FONT_MAP.put(fontPath, font);
        return font;
    }
}
