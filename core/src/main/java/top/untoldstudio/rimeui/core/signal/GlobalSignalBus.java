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
package top.untoldstudio.rimeui.core.signal;

import top.untoldstudio.rimeui.core.ui.GuiInterface;
import top.untoldstudio.rimeui.core.ui.GuiNode;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class GlobalSignalBus {
    private static final GlobalSignalBus instance = new GlobalSignalBus();

    private final Map<SignalType, List<Method>> staticMethods = new ConcurrentHashMap<>();
    private final Map<SignalType, List<InstanceMethod>> instanceMethods = new ConcurrentHashMap<>();
    private final Map<SignalType, List<Callback>> callbacks = new ConcurrentHashMap<>();

    private record InstanceMethod(Method method, SignalHandler instance) {}
    @FunctionalInterface
    public interface Callback{
        void accept(GuiNode<?> node);
    }

    public void send(SignalType type, GuiNode<?> node) {
        if (node == null){
            throw new NullPointerException("The node cannot be null");
        }
        for (Method method : staticMethods.getOrDefault(type, Collections.emptyList())){
            if (method.getParameterTypes()[0].isInstance(node)) {
                try {
                    method.invoke(null, node);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (InstanceMethod instanceMethod : instanceMethods.getOrDefault(type, Collections.emptyList())){
            if (instanceMethod.method.getParameterTypes()[0].isInstance(node)) {
                try {
                    instanceMethod.method.invoke(instanceMethod.instance, node);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (Callback callback : callbacks.getOrDefault(type, Collections.emptyList())){
            callback.accept(node);
        }
    }

    public Callback register(SignalType type, Callback callback){
        callbacks.computeIfAbsent(type, singleType -> new CopyOnWriteArrayList<>()).add(callback);
        return callback;
    }
    public void register(Class<? extends SignalHandler> clazz){
        for (Method method : clazz.getDeclaredMethods()) {
            SubscribeSignal signal = method.getAnnotation(SubscribeSignal.class);
            if (signal != null && Modifier.isStatic(method.getModifiers())) {
                validateMethod(method);
                staticMethods.computeIfAbsent(signal.getSignalType(), type -> new CopyOnWriteArrayList<>()).add(method);
            }
        }
    }
    public void unregister(SignalType type, Callback callback){
        if (callbacks.containsKey(type)){
            callbacks.get(type).remove(callback);
        }
    }
    public void unregister(Class<? extends SignalHandler> clazz){
        for (Map.Entry<SignalType, List<Method>> entry : staticMethods.entrySet()) {
            entry.getValue().removeIf(method -> method.getDeclaringClass().equals(clazz));
        }
    }
    public void unregister(SignalHandler handler){
        for (Map.Entry<SignalType, List<InstanceMethod>> entry : instanceMethods.entrySet()) {
            entry.getValue().removeIf(method -> method.instance.equals(handler));
        }
    }
    public SignalHandler register(SignalHandler handler){
        for (Method method : handler.getClass().getDeclaredMethods()) {
            SubscribeSignal signal = method.getAnnotation(SubscribeSignal.class);
            if (signal != null && !Modifier.isStatic(method.getModifiers())) {
                validateMethod(method);
                instanceMethods.computeIfAbsent(signal.getSignalType(), type -> new CopyOnWriteArrayList<>()).add(new InstanceMethod(method, handler));
            }
        }
        return handler;
    }

    private void validateMethod(Method method) {
        if (method.getParameterCount() != 1) {
            throw new IllegalArgumentException("Subscriber method must have exactly one parameter: " + method);
        }
        Class<?> paramType = method.getParameterTypes()[0];
        if (!GuiNode.class.isAssignableFrom(paramType) && !(paramType.isInterface() && GuiInterface.class.isAssignableFrom(paramType))) {
            throw new IllegalArgumentException("Invalid subscriber method parameter type: " + method);
        }
    }

    public static GlobalSignalBus getInstance(){
        return instance;
    }
}
