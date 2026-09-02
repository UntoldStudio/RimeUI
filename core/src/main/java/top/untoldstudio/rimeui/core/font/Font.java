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

import org.lwjgl.util.freetype.*;
import top.untoldstudio.rimeui.core.serialization.JsonFont;

import java.nio.ByteBuffer;

public record Font(FT_Face face, String fontPath, ByteBuffer memoryBuffer) {
    public static final Font DEFAULT_FONT = FontManager.loadFont("/font/wen_quan_wei_mi_hei.ttf");

    public JsonFont toJsonFont() {
        return new JsonFont(fontPath);
    }

    public int getStringWidth(String text, int fontSize, double italicDegrees, int boldStrength) {
        float[] bounds = computeTextBounds(text, fontSize, italicDegrees, boldStrength);
        return Math.round(bounds[1] - bounds[0]);
    }

    public int getStringHeight(String text, int fontSize, double italicDegrees, int boldStrength) {
        float[] bounds = computeTextBounds(text, fontSize, italicDegrees, boldStrength);
        return Math.round(bounds[3] - bounds[2]);
    }

    private float[] computeTextBounds(String text, int fontSize, double italicDegrees, int boldStrength) {
        FT_Set_Pixel_Sizes(face, 0, fontSize);
        float minX = Float.POSITIVE_INFINITY, maxX = Float.NEGATIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY, maxY = Float.NEGATIVE_INFINITY;
        float penX = 0;
        int previousGlyphIndex = 0;
        boolean hasPrevious = false;

        FT_Matrix italicMatrix = null;
        if (italicDegrees != 0.0) {
            italicMatrix = FT_Matrix.malloc();
            italicMatrix.xx(0x10000);
            italicMatrix.xy((int) Math.round(Math.tan(Math.toRadians(italicDegrees)) * 0x10000));
            italicMatrix.yx(0);
            italicMatrix.yy(0x10000);
        }

        int boldStrength26_6 = boldStrength * 64;

        for (int offset = 0; offset < text.length(); ) {
            int codepoint = text.codePointAt(offset);
            offset += Character.charCount(codepoint);
            int glyphIndex = FT_Get_Char_Index(face, codepoint);
            if (glyphIndex == 0) continue;

            if (hasPrevious) {
                FT_Vector kerning = FT_Vector.malloc();
                try {
                    FT_Get_Kerning(face, previousGlyphIndex, glyphIndex, FT_KERNING_DEFAULT, kerning);
                    penX += kerning.x() / 64.0f;
                } finally {
                    kerning.free();
                }
            }

            if (FT_Load_Glyph(face, glyphIndex, FT_LOAD_NO_BITMAP) != 0) continue;
            FT_GlyphSlot slot = face.glyph();
            if (slot == null) continue;

            if (italicMatrix != null) {
                FT_Outline_Transform(slot.outline(), italicMatrix);
            }

            if (boldStrength > 0) {
                FT_Outline_Embolden(slot.outline(), boldStrength26_6);
            }

            FT_BBox bbox = FT_BBox.malloc();
            try {
                FT_Outline_Get_CBox(slot.outline(), bbox);
                float glyphMinX = penX + bbox.xMin() / 64.0f;
                float glyphMaxX = penX + bbox.xMax() / 64.0f;
                float glyphMinY = bbox.yMin() / 64.0f;
                float glyphMaxY = bbox.yMax() / 64.0f;

                minX = Math.min(minX, glyphMinX);
                maxX = Math.max(maxX, glyphMaxX);
                minY = Math.min(minY, glyphMinY);
                maxY = Math.max(maxY, glyphMaxY);
            } finally {
                bbox.free();
            }

            penX += slot.advance().x() / 64.0f;
            previousGlyphIndex = glyphIndex;
            hasPrevious = true;
        }

        if (italicMatrix != null) {
            italicMatrix.free();
        }

        if (minX == Float.POSITIVE_INFINITY) {
            return new float[]{0, 0, 0, 0};
        }
        return new float[]{minX, maxX, minY, maxY};
    }

    public int getCursorX(String text, int upToIndex, int fontSize, double italicDegrees, int boldStrength) {
        if (upToIndex <= 0) return 0;
        if (upToIndex > text.length()) upToIndex = text.length();
        String sub = text.substring(0, upToIndex);
        FT_Set_Pixel_Sizes(face, 0, fontSize);
        float penX = 0;
        int previousGlyphIndex = 0;
        boolean hasPrevious = false;
        for (int offset = 0; offset < sub.length(); ) {
            int codepoint = sub.codePointAt(offset);
            offset += Character.charCount(codepoint);
            int glyphIndex = FT_Get_Char_Index(face, codepoint);
            if (glyphIndex == 0) continue;
            if (hasPrevious) {
                FT_Vector kerning = FT_Vector.malloc();
                FT_Get_Kerning(face, previousGlyphIndex, glyphIndex, FT_KERNING_DEFAULT, kerning);
                penX += kerning.x() / 64.0f;
                kerning.free();
            }
            FT_Load_Glyph(face, glyphIndex, FT_LOAD_NO_BITMAP);
            penX += face.glyph().advance().x() / 64.0f;
            previousGlyphIndex = glyphIndex;
            hasPrevious = true;
        }
        return Math.round(penX);
    }
}