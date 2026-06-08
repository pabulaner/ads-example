package com.out_of_box_games.gengine.util.collection;

import java.util.stream.Stream;

public class Pair<TFirst, TSecond> {

    private TFirst first;

    private TSecond second;

    public Pair() {
        this(null, null);
    }

    public Pair(TFirst first, TSecond second) {
        this.first = first;
        this.second = second;
    }

    public static <TValue> Stream<TValue> stream(Pair<TValue, TValue> pair) {
        return Stream.of(pair.first, pair.second);
    }

    public TFirst getFirst() {
        return first;
    }

    public void setFirst(TFirst first) {
        this.first = first;
    }

    public TSecond getSecond() {
        return second;
    }

    public void setSecond(TSecond second) {
        this.second = second;
    }
}
