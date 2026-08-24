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
package top.untoldstudio.rimeui.core.render;

import static org.lwjgl.util.freetype.FreeType.*;
import static org.lwjgl.util.harfbuzz.HarfBuzz.*;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.freetype.FT_Bitmap;
import org.lwjgl.util.freetype.FT_Face;
import org.lwjgl.util.freetype.FT_GlyphSlot;
import org.lwjgl.util.harfbuzz.hb_glyph_info_t;
import org.lwjgl.util.harfbuzz.hb_glyph_position_t;
import top.untoldstudio.rimeui.core.MathTool;
import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.font.Font;

import java.nio.ByteBuffer;

public abstract class GuiRender {
    public abstract void enableScissor(ScaleOffset position, ScaleOffset size);
    public abstract void disableScissor();

    public void drawSquare(ScaleOffset min, ScaleOffset max, RGBA color){
        ScaleOffset pointA = min.withXOffset(max.getXPixel());
        ScaleOffset pointB = min.withYOffset(max.getYPixel());
        drawTriangle(min, pointA, pointB, color, color, color);
        drawTriangle(max, pointA, pointB, color, color, color);
    }

    public abstract void begin();
    public abstract void end();
    public abstract void saveContext();
    public abstract void restoreContext();
    public void drawTriangle(ScaleOffset positionA, ScaleOffset positionB, ScaleOffset positionC, RGBA colorA, RGBA colorB, RGBA colorC){
        drawTriangle(positionA.getXPixel(), positionA.getYPixel(), positionB.getXPixel(), positionB.getYPixel(), positionC.getXPixel(), positionC.getYPixel(),
                colorA.red(), colorA.green(), colorA.blue(), colorA.alpha(),
                colorB.red(), colorB.green(), colorB.blue(), colorB.alpha(),
                colorC.red(), colorC.green(), colorC.blue(), colorC.alpha()
        );
    }
    public void drawTexture(int textureId, ScaleOffset min, ScaleOffset max, RGBA color){
        int red = color.red();
        int green = color.green();
        int blue = color.blue();
        int alpha = color.alpha();
        drawTexture(textureId, min.getXPixel(), min.getYPixel(), max.getXPixel(), max.getYPixel(),
                0, 1, 0, 1,
                red, green, blue, alpha,
                red, green, blue, alpha,
                red, green, blue, alpha,
                red, green, blue, alpha
        );
    }
    public void drawString(String text, Font font, ScaleOffset startDrawPosition, int fontSize, RGBA color){
        long hbBuffer = font.bufferPointer();
        long hbFont = font.fontPointer();
        FT_Set_Pixel_Sizes(font.face(), 0, fontSize);
        hb_font_set_scale(font.fontPointer(), fontSize * 64, fontSize * 64);
        hb_buffer_clear_contents(hbBuffer);
        ByteBuffer textBuffer = MemoryUtil.memUTF8(text);
        hb_buffer_add_utf8(hbBuffer, textBuffer, 0, -1);
        hb_buffer_set_direction(hbBuffer, HB_DIRECTION_LTR);
        hb_buffer_set_script(hbBuffer, HB_SCRIPT_UNKNOWN);
        hb_shape(hbFont, hbBuffer, null);
        int glyphCount = hb_buffer_get_length(hbBuffer);
        hb_glyph_info_t.Buffer infoBuffer = hb_buffer_get_glyph_infos(hbBuffer);
        hb_glyph_position_t.Buffer positionBuffer = hb_buffer_get_glyph_positions(hbBuffer);

        float penX = startDrawPosition.getXPixel();
        float penY = startDrawPosition.getYPixel();

        beginTextRendering();

        for (int i = 0; i < glyphCount; i++){
            FT_Face face = font.face();
            assert infoBuffer != null;
            FT_Load_Glyph(face, infoBuffer.get(i).codepoint(), FT_LOAD_RENDER);
            FT_GlyphSlot glyphSlot = face.glyph();
            assert glyphSlot != null;
            FT_Bitmap bitmap = glyphSlot.bitmap();
            assert positionBuffer != null;
            hb_glyph_position_t position = positionBuffer.get(i);
            int xOffset = position.x_offset();
            int yOffset = position.y_offset();
            int xAdvance = position.x_advance();
            int yAdvance = position.y_advance();
            float glyphX = penX + (xOffset / 64.0f);
            float glyphY = penY - (yOffset / 64.0f);
            penX += xAdvance / 64.0f;
            penY += yAdvance / 64.0f;
            drawGlyph(bitmap, MathTool.round(glyphX), MathTool.round(glyphY), color);
        }

        endTextRendering();
    }

    public abstract void drawGlyph(FT_Bitmap bitmap, int glyphX, int glyphY, RGBA color);
    protected abstract void beginTextRendering();
    protected abstract void endTextRendering();

    public void drawNiceGridTexture(int textureId, ScaleOffset position, ScaleOffset size,
                                    int textureWidth, int textureHeight,
                                    int left, int top, int right, int bottom, RGBA color) {
        int positionX = position.getXPixel();
        int positionY = position.getYPixel();
        int targetWidth = size.getXPixel();
        int targetHeight = size.getYPixel();

        if (targetWidth <= 0 || targetHeight <= 0 || textureWidth <= 0 || textureHeight <= 0) {
            return;
        }

        float widthScale = targetWidth / (float) textureWidth;

        int targetLeft = Math.min(Math.round(left * widthScale), targetWidth / 2);
        int targetRight = Math.min(Math.round(right * widthScale), targetWidth / 2);
        int targetTop = Math.min(Math.round(top * widthScale), targetHeight / 2);
        int targetBottom = Math.min(Math.round(bottom * widthScale), targetHeight / 2);

        int centerPositionX = positionX + targetLeft;
        int centerPositionY = positionY + targetTop;
        int targetCenterWidth = targetWidth - targetLeft - targetRight;
        int targetCenterHeight = targetHeight - targetTop - targetBottom;

        int textureCenterWidth = textureWidth - left - right;
        int textureCenterHeight = textureHeight - top - bottom;

        int r = color.red();
        int g = color.green();
        int b = color.blue();
        int a = color.alpha();

        int rightX = positionX + targetWidth;
        int bottomY = positionY + targetHeight;

        drawTexture(textureId,
                positionX, positionY,
                positionX + targetLeft, positionY + targetTop,
                0f, left / (float) textureWidth,
                0f, top / (float) textureHeight,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a);

        drawTexture(textureId,
                centerPositionX, positionY,
                centerPositionX + targetCenterWidth, positionY + targetTop,
                left / (float) textureWidth, (left + textureCenterWidth) / (float) textureWidth,
                0f, top / (float) textureHeight,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a);

        drawTexture(textureId,
                positionX + targetWidth - targetRight, positionY,
                rightX, positionY + targetTop,
                (textureWidth - right) / (float) textureWidth, 1f,
                0f, top / (float) textureHeight,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a);

        drawTexture(textureId,
                positionX, centerPositionY,
                positionX + targetLeft, centerPositionY + targetCenterHeight,
                0f, left / (float) textureWidth,
                top / (float) textureHeight, (top + textureCenterHeight) / (float) textureHeight,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a);

        drawTexture(textureId,
                centerPositionX, centerPositionY,
                centerPositionX + targetCenterWidth, centerPositionY + targetCenterHeight,
                left / (float) textureWidth, (left + textureCenterWidth) / (float) textureWidth,
                top / (float) textureHeight, (top + textureCenterHeight) / (float) textureHeight,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a);

        drawTexture(textureId,
                positionX + targetWidth - targetRight, centerPositionY,
                rightX, centerPositionY + targetCenterHeight,
                (textureWidth - right) / (float) textureWidth, 1f,
                top / (float) textureHeight, (top + textureCenterHeight) / (float) textureHeight,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a);

        drawTexture(textureId,
                positionX, positionY + targetHeight - targetBottom,
                positionX + targetLeft, bottomY,
                0f, left / (float) textureWidth,
                (textureHeight - bottom) / (float) textureHeight, 1f,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a);

        drawTexture(textureId,
                centerPositionX, positionY + targetHeight - targetBottom,
                centerPositionX + targetCenterWidth, bottomY,
                left / (float) textureWidth, (left + textureCenterWidth) / (float) textureWidth,
                (textureHeight - bottom) / (float) textureHeight, 1f,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a);

        drawTexture(textureId,
                positionX + targetWidth - targetRight, positionY + targetHeight - targetBottom,
                rightX, bottomY,
                (textureWidth - right) / (float) textureWidth, 1f,
                (textureHeight - bottom) / (float) textureHeight, 1f,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a,
                r, g, b, a);
    }
    public abstract void drawTriangle(int ax, int ay, int bx, int by, int cx, int cy,
                                      int aRed, int aGreen, int aBlue, int aAlpha,
                                      int bRed, int bGreen, int bBlue, int bAlpha,
                                      int cRed, int cGreen, int cBlue, int cAlpha);
    public abstract void submitBuffer();
    public abstract void drawTexture(int textureId, int ax, int ay, int bx, int by,
                            float u0, float u1, float v0, float v1,
                            int aRed, int aGreen, int aBlue, int aAlpha,
                            int bRed, int bGreen, int bBlue, int bAlpha,
                            int cRed, int cGreen, int cBlue, int cAlpha,
                            int dRed, int dGreen, int dBlue, int dAlpha
    );
}
