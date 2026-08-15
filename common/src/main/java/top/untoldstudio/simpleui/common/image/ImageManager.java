package top.untoldstudio.simpleui.common.image;

public abstract class ImageManager {
    public abstract int getTextureWidth(String path);
    public abstract int getTextureHeight(String path);

    private static ImageManager instance;
    public static ImageManager getInstance(){
        return instance;
    }
    public static void setInstance(ImageManager newInstance){
        instance = newInstance;
    }
}
