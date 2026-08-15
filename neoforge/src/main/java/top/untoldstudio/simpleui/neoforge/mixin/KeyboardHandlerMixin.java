package top.untoldstudio.simpleui.neoforge.mixin;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.untoldstudio.simpleui.common.gui.Gui;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private void cancelKeyPress(long window, int action, KeyEvent event, CallbackInfo callbackInfo){
        if (Gui.getInstance() != null){
            top.untoldstudio.simpleui.common.event.KeyEvent customEvent = new top.untoldstudio.simpleui.common.event.KeyEvent(event, action);
            Gui.getInstance().onKeyEvent(customEvent);
            if (customEvent.isCanceled()){
                callbackInfo.cancel();
            }
        }
    }
}
