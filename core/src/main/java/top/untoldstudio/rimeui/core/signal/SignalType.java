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
package top.untoldstudio.rimeui.core.signal;

public enum SignalType {
    SET_NAME,
    SET_PARENT,
    SET_RENDER_LEVEL,
    SET_X_ANCHOR,
    SET_Y_ANCHOR,
    SET_POSITION,
    SET_SIZE,
    SET_BACKGROUND_COLOR,
    SET_TEXTURE_ID,
    SET_FONT,
    SET_FONT_SIZE,
    SET_TEXT,
    SET_CAN_FRAME_BACKGROUND_DISPLAY,
    SET_TEXT_COLOR,
    SET_ITALIC_SLANT,
    SET_BOLD_STRENGTH,
    SET_HORIZONTAL_ALIGNMENT,
    SET_VERTICAL_ALIGNMENT,
    SET_DEFAULT_IMAGE,
    SET_HOVERED_IMAGE,
    SET_PRESSED_IMAGE,
    SET_SCROLL_PROGRESS,
    SET_CAN_SCROLL,
    SET_SCROLL_SCALE,
    SET_SCROLL_BAR_WIDTH,
    SET_SCROLL_BAR_TEXTURE,
    SET_SCROLL_BAR_VISIBLE,
    SET_SCROLL_BAR_COLOR,
    SET_SCROLL_SPEED,
    SET_ACCEPT_INPUT,
    SET_BLOCK_INPUT,
    SET_LAYOUT_ORDER,
    SET_FRAME_SPACING,
    SET_FRAME_START_SPACING,
    SET_VISIBLE,
    SET_NO_INPUT_TEXT,
    SET_NO_INPUT_TEXT_COLOR,
    SET_INPUT_TEXT_COLOR,

    REGISTER_RENDER_CALLBACK,
    UNREGISTER_RENDER_CALLBACK,

    ADD_CHILD,
    ADD_CAN_TRIGGER_BUTTON,

    REMOVE_CHILD,
    REMOVE_CAN_TRIGGER_BUTTON,

    BEFORE_RENDER,
    AFTER_RENDER,

    BUTTON_PRESSED,
    BUTTON_RELEASED,
}
