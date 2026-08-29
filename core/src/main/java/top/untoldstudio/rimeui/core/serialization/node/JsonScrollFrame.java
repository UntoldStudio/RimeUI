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
import top.untoldstudio.rimeui.core.serialization.JsonAbstractFrame;
import top.untoldstudio.rimeui.core.ui.node.ScrollFrame;

public final class JsonScrollFrame extends JsonAbstractFrame {
    @SerializedName("scroll_bar")
    private JsonImageButton scrollBar;
    @SerializedName("can_scroll")
    private boolean canScroll;
    @SerializedName("scroll_scale")
    private double scrollScale;
    @SerializedName("scroll_progress")
    private double scrollProgress;
    @SerializedName("scroll_speed")
    private double scrollSpeed;
    @SerializedName("is_block_input")
    private boolean isBlockInput;

    @Override
    public ScrollFrame toGuiNode(){
        ScrollFrame frame = new ScrollFrame(position, size);
        frame.setCanScroll(canScroll);
        frame.setScrollScale(scrollScale);
        frame.setScrollProgress(scrollProgress);
        frame.setScrollSpeed(scrollSpeed);
        frame.setIsBlockInput(isBlockInput);
        frame.setScrollBarWidth(scrollBar.getSize().getXPixelInWindow());
        frame.setScrollBarTexture(scrollBar.getDefaultImageData().toImageData());
        frame.setScrollBarVisible(scrollBar.isVisible());
        super.fillParentGuiNodeField(frame);
        return frame;
    }

    public JsonImageButton getScrollBar() {
        return scrollBar;
    }
    public double getScrollProgress() {
        return scrollProgress;
    }
    public boolean canScroll() {
        return canScroll;
    }
    public double getScrollSpeed() {
        return scrollSpeed;
    }
    public boolean isBlockInput() {
        return isBlockInput;
    }
    public double getScrollScale() {
        return scrollScale;
    }

    public void setScrollBar(JsonImageButton scrollBar) {
        this.scrollBar = scrollBar;
    }
    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }
    public void setScrollProgress(double scrollProgress) {
        this.scrollProgress = scrollProgress;
    }
    public void setScrollSpeed(double scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }
    public void setIsBlockInput(boolean isBlockInput) {
        this.isBlockInput = isBlockInput;
    }
    public void setScrollScale(double scrollScale) {
        this.scrollScale = scrollScale;
    }
}
