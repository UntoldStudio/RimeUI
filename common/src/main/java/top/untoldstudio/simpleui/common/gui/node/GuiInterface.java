package top.untoldstudio.simpleui.common.gui.node;

import top.untoldstudio.simpleui.common.gui.GuiNode;

public interface GuiInterface {
    default GuiNode getGuiNode(){
        return (GuiNode) this;
    }
}
