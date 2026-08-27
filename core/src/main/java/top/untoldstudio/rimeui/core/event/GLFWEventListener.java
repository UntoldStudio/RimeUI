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

public class GLFWEventListener {
    private static GLFWEventListener instance;
    private final long window;
    private static boolean enabled = false;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback cursorPositionCallback;
    private GLFWScrollCallback scrollCallback;

    public GLFWEventListener(long window) {
        instance = this;
        this.window = window;
    }

    /**
     * WARN:它会把你所有已注册的回调顶掉,如果你不想这么做,请直接调用{@link InputEventListener}的方法
     */
    public void registerCallback(){
        keyCallback = glfwSetKeyCallback(window, (currentWindow, key, scancode, action, modifiers) -> {
            if (!enabled) return;
            InputEventListener.onKeyEvent(key, action, modifiers);
        });
        mouseButtonCallback = glfwSetMouseButtonCallback(window, (currentWindow, button, action, modifiers) -> {
            if (!enabled) return;
            InputEventListener.onMouseButtonEvent(button, action, modifiers);
        });
        cursorPositionCallback = glfwSetCursorPosCallback(window, (currentWindow, x, y) -> {
            if (!enabled) return;
            InputEventListener.onCursorMoveEvent(x, y);
        });
        scrollCallback = glfwSetScrollCallback(window, (currentWindow, x, y) -> {
            if (!enabled) return;
            InputEventListener.onMouseScrollEvent(x, y);
        });
    }

    public static GLFWEventListener getInstance() {
        return instance;
    }
}
