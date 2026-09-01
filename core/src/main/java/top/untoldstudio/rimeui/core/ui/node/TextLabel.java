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
import top.untoldstudio.rimeui.core.event.KeyEvent;
import top.untoldstudio.rimeui.core.event.MouseButtonEvent;
import top.untoldstudio.rimeui.core.event.MouseMoveEvent;
import top.untoldstudio.rimeui.core.event.MouseScrollEvent;
import top.untoldstudio.rimeui.core.font.Font;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.serialization.node.JsonTextLabel;
import top.untoldstudio.rimeui.core.signal.SignalType;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;

public final class TextLabel extends AbstractFrame<TextLabel> implements InputBlocker<TextLabel>, TextDisplayable {
    private boolean canFrameBackgroundDisplay = false;
    private Font font = Font.JETBRAINS_MONO;
    private String text;
    private int fontSize = 14;
    private RGBA textColor = RGBA.WHITE;
    private double italicSlant = 0;
    private int boldStrength = 0;
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.CENTER;
    private VerticalAlignment verticalAlignment = VerticalAlignment.CENTER;
    private ScaleOffset textRenderPosition;
    private boolean isBlockInput = false;

    @Override
    public JsonTextLabel toJsonNodeTree(){
        JsonTextLabel label = new JsonTextLabel();
        label.setCanFrameBackgroundDisplay(canFrameBackgroundDisplay);
        label.setText(text);
        label.setFont(font.toJsonFont());
        label.setFontSize(fontSize);
        label.setTextColor(textColor);
        label.setItalicSlant(italicSlant);
        label.setBoldStrength(boldStrength);
        label.setHorizontalAlignment(horizontalAlignment);
        label.setVerticalAlignment(verticalAlignment);
        label.setIsBlockInput(isBlockInput);
        super.fillParentClassJsonNode(label);
        return label;
    }

    @Override
    public TextLabel clone(){
        TextLabel label = new TextLabel(text, position, size);
        super.fillFieldForClone(label);
        label.setCanFrameBackgroundDisplay(canFrameBackgroundDisplay);
        label.setText(text);
        label.setFont(font);
        label.setFontSize(fontSize);
        label.setTextColor(textColor);
        label.setItalicSlant(italicSlant);
        label.setBoldStrength(boldStrength);
        label.setHorizontalAlignment(horizontalAlignment);
        label.setVerticalAlignment(verticalAlignment);
        label.setIsBlockInput(isBlockInput);
        return label;
    }

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
    public TextLabel setItalicSlant(double italicSlant){
        this.italicSlant = italicSlant;
        sendSignal(SignalType.SET_ITALIC_SLANT);
        return this;
    }
    public TextLabel setBoldStrength(int boldStrength){
        this.boldStrength = boldStrength;
        sendSignal(SignalType.SET_BOLD_STRENGTH, boldStrength);
        return this;
    }
    public TextLabel setHorizontalAlignment(HorizontalAlignment horizontalAlignment){
        this.horizontalAlignment = horizontalAlignment;
        sendSignal(SignalType.SET_HORIZONTAL_ALIGNMENT, horizontalAlignment);
        return this;
    }
    public TextLabel setVerticalAlignment(VerticalAlignment verticalAlignment){
        this.verticalAlignment = verticalAlignment;
        sendSignal(SignalType.SET_VERTICAL_ALIGNMENT, verticalAlignment);
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
    public double getItalicSlant(){
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
    public void operationPosition(AbstractFrame<?> parentFrame, ScaleOffset parentRealPosition){
        super.operationPosition(parentFrame, parentRealPosition);
        textRenderPosition = operationTextPosition(font, text, fontSize, italicSlant, boldStrength, realPosition, realPositionMax, horizontalAlignment, verticalAlignment);
    }

    public TextLabel setIsBlockInput(boolean isAcceptInput) {
        this.isBlockInput = isAcceptInput;
        sendSignal(SignalType.SET_BLOCK_INPUT, isAcceptInput);
        return this;
    }
    public boolean isBlockInput() {
        return isBlockInput;
    }

    @Override
    protected void onKeyEvent(KeyEvent event){
        if (isBlockInput && isMouseInRange()) event.cancel();
    }
    @Override
    protected void onMouseButtonEvent(MouseButtonEvent event){
        if (isBlockInput && isMouseInRange()) event.cancel();
    }
    @Override
    protected void onMouseMoveEvent(MouseMoveEvent event){
        if (isBlockInput && isMouseInRange()) event.cancel();
    }
    @Override
    protected void onMouseScrollEvent(MouseScrollEvent event){
        if (isBlockInput && isMouseInRange()) event.cancel();
    }

    public TextLabel(String text, ScaleOffset position, ScaleOffset size) {
        super(position, size);
        this.text = text;
    }
}
