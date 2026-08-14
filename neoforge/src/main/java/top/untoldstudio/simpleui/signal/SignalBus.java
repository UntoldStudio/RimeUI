package top.untoldstudio.simpleui.signal;

import top.untoldstudio.simpleui.gui.GuiNode;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class SignalBus {
    private final Map<SignalType, List<Runnable>> callbacks = new ConcurrentHashMap<>();

    public void send(GuiNode node, SignalType type){
        List<Runnable> listeners = callbacks.get(type);
        if (listeners != null) {
            for (Runnable runnable : callbacks.get(type)){
                runnable.run();
            }
        }
        GlobalSignalBus.getInstance().send(type, node);
    }

    public void register(SignalType type, Runnable runnable){
        callbacks.computeIfAbsent(type, singleType -> new CopyOnWriteArrayList<>()).add(runnable);
    }
    public void unregister(SignalType type, Runnable runnable){
        if (callbacks.containsKey(type)){
            callbacks.get(type).remove(runnable);
        }
    }
}
