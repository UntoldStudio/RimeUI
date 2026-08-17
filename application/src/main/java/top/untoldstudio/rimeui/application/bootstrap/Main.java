package top.untoldstudio.rimeui.application.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.untoldstudio.rimeui.application.ui.Application;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        new Application().start();
    }
}
