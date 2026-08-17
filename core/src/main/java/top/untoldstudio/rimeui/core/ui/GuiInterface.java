package top.untoldstudio.rimeui.core.ui;

public interface GuiInterface {
    default GuiNode getGuiNode(){
        return (GuiNode) this;
    }
}
