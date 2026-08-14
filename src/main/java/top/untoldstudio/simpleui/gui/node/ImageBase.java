package top.untoldstudio.simpleui.gui.node;

import com.mojang.blaze3d.textures.GpuTexture;
import net.minecraft.resources.ResourceLocation;
import top.untoldstudio.simpleui.core.ARGB;
import top.untoldstudio.simpleui.core.LayoutVector2;
import top.untoldstudio.simpleui.gui.GuiRender;
import top.untoldstudio.simpleui.image.ImageData;
import top.untoldstudio.simpleui.image.ImageTool;

public interface ImageBase extends GuiInterface {
    void render(GuiRender render);
    LayoutVector2 getRealPosition();
    LayoutVector2 getRealSize();
    default void renderImage(GuiRender render, ImageData data, int width, int height, ARGB color){
        ImageTool.renderImage(render, data, getRealPosition(), getRealSize(), width, height, color);
    }
    default GpuTexture genTexture(ResourceLocation location){
        return ImageTool.genTexture(location);
    }
}
