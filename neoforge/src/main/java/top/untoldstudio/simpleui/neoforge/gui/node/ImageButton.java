package top.untoldstudio.simpleui.neoforge.gui.node;

import com.mojang.blaze3d.textures.GpuTexture;
import top.untoldstudio.simpleui.common.core.LayoutVector2;
import top.untoldstudio.simpleui.neoforge.gui.GuiRender;
import top.untoldstudio.simpleui.neoforge.image.ImageData;

import static top.untoldstudio.simpleui.neoforge.signal.SignalType.*;

/**
 * 这是一个可以点，可以悬停的图片
 */
public class ImageButton extends Button implements ImageBase {
    private ImageData defaultData;
    private GpuTexture defaultTexture;
    private ImageData placedData;
    private GpuTexture placedTexture;
    private ImageData clickData;
    private GpuTexture clickTexture;

    @Override
    public void render(GuiRender render){
        if (isMouseInRange){
            ImageData data;
            GpuTexture texture;
            if (super.isClick()) {
                data = clickData;
                texture = clickTexture;
            } else {
                data = placedData;
                texture = placedTexture;
            }
            if (data == null){
                data = defaultData;
                texture = defaultTexture;
            }
            renderImage(render, data, texture.getWidth(0), texture.getHeight(0), color);
        } else {
            renderImage(render, defaultData, defaultTexture.getWidth(0), defaultTexture.getHeight(0), color);
        }
    }

    @Override
    public void reload(){
        updateTexture();
    }

    public void setClickTexture(ImageData data){
        if (this.clickData != data){
            clickData = data;
            sendSingle(SET_CLICK_TEXTURE);
            updateTexture();
        }
    }
    public void setPlacedTexture(ImageData data){
        if (this.placedData != data){
            placedData = data;
            sendSingle(SET_PLACED_TEXTURE);
            updateTexture();
        }
    }
    public void setDefaultTexture(ImageData data){
        if (this.defaultData != data){
            defaultData = data;
            sendSingle(SET_DEFAULT_TEXTURE);
            updateTexture();
        }
    }
    public void setTexture(ImageData defaultData, ImageData placedData, ImageData clickData){
        setDefaultTexture(defaultData);
        setPlacedTexture(placedData);
        setClickTexture(clickData);
        updateTexture();
    }
    public ImageData getDefaultTexture(){
        return defaultData;
    }
    public ImageData getPlacedTexture(){
        return placedData;
    }
    public ImageData getClickData(){
        return clickData;
    }

    private void updateTexture(){
        defaultTexture = genTexture(defaultData.texture());
        if (clickData != null){
            clickTexture = genTexture(clickData.texture());
        }
        if (placedData != null){
            placedTexture = genTexture(placedData.texture());
        }
        sendSingle(UPDATE_GPU_TEXTURE);
    }

    public ImageButton(ImageData defaultData, LayoutVector2 position, LayoutVector2 size) {
        super(position, size);
        this.defaultData = defaultData;
        updateTexture();
    }
    public ImageButton(ImageData defaultData, ImageData clickData, LayoutVector2 position, LayoutVector2 size) {
        super(position, size);
        this.defaultData = defaultData;
        this.clickData = clickData;
        updateTexture();
    }
    public ImageButton(ImageData defaultData, ImageData clickData, ImageData placedData, LayoutVector2 position, LayoutVector2 size) {
        super(position, size);
        this.defaultData = defaultData;
        this.clickData = clickData;
        this.placedData = placedData;
        updateTexture();
    }
}
