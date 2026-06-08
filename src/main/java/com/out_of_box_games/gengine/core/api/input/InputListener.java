package com.out_of_box_games.gengine.core.api.input;

import com.out_of_box_games.gengine.util.math.Vector2Int;

public interface InputListener {

    default void onMouseMove(Vector2Int point) {
        // empty
    }

    default void onMousePress(MouseButton button, Vector2Int point) {
        // empty
    }

    default void onMouseRelease(MouseButton button, Vector2Int point) {
        // empty
    }
}
