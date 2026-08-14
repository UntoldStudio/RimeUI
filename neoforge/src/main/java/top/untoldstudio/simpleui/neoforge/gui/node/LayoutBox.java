package top.untoldstudio.simpleui.neoforge.gui.node;

import top.untoldstudio.simpleui.common.core.ARGB;
import top.untoldstudio.simpleui.common.core.LayoutVector2;
import top.untoldstudio.simpleui.neoforge.gui.GuiNode;
import top.untoldstudio.simpleui.neoforge.signal.SignalType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class LayoutBox extends Frame {
    protected int frameStartSpacing = 0;
    protected int frameSpacing = 0;
    protected List<Frame> sortedFrameList = new ArrayList<>();

    protected abstract void sortFrame();

    public int getFrameStartSpacing() {
        return frameStartSpacing;
    }
    public int getFrameSpacing(){
        return frameSpacing;
    }
    public void setFrameSpacing(int frameSpacing){
        if (this.frameSpacing != frameSpacing){
            this.frameSpacing = frameSpacing;
            sortFrame();
            sendSingle(SignalType.SET_FRAME_SPACING);
        }
    }
    public void setFrameStartSpacing(int frameStartSpacing){
        if (this.frameStartSpacing != frameStartSpacing){
            this.frameStartSpacing = frameStartSpacing;
            sortFrame();
            sendSingle(SignalType.SET_FRAME_START_SPACING);
        }
    }

    @Override
    public void addChild(GuiNode child){
        super.addChild(child);
        if (child instanceof Frame){
            sortList();
        }
    }

    @Override
    public void removeChild(GuiNode child){
        super.removeChild(child);
        if (child instanceof Frame){
            sortList();
        }
    }

    private void sortList(){
        List<Frame> frames = new ArrayList<>();
        for (GuiNode node : children) {
            if (node instanceof Frame frame){
                frames.add(frame);
            }
        }
        frames.sort(Comparator.comparing(Frame::getLayoutOrder));
        sortedFrameList = frames;
        sortFrame();
    }

    public LayoutBox(LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor, ARGB color){
        super(position, size, xAnchor, yAnchor, color);
    }
    public LayoutBox(LayoutVector2 position, LayoutVector2 size){
        super(position, size);
    }
    public LayoutBox(LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor){
        super(position, size, xAnchor, yAnchor);
    }
    public LayoutBox(LayoutVector2 position, LayoutVector2 size, ARGB color){
        super(position, size, color);
    }
}
