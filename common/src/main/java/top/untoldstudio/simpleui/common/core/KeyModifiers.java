package top.untoldstudio.simpleui.common.core;

import static org.lwjgl.glfw.GLFW.*;

public final class KeyModifiers {
    private final boolean isShiftPressed;
    private final boolean isCtrlPressed;
    private final boolean isAltPressed;
    private final boolean isSuperPressed;
    private final boolean isCapsLockEnabled;
    private final boolean isNumLockEnabled;

    public KeyModifiers(int modifiers){
        isShiftPressed = (modifiers & GLFW_MOD_SHIFT) != 0;
        isCtrlPressed = (modifiers & GLFW_MOD_CONTROL) != 0;
        isAltPressed = (modifiers & GLFW_MOD_ALT) != 0;
        isSuperPressed = (modifiers & GLFW_MOD_SUPER) != 0;
        isCapsLockEnabled = (modifiers & GLFW_MOD_CAPS_LOCK) != 0;
        isNumLockEnabled = (modifiers & GLFW_MOD_NUM_LOCK) != 0;
    }

    public boolean isShiftPressed() {
        return isShiftPressed;
    }
    public boolean isCtrlPressed() {
        return isCtrlPressed;
    }
    public boolean isAltPressed() {
        return isAltPressed;
    }
    public boolean isSuperPressed() {
        return isSuperPressed;
    }
    public boolean isCapsLockEnabled() {
        return isCapsLockEnabled;
    }
    public boolean isNumLockEnabled() {
        return isNumLockEnabled;
    }

    @Override
    public String toString() {
        return "{" +
                "Shift:" + isShiftPressed + ", " +
                "Ctrl:" + isCtrlPressed + ", " +
                "Alt:" + isAltPressed + ", " +
                "Super:" + isSuperPressed + ", " +
                "CapsLock:" + isCapsLockEnabled + ", " +
                "NumLock:" + isNumLockEnabled + "}";
    }
}
