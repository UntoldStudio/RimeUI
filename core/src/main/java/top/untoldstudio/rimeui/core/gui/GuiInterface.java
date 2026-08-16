package top.untoldstudio.rimeui.core.gui;

public interface GuiInterface {
    default GuiNode getGuiNode(){
        return (GuiNode) this;
    }
}
