package top.untoldstudio.rimeui.core.render.provider;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import top.untoldstudio.rimeui.core.render.RenderBackendProvider;

import java.nio.IntBuffer;

public final class OpenGLRenderBackend implements RenderBackendProvider {
    private final long windowHandle;
    private final IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
    private final IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);

    @Override
    public int getWindowWidth() {
        widthBuffer.clear();
        heightBuffer.clear();
        GLFW.glfwGetFramebufferSize(windowHandle, widthBuffer, heightBuffer);
        return widthBuffer.get(0);
    }
    @Override
    public int getWindowHeight() {
        widthBuffer.clear();
        heightBuffer.clear();
        GLFW.glfwGetFramebufferSize(windowHandle, widthBuffer, heightBuffer);
        return heightBuffer.get(0);
    }

    public OpenGLRenderBackend(long windowHandle){
        this.windowHandle = windowHandle;
    }
}
