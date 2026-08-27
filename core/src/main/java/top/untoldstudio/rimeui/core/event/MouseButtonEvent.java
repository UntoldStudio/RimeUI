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
package top.untoldstudio.rimeui.core.event;

import top.untoldstudio.rimeui.core.data.InputAction;
import top.untoldstudio.rimeui.core.data.InputModifiers;
import top.untoldstudio.rimeui.core.data.MouseButton;

public final class MouseButtonEvent extends CancelableEvent {
    private final MouseButton button;
    private final InputAction action;
    private final InputModifiers modifiers;

    public MouseButtonEvent(MouseButton button, InputAction action, InputModifiers modifiers) {
        this.button = button;
        this.action = action;
        this.modifiers = modifiers;
    }
    public MouseButtonEvent(int button, int action, int modifiers){
        this(MouseButton.fromGLFWValue(button), InputAction.fromGLFWValue(action), new InputModifiers(modifiers));
    }

    public MouseButton getButton() {
        return button;
    }
    public InputAction getAction() {
        return action;
    }
    public InputModifiers getModifiers() {
        return modifiers;
    }
}
