package top.untoldstudio.simpleui.gui.node;

import top.untoldstudio.simpleui.core.ARGB;
import top.untoldstudio.simpleui.core.LayoutVector2;

public class HBox extends LayoutBox {
    @Override
    public void sortFrame(){
        int currentOffset = frameStartSpacing;

        for (Frame frame : sortedFrameList){
            frame.setPosition(frame.getPosition().withXOffset(currentOffset));
            currentOffset += frame.getSize().getXAllPixel() + frameSpacing;
        }
    }

    public HBox(LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor, ARGB color){
        super(position, size, xAnchor, yAnchor, color);
    }
    public HBox(LayoutVector2 position, LayoutVector2 size){
        super(position, size);
    }
    public HBox(LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor){
        super(position, size, xAnchor, yAnchor);
    }
    public HBox(LayoutVector2 position, LayoutVector2 size, ARGB color){
        super(position, size, color);
    }
}
