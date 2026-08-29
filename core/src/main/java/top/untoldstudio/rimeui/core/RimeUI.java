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
package top.untoldstudio.rimeui.core;

import top.untoldstudio.rimeui.core.font.FontManager;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.serialization.JsonGuiNode;
import top.untoldstudio.rimeui.core.serialization.JsonSerialization;
import top.untoldstudio.rimeui.core.ui.GuiNode;
import top.untoldstudio.rimeui.core.ui.MainGui;
import top.untoldstudio.rimeui.core.render.provider.OpenGLGuiRender;
import top.untoldstudio.rimeui.core.ui.Window;

import java.util.List;

public final class RimeUI {
    static {
        System.setProperty("org.lwjgl.system.stackSize", "1024");
    }

    public static void initOpenGL(long windowHandle){
        initCustomGuiRender(windowHandle, new OpenGLGuiRender(windowHandle));
    }
    public static void initCustomGuiRender(long windowHandle, GuiRender render){
        init();
        new MainGui(render, new Window(windowHandle));
    }
    public static void init(){
        FontManager.init();
    }
    public static void render(){
        MainGui.getInstance().render();
    }
    public static MainGui getMainGui(){
        return MainGui.getInstance();
    }
    public static void cleanup(){
        MainGui.getInstance().cleanup();
    }

    public List<GuiNode<?>> deserializeNodeTreeToGuiNodes(String jsonString){
        return JsonSerialization.getInstance().deserializeNodeTreeToGuiNodes(jsonString);
    }
    public List<JsonGuiNode> deserializeNodeTreeToJsonGuiNodes(String jsonString) {
        return JsonSerialization.getInstance().deserializeNodeTreeToJsonGuiNodes(jsonString);
    }
    public String serializeNodeTreeFromJsonNodes(List<JsonGuiNode> rootNodeTree) {
        return JsonSerialization.getInstance().serializeNodeTreeFromJsonNodes(rootNodeTree);
    }
    public String serializeNodeTreeFromNodes(List<GuiNode<?>> rootNodes){
        return JsonSerialization.getInstance().serializeNodeTreeFromNodes(rootNodes);
    }
    public String serializeNodeTreeFromNode(GuiNode<?> node) {
        return JsonSerialization.getInstance().serializeNodeTreeFromNode(node);
    }
}
