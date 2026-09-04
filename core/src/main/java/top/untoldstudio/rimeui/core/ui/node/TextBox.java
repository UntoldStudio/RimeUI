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

import top.untoldstudio.rimeui.core.data.*;
import top.untoldstudio.rimeui.core.event.KeyEvent;
import top.untoldstudio.rimeui.core.event.MouseButtonEvent;
import top.untoldstudio.rimeui.core.font.Font;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.serialization.node.JsonTextBox;
import top.untoldstudio.rimeui.core.signal.SignalType;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;
import top.untoldstudio.rimeui.core.ui.MainGui;
import top.untoldstudio.rimeui.core.ui.task.LoopTask;

public final class TextBox extends AbstractFrame<TextBox> implements TextDisplayable {
    private String noInputText = "Please Enter Text";
    private String currentInputText = "";
    private ScaleOffset textRenderPosition;
    private boolean canFrameBackgroundDisplay = true;
    private Font font = Font.DEFAULT_FONT;
    private int fontSize = 14;
    private double italicSlant = 0;
    private int boldStrength = 0;
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.CENTER;
    private VerticalAlignment verticalAlignment = VerticalAlignment.CENTER;
    private RGBA noInputTextColor = RGBA.GRAY;
    private RGBA inputTextColor = RGBA.BLACK;
    private boolean isInFocus = false;
    private int cursorPosition = 0;
    private boolean cursorVisible = false;
    private LoopTask cursorBlinkTask;

    @Override
    public JsonTextBox toJsonNodeTree() {
        JsonTextBox box = new JsonTextBox();
        super.fillParentClassJsonNode(box);
        box.setNoInputText(noInputText);
        box.setCanFrameBackgroundDisplay(canFrameBackgroundDisplay);
        box.setFont(font.toJsonFont());
        box.setFontSize(fontSize);
        box.setItalicSlant(italicSlant);
        box.setBoldStrength(boldStrength);
        box.setHorizontalAlignment(horizontalAlignment);
        box.setVerticalAlignment(verticalAlignment);
        box.setNoInputTextColor(noInputTextColor);
        box.setInputTextColor(inputTextColor);
        return box;
    }

    @Override
    public TextBox clone() {
        TextBox box = new TextBox(position, size);
        super.fillFieldForClone(box);
        box.setNoInputText(noInputText);
        box.setCanFrameBackgroundDisplay(canFrameBackgroundDisplay);
        box.setFont(font);
        box.setFontSize(fontSize);
        box.setInputTextColor(inputTextColor);
        box.setItalicSlant(italicSlant);
        box.setBoldStrength(boldStrength);
        box.setHorizontalAlignment(horizontalAlignment);
        box.setVerticalAlignment(verticalAlignment);
        box.setNoInputTextColor(noInputTextColor);
        box.setInputTextColor(inputTextColor);
        return box;
    }

    @Override
    public void render(GuiRender render, double delta) {
        if (canFrameBackgroundDisplay) {
            super.renderFrameDefaultBackground(render, delta);
        }
        boolean hasInput = !currentInputText.isEmpty();
        render.drawString(hasInput ? currentInputText : noInputText, font, textRenderPosition, fontSize, hasInput ? inputTextColor : noInputTextColor, italicSlant, boldStrength);

        if (isInFocus && cursorVisible) {
            int cursorX = textRenderPosition.getXPixelInParent() + font.getCursorX(currentInputText, cursorPosition, fontSize, italicSlant, boldStrength);
            int cursorHeight = fontSize + 2;
            int cursorY = textRenderPosition.getYPixelInParent() - 1;
            ScaleOffset cursorMin = ScaleOffset.fromOffset(cursorX, cursorY);
            ScaleOffset cursorMax = ScaleOffset.fromOffset(cursorX + 1, cursorY + cursorHeight);
            render.drawSquare(cursorMin, cursorMax, inputTextColor);
        }

        if (isMouseInRange()){
            render.setCursorShapeInThisFrame(CursorShape.IBEAM);
        }
    }

    private void operationTextRenderPosition() {
        textRenderPosition = operationTextPosition(font, currentInputText.isEmpty() ? noInputText : currentInputText, fontSize, italicSlant, boldStrength, realPosition, realPositionMax, horizontalAlignment, verticalAlignment);
    }

    @Override
    public void operationPosition(AbstractFrame<?> parentFrame, ScaleOffset parentRealPosition) {
        super.operationPosition(parentFrame, parentRealPosition);
        operationTextRenderPosition();
    }

    @Override
    public void onMouseButtonEvent(MouseButtonEvent event) {
        boolean wasFocused = isInFocus;
        if (isMouseInRange()) {
            event.cancel();
            isInFocus = true;
            if (!wasFocused) {
                cursorVisible = true;
                cursorBlinkTask = MainGui.getInstance().runInfiniteLoopTask(() -> cursorVisible = !cursorVisible, 0, 500);
            }
        } else {
            isInFocus = false;
            if (wasFocused && cursorBlinkTask != null) {
                cursorBlinkTask.cancel();
                cursorBlinkTask = null;
            }
            cursorVisible = false;
        }
    }

    @Override
    public void onKeyEvent(KeyEvent event) {
        if (!isInFocus || event.getAction() == InputAction.RELEASE) {
            return;
        }
        cursorVisible = true;
        Key key = event.getKey();
        InputModifiers modifiers = event.getModifiers();
        if (key == Key.V && modifiers.isControlPressed()){
            String clipboard = MainGui.getInstance().getWindow().getClipboard();
            if (clipboard != null && !clipboard.isEmpty()) {
                currentInputText = new StringBuilder(currentInputText).insert(cursorPosition, clipboard).toString();
                cursorPosition += clipboard.length();
                operationTextRenderPosition();
                event.cancel();
            }
        } else if (!key.isNameNull()) {
            char inputChar = key.getName();
            if (event.getModifiers().isCapsLockEnabled()){
                inputChar = Character.toUpperCase(inputChar);
            }
            if (event.getModifiers().isShiftPressed()){
                inputChar = reverseCase(inputChar);
            }
            currentInputText = currentInputText.substring(0, cursorPosition) + inputChar + currentInputText.substring(cursorPosition);
            cursorPosition++;
            operationTextRenderPosition();
            event.cancel();
        } else if (key.equals(Key.BACKSPACE)) {
            if (cursorPosition > 0) {
                currentInputText = currentInputText.substring(0, cursorPosition - 1) + currentInputText.substring(cursorPosition);
                cursorPosition--;
                operationTextRenderPosition();
            }
            event.cancel();
        } else if (key.equals(Key.DELETE)) {
            if (cursorPosition < currentInputText.length()) {
                currentInputText = currentInputText.substring(0, cursorPosition) + currentInputText.substring(cursorPosition + 1);
                operationTextRenderPosition();
            }
            event.cancel();
        } else if (key.equals(Key.LEFT)) {
            if (cursorPosition > 0) {
                cursorPosition--;
                operationTextRenderPosition();
            }
            event.cancel();
        } else if (key.equals(Key.RIGHT)) {
            if (cursorPosition < currentInputText.length()) {
                cursorPosition++;
                operationTextRenderPosition();
            }
            event.cancel();
        } else if (key.equals(Key.HOME)) {
            cursorPosition = 0;
            operationTextRenderPosition();
            event.cancel();
        } else if (key.equals(Key.END)) {
            cursorPosition = currentInputText.length();
            operationTextRenderPosition();
            event.cancel();
        }
    }

    private static char reverseCase(char c) {
        if (Character.isUpperCase(c)) {
            return Character.toLowerCase(c);
        } else if (Character.isLowerCase(c)) {
            return Character.toUpperCase(c);
        } else {
            return c;
        }
    }

    public TextBox setNoInputText(String noInputText) {
        this.noInputText = noInputText;
        sendSignal(SignalType.SET_NO_INPUT_TEXT, noInputText);
        return this;
    }

    public TextBox setCanFrameBackgroundDisplay(boolean canFrameBackgroundDisplay) {
        this.canFrameBackgroundDisplay = canFrameBackgroundDisplay;
        sendSignal(SignalType.SET_CAN_FRAME_BACKGROUND_DISPLAY, canFrameBackgroundDisplay);
        return this;
    }

    public TextBox setFont(Font font) {
        this.font = font;
        sendSignal(SignalType.SET_FONT, font);
        return this;
    }

    public TextBox setFontSize(int fontSize) {
        this.fontSize = fontSize;
        sendSignal(SignalType.SET_FONT_SIZE, fontSize);
        return this;
    }

    public TextBox setItalicSlant(double italicSlant) {
        this.italicSlant = italicSlant;
        sendSignal(SignalType.SET_ITALIC_SLANT, italicSlant);
        return this;
    }

    public TextBox setBoldStrength(int boldStrength) {
        this.boldStrength = boldStrength;
        sendSignal(SignalType.SET_BOLD_STRENGTH, boldStrength);
        return this;
    }

    public TextBox setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        sendSignal(SignalType.SET_HORIZONTAL_ALIGNMENT);
        return this;
    }

    public TextBox setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
        sendSignal(SignalType.SET_VERTICAL_ALIGNMENT);
        return this;
    }

    public TextBox setNoInputTextColor(RGBA noInputTextColor) {
        this.noInputTextColor = noInputTextColor;
        sendSignal(SignalType.SET_NO_INPUT_TEXT_COLOR, noInputTextColor);
        return this;
    }

    public TextBox setInputTextColor(RGBA inputTextColor) {
        this.inputTextColor = inputTextColor;
        sendSignal(SignalType.SET_INPUT_TEXT_COLOR, inputTextColor);
        return this;
    }

    public String getNoInputText() {
        return noInputText;
    }

    public String getInputText() {
        return currentInputText;
    }

    public Font getFont() {
        return font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    public RGBA getNoInputTextColor() {
        return noInputTextColor;
    }

    public RGBA getInputTextColor() {
        return inputTextColor;
    }

    public boolean canFrameBackgroundDisplay() {
        return canFrameBackgroundDisplay;
    }

    public TextBox(ScaleOffset position, ScaleOffset size) {
        super(position, size);
    }
}