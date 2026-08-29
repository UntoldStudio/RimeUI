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
import top.untoldstudio.rimeui.core.ui.GuiNode;

import java.util.ArrayList;
import java.util.List;

public final class JsonSerialization {
    private static final JsonSerialization instance = new JsonSerialization();
    public final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(JsonGuiNode.class, new GuiNodeJsonDeserializer())
            .registerTypeAdapterFactory(new LowerCaseEnumTypeAdapterFactory())
            .create();

    public List<GuiNode<?>> deserializeNodeTreeToGuiNode(String jsonString){
        List<JsonGuiNode> jsonGuiNodes = deserializeNodeTree(jsonString);
        List<GuiNode<?>> guiNodes = new ArrayList<>();
        for (JsonGuiNode jsonGuiNode : jsonGuiNodes){
            guiNodes.add(jsonGuiNode.toGuiNode());
        }
        return guiNodes;
    }
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
    public String serializeNodeTreeFromJsonNodes(List<JsonGuiNode> rootNodeTree) {
        int currentNodeIdentifier = 1;
        JsonObject root = new JsonObject();
        for (JsonGuiNode node : rootNodeTree) {
            root.add("GuiNode" + currentNodeIdentifier, gson.toJsonTree(node));
            currentNodeIdentifier++;
        }
        return gson.toJson(root);
    }
    public String serializeNodeTreeFromNodes(List<GuiNode<?>> rootNodes){
        List<JsonGuiNode> jsonNodes = new ArrayList<>();
        for (GuiNode<?> node : rootNodes) {
            jsonNodes.add(node.toJsonNodeTree());
        }
        return serializeNodeTreeFromJsonNodes(jsonNodes);
    }
    public String serializeNodeTreeFromNode(GuiNode<?> node) {
        return serializeNodeTreeFromJsonNodes(List.of(node.toJsonNodeTree()));
    }

    public static JsonSerialization getInstance() {
        return instance;
    }
}
