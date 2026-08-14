package top.untoldstudio.simpleui.gui;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.cursor.CursorType;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiElementRenderState;
import net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.model.BannerFlagModel;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.profiling.ResultField;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.joml.Matrix3x2fStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import top.untoldstudio.simpleui.core.ARGB;
import top.untoldstudio.simpleui.core.LayoutVector2;

import java.util.List;

public final class GuiRender {
    private final GuiGraphics render;

    public void enableScissor(LayoutVector2 min, LayoutVector2 max) {
        render.enableScissor(min.getXAllPixel(), min.getYAllPixel(), max.getXAllPixel(), max.getYAllPixel());
    }
    public void disableScissor(){
        render.disableScissor();
    }
    public void drawEntity(EntityRenderState state, float scale, Vector3f translation, Quaternionf rotation, Quaternionf overrideCameraAngle, LayoutVector2 min, LayoutVector2 max){
        render.submitEntityRenderState(state, scale, translation, rotation, overrideCameraAngle, min.getXAllPixel(), min.getYAllPixel(), max.getXAllPixel(), max.getYAllPixel());
    }
    public void drawBookModel(BookModel model, ResourceLocation texture, float scale, float open, float flip, LayoutVector2 min, LayoutVector2 max){
        render.submitBookModelRenderState(model, texture, scale, open, flip, min.getXAllPixel(), min.getYAllPixel(), max.getXAllPixel(), max.getYAllPixel());
    }
    public void drawBannerPattern(BannerFlagModel model, DyeColor baseColor, BannerPatternLayers resultBannerPatterns, LayoutVector2 min, LayoutVector2 max){
        render.submitBannerPatternRenderState(model, baseColor, resultBannerPatterns, min.getXAllPixel(), min.getYAllPixel(), max.getXAllPixel(), max.getYAllPixel());
    }
    public void drawSign(Model.Simple signModel, float scale, WoodType woodType, LayoutVector2 min, LayoutVector2 max){
        render.submitSignRenderState(signModel, scale, woodType, min.getXAllPixel(), min.getYAllPixel(), max.getXAllPixel(), max.getYAllPixel());
    }
    public void drawProfilerChart(List<ResultField> chartData, LayoutVector2 min, LayoutVector2 max){
        render.submitProfilerChartRenderState(chartData, min.getXAllPixel(), min.getYAllPixel(), max.getXAllPixel(), max.getYAllPixel());
    }
    public void drawMap(MapRenderState renderState){
        render.submitMapRenderState(renderState);
    }
    public void drawGuiElement(GuiElementRenderState renderState){
        render.submitGuiElementRenderState(renderState);
    }
    public void drawPictureInPicture(PictureInPictureRenderState renderState){
        render.submitPictureInPictureRenderState(renderState);
    }
    public void drawTooltip(Component text, LayoutVector2 position){
        render.setTooltipForNextFrame(text, position.getXAllPixel(), position.getYAllPixel());
    }
    public void drawTooltip(List<FormattedCharSequence> lines, LayoutVector2 position){
        render.setTooltipForNextFrame(lines, position.getXAllPixel(), position.getYAllPixel());
    }
    public void drawTooltip(Font font, List<? extends FormattedCharSequence> lines, LayoutVector2 position){
        render.setTooltipForNextFrame(font, lines, position.getXAllPixel(), position.getYAllPixel());
    }
    public void drawTooltip(Font font, List<? extends FormattedCharSequence> lines, LayoutVector2 position, ResourceLocation backgroundTexture){
        render.setTooltipForNextFrame(font, lines, position.getXAllPixel(), position.getYAllPixel(), backgroundTexture);
    }
    public void drawTooltip(Font font, ItemStack stack, LayoutVector2 position){
        render.setTooltipForNextFrame(font, stack, position.getXAllPixel(), position.getYAllPixel());
    }
    public void drawComponentTooltip(Font font, List<Component> lines, LayoutVector2 position){
        render.setComponentTooltipForNextFrame(font, lines, position.getXAllPixel(), position.getYAllPixel());
    }
    public void drawComponentTooltip(Font font, List<Component> lines, LayoutVector2 position, ResourceLocation backgroundTexture){
        render.setComponentTooltipForNextFrame(font, lines, position.getXAllPixel(), position.getYAllPixel(), backgroundTexture);
    }
    public void drawComponentTooltip(Font font, List<? extends FormattedText> lines, LayoutVector2 position, ItemStack stack){
        render.setComponentTooltipForNextFrame(font, lines, position.getXAllPixel(), position.getYAllPixel(), stack);
    }
    public void drawComponentTooltip(Font font, List<? extends FormattedText> lines, LayoutVector2 position, ItemStack stack, ResourceLocation backgroundTexture){
        render.setComponentTooltipForNextFrame(font, lines, position.getXAllPixel(), position.getYAllPixel(), stack, backgroundTexture);
    }
    public void drawComponentTooltip(Font font, List<Either<FormattedText, TooltipComponent>> elements, ItemStack stack){
        Gui gui = Gui.getInstance();
        render.setComponentTooltipFromElementsForNextFrame(font, elements, (int)Math.round(gui.getLastMouseX()), (int)Math.round(gui.getLastMouseY()), stack);
    }
    public void drawComponentTooltip(Font font, List<Either<FormattedText, TooltipComponent>> elements, ItemStack stack, ResourceLocation backgroundTexture){
        Gui gui = Gui.getInstance();
        render.setComponentTooltipFromElementsForNextFrame(font, elements, (int)Math.round(gui.getLastMouseX()), (int)Math.round(gui.getLastMouseY()), stack, backgroundTexture);
    }
    public void drawTooltip(Font font, List<FormattedCharSequence> lines, ClientTooltipPositioner positioner, LayoutVector2 position, boolean focused){
        render.setTooltipForNextFrame(font, lines, positioner, position.getXAllPixel(), position.getYAllPixel(), focused);
    }
    public void drawTooltipWithHoverEffect(Font font, Style style){
        Gui gui = Gui.getInstance();
        render.renderComponentHoverEffect(font, style, (int)Math.round(gui.getLastMouseX()), (int)Math.round(gui.getLastMouseY()));
    }
    public void drawItem(ItemStack stack, LayoutVector2 position){
        render.renderItem(stack, position.getXAllPixel(), position.getYAllPixel());
    }
    public void drawItem(ItemStack stack, LayoutVector2 position, int seed){
        render.renderItem(stack, position.getXAllPixel(), position.getYAllPixel(), seed);
    }
    public void drawItem(LivingEntity itemOwner, ItemStack stack, LayoutVector2 position, int seed){
        render.renderItem(itemOwner, stack, position.getXAllPixel(), position.getYAllPixel(), seed);
    }
    public void drawItemWithDecorations(Font font, ItemStack stack, LayoutVector2 position){
        render.renderItemDecorations(font, stack, position.getXAllPixel(), position.getYAllPixel());
    }
    public void drawItemWithDecoration(Font font, ItemStack stack, LayoutVector2 position, String textInItemCount){
        render.renderItemDecorations(font, stack, position.getXAllPixel(), position.getYAllPixel(), textInItemCount);
    }
    public void drawString(Font font, FormattedCharSequence text, LayoutVector2 position, ARGB color){
        render.drawString(font, text, position.getXAllPixel(), position.getYAllPixel(), color.getIntColor());
    }
    public void drawString(Font font, FormattedCharSequence text, LayoutVector2 position, boolean isDrawShadow, ARGB color){
        render.drawString(font, text, position.getXAllPixel(), position.getYAllPixel(), color.getIntColor(), isDrawShadow);
    }
    public void drawString(Font font, Component text, LayoutVector2 position, ARGB color){
        render.drawString(font, text, position.getXAllPixel(), position.getYAllPixel(), color.getIntColor());
    }
    public void drawString(Font font, Component text, LayoutVector2 position, boolean isDrawShadow, ARGB color){
        render.drawString(font, text, position.getXAllPixel(), position.getYAllPixel(), color.getIntColor(), isDrawShadow);
    }
    public void drawString(Font font, String text, LayoutVector2 position, ARGB color) {
        render.drawString(font, text, position.getXAllPixel(), position.getYAllPixel(), color.getIntColor());
    }
    public void drawString(Font font, String text, LayoutVector2 position, boolean drawShadow, ARGB color) {
        render.drawString(font, text, position.getXAllPixel(), position.getYAllPixel(), color.getIntColor(), drawShadow);
    }
    public void drawString(Font font, float scale, Component text, LayoutVector2 position, LayoutVector2 size, ARGB color, TextAlign align, boolean wrap, boolean shadow, boolean background) {
        Matrix3x2fStack stack = render.pose();
        stack.pushMatrix();

        int areaX = position.getXAllPixel();
        int areaY = position.getYAllPixel();
        int areaWidth = size.getXAllPixel();
        int areaHeight = size.getYAllPixel();

        float centerX = areaX + areaWidth / 2.0f;
        float centerY = areaY + areaHeight / 2.0f;

        stack.translate(centerX, centerY);
        stack.scale(scale, scale);
        stack.translate(-centerX, -centerY);

        drawString(font, text, position, size, color, align, wrap, shadow, background);

        stack.popMatrix();
    }
    public void drawString(Font font, Component text, LayoutVector2 position, LayoutVector2 size, ARGB color, TextAlign align, boolean wrap, boolean shadow, boolean background) {
        int areaX = position.getXAllPixel();
        int areaY = position.getYAllPixel();
        int areaWidth = size.getXAllPixel();
        int areaHeight = size.getYAllPixel();

        List<FormattedCharSequence> lines;
        if (wrap && areaWidth > 0) {
            lines = font.split(text, areaWidth);
        } else {
            lines = List.of(text.getVisualOrderText());
        }
        if (lines.isEmpty()) return;

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
    public void drawStringInCenterWithShadow(Font font, Component text, LayoutVector2 position, ARGB color){
        render.drawCenteredString(font, text, position.getXAllPixel(), position.getYAllPixel(), color.getIntColor());
    }
    public void drawStringInCenterWithShadow(Font font, FormattedCharSequence text, LayoutVector2 position, ARGB color){
        render.drawCenteredString(font, text, position.getXAllPixel(), position.getYAllPixel(), color.getIntColor());
    }
    public void drawStringWithWordWrap(Font font, FormattedText text, LayoutVector2 position, int maxLineWidth, ARGB color){
        render.drawWordWrap(font, text, position.getXAllPixel(), position.getYAllPixel(), maxLineWidth, color.getIntColor());
    }
    public void drawStringWithWordWrap(Font font, FormattedText text, LayoutVector2 position, int maxLineWidth, boolean isDrawShadow, ARGB color){
        render.drawWordWrap(font, text, position.getXAllPixel(), position.getYAllPixel(), maxLineWidth, color.getIntColor(), isDrawShadow);
    }
    public void drawStringWithBackdrop(Font font, Component text, LayoutVector2 position, int backdropWidth, ARGB color){
        render.drawStringWithBackdrop(font, text, position.getXAllPixel(), position.getYAllPixel(), backdropWidth, color.getIntColor());
    }
    public void drawTexture(RenderPipeline pipeline, ResourceLocation location, LayoutVector2 position, float u, float v, LayoutVector2 size, int uWidth, int vHeight, int textureWidth, int textureHeight, ARGB color){
        render.blit(pipeline, location, position.getXAllPixel(), position.getYAllPixel(), u, v, size.getXAllPixel(), size.getYAllPixel(), uWidth, vHeight, textureWidth, textureHeight, color.getIntColor());
    }
    public void drawTexture(RenderPipeline pipeline, ResourceLocation location, LayoutVector2 position, float u, float v, LayoutVector2 size, int uWidth, int vHeight, int textureWidth, int textureHeight){
        drawTexture(pipeline, location, position, u, v, size, uWidth, vHeight, textureWidth, textureHeight, ARGB.WHITE);
    }
    public void drawTexture(RenderPipeline pipeline, ResourceLocation location, LayoutVector2 position, float u, float v, LayoutVector2 size, int textureWidth, int textureHeight, ARGB color){
        drawTexture(pipeline, location, position, u, v, size, size.getXAllPixel(), size.getYAllPixel(), textureWidth, textureHeight, color);
    }
    public void drawTexture(RenderPipeline pipeline, ResourceLocation location, int positionX, int positionY, float u, float v, int sizeX, int sizeY, int uWidth, int vHeight, int textureWidth, int textureHeight){
        render.blit(pipeline, location, positionX, positionY, u, v, sizeX, sizeY, uWidth, vHeight, textureWidth, textureHeight, -1);
    }
    public void drawTexture(RenderPipeline pipeline, ResourceLocation location, int positionX, int positionY, float u, float v, int sizeX, int sizeY, int uWidth, int vHeight, int textureWidth, int textureHeight, ARGB color){
        render.blit(pipeline, location, positionX, positionY, u, v, sizeX, sizeY, uWidth, vHeight, textureWidth, textureHeight, color.getIntColor());
    }

    public void drawNiceGridTexture(RenderPipeline pipeline, ResourceLocation location, LayoutVector2 position, LayoutVector2 size, int textureWidth, int textureHeight, int left, int top, int right, int bottom, ARGB color) {
        int positionX = position.getXAllPixel();
        int positionY = position.getYAllPixel();
        int targetWidth = size.getXAllPixel();
        int targetHeight = size.getYAllPixel();

        if (targetWidth <= 0 || targetHeight <= 0 || textureWidth <= 0 || textureHeight <= 0) {
            return;
        }

        float widthScale = targetWidth / (float) textureWidth;

        int targetLeft   = Math.min(Math.round(left * widthScale), targetWidth / 2);
        int targetRight  = Math.min(Math.round(right * widthScale), targetWidth / 2);
        int targetTop    = Math.min(Math.round(top * widthScale), targetHeight / 2);
        int targetBottom = Math.min(Math.round(bottom * widthScale), targetHeight / 2);

        int centerPositionX = positionX + targetLeft;
        int centerPositionY = positionY + targetTop;
        int targetCenterWidth = targetWidth - targetLeft - targetRight;
        int targetCenterHeight = targetHeight - targetTop - targetBottom;

        int textureCenterWidth = textureWidth - left - right;
        int textureCenterHeight = textureHeight - top - bottom;

        drawTexture(pipeline, location, positionX, positionY, 0, 0, targetLeft, targetTop, left, top, textureWidth, textureHeight, color);
        drawTexture(pipeline, location, centerPositionX, positionY, left, 0, targetCenterWidth, targetTop, textureCenterWidth, top, textureWidth, textureHeight, color);
        drawTexture(pipeline, location, positionX + targetWidth - targetRight, positionY, textureWidth - right, 0, targetRight, targetTop, right, top, textureWidth, textureHeight, color);

        drawTexture(pipeline, location, positionX, centerPositionY, 0, top, targetLeft, targetCenterHeight, left, textureCenterHeight, textureWidth, textureHeight, color);
        drawTexture(pipeline, location, centerPositionX, centerPositionY, left, top, targetCenterWidth, targetCenterHeight, textureCenterWidth, textureCenterHeight, textureWidth, textureHeight, color);
        drawTexture(pipeline, location, positionX + targetWidth - targetRight, centerPositionY, textureWidth - right, top, targetRight, targetCenterHeight, right, textureCenterHeight, textureWidth, textureHeight, color);

        drawTexture(pipeline, location, positionX, positionY + targetHeight - targetBottom, 0, textureHeight - bottom, targetLeft, targetBottom, left, bottom, textureWidth, textureHeight, color);
        drawTexture(pipeline, location, centerPositionX, positionY + targetHeight - targetBottom, left, textureHeight - bottom, targetCenterWidth, targetBottom, textureCenterWidth, bottom, textureWidth, textureHeight, color);
        drawTexture(pipeline, location, positionX + targetWidth - targetRight, positionY + targetHeight - targetBottom, textureWidth - right, textureHeight - bottom, targetRight, targetBottom, right, bottom, textureWidth, textureHeight, color);
    }
    public void drawTexture(RenderPipeline pipeline, ResourceLocation location, LayoutVector2 position, LayoutVector2 size, int textureWidth, int textureHeight) {
        drawTexture(pipeline, location, position.getXAllPixel(), position.getYAllPixel(), 0, 0, size.getXAllPixel(), size.getYAllPixel(), textureWidth, textureHeight, textureWidth, textureHeight);
    }
    public void drawTexture(RenderPipeline pipeline, ResourceLocation location, LayoutVector2 position, LayoutVector2 size, int textureWidth, int textureHeight, ARGB color) {
        drawTexture(pipeline, location, position.getXAllPixel(), position.getYAllPixel(), 0, 0, size.getXAllPixel(), size.getYAllPixel(), textureWidth, textureHeight, textureWidth, textureHeight, color);
    }

    private int roundValue(double value){
        return (int) Math.round(value);
    }
    public void fill(LayoutVector2 min, LayoutVector2 max, ARGB color){
        render.fill(min.getXAllPixel(), min.getYAllPixel(), max.getXAllPixel(), max.getYAllPixel(), color.getIntColor());
    }
    public void fillWithGradient(LayoutVector2 min, LayoutVector2 max, ARGB topColor, ARGB bottomColor){
        render.fillGradient(min.getXAllPixel(), min.getYAllPixel(), max.getXAllPixel(), max.getYAllPixel(), topColor.getIntColor(), bottomColor.getIntColor());
    }
    public void hLine(double minXScale, int minXOffset, double maxXScale, int maxXOffset, int yScale, int yOffset, ARGB color){
        render.hLine(WindowPixel.getWindowPixelX(minXScale, minXOffset), WindowPixel.getWindowPixelX(maxXScale, maxXOffset), WindowPixel.getWindowPixelY(yScale, yOffset), color.getIntColor());
    }
    public void hLine(int minXOffset, int maxXOffset, int yOffset, ARGB color){
        render.hLine(minXOffset, maxXOffset, yOffset, color.getIntColor());
    }
    public void vLine(double minYScale, int minYOffset, double maxYScale, int maxYOffset, int xScale, int xOffset, ARGB color){
        render.vLine(WindowPixel.getWindowPixelX(xScale, xOffset), WindowPixel.getWindowPixelY(minYScale, minYOffset), WindowPixel.getWindowPixelY(maxYScale, maxYOffset), color.getIntColor());
    }
    public void vLine(int minYOffset, int maxYOffset, int xOffset, ARGB color){
        render.vLine(xOffset, minYOffset, maxYOffset, color.getIntColor());
    }
    public void changeCursorIcon(CursorType type){
        render.requestCursor(type);
    }
    public int getScreenWidth(){
        return render.guiWidth();
    }
    public int getScreenHeight(){
        return render.guiHeight();
    }
    public void renderDeferredElements(){
        render.renderDeferredElements();
    }
    public Matrix3x2fStack pose(){
        return render.pose();
    }

    public GuiGraphics getGraphics() {
        return render;
    }
    public GuiRender(GuiGraphics render) {
        this.render = render;
    }
}
