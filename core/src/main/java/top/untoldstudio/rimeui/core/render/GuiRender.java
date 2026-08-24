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
import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.font.Font;

import java.nio.ByteBuffer;

public abstract class GuiRender {
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
                red, green, blue, alpha,
                red, green, blue, alpha,
                red, green, blue, alpha,
                red, green, blue, alpha
        );
    }
    public void drawString(String text, Font font, int size){
        FT_Set_Pixel_Sizes(font.face(), 0, size);
        hb_font_set_scale(font.fontPointer(), size * 64, size * 64);
        hb_buffer_clear_contents(font.bufferPointer());
        ByteBuffer buffer = MemoryUtil.memUTF8(text);
        hb_buffer_add_utf8(font.bufferPointer(), buffer, buffer.remaining(), 0);
        hb_buffer_set_direction(font.fontPointer(), HB_DIRECTION_LTR);
        hb_buffer_set_script(font.bufferPointer(), HB_SCRIPT_UNKNOWN);
        hb_shape(font.fontPointer(), font.bufferPointer(), null);
        int count = hb_buffer_get_length(font.bufferPointer());
        //TODO
    }
    /*public void drawNiceGridTexture(int textureId, ScaleOffset position, ScaleOffset size, int textureWidth, int textureHeight, int left, int top, int right, int bottom, RGBA color) {
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

        drawTexture(textureId, positionX, positionY, 0, 0, targetLeft, targetTop, left, top, textureWidth, textureHeight, color);
        drawTexture(textureId, centerPositionX, positionY, left, 0, targetCenterWidth, targetTop, textureCenterWidth, top, textureWidth, textureHeight, color);
        drawTexture(textureId, positionX + targetWidth - targetRight, positionY, textureWidth - right, 0, targetRight, targetTop, right, top, textureWidth, textureHeight, color);

        drawTexture(textureId, positionX, centerPositionY, 0, top, targetLeft, targetCenterHeight, left, textureCenterHeight, textureWidth, textureHeight, color);
        drawTexture(textureId, centerPositionX, centerPositionY, left, top, targetCenterWidth, targetCenterHeight, textureCenterWidth, textureCenterHeight, textureWidth, textureHeight, color);
        drawTexture(textureId, positionX + targetWidth - targetRight, centerPositionY, textureWidth - right, top, targetRight, targetCenterHeight, right, textureCenterHeight, textureWidth, textureHeight, color);

        drawTexture(textureId, positionX, positionY + targetHeight - targetBottom, 0, textureHeight - bottom, targetLeft, targetBottom, left, bottom, textureWidth, textureHeight, color);
        drawTexture(textureId, centerPositionX, positionY + targetHeight - targetBottom, left, textureHeight - bottom, targetCenterWidth, targetBottom, textureCenterWidth, bottom, textureWidth, textureHeight, color);
        drawTexture(textureId, positionX + targetWidth - targetRight, positionY + targetHeight - targetBottom, textureWidth - right, textureHeight - bottom, targetRight, targetBottom, right, bottom, textureWidth, textureHeight, color);
    }*/
    public abstract void drawTriangle(int ax, int ay, int bx, int by, int cx, int cy,
                                      int aRed, int aGreen, int aBlue, int aAlpha,
                                      int bRed, int bGreen, int bBlue, int bAlpha,
                                      int cRed, int cGreen, int cBlue, int cAlpha);
    public abstract void submitBuffer();
    public abstract void drawTexture(int textureId, int ax, int ay, int bx, int by,
                                   int aRed, int aGreen, int aBlue, int aAlpha,
                                   int bRed, int bGreen, int bBlue, int bAlpha,
                                   int cRed, int cGreen, int cBlue, int cAlpha,
                                   int dRed, int dGreen, int dBlue, int dAlpha
    );
}
