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
package top.untoldstudio.rimeui.core.data;

import com.google.gson.annotations.SerializedName;
import top.untoldstudio.rimeui.core.MathTool;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;
import top.untoldstudio.rimeui.core.ui.MainGui;

public record ScaleOffset(
        @SerializedName("x_scale")
        double xScale,
        @SerializedName("x_offset")
        int xOffset,
        @SerializedName("y_scale")
        double yScale,
        @SerializedName("y_offset")
        int yOffset
) {
    public static final ScaleOffset ZERO = new ScaleOffset(0, 0, 0, 0);

    public ScaleOffset withScale(double xScale, double yScale){
        return new ScaleOffset(xScale, xOffset, yScale, yOffset);
    }
    public ScaleOffset withOffset(int xOffset, int yOffset){
        return new ScaleOffset(xScale, xOffset, yScale, yOffset);
    }
    public ScaleOffset withXScale(double xScale){
        return withScale(xScale, yScale);
    }
    public ScaleOffset withYScale(double yScale){
        return withScale(xScale, yScale);
    }
    public ScaleOffset withXOffset(int xOffset){
        return withOffset(xOffset, yOffset);
    }
    public ScaleOffset withYOffset(int yOffset){
        return withOffset(xOffset, yOffset);
    }

    public static ScaleOffset fromScale(double xScale, double yScale) {
        return new ScaleOffset(xScale, 0, yScale, 0);
    }
    public static ScaleOffset fromOffset(int xOffset, int yOffset) {
        return new ScaleOffset(0, xOffset, 0, yOffset);
    }

    public ScaleOffset add(double xScale, int xOffset, double yScale, int yOffset){
        return new ScaleOffset(this.xScale + xScale, this.xOffset + xOffset, this.yScale + yScale, this.yOffset + yOffset);
    }
    public ScaleOffset addScale(double xScale, double yScale){
        return add(xScale, 0, yScale, 0);
    }
    public ScaleOffset addXScale(double xScale){
        return addScale(xScale, 0);
    }
    public ScaleOffset addYScale(double yScale){
        return addScale(0, yScale);
    }
    public ScaleOffset addOffset(int xOffset, int yOffset){
        return add(0, xOffset, 0, yOffset);
    }
    public ScaleOffset addXOffset(int xOffset){
        return addOffset(xOffset, 0);
    }
    public ScaleOffset addYOffset(int yOffset){
        return addOffset(0, yOffset);
    }
    public ScaleOffset add(ScaleOffset other){
        return add(other.xScale, other.xOffset, other.yScale, other.yOffset);
    }
    public ScaleOffset subScale(double xScale, double yScale){
        return sub(xScale, 0, yScale, 0);
    }
    public ScaleOffset subXScale(double xScale){
        return subScale(xScale, 0);
    }
    public ScaleOffset subYScale(double yScale){
        return subScale(0, yScale);
    }
    public ScaleOffset subOffset(int xOffset, int yOffset){
        return sub(0, xOffset, 0, yOffset);
    }
    public ScaleOffset subXOffset(int xOffset){
        return subOffset(xOffset, 0);
    }
    public ScaleOffset subYOffset(int yOffset){
        return subOffset(0, yOffset);
    }
    public ScaleOffset sub(ScaleOffset other){
        return sub(other.xScale, other.xOffset, other.yScale, other.yOffset);
    }
    public ScaleOffset sub(double xScale, int xOffset, double yScale, int yOffset){
        return new ScaleOffset(this.xScale - xScale, this.xOffset - xOffset, this.yScale - yScale, this.yOffset - yOffset);
    }

    public int getScaleXPixelInWindow(){
        return MathTool.round(MainGui.getInstance().getWindowWidth() * xScale);
    }
    public double getScaledXInWindow(){
        return (double) getXPixelInWindow() / (double) MainGui.getInstance().getWindowWidth();
    }
    public double getScaledYInWindow(){
        return (double) getYPixelInWindow() / (double) MainGui.getInstance().getWindowHeight();
    }
    public int getXPixelInWindow(){
        return getScaleXPixelInWindow() + xOffset;
    }
    public int getScaleYPixelInWindow(){
        return MathTool.round(MainGui.getInstance().getWindowHeight() * yScale);
    }
    public int getYPixelInWindow(){
        return getScaleYPixelInWindow() + yOffset;
    }

    public int getScaleXPixelInWindow(AbstractFrame<?> frame) {
        return MathTool.round(frame.getRealSize().getXPixelInWindow() * xScale);
    }
    public int getScaleYPixelInWindow(AbstractFrame<?> frame) {
        return MathTool.round(frame.getRealSize().getYPixelInWindow() * yScale);
    }
    public int getXPixelInWindow(AbstractFrame<?> frame) {
        return getScaleXPixelInWindow(frame) + xOffset;
    }
    public int getYPixelInWindow(AbstractFrame<?> frame) {
        return getScaleYPixelInWindow(frame) + yOffset;
    }
    public double getScaledXInWindow(AbstractFrame<?> frame) {
        return (double) getXPixelInWindow(frame) / frame.getRealSize().getXPixelInWindow();
    }
    public double getScaledYInWindow(AbstractFrame<?> frame) {
        return (double) getYPixelInWindow(frame) / frame.getRealSize().getYPixelInWindow();
    }
}
