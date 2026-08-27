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
package top.untoldstudio.rimeui.core.ui.node;

import top.untoldstudio.rimeui.core.data.HorizontalAlignment;
import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.data.VerticalAlignment;
import top.untoldstudio.rimeui.core.font.Font;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.signal.SignalType;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;

public final class TextLabel extends AbstractFrame<TextLabel> {
    private boolean canFrameBackgroundDisplay = false;
    private Font font = Font.JETBRAINS_MONO;
    private String text;
    private int fontSize = 14;
    private RGBA textColor = RGBA.WHITE;
    private float italicSlant = 0;
    private int boldStrength = 0;
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
    private VerticalAlignment verticalAlignment = VerticalAlignment.TOP;
    private ScaleOffset textRenderPosition;

    @Override
    public void render(GuiRender render, double delta){
        if (canFrameBackgroundDisplay){
            super.renderFrameDefaultBackground(render, delta);
        }
        render.drawString(text, font, textRenderPosition, fontSize, textColor, italicSlant, boldStrength);
    }

    public TextLabel setFont(Font font){
        this.font = font;
        sendSignal(SignalType.SET_FONT, font);
        return this;
    }
    public TextLabel setText(String text){
        this.text = text;
        sendSignal(SignalType.SET_TEXT, text);
        return this;
    }
    public TextLabel setFontSize(int fontSize){
        this.fontSize = fontSize;
        sendSignal(SignalType.SET_FONT_SIZE, fontSize);
        return this;
    }
    public TextLabel setCanFrameBackgroundDisplay(boolean canFrameBackgroundDisplay){
        this.canFrameBackgroundDisplay = canFrameBackgroundDisplay;
        sendSignal(SignalType.SET_CAN_FRAME_BACKGROUND_DISPLAY, canFrameBackgroundDisplay);
        return this;
    }
    public TextLabel setTextColor(RGBA textColor){
        this.textColor = textColor;
        sendSignal(SignalType.SET_TEXT_COLOR, textColor);
        return this;
    }
    public TextLabel setItalicSlant(float italicSlant){
        this.italicSlant = italicSlant;
        sendSignal(SignalType.SET_ITALIC_ALANT);
        return this;
    }
    public TextLabel setBoldStrength(int boldStrength){
        this.boldStrength = boldStrength;
        sendSignal(SignalType.SET_BOLD_STRENGTH, boldStrength);
        return this;
    }
    public TextLabel setHorizontalAlignment(HorizontalAlignment horizontalAlignment){
        this.horizontalAlignment = horizontalAlignment;
        sendSignal(SignalType.SET_HORIZONTAL_ALANT, horizontalAlignment);
        return this;
    }
    public TextLabel setVerticalAlignment(VerticalAlignment verticalAlignment){
        this.verticalAlignment = verticalAlignment;
        sendSignal(SignalType.SET_VERTICAL_ALANT, verticalAlignment);
        return this;
    }

    public Font getFont(){
        return font;
    }
    public String getText(){
        return text;
    }
    public int getFontSize(){
        return fontSize;
    }
    public boolean canFrameBackgroundDisplay(){
        return canFrameBackgroundDisplay;
    }
    public RGBA getTextColor(){
        return textColor;
    }
    public float getItalicSlant(){
        return italicSlant;
    }
    public int getBoldStrength(){
        return boldStrength;
    }
    public HorizontalAlignment getHorizontalAlignment(){
        return horizontalAlignment;
    }
    public VerticalAlignment getVerticalAlignment(){
        return verticalAlignment;
    }

    @Override
    protected void operationPosition(AbstractFrame<?> parentFrame, ScaleOffset parentRealPosition){
        super.operationPosition(parentFrame, parentRealPosition);
        int stringWidth = font.getStringWidth(text, fontSize, italicSlant, boldStrength);
        int stringHeight = font.getStringHeight(text, fontSize, italicSlant, boldStrength);
        int horizontalAlignmentPixel = switch (horizontalAlignment) {
            case HorizontalAlignment.LEFT -> realPosition.getXPixel();
            case HorizontalAlignment.RIGHT -> realPositionMax.getXPixel() - stringWidth;
            case HorizontalAlignment.CENTER -> (realPosition.getXPixel() + realPositionMax.getXPixel() - stringWidth) / 2;
        };
        int verticalAlignmentPixel = switch (verticalAlignment) {
            case VerticalAlignment.TOP -> realPosition.getYPixel();
            case VerticalAlignment.BOTTOM -> realPositionMax.getYPixel() - stringHeight;
            case VerticalAlignment.CENTER -> (realPosition.getYPixel() + realPositionMax.getYPixel() - stringHeight) / 2;
        };
        textRenderPosition = ScaleOffset.fromOffset(horizontalAlignmentPixel, verticalAlignmentPixel);
    }

    public TextLabel(String text, ScaleOffset position, ScaleOffset size) {
        super(position, size);
        this.text = text;
    }
}
