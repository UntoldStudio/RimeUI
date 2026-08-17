package top.untoldstudio.rimeui.core.render.provider;

import static org.lwjgl.opengl.GL33.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.render.RenderBackendProvider;
import top.untoldstudio.rimeui.core.texture.TextureManager;
import top.untoldstudio.rimeui.core.ui.MainUi;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public final class OpenGLRenderBackend implements RenderBackendProvider {
    private final long windowHandle;
    private final IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
    private final IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);

    /**
     * 警告:非特殊情况请从{@link TextureManager}加载纹理,该方法将被ImageManager的loadImage调用
     * 它不会释放stbData内存,如果你忘了就会内存泄漏！
     */
    @Override
    public int loadImage(int width, int height, ByteBuffer stbData){
        GuiRender render = MainUi.getInstance().getRender();
        render.saveContext();

        int textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, stbData);
        glBindTexture(GL_TEXTURE_2D, 0);

        render.restoreContext();

        return textureId;
    }
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
