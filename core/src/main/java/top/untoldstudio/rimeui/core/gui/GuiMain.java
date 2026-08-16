package top.untoldstudio.rimeui.core.gui;

public final class GuiMain {
    private static GuiMain instance;

    public GuiMain(){
        instance = this;
    }

    public GuiMain getInstance() {
        return instance;
    }
}
