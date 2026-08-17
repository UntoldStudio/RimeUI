package top.untoldstudio.rimeui.core.gui;

import org.jetbrains.annotations.NotNull;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.signal.SignalType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class GuiMain {
    private static GuiMain instance;
    private final List<GuiNode<?>> children = new ArrayList<>();
    private final GuiRender render;

    public void render(){
        render.begin();
        for (GuiNode<?> node : children){
            node.renderWithChildren(render);
        }
        render.end();
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
    public GuiMain addChild(@NotNull GuiNode<?> node){
        node.parent = null;
        node.parentIsGuiMain = true;
        children.add(node);
        sortChildren();
        node.sendSignal(SignalType.SET_PARENT);
        return this;
    }
    public GuiMain addChildren(@NotNull GuiNode<?>... children){
        for (GuiNode<?> node : children){
            addChild(node);
        }
        return this;
    }
    public GuiMain removeChild(@NotNull GuiNode<?> node){
        node.parentIsGuiMain = false;
        node.sendSignal(SignalType.SET_PARENT);
        return this;
    }
    public GuiMain removeChildren(@NotNull GuiNode<?>... nodes){
        for (GuiNode<?> node : nodes){
            removeChild(node);
        }
        return this;
    }
    public GuiMain removeChild(String name) {
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
    public GuiMain removeChildren(String... names){
        for (String name : names){
            removeChild(name);
        }
        return this;
    }

    void sortChildren(){
        children.sort(Comparator.comparingInt(GuiNode::getRenderLevel));
    }


    public GuiMain(GuiRender render){
        instance = this;
        this.render = render;
    }

    public static GuiMain getInstance() {
        return instance;
    }
}
