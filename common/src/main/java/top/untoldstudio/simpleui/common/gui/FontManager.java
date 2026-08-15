package top.untoldstudio.simpleui.common.gui;

public abstract class FontManager {
    private static FontManager instance;
    public abstract Font getDefaultFont();

    public static FontManager getInstance(){
        return instance;
    }
    public static void setInstance(FontManager newInstance){
        instance = newInstance;
    }
}
