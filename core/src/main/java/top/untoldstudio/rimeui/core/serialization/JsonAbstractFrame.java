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
import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;

public abstract class JsonAbstractFrame extends JsonGuiNode {
    protected ScaleOffset position;
    protected ScaleOffset size;
    @SerializedName("background_color")
    private RGBA backgroundColor;
    @SerializedName("x_anchor")
    private double xAnchor;
    @SerializedName("y_anchor")
    private double yAnchor;
    @SerializedName("is_clip_children")
    private boolean isClipChildren;
    @SerializedName("layout_order")
    private int layoutOrder;

    public final AbstractFrame<?> fillParentGuiNodeField(AbstractFrame<?> frame){
        frame.setPosition(position);
        frame.setSize(size);
        frame.setBackgroundColor(backgroundColor);
        frame.setXAnchor(xAnchor);
        frame.setYAnchor(yAnchor);
        frame.setClipChildren(isClipChildren);
        frame.setLayoutOrder(layoutOrder);
        super.fillParentGuiNodeField(frame);
        return frame;
    }

    public ScaleOffset getPosition() {
        return position;
    }
    public ScaleOffset getSize() {
        return size;
    }
    public RGBA getBackgroundColor() {
        return backgroundColor;
    }
    public double getXAnchor() {
        return xAnchor;
    }
    public double getYAnchor() {
        return yAnchor;
    }
    public boolean isClipChildren() {
        return isClipChildren;
    }
    public int getLayoutOrder() {
        return layoutOrder;
    }

    public void setPosition(ScaleOffset position) {
        this.position = position;
    }
    public void setSize(ScaleOffset size) {
        this.size = size;
    }
    public void setBackgroundColor(RGBA backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public void setXAnchor(double xAnchor) {
        this.xAnchor = xAnchor;
    }
    public void setYAnchor(double yAnchor) {
        this.yAnchor = yAnchor;
    }
    public void setClipChildren(boolean isClipChildren) {
        this.isClipChildren = isClipChildren;
    }
    public void setLayoutOrder(int layoutOrder) {
        this.layoutOrder = layoutOrder;
    }
}