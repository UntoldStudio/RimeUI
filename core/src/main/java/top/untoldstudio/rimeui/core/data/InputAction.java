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

public enum InputAction {
    PRESS(GLFW_PRESS),
    RELEASE(GLFW_RELEASE),
    REPEAT(GLFW_REPEAT),
    UNKNOWN(-1);

    private final int glfwValue;
    InputAction(int glfwValue){
        this.glfwValue = glfwValue;
    }
    public int getGLFWValue(){
        return glfwValue;
    }
    public static InputAction fromGLFWValue(int glfwValue){
        for (InputAction action : InputAction.values()){
            if (action.getGLFWValue() == glfwValue){
                return action;
            }
        }
        return UNKNOWN;
    }
}
