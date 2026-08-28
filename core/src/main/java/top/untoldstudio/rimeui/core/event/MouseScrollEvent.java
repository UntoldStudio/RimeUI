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
package top.untoldstudio.rimeui.core.event;

import top.untoldstudio.rimeui.core.ui.MainUi;

public final class MouseScrollEvent extends CancelableEvent {
    private final double xDelta;
    private final double yDelta;
    private final double xPosition;
    private final double yPosition;

    public MouseScrollEvent(double xDelta, double yDelta){
        this.xDelta = xDelta;
        this.yDelta = yDelta;
        this.xPosition = MainUi.getInstance().getMouse().getXPosition();
        this.yPosition = MainUi.getInstance().getMouse().getYPosition();
    }

    public double getXDelta(){
        return xDelta;
    }
    public double getYDelta(){
        return yDelta;
    }
    public double getXPosition(){
        return xPosition;
    }
    public double getYPosition(){
        return yPosition;
    }
}
