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

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.cursor.CursorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.untoldstudio.rimeui.core.event.InputEventListener;

@Mixin(Window.class)
public abstract class WindowMixin {
    @Inject(method = "selectCursor", at = @At(value = "HEAD"))
    private void beforeSelectCursor(CursorType type, CallbackInfo callbackInfo){
        try {
            java.lang.reflect.Field field = CursorType.class.getDeclaredField("handle");
            field.setAccessible(true);
            long handle = (long) field.get(type);
            InputEventListener.externalSettingCursor(handle);
        } catch (NoSuchFieldException | IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }
}
