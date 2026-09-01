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
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.event.GLFWEventListener;
import top.untoldstudio.rimeui.core.ui.DoubleConsumer;
import top.untoldstudio.rimeui.core.ui.MainGui;
import top.untoldstudio.rimeui.core.ui.Window;
import top.untoldstudio.rimeui.core.ui.node.TextBox;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL32C.*;

/**
 * 注意:application模块是我们的GUI编辑器而非给你的示例...
 */
public final class Application {
    private static Application instance;
    private final Window window;
    private boolean isRunning;

    public void run(){
        isRunning = true;
        long windowHandle = window.getWindowHandle();
        RimeUI.initOpenGL(windowHandle);

        GLFWEventListener listener = new GLFWEventListener(windowHandle);
        listener.registerCallback();
        listener.setEnabled(true);

        window.setWindowIcon("/texture/icon.png");

        TextBox box = new TextBox(ScaleOffset.fromScale(0.5, 0.5), ScaleOffset.fromScale(0.5, 0.5)).setAnchor(0.5, 0.5);
        RimeUI.getMainGui().addChild(box);

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

    public void runTask(Runnable task){
        MainGui.getInstance().runTask(task);
    }
    public void runTaskLater(Runnable task, long delay){
        MainGui.getInstance().runTaskLater(task, delay);
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
}
