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
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.util.freetype.*;
import top.untoldstudio.rimeui.core.data.CursorMode;
import top.untoldstudio.rimeui.core.data.CursorShape;
import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.font.Font;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class GuiRender {
    protected final long windowHandle;
    protected boolean isUseRenderMapping = false;
    protected ScaleOffset renderRegionMin;
    protected ScaleOffset renderRegionSize;
    protected long cursorShapeInThisFrame;
    protected int cursorModeInThisFrame;
    protected final Map<Integer, Long> cursorShapeMap = new HashMap<>();

    public abstract void enableScissor(ScaleOffset position, ScaleOffset size);
    public abstract void disableScissor();

    /**
     * 注意:如果你调用了该方法则必须调用setRenderRegionMin与setRenderRegionSize,否则会空指针
     */
    public void setUseRenderMapping(boolean isUseRenderMapping) {
        this.isUseRenderMapping = isUseRenderMapping;
    }
    public void setRenderRegionMin(ScaleOffset renderRegionMin) {
        this.renderRegionMin = renderRegionMin;
    }
    public void setRenderRegionSize(ScaleOffset renderRegionSize) {
        this.renderRegionSize = renderRegionSize;
    }

    public void drawSquare(ScaleOffset min, ScaleOffset max, RGBA color){
        ScaleOffset pointA = min.withXOffset(max.getXPixelInWindow());
        ScaleOffset pointB = min.withYOffset(max.getYPixelInWindow());
        drawTriangle(min, pointA, pointB, color, color, color);
        drawTriangle(max, pointA, pointB, color, color, color);
    }

    public final void beginFrame(){
        cursorShapeInThisFrame = -1;
        cursorModeInThisFrame = -1;
        begin();
    }
    public abstract void begin();
    public final void endFrame(){
        end();
        if (cursorShapeInThisFrame != -1) {
            glfwSetCursor(windowHandle, cursorShapeInThisFrame);
        }
        if (cursorModeInThisFrame != -1) {
            glfwSetInputMode(windowHandle, GLFW_CURSOR, cursorModeInThisFrame);
        }
    }
    public abstract void end();
    public void setCursorShapeInThisFrame(CursorShape cursorShapeInThisFrame) {
        this.cursorShapeInThisFrame = cursorShapeMap.get(cursorShapeInThisFrame.getGLFWValue());
    }
    public void setCursorModeInThisFrame(int cursorModeInThisFrame) {
        this.cursorModeInThisFrame = cursorModeInThisFrame;
    }
    public void setCursorModeInThisFrame(CursorMode cursorModeInThisFrame) {
        this.cursorModeInThisFrame = cursorModeInThisFrame.getGLFWValue();
    }
    public void setCursorShapeInThisFrame(long cursorShapeInThisFrame) {
        this.cursorShapeInThisFrame = cursorShapeInThisFrame;
    }

    /**
     * 警告:非特殊情况请从{@link top.untoldstudio.rimeui.core.texture.TextureManager}加载纹理,该方法将被ImageManager的loadImage调用
     * 它不会释放stbData内存,如果你忘了就会内存泄漏！
     */
    public abstract int loadImage(int width, int height, ByteBuffer stbData);
    public abstract void saveContext();
    public abstract void restoreContext();
    public void drawTriangle(ScaleOffset positionA, ScaleOffset positionB, ScaleOffset positionC, RGBA colorA, RGBA colorB, RGBA colorC){
        drawTriangle(positionA.getXPixelInWindow(), positionA.getYPixelInWindow(), positionB.getXPixelInWindow(), positionB.getYPixelInWindow(), positionC.getXPixelInWindow(), positionC.getYPixelInWindow(),
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
        drawTexture(textureId, min.getXPixelInWindow(), min.getYPixelInWindow(), max.getXPixelInWindow(), max.getYPixelInWindow(),
                0, 1, 0, 1,
                red, green, blue, alpha,
                red, green, blue, alpha,
                red, green, blue, alpha,
                red, green, blue, alpha
        );
    }

    public void drawString(String text, Font font, ScaleOffset startDrawPosition, int fontSize, RGBA color) {
        drawString(text, font, startDrawPosition, fontSize, color, 0.0, 0);
    }

    public void drawString(String text, Font font, ScaleOffset startDrawPosition, int fontSize, RGBA color, double italicDegrees, int boldStrength) {
        FT_Set_Pixel_Sizes(font.face(), 0, fontSize);
        FT_Face face = font.face();
        long ascender = Objects.requireNonNull(face.size()).metrics().ascender();
        float ascenderPx = ascender / 64.0f;

        float penX = startDrawPosition.getXPixelInWindow();
        float penY = startDrawPosition.getYPixelInWindow() + ascenderPx;

        beginTextRendering();

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
            FT_GlyphSlot glyphSlot = face.glyph();
            if (glyphSlot == null) continue;

            if (italicMatrix != null) {
                FT_Outline_Transform(glyphSlot.outline(), italicMatrix);
            }

            if (boldStrength > 0) {
                FT_Outline_Embolden(glyphSlot.outline(), boldStrength26_6);
            }

            if (FT_Render_Glyph(glyphSlot, FT_RENDER_MODE_NORMAL) != 0) continue;

            FT_Bitmap bitmap = glyphSlot.bitmap();
            int bitmapLeft = glyphSlot.bitmap_left();
            int bitmapTop = glyphSlot.bitmap_top();

            float glyphX = penX + bitmapLeft;
            float glyphY = penY - bitmapTop;

            drawGlyph(bitmap, Math.round(glyphX), Math.round(glyphY), color);

            FT_Vector advance = glyphSlot.advance();
            penX += advance.x() / 64.0f;
            penY += advance.y() / 64.0f;

            previousGlyphIndex = glyphIndex;
            hasPrevious = true;
        }

        if (italicMatrix != null) {
            italicMatrix.free();
        }

        endTextRendering();
    }

    public abstract void drawGlyph(FT_Bitmap bitmap, int glyphX, int glyphY, RGBA color);
    protected abstract void beginTextRendering();
    protected abstract void endTextRendering();
    public abstract void cleanup();

    public void drawNiceGridTexture(int textureId, ScaleOffset position, ScaleOffset size,
                                    int textureWidth, int textureHeight,
                                    int left, int right, int top, int bottom, RGBA color) {
        int positionX = position.getXPixelInWindow();
        int positionY = position.getYPixelInWindow();
        int targetWidth = size.getXPixelInWindow();
        int targetHeight = size.getYPixelInWindow();

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
                r, g, b, a, r, g, b, a, r, g, b, a, r, g, b, a);

        drawTexture(textureId,
                centerPositionX, positionY,
                centerPositionX + targetCenterWidth, positionY + targetTop,
                left / (float) textureWidth, (left + textureCenterWidth) / (float) textureWidth,
                0f, top / (float) textureHeight,
                r, g, b, a, r, g, b, a, r, g, b, a, r, g, b, a);

        drawTexture(textureId,
                positionX + targetWidth - targetRight, positionY,
                rightX, positionY + targetTop,
                (textureWidth - right) / (float) textureWidth, 1f,
                0f, top / (float) textureHeight,
                r, g, b, a, r, g, b, a, r, g, b, a, r, g, b, a);

        drawTexture(textureId,
                positionX, centerPositionY,
                positionX + targetLeft, centerPositionY + targetCenterHeight,
                0f, left / (float) textureWidth,
                top / (float) textureHeight, (top + textureCenterHeight) / (float) textureHeight,
                r, g, b, a, r, g, b, a, r, g, b, a, r, g, b, a);

        drawTexture(textureId,
                centerPositionX, centerPositionY,
                centerPositionX + targetCenterWidth, centerPositionY + targetCenterHeight,
                left / (float) textureWidth, (left + textureCenterWidth) / (float) textureWidth,
                top / (float) textureHeight, (top + textureCenterHeight) / (float) textureHeight,
                r, g, b, a, r, g, b, a, r, g, b, a, r, g, b, a);

        drawTexture(textureId,
                positionX + targetWidth - targetRight, centerPositionY,
                rightX, centerPositionY + targetCenterHeight,
                (textureWidth - right) / (float) textureWidth, 1f,
                top / (float) textureHeight, (top + textureCenterHeight) / (float) textureHeight,
                r, g, b, a, r, g, b, a, r, g, b, a, r, g, b, a);

        drawTexture(textureId,
                positionX, positionY + targetHeight - targetBottom,
                positionX + targetLeft, bottomY,
                0f, left / (float) textureWidth,
                (textureHeight - bottom) / (float) textureHeight, 1f,
                r, g, b, a, r, g, b, a, r, g, b, a, r, g, b, a);

        drawTexture(textureId,
                centerPositionX, positionY + targetHeight - targetBottom,
                centerPositionX + targetCenterWidth, bottomY,
                left / (float) textureWidth, (left + textureCenterWidth) / (float) textureWidth,
                (textureHeight - bottom) / (float) textureHeight, 1f,
                r, g, b, a, r, g, b, a, r, g, b, a, r, g, b, a);

        drawTexture(textureId,
                positionX + targetWidth - targetRight, positionY + targetHeight - targetBottom,
                rightX, bottomY,
                (textureWidth - right) / (float) textureWidth, 1f,
                (textureHeight - bottom) / (float) textureHeight, 1f,
                r, g, b, a, r, g, b, a, r, g, b, a, r, g, b, a);
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

    public long getWindowHandle(){
        return windowHandle;
    }

    public GuiRender(long windowHandle){
        this.windowHandle = windowHandle;

        for (CursorShape shape : CursorShape.values()){
            cursorShapeMap.put(shape.getGLFWValue(), glfwCreateStandardCursor(shape.getGLFWValue()));
        }
    }
}
