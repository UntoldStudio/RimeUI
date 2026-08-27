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
package top.untoldstudio.rimeui.core.ui.node;

import top.untoldstudio.rimeui.core.data.CursorShape;
import top.untoldstudio.rimeui.core.data.MouseButton;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.event.MouseMoveEvent;
import top.untoldstudio.rimeui.core.event.MouseButtonEvent;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.signal.SignalType;
import top.untoldstudio.rimeui.core.texture.ImageData;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;
import top.untoldstudio.rimeui.core.ui.MainUi;
import top.untoldstudio.rimeui.core.ui.Mouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ImageButton extends AbstractFrame<ImageButton> implements ImageBase {
    private final List<MouseButton> canTriggerButtons = new ArrayList<>(List.of(MouseButton.LEFT));
    private ImageData defaultImage;
    private ImageData pressedImage;
    private ImageData hoveredImage;

    @Override
    public void render(GuiRender render, double delta){
        Mouse mouse = MainUi.getInstance().getMouse();
        ImageData data = defaultImage;
        if (super.isMouseInRange() && getTransparency() != 1){
            if (pressedImage != null){
                boolean isPressed = false;
                for (MouseButton button : canTriggerButtons){
                    if (mouse.isMouseButtonPressed(button)){
                        isPressed = true;
                    }
                }
                if (isPressed){
                    data = pressedImage;
                }
            }
            if (data == defaultImage && hoveredImage != null){
                data = hoveredImage;
            }
            render.setCursorShapeInThisFrame(CursorShape.HAND);
        }
        renderImage(render, data, realPosition, realPositionMax, realSize, backgroundColor);
    }


    @Override
    protected void onMouseMoveEvent(MouseMoveEvent event){
        if (isMouseInRange() && getTransparency() != 1){
            event.cancel();
        }
    }
    @Override
    protected void onMouseButtonEvent(MouseButtonEvent event){
        if (isMouseInRange() && getTransparency() != 1){
            event.cancel();
        }
    }

    public ImageButton setDefaultImage(ImageData defaultImage){
        this.defaultImage = defaultImage;
        sendSignal(SignalType.SET_DEFAULT_IMAGE, defaultImage);
        return this;
    }
    public ImageButton setPressedImage(ImageData pressedImage){
        this.pressedImage = pressedImage;
        sendSignal(SignalType.SET_PRESSED_IMAGE, pressedImage);
        return this;
    }
    public ImageButton setHoveredImage(ImageData hoveredImage){
        this.hoveredImage = hoveredImage;
        sendSignal(SignalType.SET_HOVERED_IMAGE, hoveredImage);
        return this;
    }

    public ImageButton addCanTriggerButton(MouseButton... buttons){
        for (MouseButton button : buttons){
            addCanTriggerButton(button);
        }
        return this;
    }
    public ImageButton addCanTriggerButton(List<MouseButton> buttons){
        for (MouseButton button : buttons){
            addCanTriggerButton(button);
        }
        return this;
    }
    public ImageButton addCanTriggerButton(MouseButton button){
        if (!canTriggerButtons.contains(button)){
            canTriggerButtons.add(button);
            sendSignal(SignalType.ADD_CAN_TRIGGER_BUTTON);
        }
        return this;
    }
    public ImageButton removeCanTriggerButton(MouseButton button){
        if (canTriggerButtons.contains(button)){
            canTriggerButtons.remove(button);
            sendSignal(SignalType.REMOVE_CAN_TRIGGER_BUTTON);
        }
        return this;
    }
    public ImageButton removeCanTriggerButton(MouseButton... buttons){
        for (MouseButton button : buttons){
            removeCanTriggerButton(button);
        }
        return this;
    }
    public ImageButton removeCanTriggerButton(List<MouseButton> buttons){
        for (MouseButton button : buttons){
            removeCanTriggerButton(button);
        }
        return this;
    }

    public ImageData getDefaultImage(){
        return defaultImage;
    }
    public ImageData getPressedImage(){
        return pressedImage;
    }
    public ImageData getHoveredImage(){
        return hoveredImage;
    }
    public boolean isButtonCanTrigger(MouseButton button){
        return canTriggerButtons.contains(button);
    }

    public List<MouseButton> getCanTriggerButtons(){
        return Collections.unmodifiableList(canTriggerButtons);
    }

    public ImageButton(ImageData defaultImage, ScaleOffset position, ScaleOffset size){
        super(position, size);
        this.defaultImage = defaultImage;
    }
}
