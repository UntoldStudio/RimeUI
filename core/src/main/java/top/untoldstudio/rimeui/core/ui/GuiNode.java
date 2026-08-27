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
import top.untoldstudio.rimeui.core.event.*;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.signal.SignalBus;
import top.untoldstudio.rimeui.core.signal.SignalType;

import java.util.*;
import java.util.function.DoubleConsumer;

public abstract class GuiNode<T extends GuiNode<T>> {
    @SuppressWarnings("unchecked")
    protected final T self = (T) this;
    protected String name = getClass().getSimpleName();
    protected final SignalBus signalBus = new SignalBus();
    protected boolean parentIsGuiMain = false;
    protected GuiNode<?> parent;
    protected final List<GuiNode<?>> children = new ArrayList<>();
    protected int renderLevel = 0;
    protected boolean isInit = false;
    protected boolean visible = true;
    private final Map<SignalType, Object> lastSignalObjectValues = new EnumMap<>(SignalType.class);
    private final List<DoubleConsumer> renderCallbacks = new ArrayList<>();

    protected void renderWithChildren(GuiRender render, double delta){
        sendSignal(SignalType.BEFORE_RENDER);
        if (visible){
            for (DoubleConsumer renderCallback : renderCallbacks) {
                renderCallback.accept(delta);
            }
            render(render, delta);
            for (GuiNode<?> child : children) {
                child.renderWithChildren(render, delta);
            }
        }
        sendSignal(SignalType.AFTER_RENDER);
    }
    protected abstract void render(GuiRender render, double delta);
    protected boolean isVisible(){
        return visible;
    }
    protected void setVisible(boolean visible){
        this.visible = visible;
    }
    public T registerRenderCallback(DoubleConsumer callback){
        renderCallbacks.add(callback);
        sendSignal(SignalType.REGISTER_RENDER_CALLBACK, callback);
        return self;
    }
    public T unregisterRenderCallback(DoubleConsumer callback){
        renderCallbacks.remove(callback);
        sendSignal(SignalType.UNREGISTER_RENDER_CALLBACK, callback);
        return self;
    }

    protected void onWindowSizeChangeEventWithChildren(WindowSizeChangeEvent event){
        onWindowSizeChangeEvent(event);
        for (GuiNode<?> child : children) {
            child.onWindowSizeChangeEventWithChildren(event);
        }
    }
    protected void onWindowSizeChangeEvent(WindowSizeChangeEvent event){}
    public void onKeyEventWithChildren(KeyEvent event){
        for (int i = children.size() -1; i >= 0; i--){
            children.get(i).onKeyEventWithChildren(event);
            if (event.isCancelled()){
                return;
            }
        }
        onKeyEvent(event);
    }
    protected void onKeyEvent(KeyEvent event){}
    public void onMouseButtonEventWithChildren(MouseButtonEvent event){
        for (int i = children.size() -1; i >= 0; i--){
            children.get(i).onMouseButtonEventWithChildren(event);
            if (event.isCancelled()){
                return;
            }
        }
        onMouseButtonEvent(event);
    }
    protected void onMouseButtonEvent(MouseButtonEvent event){}
    public void onMouseMoveEventWithChildren(MouseMoveEvent event){
        for (int i = children.size() -1; i >= 0; i--){
            children.get(i).onMouseMoveEventWithChildren(event);
            if (event.isCancelled()){
                return;
            }
        }
        onMouseMoveEvent(event);
    }
    protected void onMouseMoveEvent(MouseMoveEvent event){}
    public void onMouseScrollEventWithChildren(MouseScrollEvent event){
        for (int i = children.size() -1; i >= 0; i--){
            children.get(i).onMouseScrollEventWithChildren(event);
            if (event.isCancelled()){
                return;
            }
        }
        onMouseScrollEvent(event);
    }
    protected void onMouseScrollEvent(MouseScrollEvent event){}

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
            if (!node.isInit){
                node.initWithChildren();
            }
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
    public void initWithChildren(){
        init();
        isInit = true;
        for (GuiNode<?> child : children){
            child.initWithChildren();
        }
    }
    protected void init(){}

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
