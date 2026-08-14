package top.untoldstudio.simpleui.gui.node;

import top.untoldstudio.simpleui.core.ARGB;
import top.untoldstudio.simpleui.core.LayoutVector2;

public class VBox extends LayoutBox {
    @Override
    protected void sortFrame(){
        int currentOffset = frameStartSpacing;

        for (Frame frame : sortedFrameList){
            frame.setPosition(frame.getPosition().withYOffset(currentOffset));
            currentOffset += frame.getSize().getYAllPixel() + frameSpacing;
        }
    }

    public VBox(LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor, ARGB color){
        super(position, size, xAnchor, yAnchor, color);
    }
    public VBox(LayoutVector2 position, LayoutVector2 size){
        super(position, size);
    }
    public VBox(LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor){
        super(position, size, xAnchor, yAnchor);
    }
    public VBox(LayoutVector2 position, LayoutVector2 size, ARGB color){
        super(position, size, color);
    }
}
