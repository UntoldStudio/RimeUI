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

import top.untoldstudio.rimeui.core.serialization.node.*;

public enum JsonNodeType {
    FRAME(JsonFrame.class),
    IMAGE_LABEL(JsonImageLabel.class),
    H_BOX(JsonHBox.class),
    V_BOX(JsonVBox.class),
    IMAGE_BUTTON(JsonImageButton.class),
    SCROLL_FRAME(JsonScrollFrame.class),
    TEXT_LABEL(JsonTextLabel.class);

    private Class<? extends JsonGuiNode> clazz;

    JsonNodeType(Class<? extends JsonGuiNode> clazz){
        this.clazz = clazz;
    }

    public Class<? extends JsonGuiNode> getJsonNodeClass() {
        return clazz;
    }
}
