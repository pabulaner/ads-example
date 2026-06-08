package com.out_of_box_games.gengine.world.component;

import com.out_of_box_games.gengine.util.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class PathComponent extends TransformComponent {

    private List<Vector2> points;

    public PathComponent() {
        points = new ArrayList<>();
    }

    public Vector2 sample(float distance) {
        for (int i = 0; i < points.size() - 1; i++) {
            Vector2 first = points.get(i);
            Vector2 second = points.get(i + 1);
            Vector2 direction = second.copy().sub(first);
            float length = direction.length();

            if (length >= distance) {
                return direction.div(length)
                        .mul(distance)
                        .add(first);
            } else {
                distance -= length;
            }
        }

        return points.isEmpty()
                ? Vector2.zero()
                : points.get(points.size() - 1).copy();
    }

    public float length() {
        float length = 0.0f;

        for (int i = 0; i < points.size() - 1; i++) {
            Vector2 first = points.get(i);
            Vector2 second = points.get(i + 1);

            length += second.copy()
                    .sub(first)
                    .length();
        }

        return length;
    }

    public List<Vector2> getPoints() {
        return points;
    }

    public void setPoints(List<Vector2> points) {
        this.points = points;
    }
}
