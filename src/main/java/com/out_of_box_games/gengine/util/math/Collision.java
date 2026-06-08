package com.out_of_box_games.gengine.util.math;

public class Collision {

    public static boolean test(Vector2[] convex, Vector2 point) {
        if (convex.length < 3) {
            return false;
        }

        for (int i = 0; i < convex.length; i++) {
            Vector2 first = convex[i];
            Vector2 second = convex[(i + 1) % convex.length];
            Vector2 line = second.copy().sub(first);
            Vector2 normal = new Vector2(-line.y, +line.x);
            Vector2 delta = point.copy().sub(first);

            if (normal.dotProduct(delta) > 0.0f) {
                return false;
            }
        }

        return true;
    }

    public static boolean test(Vector2[] convex1, Vector2[] convex2) {
        for (Vector2 point : convex1) {
            if (test(convex2, point)) {
                return true;
            }
        }

        return false;
    }
}
