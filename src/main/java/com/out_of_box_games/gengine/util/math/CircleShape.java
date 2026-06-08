package com.out_of_box_games.gengine.util.math;

public class CircleShape extends Shape {

    private float radius;

    private int pointCount;

    public CircleShape(float radius, int pointCount) {
        this.radius = radius;
        this.pointCount = pointCount;

        updateCircle();
    }

    private void updateCircle() {
        points = new Vector2[pointCount];
        float step = MathUtil.FULL_ANGLE / pointCount;

        for (int i = 0; i < pointCount; i++) {
            points[i] = new Vector2(
                    MathUtil.cos(step * i) * radius,
                    MathUtil.sin(step * i) * radius);
        }
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        updateCircle();
    }

    public int getPointCount() {
        return pointCount;
    }

    public void setPointCount(int pointCount) {
        this.pointCount = pointCount;
        updateCircle();
    }
}
