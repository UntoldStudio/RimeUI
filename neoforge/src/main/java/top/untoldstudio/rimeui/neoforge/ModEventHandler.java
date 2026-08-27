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
package top.untoldstudio.rimeui.neoforge;

import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import top.untoldstudio.rimeui.core.RimeUI;
import top.untoldstudio.rimeui.core.data.ScaleOffset;
import top.untoldstudio.rimeui.core.texture.TextureManager;
import top.untoldstudio.rimeui.core.ui.node.ImageButton;

public class ModEventHandler {
    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            RimeUI.initOpenGL(minecraft.getWindow().handle());
            ImageButton button = new ImageButton(TextureManager.loadImageWithoutNiceGrid("/test1.png"), ScaleOffset.fromScale(0.5, 0.5), ScaleOffset.fromScale(0.5, 0.5))
                    .setHoveredImage(TextureManager.loadImageWithoutNiceGrid("/test2.png"))
                    .setPressedImage(TextureManager.loadImageWithoutNiceGrid("/test3.png"))
                    .setAnchor(0.5, 0.5)
                    ;
            RimeUI.getMainGui().addChild(button);
        });
    }
}
