package com.out_of_box_games.gengine.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Registry<TType, TData extends TypeData<TData, TType>> {

    private final Map<TType, TData> entries;

    @SafeVarargs
    public Registry(TData... entries) {
        this.entries = new HashMap<>();

        for (TData entry : entries) {
            this.entries.put(entry.getType(), entry);
        }
    }

    public TData get(TType type) {
        return getOrDefault(type, null);
    }

    public TData getOrDefault(TType type, TData defaultValue) {
        return entries.getOrDefault(type, defaultValue);
    }

    public Collection<TType> all() {
        return entries.keySet();
    }
}
