package top.untoldstudio.rimeui.core.ui.node;

import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.render.GuiRender;
import top.untoldstudio.rimeui.core.signal.SignalType;
import top.untoldstudio.rimeui.core.texture.TextureManager;
import top.untoldstudio.rimeui.core.ui.AbstractFrame;

public final class ImageLabel extends AbstractFrame<ImageLabel> {
    private int textureId;

    @Override
    protected void render(GuiRender render){
        render.drawTexture(textureId, realPosition, realPositionMax, color);
    }

    public ImageLabel setTextureId(int textureId){
        this.textureId = textureId;
        sendSignal(SignalType.SET_TEXTURE_ID, textureId);
        return self;
    }
    public ImageLabel setTexturePath(String texturePath){
        setTextureId(TextureManager.loadImage(texturePath));
        return self;
    }

    public ImageLabel(int textureId, ScaleOffset position, ScaleOffset size) {
        super(position, size);
        this.textureId = textureId;
    }
    public ImageLabel(String imagePath, ScaleOffset position, ScaleOffset size){
        this(TextureManager.loadImage(imagePath), position, size);
    }
}
