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
import top.untoldstudio.rimeui.core.ui.node.TextBox;

public final class JsonTextBox extends JsonAbstractFrame {
    @SerializedName("no_input_text")
    private String noInputText;
    @SerializedName("can_frame_background_display")
    private boolean canFrameBackgroundDisplay;
    private JsonFont font;
    @SerializedName("font_size")
    private int fontSize;
    @SerializedName("italic_slant")
    private double italicSlant;
    @SerializedName("bold_strength")
    private int boldStrength;
    @SerializedName("horizontal_alignment")
    private HorizontalAlignment horizontalAlignment;
    @SerializedName("vertical_alignment")
    private VerticalAlignment verticalAlignment;
    @SerializedName("no_input_text_color")
    private RGBA noInputTextColor;
    @SerializedName("input_text_color")
    private RGBA inputTextColor;

    @Override
    public TextBox toGuiNode(){
        TextBox box = new TextBox(position, size);
        box.setNoInputText(noInputText);
        box.setCanFrameBackgroundDisplay(canFrameBackgroundDisplay);
        box.setFont(font.toFont());
        box.setFontSize(fontSize);
        box.setItalicSlant(italicSlant);
        box.setBoldStrength(boldStrength);
        box.setHorizontalAlignment(horizontalAlignment);
        box.setVerticalAlignment(verticalAlignment);
        box.setNoInputTextColor(noInputTextColor);
        box.setInputTextColor(inputTextColor);
        super.fillParentGuiNodeField(box);
        return box;
    }

    public void setNoInputText(String noInputText) {
        this.noInputText = noInputText;
    }
    public void setCanFrameBackgroundDisplay(boolean canFrameBackgroundDisplay) {
        this.canFrameBackgroundDisplay = canFrameBackgroundDisplay;
    }
    public void setFont(JsonFont font) {
        this.font = font;
    }
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    public void setItalicSlant(double italicSlant){
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
    public void setNoInputTextColor(RGBA noInputTextColor) {
        this.noInputTextColor = noInputTextColor;
    }
    public void setInputTextColor(RGBA inputTextColor) {
        this.inputTextColor = inputTextColor;
    }

    public String getNoInputText() {
        return noInputText;
    }
    public boolean canFrameBackgroundDisplay() {
        return canFrameBackgroundDisplay;
    }
    public JsonFont getFont() {
        return font;
    }
    public int getFontSize() {
        return fontSize;
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
    public RGBA getNoInputTextColor() {
        return noInputTextColor;
    }
    public RGBA getInputTextColor() {
        return inputTextColor;
    }
}
