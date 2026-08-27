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
package top.untoldstudio.rimeui.core.ui;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import top.untoldstudio.rimeui.core.event.WindowSizeChangeEvent;

/**
 * 如果你已经有一个窗口了你不想再创造一个新的窗口/不能自己控制窗口的生命周期请不要调用create方法!
 */
public final class Window {
    private final long windowHandle;
    private boolean isClose = false;
    private int width;
    private int height;
    private final GLFWWindowSizeCallback windowSizeCallback;

    public Window(long windowHandle){
        this.windowHandle = windowHandle;
        windowSizeCallback = glfwSetWindowSizeCallback(windowHandle, (window, newWidth, newHeight) -> {
            int oldWidth = width;
            int oldHeight = height;
            width = newWidth;
            height = newHeight;
            MainUi.getInstance().onWindowSizeChangeEvent(new WindowSizeChangeEvent(oldWidth, oldHeight, newWidth, newHeight));
        });
    }

    public static Window create(String title, int width, int height){
        if (!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        long window = glfwCreateWindow(width, height, title, 0, 0);
        if (window == 0){
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        glfwMakeContextCurrent(window);

        GL.createCapabilities();

        return new Window(window);
    }

    public void close(){
        this.isClose = true;
        windowSizeCallback.free();
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public boolean isClose(){
        return isClose;
    }
    public boolean isWindowShouldClose(){
        return isClose || glfwWindowShouldClose(windowHandle);
    }

    public long getWindowHandle(){
        return windowHandle;
    }
}
