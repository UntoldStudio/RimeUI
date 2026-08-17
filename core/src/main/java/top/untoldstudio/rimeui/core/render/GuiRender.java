package top.untoldstudio.rimeui.core.render;

import top.untoldstudio.rimeui.core.data.RGBA;
import top.untoldstudio.rimeui.core.data.ScaleOffset;

public abstract class GuiRender {
    public void drawSquare(ScaleOffset min, ScaleOffset max, RGBA color){
        ScaleOffset pointA = min.addXOffset(max.getXPixel());
        ScaleOffset pointB = min.addYOffset(max.getYPixel());
        drawTriangle(min, pointA, pointB, color, color, color);
        drawTriangle(max, pointA, pointB, color, color, color);
    }

    public abstract void begin();
    public abstract void end();
    public void drawTriangle(ScaleOffset positionA, ScaleOffset positionB, ScaleOffset positionC, RGBA colorA, RGBA colorB, RGBA colorC){
        drawTriangle(positionA.getXPixel(), positionA.getYPixel(), positionB.getXPixel(), positionB.getYPixel(), positionC.getXPixel(), positionC.getYPixel(),
                colorA.red(), colorA.green(), colorA.blue(), colorA.alpha(),
                colorB.red(), colorB.green(), colorB.blue(), colorB.alpha(),
                colorB.red(), colorB.green(), colorB.blue(), colorB.alpha()
        );
    }
    public abstract void drawTriangle(int ax, int ay, int bx, int by, int cx, int cy,
                                      int aRed, int aGreen, int aBlue, int aAlpha,
                                      int bRed, int bGreen, int bBlue, int bAlpha,
                                      int cRed, int cGreen, int cBlue, int cAlpha);
    public abstract void submitBuffer();
}
