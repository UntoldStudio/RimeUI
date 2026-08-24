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
