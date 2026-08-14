package top.untoldstudio.simpleui.event;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.neoforged.neoforge.client.event.InputEvent;
import top.untoldstudio.simpleui.core.KeyModifiers;

public final class MouseClickEvent {
    private final InputEvent.MouseButton.Pre event;
    private final ButtonType buttonType;
    private final ActionType actionType;
    private final KeyModifiers modifiers;
    private final double mouseX;
    private final double mouseY;

    public MouseClickEvent(InputEvent.MouseButton.Pre event){
        this.event = event;
        this.buttonType = ButtonType.fromValue(event.getButton());
        this.actionType = ActionType.fromValue(event.getAction());
        modifiers = new KeyModifiers(event.getModifiers());
        Minecraft minecraft = Minecraft.getInstance();
        Window window = minecraft.getWindow();
        MouseHandler mouseHandler = minecraft.mouseHandler;
        mouseX = (double) window.getGuiScaledWidth() / (double) window.getScreenWidth() * mouseHandler.xpos();
        mouseY = (double) window.getGuiScaledHeight() / (double) window.getScreenHeight() * mouseHandler.ypos();
    }

    public ButtonType getButtonType() {
        return buttonType;
    }
    public ActionType getActionType() {
        return actionType;
    }
    public boolean isCanceled(){
        return event.isCanceled();
    }
    public void cancel(){
        setCanceled(true);
    }
    public void setCanceled(boolean value){
        event.setCanceled(value);
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
