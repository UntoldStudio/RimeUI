package top.untoldstudio.rimeui.core.render;

import java.nio.ByteBuffer;

public interface RenderBackendProvider {
    int getWindowWidth();
    int getWindowHeight();
    int loadImage(int width, int height, ByteBuffer stbData);
}
