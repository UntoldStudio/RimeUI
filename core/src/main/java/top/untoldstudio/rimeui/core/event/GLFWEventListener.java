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
    private GLFWKeyCallback keyCallback = GLFWKeyCallback.create((currentWindow, key, scancode, action, modifiers) -> {
        if (!enabled) return;
        InputEventListener.onKeyEvent(key, action, modifiers);
    });
    private GLFWMouseButtonCallback mouseButtonCallback = GLFWMouseButtonCallback.create((currentWindow, button, action, modifiers) -> {
        if (!enabled) return;
        InputEventListener.onMouseButtonEvent(button, action, modifiers);
    });
    private GLFWCursorPosCallback cursorPositionCallback = GLFWCursorPosCallback.create((currentWindow, x, y) -> {
        if (!enabled) return;
        InputEventListener.onMouseMoveEvent(x, y);
    });
    private GLFWScrollCallback scrollCallback = GLFWScrollCallback.create((currentWindow, x, y) -> {
        if (!enabled) return;
        InputEventListener.onMouseScrollEvent(x, y);
    });

    public GLFWEventListener(long window) {
        instance = this;
        this.window = window;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        GLFWEventListener.enabled = enabled;
    }

    /**
     * WARN:它会把你所有已注册的回调顶掉,且不会把事件对象给你让你检查是否取消以取消宿主的后续处理,如果你不想这么做,请直接调用{@link InputEventListener}的方法以获取事件返回值,如果你使用了我们的NeoForge模组绑定你就不需要手动接入输入,我们的模组做了
     */
    public void registerCallback(){
        glfwSetKeyCallback(window, keyCallback);
        glfwSetMouseButtonCallback(window, mouseButtonCallback);
        glfwSetCursorPosCallback(window, cursorPositionCallback);
        glfwSetScrollCallback(window, scrollCallback);
    }

    public void clean(){
        keyCallback.free();
        mouseButtonCallback.free();
        cursorPositionCallback.free();
        scrollCallback.free();
    }

    public static GLFWEventListener getInstance() {
        return instance;
    }
}
