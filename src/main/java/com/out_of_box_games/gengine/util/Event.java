package com.out_of_box_games.gengine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Event<TData> {

    private final List<Consumer<TData>> listeners;

    public Event() {
        listeners = new ArrayList<>();
    }

    public void invoke() {
        invoke(null);
    }

    public void invoke(TData data) {
        listeners.forEach(listener -> listener.accept(data));
    }

    public void addListener(Consumer<TData> listener) {
        listeners.add(listener);
    }

    public void removeListener(Consumer<TData> listener) {
        listeners.remove(listener);
    }
}
