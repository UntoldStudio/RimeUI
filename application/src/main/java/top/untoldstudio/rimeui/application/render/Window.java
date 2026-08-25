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
package top.untoldstudio.rimeui.application.render;

import org.lwjgl.opengl.GL;
import top.untoldstudio.rimeui.core.RimeUI;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public final class Window {
    private final long windowHandle;

    public Window(int width, int height, String title) {
        if (!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        windowHandle = glfwCreateWindow(width, height, title, 0, 0);
        if (windowHandle == 0){
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        glfwMakeContextCurrent(windowHandle);

        GL.createCapabilities();
    }

    public void render(){
        glfwPollEvents();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        RimeUI.render();
        glfwSwapBuffers(windowHandle);
    }
    public boolean isWindowShouldClose(){
        return glfwWindowShouldClose(windowHandle);
    }
    public void flush(){
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
    }

    public long getWindowHandle() {
        return windowHandle;
    }
}
