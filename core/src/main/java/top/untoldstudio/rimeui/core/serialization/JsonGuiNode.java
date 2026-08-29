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

import java.util.List;

public abstract class JsonGuiNode {
    @SerializedName("type_name")
    private String typeName;
    private String name;
    @SerializedName("render_level")
    private int renderLevel;
    private boolean visible;
    private List<JsonGuiNode> children;

    public String getTypeName() {
        return typeName;
    }
    public JsonNodeType getNodeType() {
        try {
            return JsonNodeType.valueOf(typeName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid JsonNodeType: " + typeName);
        }
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
}
