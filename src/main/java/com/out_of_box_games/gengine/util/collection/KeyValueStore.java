package com.out_of_box_games.gengine.util.collection;

import java.util.HashMap;
import java.util.Map;

public class KeyValueStore<TKey, TValue> {

    private final Map<TKey, TValue> data;

    public KeyValueStore() {
        data = new HashMap<>();
    }

    public boolean hasKey(TKey key) {
        return data.containsKey(key);
    }

    public boolean hasValue(TValue value) {
        return data.containsValue(value);
    }

    public TValue get(TKey key) {
        return data.get(key);
    }

    public TValue getOrDefault(TKey key, TValue defValue) {
        return data.getOrDefault(key, defValue);
    }

    public TValue set(TKey key, TValue value) {
        return data.put(key, value);
    }

    public TValue remove(TKey key) {
        return data.remove(key);
    }

    public void clear() {
        data.clear();
    }
}
