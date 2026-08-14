package top.untoldstudio.simpleui.gui.node;

import top.untoldstudio.simpleui.gui.GuiNode;

public interface GuiInterface {
    default GuiNode getGuiNode(){
        return (GuiNode) this;
    }
}
