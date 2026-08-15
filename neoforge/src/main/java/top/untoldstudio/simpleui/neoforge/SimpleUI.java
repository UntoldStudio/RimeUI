package top.untoldstudio.simpleui.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.untoldstudio.simpleui.common.Platform;
import top.untoldstudio.simpleui.common.gui.Gui;

@Mod(value = SimpleUI.MOD_ID, dist = Dist.CLIENT)
public final class SimpleUI {
    public static final String MOD_ID = "simpleui";
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleUI.class);
    private static SimpleUI instance;
    private final Gui gui = new Gui();
    private final GameEventHandler gameEventHandler = new GameEventHandler(gui);
    private final ModEventHandler modEventHandler = new ModEventHandler();

    public SimpleUI(IEventBus modEventBus, ModContainer container) {
        LOGGER.info("Initializing {}", MOD_ID);
        instance = this;
        Platform.setPlatformProvider(new NeoForgePlatformProvider());
        modEventBus.register(modEventHandler);
        LOGGER.info("Mod Event Handler was registered");
        NeoForge.EVENT_BUS.register(gameEventHandler);
        LOGGER.info("Game Event Handler was registered");
    }

    public ModEventHandler getModEventHandler(){
        return modEventHandler;
    }
    public GameEventHandler getGameEventHandler(){
        return gameEventHandler;
    }
    public Gui getMainGui(){
        return gui;
    }

    public static SimpleUI getInstance(){
        return instance;
    }
}
