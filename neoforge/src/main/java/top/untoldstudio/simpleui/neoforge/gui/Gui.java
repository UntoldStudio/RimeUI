package top.untoldstudio.simpleui.neoforge.gui;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.GuiGraphics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.untoldstudio.simpleui.neoforge.event.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 全局单例,所有GUI节点最终都挂在这个上面
 */
public final class Gui {
    private static final Logger LOGGER = LoggerFactory.getLogger(Gui.class);
    private static Gui instance;
    private final List<GuiNode> guiNodes = new ArrayList<>();
    private double lastMouseX;
    private double lastMouseY;
    private int lastWindowSizeX;
    private int lastWindowSizeY;
    private boolean isInit = false;

    @ParametersAreNonnullByDefault
    public void render(GuiGraphics graphics){
        Minecraft minecraft = Minecraft.getInstance();
        MouseHandler mouseHandler = minecraft.mouseHandler;
        Window window = minecraft.getWindow();
        int newWindowSizeX = window.getGuiScaledWidth();
        int newWindowSizeY = window.getGuiScaledHeight();
        double newMouseX = mouseHandler.xpos() * (double)newWindowSizeX / (double)window.getScreenWidth();
        double newMouseY = mouseHandler.ypos() * (double)newWindowSizeY / (double)window.getScreenHeight();
        if (newMouseX != lastMouseX || newMouseY != lastMouseY){
            MouseMoveEvent mouseMoveEvent = new MouseMoveEvent(lastMouseX, lastMouseY, newMouseX, newMouseY);
            for (GuiNode guiNode : guiNodes){
                guiNode.onMouseMoveWithChildren(mouseMoveEvent);
            }
            lastMouseX = newMouseX;
            lastMouseY = newMouseY;
        }

        if (lastWindowSizeX != newWindowSizeX || lastWindowSizeY != newWindowSizeY){
            WindowSizeChangeEvent event = new WindowSizeChangeEvent(lastWindowSizeX, lastWindowSizeY, newWindowSizeX, newWindowSizeY);
            for (GuiNode guiNode : guiNodes){
                guiNode.onWindowSizeChangeEventWithChildren(event);
            }
            lastWindowSizeX = newWindowSizeX;
            lastWindowSizeY = newWindowSizeY;
        }

        GuiRender render = new GuiRender(graphics);

        for (GuiNode guiNode : guiNodes){
            guiNode.renderWithChildren(render);
        }

        render.renderDeferredElements();
    }

    public void onKeyEvent(KeyEvent event){
        for (GuiNode guiNode : guiNodes){
            guiNode.onKeyEventWithChildren(event);
        }
    }

    public void init(){
        for (GuiNode guiNode : guiNodes){
            guiNode.initWithChildren();
        }
        isInit = true;
        LOGGER.info("Gui was initialized");
    }
    public void reload(){
        for (GuiNode guiNode : guiNodes){
            guiNode.reloadWithChildren();
        }
        LOGGER.info("Gui was reloaded");
    }

    public void tick(){
        for (GuiNode guiNode : guiNodes){
            guiNode.tickWithChildren();
        }
    }
    public void preLogicalTick(){
        for (GuiNode guiNode : guiNodes){
            guiNode.preLogicalTickWithChildren();
        }
    }

    public void onMouseScrollEvent(MouseScrollEvent event){
        for (GuiNode guiNode : guiNodes){
            guiNode.onMouseScrollEventWithChildren(event);
        }
    }

    public void onMouseButtonEvent(MouseClickEvent event){
        switch (event.getButtonType()){
            case LEFT_MOUSE_BUTTON -> {
                if (event.getActionType() == MouseClickEvent.ActionType.PRESS){
                    for (GuiNode guiNode : guiNodes){
                        guiNode.onLeftMouseButtonPressedWithChildren(event);
                    }
                } else {
                    for (GuiNode guiNode : guiNodes){
                        guiNode.onLeftMouseButtonReleasedWithChildren(event);
                    }
                }
            }
            case RIGHT_MOUSE_BUTTON -> {
                if (event.getActionType() == MouseClickEvent.ActionType.PRESS){
                    for (GuiNode guiNode : guiNodes){
                        guiNode.onRightMouseButtonPressedWithChildren(event);
                    }
                } else {
                    for (GuiNode guiNode : guiNodes){
                        guiNode.onRightMouseButtonReleasedWithChildren(event);
                    }
                }
            }
            case MIDDLE_MOUSE_BUTTON -> {
                if (event.getActionType() == MouseClickEvent.ActionType.PRESS){
                    for (GuiNode guiNode : guiNodes){
                        guiNode.onMiddleMouseButtonPressedWithChildren(event);
                    }
                } else {
                    for (GuiNode guiNode : guiNodes){
                        guiNode.onMiddleMouseButtonReleasedWithChildren(event);
                    }
                }
            }
        }
    }

    public void addGuiNode(GuiNode node){
        guiNodes.add(node);
        node.setGui(this);
        sortGuiNodeList();
        if (isInit){
            node.initWithChildren();
        }
    }
    public void addChild(GuiNode node){
        addGuiNode(node);
    }
    public void addGuiNodes(GuiNode... nodes){
        for (GuiNode guiNode : nodes){
            addGuiNode(guiNode);
        }
    }
    public void addChildren(GuiNode... nodes){
        addGuiNodes(nodes);
    }
    public void removeGuiNode(GuiNode node){
        if (guiNodes.contains(node)){
            guiNodes.remove(node);
            node.setGui(null);
        }
    }

    public double getLastMouseX(){
        return lastMouseX;
    }
    public double getLastMouseY(){
        return lastMouseY;
    }
    public void sortGuiNodeList(){
        guiNodes.sort(Comparator.naturalOrder());
    }
    public Gui(){
        instance = this;
        LOGGER.info("Gui was created");
    }
    public static Gui getInstance(){
        return instance;
    }
}
