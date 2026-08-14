package top.untoldstudio.simpleui.neoforge.gui.node;

import com.mojang.blaze3d.textures.GpuTexture;
import net.minecraft.resources.ResourceLocation;
import top.untoldstudio.simpleui.common.core.ARGB;
import top.untoldstudio.simpleui.common.core.LayoutVector2;
import top.untoldstudio.simpleui.neoforge.gui.GuiRender;
import top.untoldstudio.simpleui.neoforge.image.ImageData;
import top.untoldstudio.simpleui.neoforge.image.ImageTool;

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
