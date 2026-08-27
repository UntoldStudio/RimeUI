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

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import top.untoldstudio.rimeui.core.RimeUI;
import top.untoldstudio.rimeui.core.data.RGBA;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * 如果你已经有一个窗口了你不想再创造一个新的窗口/不能自己控制窗口的生命周期请不要调用create方法!
 * 如果你你不能控制窗口的生命周期请不要调用create,render,close,bootstrap!
 * 尤其是bootstrap,它会开启渲染循环!
 * 就算你能控制窗口生命周期我们不太推荐你调用bootstrap因为它会开启渲染循环...它可以当作渲染循环的一个示例
 */
public final class Window {
    private final long windowHandle;
    private boolean isClose = false;
    private int width;
    private int height;
    private final List<Runnable> beforeRenderCallbacks = new ArrayList<>();
    private final List<Runnable> afterRenderCallbacks = new ArrayList<>();

    public Window(long windowHandle){
        this.windowHandle = windowHandle;
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

    public void bootstrap(){
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        RimeUI.initOpenGL(windowHandle);
        while (!isWindowShouldClose()){
            for (Runnable beforeRenderCallback : beforeRenderCallbacks){
                beforeRenderCallback.run();
            }
            render();
            glfwSwapBuffers(windowHandle);
            for (Runnable afterRenderCallback : afterRenderCallbacks){
                afterRenderCallback.run();
            }
        }
        close();
    }

    public void registerBeforeRenderCallback(Runnable runnable){
        beforeRenderCallbacks.add(runnable);
    }
    public void registerAfterRenderCallback(Runnable runnable){
        afterRenderCallbacks.add(runnable);
    }
    public void unregisterBeforeRenderCallback(Runnable runnable){
        beforeRenderCallbacks.remove(runnable);
    }
    public void unregisterAfterRenderCallback(Runnable runnable){
        afterRenderCallbacks.remove(runnable);
    }

    public void setBackgroundColor(RGBA customBackground){
        glClearColor(customBackground.getRedFloat(), customBackground.getGreenFloat(), customBackground.getBlueFloat(), customBackground.getAlphaFloat());
    }

    public void render(){
        glfwPollEvents();
        glClear(GL_COLOR_BUFFER_BIT);
        RimeUI.render();
    }

    public void close(){
        this.isClose = true;
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
    }

    public void updateWindowWidthAndHeight(){
        int[] width = new int[1];
        int[] height = new int[1];
        GLFW.glfwGetFramebufferSize(windowHandle, width, height);
        this.width = width[0];
        this.height = height[0];
    }
    public int getWindowWidth(){
        return width;
    }
    public int getWindowHeight(){
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
