package top.untoldstudio.simpleui.neoforge;

import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public final class Tools {
    public static ResourceLocation getResourceLocationBySimpleUINameSpace(String path){
        return ResourceLocation.fromNamespaceAndPath(SimpleUI.MOD_ID, path);
    }
    public static ResourceLocation getResourceLocationByVanillaNameSpace(String path){
        return ResourceLocation.withDefaultNamespace(path);
    }
    public static Style getCustomFontStyle(ResourceLocation fontJsonPath){
        return Style.EMPTY.withFont(new FontDescription.Resource(fontJsonPath));
    }
}
