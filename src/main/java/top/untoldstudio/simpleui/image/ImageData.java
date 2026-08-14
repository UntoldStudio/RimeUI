package top.untoldstudio.simpleui.image;

import net.minecraft.resources.ResourceLocation;

/**
 * 我推荐你们不要用这个主构造函数，用下面两个重载
 * @param isNiceGridTexture 是否是九宫格纹理
 * @param texture 纹理路径
 * @param left 左边框厚度
 * @param top 上边框厚度
 * @param right 右边框厚度
 * @param bottom 底边框厚度
 */
public record ImageData(boolean isNiceGridTexture, ResourceLocation texture, int left, int top, int right, int bottom) {
    public ImageData(ResourceLocation texture){
        this(false, texture, 0, 0, 0, 0);
    }
    public ImageData(ResourceLocation texture, int left, int top, int right, int bottom){
        this(true, texture, left, top, right, bottom);
    }
}
