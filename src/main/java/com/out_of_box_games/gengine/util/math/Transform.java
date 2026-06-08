package com.out_of_box_games.gengine.util.math;

public class Transform {

    private Vector2 translation;

    private float rotation;

    private Vector2 scale;

    public Transform() {
        this(Vector2.zero(), 0, Vector2.one());
    }

    public Transform(Vector2 translation, float rotation, Vector2 scale) {
        this.translation = translation;
        this.rotation = rotation;
        this.scale = scale;
    }

    public static Transform identity() {
        return new Transform();
    }

    public Transform inverse() {
        return new Transform(
                translation.copy().mul(-1.0f),
                -rotation,
                Vector2.one().div(scale));
    }

    public Transform copy() {
        return new Transform(translation.copy(), rotation, scale.copy());
    }

    public Vector2 getTranslation() {
        return translation;
    }

    public void setTranslation(Vector2 translation) {
        this.translation = translation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Vector2 getScale() {
        return scale;
    }

    public void setScale(Vector2 scale) {
        this.scale = scale;
    }

    public Transform applyTo(Transform other) {
        other.getTranslation()
                .mul(scale)
                .rotate(rotation)
                .add(translation);
        other.setRotation(rotation + other.getRotation());
        other.getScale().mul(scale);

        return other;
    }

    public Vector2 applyTo(Vector2 point) {
        return point.mul(scale)
                .rotate(rotation)
                .add(translation);
    }
}