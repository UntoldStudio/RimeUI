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

import com.google.gson.*;
import top.untoldstudio.rimeui.core.error.JsonSerializeError;

import java.util.ArrayList;
import java.util.List;

public final class JsonSerialization {
    private static final JsonSerialization instance = new JsonSerialization();
    public final Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(JsonGuiNode.class, new GuiNodeJsonDeserializer()).registerTypeAdapter(JsonGuiNode.class, new GuiNodeJsonSerializer()).create();

    public List<JsonGuiNode> deserializeNodeTree(String jsonString) {
        JsonObject root = JsonParser.parseString(jsonString).getAsJsonObject();

        List<JsonElement> children = new ArrayList<>(root.asMap().values());
        List<JsonGuiNode> rootNodes = new ArrayList<>();
        for (JsonElement child : children) {
            if (!child.isJsonObject()) {
                throw new JsonSerializeError("Illegal node tree");
            }
            JsonObject object = child.getAsJsonObject();
            JsonGuiNode node = gson.fromJson(object, JsonGuiNode.class);
            rootNodes.add(node);
        }

        return rootNodes;
    }
    public String serializeNodeTree(List<JsonGuiNode> rootNodeTree) {
        int currentNodeIdentifier = 1;
        JsonObject root = new JsonObject();
        for (JsonGuiNode node : rootNodeTree) {
            root.add("GuiNode" + currentNodeIdentifier, gson.toJsonTree(node));
            currentNodeIdentifier++;
        }
        return gson.toJson(root);
    }

    public static JsonSerialization getInstance() {
        return instance;
    }
}
