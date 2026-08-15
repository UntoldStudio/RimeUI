package top.untoldstudio.simpleui.common.gui;

import top.untoldstudio.simpleui.common.event.*;
import top.untoldstudio.simpleui.common.gui.node.Frame;
import top.untoldstudio.simpleui.common.signal.SignalBus;
import top.untoldstudio.simpleui.common.signal.SignalType;

import java.util.*;

import static top.untoldstudio.simpleui.common.signal.SignalType.*;

/**
 * 只负责定义一个节点，位置和大小在{@link Frame}里面
 * 注意:
 * 如果你不想直接接触底层渲染,uv等,我通常不推荐你直接继承GuiNode,而是使用现有节点
 * 如果你不需要接触这些,你只是想造一个可复用的界面,那么我推荐你造一个不继承任何节点的类里面有一个父Frame以及许多子预设节点而非直接继承某个节点
 */
public abstract class GuiNode implements Comparable<GuiNode> {
    protected String name = getClass().getName();
    protected final SignalBus signalBus = new SignalBus();
    protected int renderLevel = 0;
    protected Gui gui;
    protected GuiNode parent;
    protected final List<GuiNode> children = new ArrayList<>();
    protected List<GuiNode> reversedChildren = new ArrayList<>();
    protected boolean visible = true;
    private boolean isInit = false;

    protected void sendSingle(SignalType type){
        signalBus.send(this, type);
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        if (!this.name.equals(name)){
            this.name = name;
            sendSingle(SET_NAME);
        }
    }

    public final void onKeyEventWithChildren(KeyEvent event){
        if (!getVisible() || isPreventAllInputEvent()) return;
        for (GuiNode child : reversedChildren){
            child.onKeyEventWithChildren(event);
            if (event.isCanceled()){
                return;
            }
        }
        onKeyEvent(event);
    }
    protected void onKeyEvent(KeyEvent event){}
    public final void onMouseMoveWithChildren(MouseMoveEvent event){
        if (!getVisible() || isPreventAllInputEvent()) return;
        for (GuiNode child : reversedChildren){
            child.onMouseMoveWithChildren(event);
            if (event.isCanceled()){
                return;
            }
        }
        onMouseMove(event);
    }
    protected void onMouseMove(MouseMoveEvent event){}
    public final void onLeftMouseButtonPressedWithChildren(MouseClickEvent event){
        if (!getVisible() || isPreventAllInputEvent()) return;
        for (GuiNode child : reversedChildren){
            child.onLeftMouseButtonPressedWithChildren(event);
            if (event.isCanceled()){
                return;
            }
        }
        onLeftMouseButtonPressed(event);
    }
    protected void onLeftMouseButtonPressed(MouseClickEvent event){}
    public final void onLeftMouseButtonReleasedWithChildren(MouseClickEvent event){
        if (!getVisible() || isPreventAllInputEvent()) return;
        for (GuiNode child : reversedChildren){
            child.onLeftMouseButtonReleasedWithChildren(event);
            if (event.isCanceled()){
                return;
            }
        }
        onLeftMouseButtonReleased(event);
    }
    protected void onLeftMouseButtonReleased(MouseClickEvent event){}
    public final void onRightMouseButtonPressedWithChildren(MouseClickEvent event){
        if (!getVisible() || isPreventAllInputEvent()) return;
        for (GuiNode child : reversedChildren){
            child.onRightMouseButtonPressedWithChildren(event);
            if (event.isCanceled()){
                return;
            }
        }
        onRightMouseButtonPressed(event);
    }
    protected void onRightMouseButtonPressed(MouseClickEvent event){}
    public final void onRightMouseButtonReleasedWithChildren(MouseClickEvent event){
        if (!getVisible() || isPreventAllInputEvent()) return;
        for (GuiNode child : reversedChildren){
            child.onRightMouseButtonReleasedWithChildren(event);
            if (event.isCanceled()){
                return;
            }
        }
        onRightMouseButtonReleased(event);
    }
    protected void onRightMouseButtonReleased(MouseClickEvent event){}
    public final void onMiddleMouseButtonPressedWithChildren(MouseClickEvent event){
        if (!getVisible() || isPreventAllInputEvent()) return;
        for (GuiNode child : reversedChildren){
            child.onMiddleMouseButtonPressedWithChildren(event);
            if (event.isCanceled()){
                return;
            }
        }
        onMiddleMouseButtonPressed(event);
    }
    protected void onMiddleMouseButtonPressed(MouseClickEvent event){}
    public final void onMiddleMouseButtonReleasedWithChildren(MouseClickEvent event){
        if (!getVisible() || isPreventAllInputEvent()) return;
        for (GuiNode child : reversedChildren){
            child.onMiddleMouseButtonReleasedWithChildren(event);
            if (event.isCanceled()){
                return;
            }
        }
        onMiddleMouseButtonReleased(event);
    }
    protected void onMiddleMouseButtonReleased(MouseClickEvent event){}
    public final void onMouseScrollEventWithChildren(MouseScrollEvent event){
        if (!getVisible() || isPreventAllInputEvent()) return;
        for (GuiNode child : reversedChildren){
            child.onMouseScrollEventWithChildren(event);
            if (event.isCanceled()){
                return;
            }
        }
        onMouseScrollEvent(event);
    }
    protected void onMouseScrollEvent(MouseScrollEvent event){}
    public final void initWithChildren(){
        init();
        for (GuiNode child : children){
            child.initWithChildren();
        }
        isInit = true;
    }
    protected void init(){}
    public final void reloadWithChildren(){
        reload();
        sendSingle(RELOAD);
        for (GuiNode child : children){
            child.reloadWithChildren();
        }
    }
    protected void reload(){}
    public final void tickWithChildren(){
        tick();
        sendSingle(TICK);
        for (GuiNode child : children){
            child.tickWithChildren();
        }
    }
    protected void tick(){}
    public final void preLogicalTickWithChildren(){
        preLogicalTick();
        sendSingle(PRE_LOGICAL_TICK);
        for (GuiNode guiNode : children){
            guiNode.preLogicalTickWithChildren();
        }
    }
    protected void preLogicalTick(){}
    public void renderWithChildren(GuiRender render){
        if (!getVisible()){
            return;
        }
        render(render);
        sendSingle(RENDER);
        for (GuiNode guiNode : children){
            guiNode.renderWithChildren(render);
        }
    }
    public final void onWindowSizeChangeEventWithChildren(WindowSizeChangeEvent event){
        onWindowSizeChangeEvent(event);
        for (GuiNode child : children){
            child.onWindowSizeChangeEventWithChildren(event);
        }
    }
    protected void onWindowSizeChangeEvent(WindowSizeChangeEvent event){}
    protected abstract void render(GuiRender render);
    public void setGui(Gui gui){
        this.gui = gui;
    }
    public void setRenderLevel(int level){
        if (this.renderLevel != level){
            renderLevel = level;
            if (parent != null){
                parent.sortGuiNodeList();
            } else if (gui != null){
                gui.sortGuiNodeList();
            }
            sendSingle(SET_RENDER_LEVEL);
        }
    }
    public void addChild(GuiNode child){
        if (!children.contains(child)){
            children.add(child);
            child.parent = this;
            child.gui = gui;
            sortGuiNodeList();
            if (isInit){
                child.initWithChildren();
            }
            sendSingle(ADD_CHILD);
        }
    }
    public void addChildren(GuiNode... children){
        for (GuiNode child : children){
            addChild(child);
        }
    }
    public void removeChild(GuiNode child){
        if (children.contains(child)){
            children.remove(child);
            child.parent = null;
            child.gui = null;
            sendSingle(REMOVE_CHILD);
        }
    }
    public void removeChild(String childName){
        for (GuiNode child : children){
            if (child.getName().equals(childName)){
                removeChild(child);
            }
        }
    }
    public GuiNode getParent(){
        return parent;
    }
    public void setParent(GuiNode parent){
        if (this.parent != parent){
            if (this.parent != null){
                this.parent.removeChild(this);
            }
            parent.addChild(this);
            sendSingle(REPARENT);
        }
    }
    public void setParent(Gui gui){
        if (this.gui == null || parent != null){
            if (this.parent != null){
                this.parent.removeChild(this);
            }
            this.gui = gui;
            gui.addGuiNode(this);
            this.parent = null;
            sendSingle(REPARENT);
        }
    }
    public int getRenderLevel(){
        return renderLevel;
    }
    public SignalBus getSingleBus() {
        return signalBus;
    }
    protected boolean isPreventAllInputEvent(){
        return false;
    }
    public void sortGuiNodeList(){
        children.sort(Comparator.naturalOrder());
        reversedChildren = getReversedChildren();
        sendSingle(GUI_NODE_CHILDREN_SORT);
    }
    public List<GuiNode> getReversedChildren(){
        List<GuiNode> reversed = new ArrayList<>();
        ListIterator<GuiNode> it = children.listIterator(children.size());
        while (it.hasPrevious()) {
            reversed.add(it.previous());
        }
        return reversed;
    }
    public void onVisibleChange(boolean newVisible){}
    public final void setVisible(boolean visible){
        if (this.visible != visible){
            this.visible = visible;
            onVisibleChange(visible);
            sendSingle(VISIBLE_CHANGE);
        }
    }
    public final boolean getVisible(){
        return visible;
    }
    @Override
    public int compareTo(GuiNode other) {
        return Integer.compare(renderLevel, other.getRenderLevel());
    }
}
