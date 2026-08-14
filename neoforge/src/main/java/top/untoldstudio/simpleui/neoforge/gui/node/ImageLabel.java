package top.untoldstudio.simpleui.neoforge.gui.node;

import com.mojang.blaze3d.textures.GpuTexture;
import top.untoldstudio.simpleui.common.core.ARGB;
import top.untoldstudio.simpleui.common.core.LayoutVector2;
import top.untoldstudio.simpleui.neoforge.gui.GuiRender;
import top.untoldstudio.simpleui.neoforge.image.ImageData;
import top.untoldstudio.simpleui.neoforge.signal.SignalType;

/**
 * 仅仅渲染一个不带任何功能的图片
 */
public class ImageLabel extends Frame implements ImageBase {
    private GpuTexture texture;
    private ImageData data;

    @Override
    public void render(GuiRender render){
        renderImage(render, data, texture.getWidth(0), texture.getHeight(0), color);
    }

    public void setTexture(ImageData data){
        if (this.data != data){
            this.data = data;
            sendSingle(SignalType.SET_DEFAULT_TEXTURE);
            updateTexture();
        }
    }
    public int getTextureWidth(){
        return texture.getWidth(0);
    }
    public int getTextureHeight(){
        return texture.getHeight(0);
    }
    private void updateTexture(){
        texture = genTexture(data.texture());
        sendSingle(SignalType.UPDATE_GPU_TEXTURE);
    }

    public ImageLabel(ImageData data, LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor, ARGB color){
        super(position, size, xAnchor, yAnchor, color);
        this.data = data;
        updateTexture();
    }
    public ImageLabel(ImageData data, LayoutVector2 position, LayoutVector2 size){
        super(position, size);
        this.data = data;
        updateTexture();
    }
    public ImageLabel(ImageData data, LayoutVector2 position, LayoutVector2 size, double xAnchor, double yAnchor){
        super(position, size, xAnchor, yAnchor);
        this.data = data;
        updateTexture();
    }
    public ImageLabel(ImageData data, LayoutVector2 position, LayoutVector2 size, ARGB color){
        super(position, size, color);
        this.data = data;
        updateTexture();
    }
}
