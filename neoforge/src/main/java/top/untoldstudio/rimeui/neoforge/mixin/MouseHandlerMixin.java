/*
 * Copyright 2026 Untold Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.untoldstudio.rimeui.neoforge.mixin;

import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.untoldstudio.rimeui.core.event.MouseMoveEvent;
import top.untoldstudio.rimeui.core.event.InputEventListener;
import top.untoldstudio.rimeui.core.event.MouseScrollEvent;
import top.untoldstudio.rimeui.core.ui.MainUi;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Shadow
    private double xpos;
    @Shadow
    private double ypos;
    @Shadow
    private double accumulatedDX;
    @Shadow
    private double accumulatedDY;

    @Inject(
            method = "handleAccumulatedMovement",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onHandleAccumulatedMovement(CallbackInfo callbackInfo) {
        if (MainUi.getInstance() != null){
            MouseMoveEvent event = new MouseMoveEvent(xpos, ypos);
            InputEventListener.onMouseMoveEvent(event);
            if (event.isCancelled()) {
                callbackInfo.cancel();
                this.accumulatedDX = 0.0;
                this.accumulatedDY = 0.0;
            }
        }
    }

    @Inject(
            method = "onScroll",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onScroll(long window, double xOffset, double yOffset, CallbackInfo callbackInfo) {
        if (MainUi.getInstance() != null) {
            MouseScrollEvent event = InputEventListener.onMouseScrollEvent(xOffset, yOffset);
            if (event.isCancelled()) {
                callbackInfo.cancel();
            }
        }
    }
}
