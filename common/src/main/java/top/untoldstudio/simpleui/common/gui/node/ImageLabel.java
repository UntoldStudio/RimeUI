package top.untoldstudio.simpleui.common.gui.node;

import top.untoldstudio.simpleui.common.core.ARGB;
import top.untoldstudio.simpleui.common.core.LayoutVector2;
import top.untoldstudio.simpleui.common.gui.GuiRender;
import top.untoldstudio.simpleui.common.image.ImageData;
import top.untoldstudio.simpleui.common.image.ImageManager;
import top.untoldstudio.simpleui.common.signal.SignalType;

/**
 * 仅仅渲染一个不带任何功能的图片
 */
public class ImageLabel extends Frame implements ImageBase {
    private ImageData data;

    @Override
    public void render(GuiRender render){
        renderImage(render, data, ImageManager.getInstance().getTextureWidth(data.texturePath()), ImageManager.getInstance().getTextureHeight(data.texturePath()), color);
    }

    public void setTexture(ImageData data){
        if (this.data != data){
            this.data = data;
            sendSingle(SignalType.SET_DEFAULT_TEXTURE);
        }
    }

    public ImageLabel(ImageData data, LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor, ARGB color){
        super(position, size, xAnchor, yAnchor, color);
        this.data = data;
    }
    public ImageLabel(ImageData data, LayoutVector2 position, LayoutVector2 size){
        super(position, size);
        this.data = data;
    }
    public ImageLabel(ImageData data, LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor){
        super(position, size, xAnchor, yAnchor);
        this.data = data;
    }
    public ImageLabel(ImageData data, LayoutVector2 position, LayoutVector2 size, ARGB color){
        super(position, size, color);
        this.data = data;
    }
}
