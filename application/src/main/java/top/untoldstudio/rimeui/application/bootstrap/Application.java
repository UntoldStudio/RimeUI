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
package top.untoldstudio.rimeui.application.bootstrap;

import top.untoldstudio.rimeui.core.RimeUI;
import top.untoldstudio.rimeui.core.ui.Window;
import top.untoldstudio.rimeui.core.ui.node.Frame;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL32C.*;

public final class Application {
    private static Application instance;
    private final Window window;
    private boolean isRunning;
    private Frame userCustomNodeParent;

    public void run(){
        isRunning = true;
        long windowHandle = window.getWindowHandle();
        RimeUI.initOpenGL(windowHandle);

        window.setWindowIcon("/texture/icon.png");

        Bootstrap.buildDefaultGuiNodes();

        while (isRunning && !window.isWindowShouldClose()){
            glfwPollEvents();
            glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);
            RimeUI.getMainGui().render();
            glfwSwapBuffers(windowHandle);
        }
        stop();
    }

    public void stop(){
        isRunning = false;
        RimeUI.cleanup();
    }

    public Application() {
        instance = this;
        window = Window.create("RimeUI Layout Builder", 800, 600);
    }

    public static Application getInstance() {
        return instance;
    }

    public Frame getUserCustomNodeParent() {
        return userCustomNodeParent;
    }

    public void setUserCustomNodeParent(Frame userCustomNodeParent) {
        this.userCustomNodeParent = userCustomNodeParent;
    }
}
