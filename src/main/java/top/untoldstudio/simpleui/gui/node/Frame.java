package top.untoldstudio.simpleui.gui.node;

import top.untoldstudio.simpleui.core.ARGB;
import top.untoldstudio.simpleui.event.WindowSizeChangeEvent;
import top.untoldstudio.simpleui.gui.Gui;
import top.untoldstudio.simpleui.gui.GuiNode;
import top.untoldstudio.simpleui.gui.GuiRender;
import top.untoldstudio.simpleui.core.LayoutVector2;
import static top.untoldstudio.simpleui.signal.SignalType.*;

/**
 * 核心类,几乎每一个有Position和Size的都继承它,它自己也可以独立运作
 */
public class Frame extends GuiNode {
    protected int layoutOrder = 0;
    protected LayoutVector2 position;
    protected LayoutVector2 size;
    protected LayoutVector2 realSize;
    protected ARGB color;
    protected double xAnchor;
    protected double yAnchor;
    protected LayoutVector2 realPosition;
    protected LayoutVector2 maxPosition;
    protected boolean clipChildren = false;

    @Override
    public void renderWithChildren(GuiRender render){
        if (isClipChildren()){
            render.enableScissor(realPosition, maxPosition);
        }
        super.renderWithChildren(render);
        if (isClipChildren()){
            render.disableScissor();
        }
    }
    @Override
    protected void render(GuiRender render) {
        render.fill(realPosition, maxPosition, color);
    }

    protected boolean isMouseInRange(double mouseX, double mouseY){
        int rangeLeft = realPosition.getXAllPixel();
        LayoutVector2 rightBottom = realPosition.add(realSize);
        int rangeRight = rightBottom.getXAllPixel();
        int rangeTop = realPosition.getYAllPixel();
        int rangeBottom = rightBottom.getYAllPixel();
        return mouseX > rangeLeft && mouseX < rangeRight && mouseY > rangeTop && mouseY < rangeBottom;
    }
    public boolean isMouseInRangeNow(){
        return isMouseInRange(Gui.getInstance().getLastMouseX(), Gui.getInstance().getLastMouseY());
    }

    public void setTransparency(double transparency){
        if (getTransparency() != transparency){
            int value = (int)Math.round(255 * (1 - transparency));
            color = color.withAlpha(value);
            sendSingle(SET_TRANSPARENCY);
        }
    }
    public void setAnchor(double xAnchor, double yAnchor){
        setXAnchor(xAnchor);
        setYAnchor(yAnchor);
        operationPosition();
    }
    public void setXAnchor(double xAnchor){
        if (this.xAnchor != xAnchor){
            this.xAnchor = xAnchor;
            sendSingle(SET_X_ANCHOR);
            operationPosition();
        }
    }
    public void setYAnchor(double yAnchor){
        if (this.yAnchor != yAnchor){
            this.yAnchor = yAnchor;
            sendSingle(SET_Y_ANCHOR);
            operationPosition();
        }
    }
    public void setPosition(LayoutVector2 position){
        if (!this.position.equals(position)){
            this.position = position;
            sendSingle(SET_POSITION);
            operationPosition();
        }
    }
    public void setLayoutOrder(int layoutOrder){
        if (this.layoutOrder != layoutOrder){
            this.layoutOrder = layoutOrder;
            sendSingle(SET_LAYOUT_ORDER);
        }
    }

    public void setSize(LayoutVector2 size){
        if (!this.size.equals(size)){
            this.size = size;
            sendSingle(SET_SIZE);
            operationPosition();
        }
    }
    public void setColor(ARGB color){
        if (!this.color.equals(color)){
            this.color = color;
            sendSingle(SET_COLOR);
        }
    }
    public void setClipChildren(boolean value){
        if (this.clipChildren != value){
            this.clipChildren = value;
            sendSingle(SET_CLIP_CHILDREN);
        }
    }
    public double getTransparency(){
        if (color.getAlpha() == 0){
            return 1;
        }
        return 1 - (double)color.getAlpha() / (double)255;
    }
    public LayoutVector2 getPosition(){
        return position;
    }
    public LayoutVector2 getSize(){
        return size;
    }
    public ARGB getColor(){
        return color;
    }
    public double getXAnchor(){
        return xAnchor;
    }
    public double getYAnchor(){
        return yAnchor;
    }
    public LayoutVector2 getRealSize(){
        return realSize;
    }
    public LayoutVector2 getRealPosition(){
        return realPosition;
    }
    public int getLayoutOrder(){
        return layoutOrder;
    }
    public boolean isClipChildren(){
        return clipChildren;
    }
    @Override
    public void setParent(GuiNode parent){
        super.setParent(parent);
        operationPosition();
    }
    @Override
    protected void onWindowSizeChangeEvent(WindowSizeChangeEvent event){
        operationPosition();
    }
    protected void operationPosition(){
        if (getParent() != null && getParent() instanceof Frame parentFrame){
            operationPosition(parentFrame, parentFrame.getRealPosition());
        } else {
            operationPosition(null, LayoutVector2.ZERO);
        }
    }
    protected void operationPosition(Frame parentFrame, LayoutVector2 parentRealPosition) {
        if (parentFrame != null) {
            realSize = LayoutVector2.fromOffset((int) Math.round(size.getXScale() * parentFrame.getRealSize().getXAllPixel()) + size.getXOffset(), (int) Math.round(size.getYScale() * parentFrame.getRealSize().getYAllPixel()) + size.getYOffset());
            realPosition = LayoutVector2.fromOffset(
                    parentRealPosition.getXAllPixel() + (int) Math.round(position.getXScale() * parentFrame.getRealSize().getXAllPixel()) + position.getXOffset() - (int) Math.round(realSize.getXAllPixel() * xAnchor),
                    parentRealPosition.getYAllPixel() + (int) Math.round(position.getYScale() * parentFrame.getRealSize().getYAllPixel()) + position.getYOffset() - (int) Math.round(realSize.getYAllPixel() * yAnchor)
            );
        } else {
            realSize = LayoutVector2.fromOffset(size.getXAllPixel(), size.getYAllPixel());
            realPosition = LayoutVector2.fromOffset(position.getXAllPixel() - (int) Math.round(realSize.getXAllPixel() * xAnchor), position.getYAllPixel() - (int) Math.round(realSize.getYAllPixel() * yAnchor));
        }
        maxPosition = realPosition.add(realSize);
        sendSingle(OPERATION_POSITION);
        letChildrenOperationPosition();
    }
    protected void letChildrenOperationPosition(){
        for (GuiNode guiNode : children){
            if (guiNode instanceof Frame frame){
                frame.operationPosition();
            }
        }
    }
    @Override
    protected void init(){
        operationPosition();
    }
    @Override
    public void setParent(Gui gui){
        super.setParent(gui);
        operationPosition();
    }
    public Frame(LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor, ARGB color){
        this.position = position;
        this.size = size;
        this.realSize = size;
        this.xAnchor = xAnchor;
        this.yAnchor = yAnchor;
        this.color = color;
        operationPosition();
    }
    public Frame(LayoutVector2 position, LayoutVector2 size){
        this(position, size, 0, 0, ARGB.WHITE);
    }
    public Frame(LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor){
        this(position, size, xAnchor, yAnchor, ARGB.WHITE);
    }
    public Frame(LayoutVector2 position, LayoutVector2 size, ARGB color){
        this(position, size, 0, 0, color);
    }
}
