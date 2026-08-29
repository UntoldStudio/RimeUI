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

import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.serialization.node.JsonHBox;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;

public final class HBox extends LayoutBox<HBox> {
    @Override
    protected void render(GuiRender render, double delta){
        super.renderFrameDefaultBackground(render, delta);
    }

    @Override
    public HBox clone(){
        HBox box = new HBox(position, size);
        return super.fillFieldForClone(box);
    }

    @Override
    public JsonHBox toJsonNodeTree(){
        JsonHBox box = new JsonHBox();
        super.fillParentClassJsonNode(box);
        return box;
    }

    @Override
    public void sortFrame(){
        int currentOffset = frameStartSpacing;

        for (AbstractFrame<?> frame : sortedFrameList){
            frame.setPosition(ScaleOffset.fromOffset(currentOffset, frame.getPosition().getYPixelInWindow(this)));
            currentOffset += frame.getSize().getXPixelInWindow(this) + frameSpacing;
        }
    }

    public HBox(ScaleOffset position, ScaleOffset size){
        super(position, size);
    }
}
