package top.untoldstudio.simpleui.neoforge.gui.node;

import top.untoldstudio.simpleui.neoforge.gui.GuiNode;

public interface GuiInterface {
    default GuiNode getGuiNode(){
        return (GuiNode) this;
    }
}
