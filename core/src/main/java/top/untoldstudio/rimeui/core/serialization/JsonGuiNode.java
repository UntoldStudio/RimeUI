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
package top.untoldstudio.rimeui.core.serialization;

import com.google.gson.annotations.SerializedName;
import top.untoldstudio.rimeui.core.ui.GuiNode;

import java.util.List;

public abstract class JsonGuiNode {
    @SerializedName("type_name")
    private JsonNodeType nodeType;
    private String name;
    @SerializedName("render_level")
    private int renderLevel;
    private boolean visible;
    private List<JsonGuiNode> children;

    public abstract GuiNode<?> toGuiNode();
    public final GuiNode<?> fillParentGuiNodeField(GuiNode<?> node){
        node.setName(name).setRenderLevel(renderLevel).setVisible(visible);
        for (JsonGuiNode child : children){
            node.addChild(child.toGuiNode());
        }
        return node;
    }

    public JsonNodeType getTypeName() {
        return nodeType;
    }
    public String getName() {
        return name;
    }
    public int getRenderLevel() {
        return renderLevel;
    }
    public List<JsonGuiNode> getChildren() {
        return children;
    }
    public boolean isVisible() {
        return visible;
    }

    public void setNodeType(JsonNodeType nodeType) {
        this.nodeType = nodeType;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public void setChildren(List<JsonGuiNode> children) {
        this.children = children;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setRenderLevel(int renderLevel) {
        this.renderLevel = renderLevel;
    }
}
