package com.out_of_box_games.gengine.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ClassFactory<TData, TClass> {

    private final Map<Class<?>, Function<TData, TClass>> factories;

    public ClassFactory() {
        factories = new HashMap<>();
    }

    public void addFactory(Class<? extends TClass> key, Supplier<TClass> factory) {
        factories.put(key, ignored -> factory.get());
    }

    public void addFactory(Class<? extends TClass> key, Function<TData, TClass> factory) {
        factories.put(key, factory);
    }

    public <TSubClass extends TClass> TSubClass create(Class<TSubClass> key) {
        return create(key, null);
    }

    @SuppressWarnings("unchecked")
    public <TSubClass extends TClass> TSubClass create(Class<TSubClass> key, TData data) {
        return (TSubClass) factories.get(key).apply(data);
    }
}
