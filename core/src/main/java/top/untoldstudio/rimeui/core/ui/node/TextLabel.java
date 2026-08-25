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

import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.font.Font;
import top.untoldstudio.rimeui.core.font.FontManager;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.signal.SignalType;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;

public class TextLabel extends AbstractFrame<TextLabel> {
    private boolean canFrameBackgroundDisplay = false;
    private Font font;
    private String text;
    private int fontSize = 14;

    @Override
    public void render(GuiRender render){
        if (canFrameBackgroundDisplay){
            super.renderFrameDefaultBackground(render);
        }
        render.drawString(text, font, realPosition, fontSize, color);
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

    public TextLabel(String text, ScaleOffset position, ScaleOffset size) {
        super(position, size);
        font = FontManager.loadFont("/font/jetbrains_mono.ttf");
        this.text = text;
    }
}
