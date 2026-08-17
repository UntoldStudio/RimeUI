package top.untoldstudio.rimeui.core;

import static org.lwjgl.util.freetype.FreeType.*;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;
import top.untoldstudio.rimeui.core.render.RenderBackend;
import top.untoldstudio.rimeui.core.render.provider.OpenGLRenderBackend;
import top.untoldstudio.rimeui.core.ui.MainUi;
import top.untoldstudio.rimeui.core.render.provider.OpenGLGuiRender;

public final class RimeUI {
    public static void initOpenGL(long windowHandle){
        init();
        RenderBackend.setProvider(new OpenGLRenderBackend(windowHandle));
        new MainUi(new OpenGLGuiRender(windowHandle));
    }
    private static void init(){
        PointerBuffer buffer = MemoryUtil.memAllocPointer(1);
        FT_Init_FreeType(buffer);
        //TODO
    }
    public static void render(){
        MainUi.getInstance().render();
    }
    public static MainUi getMainGui(){
        return MainUi.getInstance();
    }
}
