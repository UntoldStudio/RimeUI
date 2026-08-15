package top.untoldstudio.simpleui.common.event;

import top.untoldstudio.simpleui.common.Platform;
import top.untoldstudio.simpleui.common.PlatformProvider;
import top.untoldstudio.simpleui.common.core.KeyModifiers;

public final class MouseClickEvent extends CanceledEvent {
    private final ButtonType buttonType;
    private final ActionType actionType;
    private final KeyModifiers modifiers;
    private final double mouseX;
    private final double mouseY;

    public MouseClickEvent(int buttonType, int actionType, int modifiers){
        this.buttonType = ButtonType.fromValue(buttonType);
        this.actionType = ActionType.fromValue(actionType);
        this.modifiers = new KeyModifiers(modifiers);
        PlatformProvider provider = Platform.getPlatformProvider();
        mouseX = provider.getMouseX();
        mouseY = provider.getMouseY();
    }

    public ButtonType getButtonType() {
        return buttonType;
    }
    public ActionType getActionType() {
        return actionType;
    }
    public double getMouseX(){
        return mouseX;
    }
    public double getMouseY(){
        return mouseY;
    }
    public KeyModifiers getModifiers() {
        return modifiers;
    }

    public enum ButtonType{
        LEFT_MOUSE_BUTTON(0),
        RIGHT_MOUSE_BUTTON(1),
        MIDDLE_MOUSE_BUTTON(2),
        UNKNOWN(-1);

        private final int value;
        ButtonType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }
        public static ButtonType fromValue(int value){
            for (ButtonType buttonType : ButtonType.values()){
                if (buttonType.getValue() == value){
                    return buttonType;
                }
            }
            return UNKNOWN;
        }
    }
    public enum ActionType{
        RELEASE(0),
        PRESS(1),
        UNKNOWN(-1);

        private final int value;
        ActionType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }
        public static ActionType fromValue(int value){
            for (ActionType actionType : ActionType.values()){
                if (actionType.getValue() == value){
                    return actionType;
                }
            }
            return UNKNOWN;
        }
    }
}
