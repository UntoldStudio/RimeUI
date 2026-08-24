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
package top.untoldstudio.rimeui.core.render;

import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.ScaleOffset;

public abstract class GuiRender {
    public void drawSquare(ScaleOffset min, ScaleOffset max, RGBA color){
        ScaleOffset pointA = min.withXOffset(max.getXPixel());
        ScaleOffset pointB = min.withYOffset(max.getYPixel());
        drawTriangle(min, pointA, pointB, color, color, color);
        drawTriangle(max, pointA, pointB, color, color, color);
    }

    public abstract void begin();
    public abstract void end();
    public abstract void saveContext();
    public abstract void restoreContext();
    public void drawTriangle(ScaleOffset positionA, ScaleOffset positionB, ScaleOffset positionC, RGBA colorA, RGBA colorB, RGBA colorC){
        drawTriangle(positionA.getXPixel(), positionA.getYPixel(), positionB.getXPixel(), positionB.getYPixel(), positionC.getXPixel(), positionC.getYPixel(),
                colorA.red(), colorA.green(), colorA.blue(), colorA.alpha(),
                colorB.red(), colorB.green(), colorB.blue(), colorB.alpha(),
                colorC.red(), colorC.green(), colorC.blue(), colorC.alpha()
        );
    }
    public void drawTexture(int textureId, ScaleOffset min, ScaleOffset max, RGBA color){
        int red = color.red();
        int green = color.green();
        int blue = color.blue();
        int alpha = color.alpha();
        drawTexture(textureId, min.getXPixel(), min.getYPixel(), max.getXPixel(), max.getYPixel(),
                red, green, blue, alpha,
                red, green, blue, alpha,
                red, green, blue, alpha,
                red, green, blue, alpha
        );
    }
    public abstract void drawTriangle(int ax, int ay, int bx, int by, int cx, int cy,
                                      int aRed, int aGreen, int aBlue, int aAlpha,
                                      int bRed, int bGreen, int bBlue, int bAlpha,
                                      int cRed, int cGreen, int cBlue, int cAlpha);
    public abstract void submitBuffer();
    public abstract void drawTexture(int textureId, int ax, int ay, int bx, int by,
                                   int aRed, int aGreen, int aBlue, int aAlpha,
                                   int bRed, int bGreen, int bBlue, int bAlpha,
                                   int cRed, int cGreen, int cBlue, int cAlpha,
                                   int dRed, int dGreen, int dBlue, int dAlpha
    );
}
