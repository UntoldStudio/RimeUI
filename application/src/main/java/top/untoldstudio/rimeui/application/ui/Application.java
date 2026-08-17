package top.untoldstudio.rimeui.application.ui;

import top.untoldstudio.rimeui.application.render.Window;
import top.untoldstudio.rimeui.core.RimeUI;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.ui.node.ImageLabel;

public final class Application {
    private static Application instance;
    private final Window window;

    public Application(){
        window = new Window(800, 600, "RimeUI Layout Builder");
        instance = this;
    }

    public void start(){
        RimeUI.initOpenGL(window.getWindowHandle());
        ImageLabel label = new ImageLabel("/test.png", ScaleOffset.fromScale(0.5, 0.5), ScaleOffset.fromScale(0.5, 0.5)).setAnchor(0.5, 0.5);
        RimeUI.getMainGui().addChild(label);
        while (!window.isWindowShouldClose()){
            window.isWindowShouldClose();
            window.render();
        }
        stop();
    }

    public void stop(){
        window.flush();
    }

    public static Application getInstance(){
        return instance;
    }
}
