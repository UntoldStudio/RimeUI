package top.untoldstudio.simpleui.neoforge.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.untoldstudio.simpleui.common.gui.Gui;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "render", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Gui;renderDeferredSubtitles()V",
            shift = At.Shift.AFTER
    ))
    private void afterGuiRender(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo callbackInfo, @Local(ordinal = 0) GuiGraphics guiGraphics){
        if (Gui.getInstance() != null){
            Gui.getInstance().render(guiGraphics);
        }
    }
}
