package top.untoldstudio.simpleui.neoforge;

import net.minecraft.client.Minecraft;
import top.untoldstudio.simpleui.common.PlatformProvider;

public class NeoForgePlatformProvider implements PlatformProvider {
    @Override
    public int getWindowWidth() {
        return Minecraft.getInstance().getWindow().getGuiScaledWidth();
    }
    @Override
    public int getWindowHeight() {
        return Minecraft.getInstance().getWindow().getGuiScaledHeight();
    }
}
