/*
 * Copyright 2026 Untold Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.untoldstudio.rimeui.core.data;

import static org.lwjgl.glfw.GLFW.*;

public enum Key {
    A(GLFW_KEY_A, 'a'),
    B(GLFW_KEY_B, 'b'),
    C(GLFW_KEY_C, 'c'),
    D(GLFW_KEY_D, 'd'),
    E(GLFW_KEY_E, 'e'),
    F(GLFW_KEY_F, 'f'),
    G(GLFW_KEY_G, 'g'),
    H(GLFW_KEY_H, 'h'),
    I(GLFW_KEY_I, 'i'),
    J(GLFW_KEY_J, 'j'),
    K(GLFW_KEY_K, 'k'),
    L(GLFW_KEY_L, 'l'),
    M(GLFW_KEY_M, 'm'),
    N(GLFW_KEY_N, 'n'),
    O(GLFW_KEY_O, 'o'),
    P(GLFW_KEY_P, 'p'),
    Q(GLFW_KEY_Q, 'q'),
    R(GLFW_KEY_R, 'r'),
    S(GLFW_KEY_S, 's'),
    T(GLFW_KEY_T, 't'),
    U(GLFW_KEY_U, 'u'),
    V(GLFW_KEY_V, 'v'),
    W(GLFW_KEY_W, 'w'),
    X(GLFW_KEY_X, 'x'),
    Y(GLFW_KEY_Y, 'y'),
    Z(GLFW_KEY_Z, 'z'),
    MAIN_0(GLFW_KEY_0, '0'),
    MAIN_1(GLFW_KEY_1, '1'),
    MAIN_2(GLFW_KEY_2, '2'),
    MAIN_3(GLFW_KEY_3, '3'),
    MAIN_4(GLFW_KEY_4, '4'),
    MAIN_5(GLFW_KEY_5, '5'),
    MAIN_6(GLFW_KEY_6, '6'),
    MAIN_7(GLFW_KEY_7, '7'),
    MAIN_8(GLFW_KEY_8, '8'),
    MAIN_9(GLFW_KEY_9, '9'),
    KP_0(GLFW_KEY_KP_0, '0'),
    KP_1(GLFW_KEY_KP_1, '1'),
    KP_2(GLFW_KEY_KP_2, '2'),
    KP_3(GLFW_KEY_KP_3, '3'),
    KP_4(GLFW_KEY_KP_4, '4'),
    KP_5(GLFW_KEY_KP_5, '5'),
    KP_6(GLFW_KEY_KP_6, '6'),
    KP_7(GLFW_KEY_KP_7, '7'),
    KP_8(GLFW_KEY_KP_8, '8'),
    KP_9(GLFW_KEY_KP_9, '9'),
    KP_DECIMAL(GLFW_KEY_KP_DECIMAL, '\u0000'),
    KP_DIVIDE(GLFW_KEY_KP_DIVIDE, '\u0000'),
    KP_MULTIPLY(GLFW_KEY_KP_MULTIPLY, '\u0000'),
    KP_SUBTRACT(GLFW_KEY_KP_SUBTRACT, '\u0000'),
    KP_ADD(GLFW_KEY_KP_ADD, '\u0000'),
    KP_ENTER(GLFW_KEY_KP_ENTER, '\u0000'),
    KP_EQUAL(GLFW_KEY_KP_EQUAL, '\u0000'),
    F1(GLFW_KEY_F1, '\u0000'),
    F2(GLFW_KEY_F2, '\u0000'),
    F3(GLFW_KEY_F3, '\u0000'),
    F4(GLFW_KEY_F4, '\u0000'),
    F5(GLFW_KEY_F5, '\u0000'),
    F6(GLFW_KEY_F6, '\u0000'),
    F7(GLFW_KEY_F7, '\u0000'),
    F8(GLFW_KEY_F8, '\u0000'),
    F9(GLFW_KEY_F9, '\u0000'),
    F10(GLFW_KEY_F10, '\u0000'),
    F11(GLFW_KEY_F11, '\u0000'),
    F12(GLFW_KEY_F12, '\u0000'),
    F13(GLFW_KEY_F13, '\u0000'),
    F14(GLFW_KEY_F14, '\u0000'),
    F15(GLFW_KEY_F15, '\u0000'),
    F16(GLFW_KEY_F16, '\u0000'),
    F17(GLFW_KEY_F17, '\u0000'),
    F18(GLFW_KEY_F18, '\u0000'),
    F19(GLFW_KEY_F19, '\u0000'),
    F20(GLFW_KEY_F20, '\u0000'),
    F21(GLFW_KEY_F21, '\u0000'),
    F22(GLFW_KEY_F22, '\u0000'),
    F23(GLFW_KEY_F23, '\u0000'),
    F24(GLFW_KEY_F24, '\u0000'),
    F25(GLFW_KEY_F25, '\u0000'),
    APOSTROPHE(GLFW_KEY_APOSTROPHE, '\u0000'),
    COMMA(GLFW_KEY_COMMA, '\u0000'),
    MINUS(GLFW_KEY_MINUS, '\u0000'),
    PERIOD(GLFW_KEY_PERIOD, '\u0000'),
    SLASH(GLFW_KEY_SLASH, '\u0000'),
    SEMICOLON(GLFW_KEY_SEMICOLON, '\u0000'),
    EQUAL(GLFW_KEY_EQUAL, '\u0000'),
    LEFT_BRACKET(GLFW_KEY_LEFT_BRACKET, '\u0000'),
    RIGHT_BRACKET(GLFW_KEY_RIGHT_BRACKET, '\u0000'),
    BACKSLASH(GLFW_KEY_BACKSLASH, '\u0000'),
    GRAVE_ACCENT(GLFW_KEY_GRAVE_ACCENT, '\u0000'),
    ESCAPE(GLFW_KEY_ESCAPE, '\u0000'),
    ENTER(GLFW_KEY_ENTER, '\u0000'),
    TAB(GLFW_KEY_TAB, '\u0000'),
    BACKSPACE(GLFW_KEY_BACKSPACE, '\u0000'),
    INSERT(GLFW_KEY_INSERT, '\u0000'),
    DELETE(GLFW_KEY_DELETE, '\u0000'),
    HOME(GLFW_KEY_HOME, '\u0000'),
    END(GLFW_KEY_END, '\u0000'),
    PAGE_UP(GLFW_KEY_PAGE_UP, '\u0000'),
    PAGE_DOWN(GLFW_KEY_PAGE_DOWN, '\u0000'),
    CAPS_LOCK(GLFW_KEY_CAPS_LOCK, '\u0000'),
    SCROLL_LOCK(GLFW_KEY_SCROLL_LOCK, '\u0000'),
    NUM_LOCK(GLFW_KEY_NUM_LOCK, '\u0000'),
    PRINT_SCREEN(GLFW_KEY_PRINT_SCREEN, '\u0000'),
    PAUSE(GLFW_KEY_PAUSE, '\u0000'),
    UP(GLFW_KEY_UP, '\u0000'),
    DOWN(GLFW_KEY_DOWN, '\u0000'),
    LEFT(GLFW_KEY_LEFT, '\u0000'),
    RIGHT(GLFW_KEY_RIGHT, '\u0000'),
    LEFT_SHIFT(GLFW_KEY_LEFT_SHIFT, '\u0000'),
    RIGHT_SHIFT(GLFW_KEY_RIGHT_SHIFT, '\u0000'),
    LEFT_CONTROL(GLFW_KEY_LEFT_CONTROL, '\u0000'),
    RIGHT_CONTROL(GLFW_KEY_RIGHT_CONTROL, '\u0000'),
    LEFT_ALT(GLFW_KEY_LEFT_ALT, '\u0000'),
    RIGHT_ALT(GLFW_KEY_RIGHT_ALT, '\u0000'),
    LEFT_SUPER(GLFW_KEY_LEFT_SUPER, '\u0000'),
    RIGHT_SUPER(GLFW_KEY_RIGHT_SUPER, '\u0000'),
    SPACE(GLFW_KEY_SPACE, '\u0000'),
    MENU(GLFW_KEY_MENU, '\u0000'),
    WORLD_1(GLFW_KEY_WORLD_1, '\u0000'),
    WORLD_2(GLFW_KEY_WORLD_2, '\u0000'),
    UNKNOWN(GLFW_KEY_UNKNOWN, '\u0000');

    private final int glfwValue;
    private final char name;
    Key(int glfwValue, char name){
        this.glfwValue = glfwValue;
        this.name = name;
    }

    public boolean isNull(){
        return name == '\u0000';
    }
    public char getName(){
        return name;
    }

    public int getGLFWValue(){
        return glfwValue;
    }
    public static Key fromGLFWValue(int glfwValue){
        for (Key key : Key.values()){
            if (key.getGLFWValue() == glfwValue){
                return key;
            }
        }
        return UNKNOWN;
    }
}
