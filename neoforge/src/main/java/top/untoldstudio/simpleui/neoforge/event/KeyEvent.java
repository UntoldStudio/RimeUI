package top.untoldstudio.simpleui.neoforge.event;

import top.untoldstudio.simpleui.common.core.Key;
import top.untoldstudio.simpleui.common.core.KeyModifiers;

public final class KeyEvent extends CanceledEvent {
    private final KeyModifiers modifiers;
    private final KeyAction action;
    private final Key key;

    public KeyEvent(net.minecraft.client.input.KeyEvent event, int action){
        modifiers = new KeyModifiers(event.modifiers());
        this.action = KeyAction.fromValue(action);
        this.key = Key.fromGLFWValue(event.key());
    }

    public KeyModifiers getModifiers() {
        return modifiers;
    }
    public KeyAction getAction() {
        return action;
    }
    public Key getKey() {
        return key;
    }

    public enum KeyAction{
        PRESSED(1),
        RELEASED(0),
        REPEAT(2),
        UNKNOWN(-1);

        private final int value;
        KeyAction(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }
        public static KeyAction fromValue(int value){
            for (KeyAction action : KeyAction.values()){
                if (value == action.getValue()){
                    return action;
                }
            }
            return UNKNOWN;
        }
    }

    @Override
    public String toString(){
        return "KeyEvent:{KeyModifiers:" + modifiers + ", Action:" + action.name() + ", Key:" + key.name() + "}";
    }
}
