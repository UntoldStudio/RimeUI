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
package top.untoldstudio.rimeui.core.ui;

import top.untoldstudio.rimeui.core.data.MouseButton;

import java.util.EnumMap;
import java.util.Map;

public final class Mouse {
    private final long window;
    private double xPosition;
    private double yPosition;
    private final Map<MouseButton, Boolean> pressMap = new EnumMap<>(MouseButton.class);

    public void updateXAndYPosition(double x, double y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    public void setMouseButtonPressed(MouseButton button){
        pressMap.put(button, true);
    }
    public void setMouseButtonReleased(MouseButton button){
        pressMap.put(button, false);
    }
    public boolean isMouseButtonPressed(MouseButton button){
        Boolean pressed = pressMap.get(button);
        return pressed != null && pressed;
    }

    public double getXPosition() {
        return xPosition;
    }
    public double getYPosition() {
        return yPosition;
    }

    public Mouse(long window){
        this.window = window;
    }

    public long getWindow() {
        return window;
    }
}
