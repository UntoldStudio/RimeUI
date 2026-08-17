package top.untoldstudio.rimeui.core.ui;

import org.jetbrains.annotations.NotNull;
import top.untoldstudio.rimeui.core.event.WindowSizeChangeEvent;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.render.RenderBackend;
import top.untoldstudio.rimeui.core.signal.SignalType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class MainUi {
    private static MainUi instance;
    private final List<GuiNode<?>> children = new ArrayList<>();
    private final GuiRender render;
    private int windowWidth;
    private int windowHeight;

    public void render(){
        int oldWidth = windowWidth;
        int oldHeight = windowHeight;
        this.windowWidth = RenderBackend.getProvider().getWindowWidth();
        this.windowHeight = RenderBackend.getProvider().getWindowHeight();
        if (oldWidth != windowWidth || oldHeight != windowHeight) {
            onWindowSizeChangeEvent(new WindowSizeChangeEvent(oldWidth, oldHeight, windowWidth, windowHeight));
        }
        render.saveContext();
        render.begin();
        for (GuiNode<?> node : children){
            node.renderWithChildren(render);
        }
        render.submitBuffer();
        render.restoreContext();
        render.end();
    }

    private void onWindowSizeChangeEvent(WindowSizeChangeEvent event){
        for (GuiNode<?> child : children){
            child.onWindowSizeChangeEventWithChildren(event);
        }
    }

    public boolean hasChild(GuiNode<?> node){
        return children.contains(node);
    }
    public boolean hasChild(String childName){
        for (GuiNode<?> node : children){
            if (node.getName().equals(childName)){
                return true;
            }
        }
        return false;
    }
    public MainUi addChild(@NotNull GuiNode<?> node){
        node.parent = null;
        node.parentIsGuiMain = true;
        children.add(node);
        sortChildren();
        node.sendSignal(SignalType.SET_PARENT);
        return this;
    }
    public MainUi addChildren(@NotNull GuiNode<?>... children){
        for (GuiNode<?> node : children){
            addChild(node);
        }
        return this;
    }
    public MainUi removeChild(@NotNull GuiNode<?> node){
        node.parentIsGuiMain = false;
        node.sendSignal(SignalType.SET_PARENT);
        return this;
    }
    public MainUi removeChildren(@NotNull GuiNode<?>... nodes){
        for (GuiNode<?> node : nodes){
            removeChild(node);
        }
        return this;
    }
    public MainUi removeChild(String name) {
        GuiNode<?> target = null;
        for (GuiNode<?> child : children) {
            if (child.getName().equals(name)) {
                target = child;
                break;
            }
        }
        if (target != null) {
            removeChild(target);
        }
        return this;
    }
    public MainUi removeChildren(String... names){
        for (String name : names){
            removeChild(name);
        }
        return this;
    }

    void sortChildren(){
        children.sort(Comparator.comparingInt(GuiNode::getRenderLevel));
    }


    public MainUi(GuiRender render){
        instance = this;
        this.render = render;
    }

    public static MainUi getInstance() {
        return instance;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public GuiRender getRender(){
        return render;
    }
}
