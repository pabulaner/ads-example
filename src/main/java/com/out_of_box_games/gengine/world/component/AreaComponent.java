package com.out_of_box_games.gengine.world.component;

import com.out_of_box_games.gengine.core.api.input.InputListener;
import com.out_of_box_games.gengine.util.Event;
import com.out_of_box_games.gengine.util.math.Shape;

public class AreaComponent extends TransformComponent implements InputListener {

    private Shape shape;

    private boolean clickable;

    private final Event<Void> onClick;

    public AreaComponent() {
        shape = null;
        clickable = false;
        onClick = new Event<>();
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public Event<Void> onClick() {
        return onClick;
    }
}
