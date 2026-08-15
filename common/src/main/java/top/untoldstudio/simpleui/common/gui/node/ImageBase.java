package top.untoldstudio.simpleui.common.gui.node;

import top.untoldstudio.simpleui.common.core.ARGB;
import top.untoldstudio.simpleui.common.core.LayoutVector2;
import top.untoldstudio.simpleui.common.gui.GuiRender;
import top.untoldstudio.simpleui.common.image.ImageData;

public interface ImageBase extends GuiInterface {
    void render(GuiRender render);
    LayoutVector2 getRealPosition();
    LayoutVector2 getRealSize();
    default void renderImage(GuiRender render, ImageData data, int width, int height, ARGB color){
        if (data.isNiceGridTexture()){
            render.drawNiceGridTexture(data.texturePath(), getRealPosition(), getRealSize(), width, height, data.left(), data.top(), data.right(), data.bottom(), color);
        } else {
            render.drawTexture(data.texturePath(), getRealPosition(), getRealSize(), width, height, color);
        }
    }
}
