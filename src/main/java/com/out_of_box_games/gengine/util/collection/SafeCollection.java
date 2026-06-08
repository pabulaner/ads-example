package com.out_of_box_games.gengine.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SafeCollection<TValue> implements Collection<TValue> {

    private class SafeIterator implements Iterator<TValue> {

        private int index;

        public SafeIterator() {
            index = 0;
            iterators.add(this);
        }

        @Override
        public boolean hasNext() {
            if (index < values.size()) {
                return true;
            }

            iterators.remove(this);
            return false;
        }

        @Override
        public TValue next() {
            return values.get(index++);
        }

        @Override
        public void remove() {
            SafeCollection.this.remove(index - 1);
        }
    }

    private final List<TValue> values;

    private final List<SafeIterator> iterators;

    public SafeCollection() {
        values = new ArrayList<>();
        iterators = new ArrayList<>();
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean contains(Object value) {
        return values.contains(value);
    }

    @Override
    public Iterator<TValue> iterator() {
        return new SafeIterator();
    }

    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @Override
    public <T> T[] toArray(T[] values) {
        return this.values.toArray(values);
    }

    @Override
    public boolean add(TValue value) {
        return values.add(value);
    }

    @Override
    public boolean remove(Object value) {
        int index = values.indexOf(value);

        if (index >= 0) {
            remove(index);
            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> values) {
        return this.values.containsAll(values);
    }

    @Override
    public boolean addAll(Collection<? extends TValue> values) {
        return this.values.addAll(values);
    }

    @Override
    public boolean removeAll(Collection<?> values) {
        final boolean[] result = { false };
        values.forEach(value -> result[0] |= remove(value));

        return result[0];
    }

    @Override
    public boolean retainAll(Collection<?> values) {
        return this.values.retainAll(values);
    }

    @Override
    public void clear() {
        values.clear();
        iterators.forEach(it -> it.index = 0);
    }

    private void remove(int index) {
        values.remove(index);

        iterators.forEach(it -> {
            if (it.index > index) {
                it.index -= 1;
            }
        });
    }
}
