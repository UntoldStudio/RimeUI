package top.untoldstudio.rimeui.application.ui;

import top.untoldstudio.rimeui.application.render.Window;
import top.untoldstudio.rimeui.core.RimeUI;

public final class Application {
    private static Application instance;
    private final Window window;

    public Application(){
        window = new Window(800, 600, "RimeUI Layout Builder");
        instance = this;
    }

    public void start(){
        RimeUI.initOpenGL(window.getWindowHandle());
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
