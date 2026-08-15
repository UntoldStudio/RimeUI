package top.untoldstudio.simpleui.common.gui.node;

import top.untoldstudio.simpleui.common.core.ARGB;
import top.untoldstudio.simpleui.common.core.LayoutVector2;
import top.untoldstudio.simpleui.common.event.MouseClickEvent;
import top.untoldstudio.simpleui.common.event.MouseMoveEvent;

import static top.untoldstudio.simpleui.common.signal.SignalType.*;

public abstract class Button extends Frame {
    protected boolean canLeftMouseButtonClick = true;
    protected boolean canRightMouseButtonClick = true;
    protected boolean canMiddleMouseButtonClick = true;
    protected boolean isMouseLeftClick;
    protected boolean isMouseMiddleClick;
    protected boolean isMouseRightClick;
    protected boolean isMouseInRange;

    protected boolean isClick(){
        return (isMouseLeftClick && canLeftMouseButtonClick) || (isMouseMiddleClick && canMiddleMouseButtonClick) || (isMouseRightClick && canRightMouseButtonClick);
    }
    @Override
    protected void onMouseMove(MouseMoveEvent event){
        isMouseInRange = super.isMouseInRange(event.getNewX(), event.getNewY());
        if (isMouseInRange){
            event.cancel();
            sendSingle(MOUSE_MOVE);
        }
    }
    @Override
    protected void onLeftMouseButtonPressed(MouseClickEvent event){
        isMouseLeftClick = true;
        if (isMouseInRange){
            if (canLeftMouseButtonClick){
                event.cancel();
            }
            sendSingle(LEFT_MOUSE_BUTTON_PRESSED);
        }
    }
    @Override
    protected void onLeftMouseButtonReleased(MouseClickEvent event){
        isMouseLeftClick = false;
        if (isMouseInRange){
            if (canLeftMouseButtonClick){
                event.cancel();
            }
            sendSingle(LEFT_MOUSE_BUTTON_RELEASED);
        }
    }
    @Override
    protected void onMiddleMouseButtonPressed(MouseClickEvent event){
        isMouseMiddleClick = true;
        if (isMouseInRange){
            if (canMiddleMouseButtonClick){
                event.cancel();
            }
            sendSingle(MIDDLE_MOUSE_BUTTON_PRESSED);
        }
    }
    @Override
    protected void onMiddleMouseButtonReleased(MouseClickEvent event){
        isMouseMiddleClick = false;
        if (isMouseInRange){
            if (canMiddleMouseButtonClick){
                event.cancel();
            }
            sendSingle(MIDDLE_MOUSE_BUTTON_RELEASED);
        }
    }
    @Override
    protected void onRightMouseButtonPressed(MouseClickEvent event){
        isMouseRightClick = true;
        if (isMouseInRange){
            if (canRightMouseButtonClick){
                event.cancel();
            }
            sendSingle(RIGHT_MOUSE_BUTTON_PRESSED);
        }
    }
    @Override
    protected void onRightMouseButtonReleased(MouseClickEvent event){
        isMouseRightClick = false;
        if (isMouseInRange){
            if (canRightMouseButtonClick){
                event.cancel();
            }
            sendSingle(RIGHT_MOUSE_BUTTON_RELEASED);
        }
    }
    public void setCanLeftMouseButtonClick(boolean value){
        if (canLeftMouseButtonClick != value){
            canLeftMouseButtonClick = value;
            sendSingle(SET_CAN_LEFT_MOUSE_BUTTON_CLICK);
        }
    }
    public void setCanMiddleMouseButtonClick(boolean value){
        if (this.canMiddleMouseButtonClick != value){
            canMiddleMouseButtonClick = value;
            sendSingle(SET_CAN_MIDDLE_MOUSE_BUTTON_CLICK);
        }
    }
    public void setCanRightMouseButtonClick(boolean value){
        if (this.canRightMouseButtonClick != value){
            canRightMouseButtonClick = value;
            sendSingle(SET_CAN_RIGHT_MOUSE_BUTTON_CLICK);
        }
    }
    public boolean canLeftMouseButtonClick(){
        return canLeftMouseButtonClick;
    }
    public boolean canMiddleMouseButtonClick(){
        return canMiddleMouseButtonClick;
    }
    public boolean canRightMouseButtonClick(){
        return canRightMouseButtonClick;
    }
    public boolean isMouseInRange(){
        return isMouseInRange;
    }
    public boolean isMouseLeftClick(){
        return isMouseLeftClick;
    }
    public boolean isMouseMiddleClick(){
        return isMouseMiddleClick;
    }
    public boolean isMouseRightClick(){
        return isMouseRightClick;
    }

    public Button(LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor, ARGB color){
        super(position, size, xAnchor, yAnchor, color);
    }
    public Button(LayoutVector2 position, LayoutVector2 size){
        super(position, size);
    }
    public Button(LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor){
        super(position, size, xAnchor, yAnchor);
    }
    public Button(LayoutVector2 position, LayoutVector2 size, ARGB color){
        super(position, size, color);
    }
}
