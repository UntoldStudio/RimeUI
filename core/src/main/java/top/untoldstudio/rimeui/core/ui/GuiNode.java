/*
 * Copyright 2026 Untold Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.untoldstudio.rimeui.core.ui;

import org.jetbrains.annotations.NotNull;
import top.untoldstudio.rimeui.core.event.WindowSizeChangeEvent;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.signal.SignalBus;
import top.untoldstudio.rimeui.core.signal.SignalType;

import java.util.*;

public abstract class GuiNode<T extends GuiNode<T>> {
    @SuppressWarnings("unchecked")
    protected final T self = (T) this;
    protected String name = getClass().getSimpleName();
    protected final SignalBus signalBus = new SignalBus();
    protected boolean parentIsGuiMain = false;
    protected GuiNode<?> parent;
    protected final List<GuiNode<?>> children = new ArrayList<>();
    protected int renderLevel = 0;
    private final Map<SignalType, Object> lastSignalObjectValues = new EnumMap<>(SignalType.class);

    protected void renderWithChildren(GuiRender render){
        render(render);
        for (GuiNode<?> child : children) {
            child.renderWithChildren(render);
        }
    }
    protected abstract void render(GuiRender render);

    protected final void onWindowSizeChangeEventWithChildren(WindowSizeChangeEvent event){
        onWindowSizeChangeEvent(event);
        for (GuiNode<?> child : children) {
            child.onWindowSizeChangeEventWithChildren(event);
        }
    }
    protected void onWindowSizeChangeEvent(WindowSizeChangeEvent event){}

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
    public boolean hasChildren(GuiNode<?> node){
        return children.contains(node);
    }
    public boolean hasChildren(String name){
        for (GuiNode<?> child : children){
            if (child.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    public T addChild(@NotNull GuiNode<?> node){
        if (!children.contains(node)){
            if (node.parent != null){
                node.parent.children.remove(node);
            } else if (node.parentIsGuiMain){
                MainUi.getInstance().removeChild(node);
            }
            node.parent = this;
            children.add(node);
            sortChildren();
            sendSignal(SignalType.ADD_CHILD);
            node.sendSignal(SignalType.SET_PARENT);
        }
        return self;
    }
    public T addChildren(@NotNull GuiNode<?>... children){
        for (GuiNode<?> node : children){
            addChild(node);
        }
        return self;
    }
    public T removeChild(@NotNull GuiNode<?> node){
        if (node.parent == this){
            node.parent = null;
            children.remove(node);
            sendSignal(SignalType.REMOVE_CHILD);
            node.sendSignal(SignalType.SET_PARENT);
        }
        return self;
    }
    public T removeChildren(@NotNull GuiNode<?>... children){
        for (GuiNode<?> node : children){
            removeChild(node);
        }
        return self;
    }
    public T removeChild(String name) {
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
        return self;
    }
    public T removeChildren(String... names){
        for (String name : names){
            removeChild(name);
        }
        return self;
    }
    public GuiNode<?> getChild(String name){
        for (GuiNode<?> child : children){
            if (child.getName().equals(name)){
                return child;
            }
        }
        return null;
    }
    public T setParent(@NotNull GuiNode<?> node){
        node.addChild(this);
        return self;
    }
    public T setRenderLevel(int renderLevel){
        this.renderLevel = renderLevel;
        if (parent != null){
            parent.sortChildren();
        } else if (parentIsGuiMain){
            MainUi.getInstance().sortChildren();
        }
        sendSignal(SignalType.SET_RENDER_LEVEL, renderLevel);
        return self;
    }

    public GuiNode<?> getParent(){
        return parent;
    }
    public T setName(String name){
        this.name = name;
        sendSignal(SignalType.SET_NAME, name);
        return self;
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
