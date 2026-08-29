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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import top.untoldstudio.rimeui.core.error.JsonDeserializeError;
import top.untoldstudio.rimeui.core.serialization.node.*;

import java.lang.reflect.Type;

public final class GuiNodeJsonDeserializer implements JsonDeserializer<JsonGuiNode> {
    @Override
    public JsonGuiNode deserialize(JsonElement element, Type targetType, JsonDeserializationContext context){
        JsonObject object = element.getAsJsonObject();
        String type = object.get("type_name").getAsString().toUpperCase();
        JsonNodeType nodeType;

        try {
            nodeType = JsonNodeType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new JsonDeserializeError("Invalid type", e);
        }
        return switch (nodeType){
            case FRAME -> context.deserialize(object, JsonFrame.class);
            case IMAGE_LABEL -> context.deserialize(object, JsonImageLabel.class);
            case H_BOX -> context.deserialize(object, JsonHBox.class);
            case V_BOX -> context.deserialize(object, JsonVBox.class);
            case IMAGE_BUTTON -> context.deserialize(object, JsonImageButton.class);
            case SCROLL_FRAME -> context.deserialize(object, JsonScrollFrame.class);
            case TEXT_LABEL -> context.deserialize(object, JsonTextLabel.class);
        };
    }
}
