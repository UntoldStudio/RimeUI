package top.untoldstudio.simpleui.image;

import com.mojang.blaze3d.textures.GpuTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import top.untoldstudio.simpleui.core.ARGB;
import top.untoldstudio.simpleui.core.LayoutVector2;
import top.untoldstudio.simpleui.gui.GuiRender;

public final class ImageTool {
    public static void renderImage(GuiRender render, ImageData data, LayoutVector2 realPosition, LayoutVector2 realSize, int width, int height, ARGB color){
        if (data.isNiceGridTexture()){
            render.drawNiceGridTexture(RenderPipelines.GUI_TEXTURED, data.texture(),
                    realPosition, realSize, width, height,
                    data.left(), data.top(), data.right(), data.bottom(), color
            );
        } else {
            render.drawTexture(RenderPipelines.GUI_TEXTURED, data.texture(), realPosition, realSize, width, height);
        }
    }
    public static GpuTexture genTexture(ResourceLocation location){
        return Minecraft.getInstance().getTextureManager().getTexture(location).getTexture();
    }
}
