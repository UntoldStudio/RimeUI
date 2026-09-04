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
package top.untoldstudio.rimeui.application.bootstrap;

import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.ui.MainGui;
import top.untoldstudio.rimeui.core.ui.node.Frame;
import top.untoldstudio.rimeui.core.ui.node.HBox;
import top.untoldstudio.rimeui.core.ui.node.ScrollFrame;

public final class Bootstrap {
    public static void buildDefaultGuiNodes(){
        Frame userCustomNodeParent = new Frame(ScaleOffset.ZERO, ScaleOffset.fromScale(0.8, 1))
                .setTransparency(1);
        Application.getInstance().setUserCustomNodeParent(userCustomNodeParent);

        MainGui gui = MainGui.getInstance();

        ScrollFrame resourceManagerFrame = new ScrollFrame(ScaleOffset.fromScale(0.8, 0), ScaleOffset.fromScale(0.2, 0.5))
                .setBackgroundColor(RGBA.GRAY).setScrollBarWidth(2).setScrollBarColor(RGBA.WHITE)
                ;
        ScrollFrame propertyPanel = resourceManagerFrame.clone().setPosition(ScaleOffset.fromScale(0.8, 0.5));
        HBox topPanel = new HBox(ScaleOffset.fromScale(0, 0), new ScaleOffset(1, 0, 0, 20)).setBackgroundColor(RGBA.GRAY);

        gui.addChildren(userCustomNodeParent, resourceManagerFrame, propertyPanel, topPanel);
    }
}
