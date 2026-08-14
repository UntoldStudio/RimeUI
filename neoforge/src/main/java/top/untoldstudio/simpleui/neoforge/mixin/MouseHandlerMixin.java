package top.untoldstudio.simpleui.neoforge.mixin;

import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.untoldstudio.simpleui.neoforge.event.MouseScrollEvent;
import top.untoldstudio.simpleui.neoforge.gui.Gui;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long windowPointer, double xOffset, double yOffset, CallbackInfo callbackInfo){
        if (Gui.getInstance() != null) {
            MouseScrollEvent event = new MouseScrollEvent(xOffset, yOffset);
            Gui.getInstance().onMouseScrollEvent(event);
            if (event.isCanceled()){
                callbackInfo.cancel();
            }
        }
    }
}
