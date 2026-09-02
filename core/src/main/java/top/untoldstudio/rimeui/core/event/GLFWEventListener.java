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

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import top.untoldstudio.rimeui.core.ui.MainGui;

public class GLFWEventListener {
    private static GLFWEventListener instance;
    private final long window;

    private final GLFWKeyCallback keyCallback;
    private final GLFWMouseButtonCallback mouseButtonCallback;
    private final GLFWCursorPosCallback cursorPositionCallback;
    private final GLFWScrollCallback scrollCallback;
    private GLFWKeyCallback oldKeyCallback = null;
    private GLFWMouseButtonCallback oldMouseButtonCallback = null;
    private GLFWCursorPosCallback oldCursorPositionCallback = null;
    private GLFWScrollCallback oldScrollCallback = null;

    public GLFWEventListener(long window) {
        instance = this;
        this.window = window;
        keyCallback = GLFWKeyCallback.create((currentWindow, key, scancode, action, modifiers) -> {
            if (MainGui.getInstance() == null) return;
            KeyEvent event = new KeyEvent(key, action, modifiers);
            MainGui.getInstance().onKeyEvent(event);
            if (!event.isCancelled() && oldKeyCallback != null) {
                oldKeyCallback.invoke(window, key, scancode, action, modifiers);
            }
        });
        mouseButtonCallback = GLFWMouseButtonCallback.create((currentWindow, button, action, modifiers) -> {
            if (MainGui.getInstance() == null) return;
            MouseButtonEvent event = new MouseButtonEvent(button, action, modifiers);
            MainGui.getInstance().onMouseButtonEvent(event);
            if (!event.isCancelled() && oldMouseButtonCallback != null) {
                oldMouseButtonCallback.invoke(window, button, action, modifiers);
            }
        });
        cursorPositionCallback = GLFWCursorPosCallback.create((currentWindow, x, y) -> {
            if (MainGui.getInstance() == null) return;
            MouseMoveEvent event = new MouseMoveEvent(x, y);
            MainGui.getInstance().onMouseMoveEvent(event);
            if (!event.isCancelled() && oldCursorPositionCallback != null) {
                oldCursorPositionCallback.invoke(window, x, y);
            }
        });
        scrollCallback = GLFWScrollCallback.create((currentWindow, x, y) -> {
            if (MainGui.getInstance() == null) return;
            MouseScrollEvent event = new MouseScrollEvent(x, y);
            MainGui.getInstance().onMouseScrollEvent(event);
            if (!event.isCancelled() && oldScrollCallback != null) {
                oldScrollCallback.invoke(window, x, y);
            }
        });
        oldKeyCallback = glfwSetKeyCallback(window, keyCallback);
        oldMouseButtonCallback = glfwSetMouseButtonCallback(window, mouseButtonCallback);
        oldCursorPositionCallback = glfwSetCursorPosCallback(window, cursorPositionCallback);
        oldScrollCallback = glfwSetScrollCallback(window, scrollCallback);
    }

    public void clean(){
        keyCallback.free();
        mouseButtonCallback.free();
        cursorPositionCallback.free();
        scrollCallback.free();
    }

    public long getWindow() {
        return window;
    }

    public static GLFWEventListener getInstance() {
        return instance;
    }
}
