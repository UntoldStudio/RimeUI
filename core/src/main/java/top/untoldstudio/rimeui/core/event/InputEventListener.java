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

import top.untoldstudio.rimeui.core.ui.MainUi;

public final class InputEventListener {
    public static KeyEvent onKeyEvent(int key, int action, int modifiers){
        KeyEvent event = new KeyEvent(key, action, modifiers);
        onKeyEvent(event);
        return event;
    }
    public static void onKeyEvent(KeyEvent event){
        MainUi.getInstance().onKeyEvent(event);
    }
    public static MouseButtonEvent onMouseButtonEvent(int button, int action, int modifiers){
        MouseButtonEvent event = new MouseButtonEvent(button, action, modifiers);
        onMouseButtonEvent(event);
        return event;
    }
    public static void onMouseButtonEvent(MouseButtonEvent event){
        MainUi.getInstance().onMouseButtonEvent(event);
    }
    public static CursorMoveEvent onCursorMoveEvent(double x, double y){
        CursorMoveEvent event = new CursorMoveEvent(x, y);
        onCursorMoveEvent(event);
        return event;
    }
    public static void onCursorMoveEvent(CursorMoveEvent event){
        MainUi.getInstance().onCursorMoveEvent(event);
    }
    public static MouseScrollEvent onMouseScrollEvent(double x, double y){
        MouseScrollEvent event = new MouseScrollEvent(x, y);
        onMouseScrollEvent(event);
        return event;
    }
    public static void onMouseScrollEvent(MouseScrollEvent event){
        MainUi.getInstance().onMouseScrollEvent(event);
    }
}
