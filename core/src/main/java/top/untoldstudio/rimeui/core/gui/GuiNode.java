package top.untoldstudio.rimeui.core.gui;

import org.jetbrains.annotations.NotNull;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.signal.SignalBus;
import top.untoldstudio.rimeui.core.signal.SignalType;

import java.util.*;

public abstract class GuiNode {
    protected String name = getClass().getSimpleName();
    protected final SignalBus signalBus = new SignalBus();
    protected GuiNode parent;
    protected final List<GuiNode> children = new ArrayList<>();
    protected int renderLevel = 0;
    private final Map<SignalType, Object> lastSignalObjectValues = new EnumMap<>(SignalType.class);

    protected final void renderWithChildren(GuiRender render){
        render(render);
        for (GuiNode child : children) {
            child.render(render);
        }
    }
    protected abstract void render(GuiRender render);

    protected void sendSignal(SignalType type, Object value) {
        Object lastValue = lastSignalObjectValues.get(type);
        if (lastValue == null || !lastValue.equals(value)) {
            signalBus.send(this, type);
        }
        lastSignalObjectValues.put(type, value);
    }
    protected void sendSignal(SignalType type) {
        signalBus.send(this, type);
    }
    public boolean hasChildren(GuiNode node){
        return children.contains(node);
    }
    public boolean hasChildren(String name){
        for (GuiNode child : children){
            if (child.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    public void addChild(@NotNull GuiNode node){
        if (!children.contains(node)){
            if (node.parent != null){
                node.parent.children.remove(node);
            }
            node.parent = this;
            children.add(node);
            sortChildren();
            sendSignal(SignalType.ADD_CHILD);
            node.sendSignal(SignalType.SET_PARENT);
        }
    }
    public void addChildren(@NotNull GuiNode... children){
        for (GuiNode node : children){
            addChild(node);
        }
    }
    public void removeChild(@NotNull GuiNode node){
        if (node.parent == this){
            node.parent = null;
            children.remove(node);
            sendSignal(SignalType.REMOVE_CHILD);
            node.sendSignal(SignalType.SET_PARENT);
        }
    }
    public void removeChildren(@NotNull GuiNode... children){
        for (GuiNode node : children){
            removeChild(node);
        }
    }
    public void removeChild(String name) {
        GuiNode target = null;
        for (GuiNode child : children) {
            if (child.getName().equals(name)) {
                target = child;
                break;
            }
        }
        if (target != null) {
            removeChild(target);
        }
    }
    public void removeChildren(String... names){
        for (String name : names){
            removeChild(name);
        }
    }
    public GuiNode getChild(String name){
        for (GuiNode child : children){
            if (child.getName().equals(name)){
                return child;
            }
        }
        return null;
    }
    public void setParent(@NotNull GuiNode node){
        node.addChild(this);
    }
    public void setRenderLevel(int renderLevel){
        this.renderLevel = renderLevel;
        if (parent != null){
            parent.sortChildren();
        }
        sendSignal(SignalType.SET_RENDER_LEVEL, renderLevel);
    }

    public GuiNode getParent(){
        return parent;
    }
    public void setName(String name){
        this.name = name;
        sendSignal(SignalType.SET_NAME, name);
    }
    public String getName() {
        return name;
    }
    public SignalBus getSignalBus() {
        return signalBus;
    }
    public int getRenderLevel() {
        return renderLevel;
    }

    protected void sortChildren(){
        children.sort(Comparator.comparingInt(GuiNode::getRenderLevel));
    }
}
