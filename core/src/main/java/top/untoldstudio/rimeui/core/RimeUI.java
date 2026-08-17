package top.untoldstudio.rimeui.core;

import top.untoldstudio.rimeui.core.render.RenderBackend;
import top.untoldstudio.rimeui.core.render.provider.OpenGLRenderBackend;
import top.untoldstudio.rimeui.core.ui.MainUi;
import top.untoldstudio.rimeui.core.render.provider.OpenGLGuiRender;

public final class RimeUI {
    public static void initOpenGL(long windowHandle){
        RenderBackend.setProvider(new OpenGLRenderBackend(windowHandle));
        new MainUi(new OpenGLGuiRender(windowHandle));
    }
    public static void render(){
        MainUi.getInstance().render();
    }
    public static MainUi getMainGui(){
        return MainUi.getInstance();
    }
}
