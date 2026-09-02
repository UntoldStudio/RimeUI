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
import top.untoldstudio.rimeui.core.data.CursorShape;
import top.untoldstudio.rimeui.core.data.InputAction;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.event.*;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.signal.SignalType;
import top.untoldstudio.rimeui.core.texture.TextureManager;
import top.untoldstudio.rimeui.core.ui.task.DelayTask;
import top.untoldstudio.rimeui.core.ui.task.LoopTask;
import top.untoldstudio.rimeui.core.ui.task.NormalTask;
import top.untoldstudio.rimeui.core.ui.task.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public final class MainGui {
    private static MainGui instance;
    private final List<GuiNode<?>> children = new ArrayList<>();
    private final GuiRender render;
    private double lastRenderTime = System.nanoTime() / 1_000_000_000.0;
    private final Window window;
    private final Mouse mouse;
    private long externalSettingCursor = -1;
    private boolean isLastMouseMoveEventCanceled = false;
    private final PriorityBlockingQueue<Task> tasks = new PriorityBlockingQueue<>();
    private final GLFWEventListener listener;

    public void render() {
        render.saveContext();
        render.beginFrame();

        if (externalSettingCursor != -1 && !isLastMouseMoveEventCanceled) {
            render.setCursorShapeInThisFrame(externalSettingCursor);
        } else {
            render.setCursorShapeInThisFrame(CursorShape.ARROW);
        }

        long currentTimeMillis = System.nanoTime() / 1_000_000;
        double startRenderTime = System.nanoTime() / 1_000_000_000.0;

        runTasks(currentTimeMillis);

        for (GuiNode<?> node : children) {
            node.renderWithChildren(render, startRenderTime - lastRenderTime);
        }
        render.submitBuffer();
        lastRenderTime = System.nanoTime();
        render.restoreContext();
        render.endFrame();
    }

    private void runTasks(long currentTimeMillis) {
        Task task;
        while ((task = tasks.peek()) != null) {
            if (task.isCanceled() || task.tryRun(currentTimeMillis)) {
                tasks.poll();
            } else {
                if (task.isNeedResort()) {
                    tasks.poll();
                    tasks.offer(task);
                    task.setNeedResort(false);
                }
                break;
            }
        }
    }

    public NormalTask runTask(Runnable task) {
        NormalTask normalTask = new NormalTask(task);
        tasks.add(normalTask);
        return normalTask;
    }

    public DelayTask runTaskLater(Runnable task, long waitMilliseconds) {
        long currentTimeMillis = System.nanoTime() / 1_000_000;
        DelayTask delayTask = new DelayTask(currentTimeMillis + waitMilliseconds, task);
        tasks.offer(delayTask);
        return delayTask;
    }

    public LoopTask runLoopTask(Runnable task, long startWaitMilliseconds, long loopWaitMilliseconds, int loopCount) {
        LoopTask loopTask = new LoopTask(startWaitMilliseconds, loopWaitMilliseconds, loopCount, task);
        tasks.offer(loopTask);
        return loopTask;
    }

    public LoopTask runInfiniteLoopTask(Runnable task, long startWaitMilliseconds, long loopWaitMilliseconds) {
        return runLoopTask(task, startWaitMilliseconds, loopWaitMilliseconds, Integer.MAX_VALUE);
    }

    public void setExternalSettingCursor(long externalSettingCursor) {
        this.externalSettingCursor = externalSettingCursor;
    }

    public void onWindowSizeChangeEvent(WindowSizeChangeEvent event) {
        for (GuiNode<?> child : children) {
            child.onWindowSizeChangeEventWithChildren(event);
        }
    }

    public boolean isMouseInRange(ScaleOffset min, ScaleOffset max) {
        double mouseX = MainGui.getInstance().getMouse().getXPosition();
        double mouseY = MainGui.getInstance().getMouse().getYPosition();
        int minX = min.getXPixelInWindow();
        int minY = min.getYPixelInWindow();
        int maxX = max.getXPixelInWindow();
        int maxY = max.getYPixelInWindow();
        return mouseX >= minX && mouseX <= maxX && mouseY >= minY && mouseY <= maxY;
    }

    public void onKeyEvent(KeyEvent event) {
        for (int i = children.size() - 1; i >= 0; i--) {
            children.get(i).onKeyEventWithChildren(event);
            if (event.isCancelled()) return;
        }
    }

    public void onMouseButtonEvent(MouseButtonEvent event) {
        if (event.getAction() == InputAction.RELEASE) {
            mouse.setMouseButtonReleased(event.getButton());
        } else if (event.getAction() == InputAction.PRESS) {
            mouse.setMouseButtonPressed(event.getButton());
        }

        for (int i = children.size() - 1; i >= 0; i--) {
            children.get(i).onMouseButtonEventWithChildren(event);
            if (event.isCancelled()) return;
        }
    }

    public void onMouseMoveEvent(MouseMoveEvent event) {
        mouse.updateXAndYPosition(event.getX(), event.getY());

        for (int i = children.size() - 1; i >= 0; i--) {
            children.get(i).onMouseMoveEventWithChildren(event);
            if (event.isCancelled()) {
                isLastMouseMoveEventCanceled = true;
                return;
            }
        }

        isLastMouseMoveEventCanceled = false;
    }

    public void onMouseScrollEvent(MouseScrollEvent event) {
        for (int i = children.size() - 1; i >= 0; i--) {
            children.get(i).onMouseScrollEventWithChildren(event);
            if (event.isCancelled()) return;
        }
    }

    public boolean hasChild(GuiNode<?> node) {
        return children.contains(node);
    }

    public boolean hasChild(String childName) {
        for (GuiNode<?> node : children) {
            if (node.getName().equals(childName)) {
                return true;
            }
        }
        return false;
    }

    public MainGui addChild(@NotNull GuiNode<?> node) {
        node.parent = null;
        node.parentIsGuiMain = true;
        children.add(node);
        sortChildren();
        if (!node.isInit) {
            node.initWithChildren();
        }
        node.sendSignal(SignalType.SET_PARENT);
        return this;
    }

    public MainGui addChildren(@NotNull GuiNode<?>... children) {
        for (GuiNode<?> node : children) {
            addChild(node);
        }
        return this;
    }

    public MainGui removeChild(@NotNull GuiNode<?> node) {
        node.parentIsGuiMain = false;
        node.sendSignal(SignalType.SET_PARENT);
        children.remove(node);
        return this;
    }

    public MainGui removeChildren(@NotNull GuiNode<?>... nodes) {
        for (GuiNode<?> node : nodes) {
            removeChild(node);
        }
        return this;
    }

    public MainGui removeChild(String name) {
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

    public MainGui removeChildren(String... names) {
        for (String name : names) {
            removeChild(name);
        }
        return this;
    }

    void sortChildren() {
        children.sort(Comparator.comparingInt(GuiNode::getRenderLevel));
    }

    public MainGui(GuiRender render, Window window) {
        instance = this;
        this.render = render;
        this.window = window;
        this.mouse = new Mouse(window.getWindowHandle());
        this.listener = new GLFWEventListener(window.getWindowHandle());
    }

    public static MainGui getInstance() {
        return instance;
    }

    public int getWindowWidth() {
        return window.getWidth();
    }

    public int getWindowHeight() {
        return window.getHeight();
    }

    public GuiRender getRender() {
        return render;
    }

    public Window getWindow() {
        return window;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void cleanup() {
        window.close();
        render.cleanup();
        TextureManager.cleanup();
    }

    public GLFWEventListener getListener() {
        return listener;
    }
}