package com.out_of_box_games.gengine.util.math;

public class RectangleShape extends Shape {

    private Vector2 size;

    public RectangleShape(Vector2 size) {
        this.points = new Vector2[4];
        this.size = size;

        updateRect();
    }

    private void updateRect() {
        points[0] = new Vector2(-size.x, size.y).mul(0.5f);
        points[1] = new Vector2(size.x, size.y).mul(0.5f);
        points[2] = new Vector2(size.x, -size.y).mul(0.5f);
        points[3] = new Vector2(-size.x, -size.y).mul(0.5f);
    }

    public Vector2 getSize() {
        return size.copy();
    }

    public void setSize(Vector2 size) {
        this.size = size;
        updateRect();
    }
}
