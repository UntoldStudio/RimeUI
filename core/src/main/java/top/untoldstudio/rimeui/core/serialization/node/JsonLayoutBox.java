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
import top.untoldstudio.rimeui.core.ui.node.LayoutBox;

public abstract class JsonLayoutBox extends JsonAbstractFrame {
    @SerializedName("frame_start_spacing")
    private int frameStartSpacing;
    @SerializedName("frame_spacing")
    private int frameSpacing;
    @SerializedName("is_block_input")
    private boolean isBlockInput;

    public final LayoutBox<?> fillParentGuiNodeField(LayoutBox<?> box){
        box.setFrameStartSpacing(frameStartSpacing);
        box.setFrameSpacing(frameSpacing);
        box.setIsBlockInput(isBlockInput);
        return box;
    }

    public int getFrameStartSpacing(){
        return frameStartSpacing;
    }
    public int getFrameSpacing(){
        return frameSpacing;
    }
    public boolean isBlockInput(){
        return isBlockInput;
    }

    public void setFrameStartSpacing(int frameStartSpacing){
        this.frameStartSpacing = frameStartSpacing;
    }
    public void setFrameSpacing(int frameSpacing){
        this.frameSpacing = frameSpacing;
    }
    public void setIsBlockInput(boolean isBlockInput){
        this.isBlockInput = isBlockInput;
    }
}
