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
package top.untoldstudio.rimeui.core.serialization.node;

import com.google.gson.annotations.SerializedName;
import top.untoldstudio.rimeui.core.data.MouseButton;
import top.untoldstudio.rimeui.core.serialization.JsonAbstractFrame;
import top.untoldstudio.rimeui.core.serialization.JsonImageData;
import top.untoldstudio.rimeui.core.ui.node.ImageButton;

import java.util.List;

public final class JsonImageButton extends JsonAbstractFrame {
    @SerializedName("can_trigger_buttons")
    private List<MouseButton> canTriggerButtons;
    @SerializedName("default_image_data")
    private JsonImageData defaultImageData;
    @SerializedName("pressed_image_data")
    private JsonImageData pressedImageData;
    @SerializedName("hovered_image_data")
    private JsonImageData hoveredImageData;

    @Override
    public ImageButton toGuiNode(){
        ImageButton button = new ImageButton(defaultImageData.toImageData(), position, size);
        for (MouseButton mouseButton : canTriggerButtons){
            button.addCanTriggerButton(mouseButton);
        }
        button.setDefaultImage(defaultImageData.toImageData());
        button.setPressedImage(pressedImageData.toImageData());
        button.setHoveredImage(hoveredImageData.toImageData());
        super.fillParentGuiNodeField(button);
        return button;
    }

    public List<MouseButton> getCanTriggerButtons() {
        return canTriggerButtons;
    }
    public JsonImageData getDefaultImageData() {
        return defaultImageData;
    }
    public JsonImageData getPressedImageData() {
        return pressedImageData;
    }
    public JsonImageData getHoveredImageData() {
        return hoveredImageData;
    }
    public void setCanTriggerButtons(List<MouseButton> canTriggerButtons) {
        this.canTriggerButtons = canTriggerButtons;
    }
    public void setDefaultImageData(JsonImageData defaultImageData) {
        this.defaultImageData = defaultImageData;
    }
    public void setPressedImageData(JsonImageData pressedImageData) {
        this.pressedImageData = pressedImageData;
    }
    public void setHoveredImageData(JsonImageData hoveredImageData) {
        this.hoveredImageData = hoveredImageData;
    }
}
