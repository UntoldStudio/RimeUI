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
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
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
