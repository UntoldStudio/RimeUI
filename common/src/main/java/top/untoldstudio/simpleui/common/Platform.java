package top.untoldstudio.simpleui.common;

public final class Platform {
    public static PlatformProvider provider;

    public static PlatformProvider getPlatformProvider(){
        return provider;
    }

    public static void setPlatformProvider(PlatformProvider newProvider){
        provider = newProvider;
    }
}
