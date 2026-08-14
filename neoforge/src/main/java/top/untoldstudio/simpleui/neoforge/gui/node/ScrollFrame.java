package top.untoldstudio.simpleui.neoforge.gui.node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.untoldstudio.simpleui.common.core.ARGB;
import top.untoldstudio.simpleui.common.core.LayoutVector2;
import top.untoldstudio.simpleui.neoforge.Tools;
import top.untoldstudio.simpleui.neoforge.event.MouseScrollEvent;
import top.untoldstudio.simpleui.neoforge.event.WindowSizeChangeEvent;
import top.untoldstudio.simpleui.neoforge.gui.Gui;
import top.untoldstudio.simpleui.neoforge.gui.GuiNode;
import top.untoldstudio.simpleui.neoforge.gui.GuiRender;
import top.untoldstudio.simpleui.neoforge.image.ImageData;
import top.untoldstudio.simpleui.neoforge.signal.SignalType;

/**
 * 请不要取消裁剪!
 */
public class ScrollFrame extends Frame {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScrollFrame.class);
    private double scrollScale = 2;
    private boolean canScroll = true;
    private double scrollProgress = 1;
    private final ImageButton scrollBar = new ImageButton(
            new ImageData(Tools.getResourceLocationBySimpleUINameSpace("textures/default_scroll_bar.png"), 0, 20, 0, 20),
            new LayoutVector2(1, 0, 0, 0),
            new LayoutVector2(0, 6, 1, 0)
    ){
        @Override
        public void onWindowSizeChangeEvent(WindowSizeChangeEvent event) {
        }
    };

    @Override
    public void renderWithChildren(GuiRender render){
        render.enableScissor(realPosition, maxPosition);
        super.renderWithChildren(render);
        render.disableScissor();
    }

    @Override
    public LayoutVector2 getRealPosition() {
        LayoutVector2 base = super.getRealPosition();
        return base.withYOffset(base.getYOffset() - (int) ((int) ((scrollScale - 1) * realSize.getYAllPixel()) * (1 - scrollProgress)));
    }

    private void setScrollBarProcessState() {
        double yScale = 1 / scrollScale;
        scrollBar.size = scrollBar.getSize().withYScale(yScale);
        double allPosition = 1 - yScale;
        scrollBar.position = scrollBar.getPosition().withYScale(allPosition - scrollProgress * allPosition);
        operationPosition();
    }

    @Override
    protected void letChildrenOperationPosition(){
        for (GuiNode guiNode : children){
            if (guiNode == scrollBar){
                scrollBar.operationPosition(this, super.getRealPosition());
            } else if (guiNode instanceof Frame frame){
                frame.operationPosition();
            }
        }
    }

    @Override
    public void onMouseScrollEvent(MouseScrollEvent event){
        if (canScroll && isMouseInRange(Gui.getInstance().getLastMouseX(), Gui.getInstance().getLastMouseY())){
            double value = event.getYOffset() / 10;
            double tempProcess = scrollProgress + value;
            scrollProgress = Math.clamp(tempProcess, 0, 1);
            event.cancel();
            setScrollBarProcessState();
        }
    }

    @Override
    public void init(){
        scrollBar.setAnchor(1, 0);
        addChild(scrollBar);
        super.init();
        setScrollBarProcessState();
        scrollBar.setColor(ARGB.GRAY);
    }

    @Override
    public void onWindowSizeChangeEvent(WindowSizeChangeEvent event){
        setScrollBarProcessState();
    }

    public void setScrollProgress(double progress){
        if (getScrollProgress() != progress){
            progress = Math.clamp(progress, 0, 1);
            this.scrollProgress = progress;
            sendSingle(SignalType.SET_SCROLL_PROGRESS);
            setScrollBarProcessState();
        }
    }
    public void setCanScroll(boolean canScroll){
        if (this.canScroll != canScroll){
            this.canScroll = canScroll;
            sendSingle(SignalType.SET_CAN_SCROLL);
        }
    }
    public void setScrollScale(double scrollScale){
        if (this.scrollScale != scrollScale){
            this.scrollScale = scrollScale;
            sendSingle(SignalType.SET_SCROLL_SCALE);
            setScrollBarProcessState();
        }
    }
    public void setScrollBarWidth(int width){
        if (scrollBar.getSize().getXAllPixel() != width){
            scrollBar.setSize(scrollBar.getSize().withX(width));
            sendSingle(SignalType.SET_SCROLL_BAR_WIDTH);
        }
    }
    public void setScrollBarTexture(ImageData data){
        if (scrollBar.getDefaultTexture() != data){
            scrollBar.setDefaultTexture(data);
            sendSingle(SignalType.SET_SCROLL_BAR_TEXTURE);
        }
    }
    public void setScrollBarVisible(boolean visible){
        if (scrollBar.getVisible() != visible){
            scrollBar.setVisible(visible);
            sendSingle(SignalType.SET_SCROLL_BAR_VISIBLE);
        }
    }
    public double getScrollScale(){
        return scrollScale;
    }
    public int getScrollBarWidth(){
        return scrollBar.getPosition().getXAllPixel();
    }
    public ImageData getScrollBarTexture(){
        return scrollBar.getDefaultTexture();
    }
    public boolean getScrollBarVisible(){
        return scrollBar.getVisible();
    }
    public boolean canScroll(){
        return canScroll;
    }
    public double getScrollProgress(){
        return scrollProgress;
    }

    public ScrollFrame(LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor, ARGB color){
        super(position, size, xAnchor, yAnchor, color);
    }
    public ScrollFrame(LayoutVector2 position, LayoutVector2 size){
        super(position, size);
    }
    public ScrollFrame(LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor){
        super(position, size, xAnchor, yAnchor);
    }
    public ScrollFrame(LayoutVector2 position, LayoutVector2 size, ARGB color){
        super(position, size, color);
    }

    @Override
    public void setClipChildren(boolean clipChildren) {
        LOGGER.warn("Cannot clip for a ScrollFrame!");
    }
}
