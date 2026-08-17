package top.untoldstudio.rimeui.core.gui.node;

import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.gui.AbstractFrame;
import top.untoldstudio.rimeui.core.render.GuiRender;

public final class Frame extends AbstractFrame<Frame> {
    @Override
    public void render(GuiRender render){
        render.drawSquare(realPosition, realPositionMax, color);
    }

    public Frame(ScaleOffset position, ScaleOffset size) {
        super(position, size);
    }
}
