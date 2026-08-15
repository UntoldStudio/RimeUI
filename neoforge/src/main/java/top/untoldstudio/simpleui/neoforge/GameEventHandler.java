package top.untoldstudio.simpleui.neoforge;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientResourceLoadFinishedEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.untoldstudio.simpleui.common.event.MouseClickEvent;
import top.untoldstudio.simpleui.common.gui.Gui;

public final class GameEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameEventHandler.class);
    private static GameEventHandler instance;
    private final Gui gui;

    @SubscribeEvent
    public void beforeLogicalTick(ClientTickEvent.Pre event){
        gui.preLogicalTick();
    }
    @SubscribeEvent
    public void afterLogicalTick(ClientTickEvent.Post event){
        gui.tick();
    }
    @SubscribeEvent
    public void onMouseButtonInput(InputEvent.MouseButton.Pre event){
        gui.onMouseButtonEvent(new MouseClickEvent(event));
    }
    @SubscribeEvent
    public void onResourceReload(ClientResourceLoadFinishedEvent event){
        if (event.isInitial()){
            gui.init();
        } else {
            gui.reload();
        }
    }

    public GameEventHandler(Gui gui){
        instance = this;
        this.gui = gui;
        LOGGER.info("GameEventHandler was created");
    }
    public static GameEventHandler getInstance(){
        return instance;
    }
}
