package com.feather.loader.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FeatherEvents {
    private static final List<Runnable> ON_INIT_LISTENERS = new ArrayList<>();
    private static final List<Consumer<String>> ON_CHAT_LISTENERS = new ArrayList<>();

    public static void subscribeInit(Runnable listener) {
        ON_INIT_LISTENERS.add(listener);
    }

    public static void subscribeChat(Consumer<String> listener) {
        ON_CHAT_LISTENERS.add(listener);
    }

    public static void postInit() {
        ON_INIT_LISTENERS.forEach(Runnable::run);
    }

    public static void postChat(String message) {
        ON_CHAT_LISTENERS.forEach(listener -> listener.accept(message));
    }
}
