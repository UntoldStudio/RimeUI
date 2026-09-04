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

import top.untoldstudio.rimeui.core.data.HorizontalAlignment;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.data.VerticalAlignment;
import top.untoldstudio.rimeui.core.font.Font;
import top.untoldstudio.rimeui.core.ui.GuiInterface;

public interface TextDisplayable extends GuiInterface {
    default ScaleOffset operationTextPosition(Font font, String text, int fontSize, double italicSlant, int boldStrength, ScaleOffset realPosition, ScaleOffset realPositionMax, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment){
        int stringWidth = font.getStringWidth(text, fontSize, italicSlant, boldStrength);
        int stringHeight = font.getStringHeight(text, fontSize, italicSlant, boldStrength);
        int horizontalAlignmentPixel = switch (horizontalAlignment) {
            case HorizontalAlignment.LEFT -> realPosition.getXPixelInParent();
            case HorizontalAlignment.RIGHT -> realPositionMax.getXPixelInParent() - stringWidth;
            case HorizontalAlignment.CENTER -> (realPosition.getXPixelInParent() + realPositionMax.getXPixelInParent() - stringWidth) / 2;
        };
        int verticalAlignmentPixel = switch (verticalAlignment) {
            case VerticalAlignment.TOP -> realPosition.getYPixelInParent();
            case VerticalAlignment.BOTTOM -> realPositionMax.getYPixelInParent() - stringHeight;
            case VerticalAlignment.CENTER -> (realPosition.getYPixelInParent() + realPositionMax.getYPixelInParent() - stringHeight) / 2;
        };
        return ScaleOffset.fromOffset(horizontalAlignmentPixel, verticalAlignmentPixel);
    }
}
