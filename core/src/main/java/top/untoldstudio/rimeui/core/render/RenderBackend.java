package top.untoldstudio.rimeui.core.render;

public class RenderBackend {
    private static RenderBackendProvider provider;

    public static RenderBackendProvider getProvider(){
        return provider;
    }
    public static void setProvider(RenderBackendProvider newProvider){
        provider = newProvider;
    }
}
