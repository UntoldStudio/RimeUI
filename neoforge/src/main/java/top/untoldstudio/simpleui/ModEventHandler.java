package top.untoldstudio.simpleui;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ModEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModEventHandler.class);
    private static ModEventHandler instance;

    /**
     * 仅仅是为了不让NeoForge看到一个类中没有任何监听方法但是注册了监听器而崩溃
     */
    @SubscribeEvent
    public void thisIsOnlyPreventCollapse(FMLLoadCompleteEvent event){
        //ignore
    }

    public ModEventHandler(){
        instance = this;
        LOGGER.info("ModEventHandler was created");
    }
    public static ModEventHandler getInstance(){
        return instance;
    }
}
