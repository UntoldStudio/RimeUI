package top.untoldstudio.rimeui.core.render;

public class RenderBackend {
    private static final RenderBackend instance = new RenderBackend();
    private RenderBackendProvider provider;

    public static RenderBackend getInstance(){
        return instance;
    }
    public RenderBackendProvider getProvider(){
        return provider;
    }
    public void setProvider(RenderBackendProvider provider){
        this.provider = provider;
    }
}
