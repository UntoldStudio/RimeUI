package top.untoldstudio.simpleui.common.gui;

import top.untoldstudio.simpleui.common.core.ARGB;
import top.untoldstudio.simpleui.common.core.LayoutVector2;

import java.util.List;

public abstract class GuiRender {
    public abstract void drawTexture(String texturePath, int positionX, int positionY, float u, float v, int sizeX, int sizeY, int uWidth, int vHeight, int textureWidth, int textureHeight, ARGB color);
    public abstract void renderDeferredElements();
    public abstract void enableScissor(LayoutVector2 start, LayoutVector2 end);
    public abstract void disableScissor();
    public abstract void fill(LayoutVector2 start, LayoutVector2 end, ARGB color);
    public void drawString(Font font, double scale, LayoutVector2 position, LayoutVector2 size, TextAlign align, ARGB color, boolean isWrap, boolean hasShadow, boolean hasBackground){
        int areaX = position.getXAllPixel();
        int areaY = position.getYAllPixel();
        int areaWidth = size.getXAllPixel();
        int areaHeight = size.getYAllPixel();

        int lineHeight = 9;
        int textWidth = 0;
        for (FormattedCharSequence line : lines) {
            int width = font.width(line);
            if (width > textWidth) textWidth = width;
        }
        int textHeight = lines.size() * lineHeight;

        int x = areaX;
        int y = areaY;

        switch (align) {
            case TOP_CENTER -> x = areaX + (areaWidth - textWidth) / 2;
            case TOP_RIGHT -> x = areaX + areaWidth - textWidth;
            case CENTER_LEFT -> y = areaY + (areaHeight - textHeight) / 2;
            case CENTER -> {
                x = areaX + (areaWidth - textWidth) / 2;
                y = areaY + (areaHeight - textHeight) / 2;
            }
            case CENTER_RIGHT -> {
                x = areaX + areaWidth - textWidth;
                y = areaY + (areaHeight - textHeight) / 2;
            }
            case BOTTOM_LEFT -> y = areaY + areaHeight - textHeight;
            case BOTTOM_CENTER -> {
                x = areaX + (areaWidth - textWidth) / 2;
                y = areaY + areaHeight - textHeight;
            }
            case BOTTOM_RIGHT -> {
                x = areaX + areaWidth - textWidth;
                y = areaY + areaHeight - textHeight;
            }
        }

        if (background) {
            int bgColor = Minecraft.getInstance().options.getBackgroundColor(0.0F);
            if (bgColor != 0) {
                int alpha = (color.getIntColor() >> 24) & 0xFF;
                int bgAlpha = (bgColor >> 24) & 0xFF;
                int blendedAlpha = (bgAlpha * alpha) / 255;
                int blendedColor = (bgColor & 0x00FFFFFF) | (blendedAlpha << 24);
                render.fill(x - 2, y - 2, x + textWidth + 2, y + textHeight + 2, blendedColor);
            }
        }

        int currentY = y;
        for (FormattedCharSequence line : lines) {
            render.drawString(font, line, x, currentY, color.getIntColor(), shadow);
            currentY += lineHeight;
        }
    }
    public void drawTexture(String texturePath, LayoutVector2 position, LayoutVector2 size, int textureWidth, int textureHeight, ARGB color){
        drawTexture(texturePath, position.getXAllPixel(), position.getYAllPixel(), 0, 0, size.getXAllPixel(), size.getYAllPixel(), textureWidth, textureHeight, textureWidth, textureHeight, color);
    }
    public void drawNiceGridTexture(String texturePath, LayoutVector2 position, LayoutVector2 size, int textureWidth, int textureHeight, int left, int top, int right, int bottom, ARGB color) {
        int positionX = position.getXAllPixel();
        int positionY = position.getYAllPixel();
        int targetWidth = size.getXAllPixel();
        int targetHeight = size.getYAllPixel();

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

        drawTexture(texturePath, positionX, positionY, 0, 0, targetLeft, targetTop, left, top, textureWidth, textureHeight, color);
        drawTexture(texturePath, centerPositionX, positionY, left, 0, targetCenterWidth, targetTop, textureCenterWidth, top, textureWidth, textureHeight, color);
        drawTexture(texturePath, positionX + targetWidth - targetRight, positionY, textureWidth - right, 0, targetRight, targetTop, right, top, textureWidth, textureHeight, color);

        drawTexture(texturePath, positionX, centerPositionY, 0, top, targetLeft, targetCenterHeight, left, textureCenterHeight, textureWidth, textureHeight, color);
        drawTexture(texturePath, centerPositionX, centerPositionY, left, top, targetCenterWidth, targetCenterHeight, textureCenterWidth, textureCenterHeight, textureWidth, textureHeight, color);
        drawTexture(texturePath, positionX + targetWidth - targetRight, centerPositionY, textureWidth - right, top, targetRight, targetCenterHeight, right, textureCenterHeight, textureWidth, textureHeight, color);

        drawTexture(texturePath, positionX, positionY + targetHeight - targetBottom, 0, textureHeight - bottom, targetLeft, targetBottom, left, bottom, textureWidth, textureHeight, color);
        drawTexture(texturePath, centerPositionX, positionY + targetHeight - targetBottom, left, textureHeight - bottom, targetCenterWidth, targetBottom, textureCenterWidth, bottom, textureWidth, textureHeight, color);
        drawTexture(texturePath, positionX + targetWidth - targetRight, positionY + targetHeight - targetBottom, textureWidth - right, textureHeight - bottom, targetRight, targetBottom, right, bottom, textureWidth, textureHeight, color);
    }
}
