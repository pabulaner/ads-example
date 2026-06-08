package com.out_of_box_games.gengine.util.math;

import java.io.Serializable;

public class Vector2Int implements Serializable {

    public int x;

    public int y;

    public Vector2Int() {
        this(0);
    }

    public Vector2Int(int value) {
        this(value, value);
    }

    public Vector2Int(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2Int copy() {
        return new Vector2Int(x, y);
    }

    public static Vector2Int zero() {
        return new Vector2Int();
    }

    public static Vector2Int one() {
        return new Vector2Int(1);
    }

    public Vector2 toVector2() {
        return new Vector2(x, y);
    }

    @Override
    public String toString() {
        return String.format("{ x: %d, y: %d }", x, y);
    }
}
