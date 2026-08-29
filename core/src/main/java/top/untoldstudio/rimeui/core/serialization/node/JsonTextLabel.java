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
package top.untoldstudio.rimeui.core.serialization.node;

import com.google.gson.annotations.SerializedName;
import top.untoldstudio.rimeui.core.data.HorizontalAlignment;
import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.VerticalAlignment;
import top.untoldstudio.rimeui.core.serialization.JsonAbstractFrame;
import top.untoldstudio.rimeui.core.serialization.JsonFont;
import top.untoldstudio.rimeui.core.ui.node.TextLabel;

public final class JsonTextLabel extends JsonAbstractFrame {
    @SerializedName("can_frame_background_display")
    private boolean canFrameBackgroundDisplay;
    private JsonFont font;
    private String text;
    @SerializedName("font_size")
    private int fontSize;
    @SerializedName("text_color")
    private RGBA textColor;
    @SerializedName("italic_slant")
    private float italicSlant;
    @SerializedName("bold_strength")
    private int boldStrength;
    @SerializedName("horizontal_alignment")
    private HorizontalAlignment horizontalAlignment;
    @SerializedName("vertical_alignment")
    private VerticalAlignment verticalAlignment;
    @SerializedName("is_block_input")
    private boolean isBlockInput;

    @Override
    public TextLabel toGuiNode(){
        TextLabel label = new TextLabel(text, position, size);
        label.setCanFrameBackgroundDisplay(canFrameBackgroundDisplay);
        label.setFont(font.toFont());
        label.setText(text);
        label.setFontSize(fontSize);
        label.setTextColor(textColor);
        label.setItalicSlant(italicSlant);
        label.setBoldStrength(boldStrength);
        label.setHorizontalAlignment(horizontalAlignment);
        label.setVerticalAlignment(verticalAlignment);
        label.setIsBlockInput(isBlockInput);
        super.fillParentGuiNodeField(label);
        return label;
    }

    public boolean canFrameBackgroundDisplay() {
        return canFrameBackgroundDisplay;
    }
    public JsonFont getFont() {
        return font;
    }
    public String getText() {
        return text;
    }
    public int getFontSize() {
        return fontSize;
    }
    public RGBA getTextColor() {
        return textColor;
    }
    public float getItalicSlant(){
        return italicSlant;
    }
    public int getBoldStrength(){
        return boldStrength;
    }
    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }
    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }
    public boolean isBlockInput() {
        return isBlockInput;
    }

    public void setCanFrameBackgroundDisplay(boolean canFrameBackgroundDisplay) {
        this.canFrameBackgroundDisplay = canFrameBackgroundDisplay;
    }
    public void setFont(JsonFont font) {
        this.font = font;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    public void setTextColor(RGBA textColor) {
        this.textColor = textColor;
    }
    public void setItalicSlant(float italicSlant){
        this.italicSlant = italicSlant;
    }
    public void setBoldStrength(int boldStrength) {
        this.boldStrength = boldStrength;
    }
    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }
    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }
    public void setIsBlockInput(boolean isBlockInput) {
        this.isBlockInput = isBlockInput;
    }
}
