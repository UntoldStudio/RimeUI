package top.untoldstudio.simpleui;

import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public final class Tools {
    public static Style getCustomFontStyle(ResourceLocation fontJsonPath){
        return Style.EMPTY.withFont(new FontDescription.Resource(fontJsonPath));
    }
}
