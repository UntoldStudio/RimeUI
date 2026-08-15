package top.untoldstudio.simpleui.common.gui.node;

import top.untoldstudio.simpleui.common.core.LayoutVector2;
import top.untoldstudio.simpleui.common.gui.GuiRender;
import top.untoldstudio.simpleui.common.image.ImageData;
import top.untoldstudio.simpleui.common.image.ImageManager;

import static top.untoldstudio.simpleui.common.signal.SignalType.*;

/**
 * 这是一个可以点，可以悬停的图片
 */
public class ImageButton extends Button implements ImageBase {
    private ImageData defaultData;
    private ImageData placedData;
    private ImageData clickData;

    @Override
    public void render(GuiRender render){
        ImageManager manager = ImageManager.getInstance();
        if (isMouseInRange){
            ImageData data;
            if (super.isClick()) {
                data = clickData;
            } else {
                data = placedData;
            }
            if (data == null){
                data = defaultData;
            }
            renderImage(render, data, manager.getTextureWidth(data.texturePath()), manager.getTextureHeight(data.texturePath()), color);
        } else {
            renderImage(render, defaultData, manager.getTextureWidth(defaultData.texturePath()), manager.getTextureHeight(defaultData.texturePath()), color);
        }
    }

    public void setClickTexture(ImageData data){
        if (this.clickData != data){
            clickData = data;
            sendSingle(SET_CLICK_TEXTURE);
        }
    }
    public void setPlacedTexture(ImageData data){
        if (this.placedData != data){
            placedData = data;
            sendSingle(SET_PLACED_TEXTURE);
        }
    }
    public void setDefaultTexture(ImageData data){
        if (this.defaultData != data){
            defaultData = data;
            sendSingle(SET_DEFAULT_TEXTURE);
        }
    }
    public void setTexture(ImageData defaultData, ImageData placedData, ImageData clickData){
        setDefaultTexture(defaultData);
        setPlacedTexture(placedData);
        setClickTexture(clickData);
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

    public ImageButton(ImageData defaultData, LayoutVector2 position, LayoutVector2 size) {
        super(position, size);
        this.defaultData = defaultData;
    }
    public ImageButton(ImageData defaultData, ImageData clickData, LayoutVector2 position, LayoutVector2 size) {
        super(position, size);
        this.defaultData = defaultData;
        this.clickData = clickData;
    }
    public ImageButton(ImageData defaultData, ImageData clickData, ImageData placedData, LayoutVector2 position, LayoutVector2 size) {
        super(position, size);
        this.defaultData = defaultData;
        this.clickData = clickData;
        this.placedData = placedData;
    }
}
