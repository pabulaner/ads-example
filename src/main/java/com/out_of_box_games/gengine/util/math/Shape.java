package com.out_of_box_games.gengine.util.math;

import java.util.Arrays;

public abstract class Shape {

    protected Vector2[] points;

    public Shape() {
        points = null;
    }

    public Vector2[] getPoints() {
        return points;
    }

    public Vector2[] getPoints(Transform transform) {
        return Arrays.stream(points)
                .map(Vector2::copy)
                .map(transform::applyTo)
                .toArray(Vector2[]::new);
    }
}
