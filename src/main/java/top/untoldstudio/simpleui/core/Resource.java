package top.untoldstudio.simpleui.core;

import net.minecraft.resources.ResourceLocation;
import top.untoldstudio.simpleui.SimpleUI;

public final class Resource {
    public static ResourceLocation getResourceLocationBySimpleUINameSpace(String path){
        return ResourceLocation.fromNamespaceAndPath(SimpleUI.MOD_ID, path);
    }
    public static ResourceLocation getResourceLocationByVanillaNameSpace(String path){
        return ResourceLocation.withDefaultNamespace(path);
    }
}
