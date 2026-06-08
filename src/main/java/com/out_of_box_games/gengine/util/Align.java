package com.out_of_box_games.gengine.util;

import com.out_of_box_games.gengine.util.math.Vector2Int;

public enum Align {

    TOP_LEFT(-1, -1),
    TOP_CENTER(0, -1),
    TOP_RIGHT(1, -1),
    CENTER_LEFT(-1, 0),
    CENTER(0, 0),
    CENTER_RIGHT(1, 0),
    BOTTOM_LEFT(-1, 1),
    BOTTOM_CENTER(0, 1),
    BOTTOM_RIGHT(1, 1);

    private final Vector2Int value;

    Align(int x, int y) {
        value = new Vector2Int(x, y);
    }

    public Vector2Int getValue() {
        return value.copy();
    }
}
