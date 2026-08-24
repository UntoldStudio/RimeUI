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
package top.untoldstudio.rimeui.application.ui;

import top.untoldstudio.rimeui.application.render.Window;
import top.untoldstudio.rimeui.core.RimeUI;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.ui.node.ImageLabel;

public final class Application {
    private static Application instance;
    private final Window window;

    public Application(){
        window = new Window(800, 600, "RimeUI Layout Builder");
        instance = this;
    }

    public void start(){
        RimeUI.initOpenGL(window.getWindowHandle());
        ImageLabel label = new ImageLabel("/test.png", ScaleOffset.fromScale(0.5, 0.5), ScaleOffset.fromScale(0.5, 0.5)).setAnchor(0.5, 0.5);
        RimeUI.getMainGui().addChild(label);
        while (!window.isWindowShouldClose()){
            window.isWindowShouldClose();
            window.render();
        }
        stop();
    }

    public void stop(){
        window.flush();
    }

    public static Application getInstance(){
        return instance;
    }
}
