package com.out_of_box_games.gengine.util.math;

import java.io.Serializable;
import java.util.Objects;

public class Vector2 implements Serializable {

    public float x;

    public float y;

    public Vector2() {
        this(0.0f);
    }

    public Vector2(float value) {
        this(value, value);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2 zero() {
        return new Vector2();
    }

    public static Vector2 one() {
        return new Vector2(1.0f);
    }

    public static Vector2 up() {
        return new Vector2(0.0f, -1.0f);
    }

    public static Vector2 down() {
        return new Vector2(0.0f, 1.0f);
    }

    public static Vector2 left() {
        return new Vector2(-1.0f, 0.0f);
    }

    public static Vector2 right() {
        return new Vector2(1.0f, 0.0f);
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }

    public Vector2 add(float value) {
        x += value;
        y += value;

        return this;
    }

    public Vector2 add(Vector2 other) {
        x += other.x;
        y += other.y;

        return this;
    }

    public Vector2 sub(float value) {
        x -= value;
        y -= value;

        return this;
    }

    public Vector2 sub(Vector2 other) {
        x -= other.x;
        y -= other.y;

        return this;
    }

    public Vector2 mul(float value) {
        x *= value;
        y *= value;

        return this;
    }

    public Vector2 mul(Vector2 other) {
        x *= other.x;
        y *= other.y;

        return this;
    }

    public Vector2 div(float value) {
        x /= value;
        y /= value;

        return this;
    }

    public Vector2 div(Vector2 other) {
        x /= other.x;
        y /= other.y;

        return this;
    }

    public Vector2 normalize() {
        float length = length();

        x /= length;
        y /= length;

        return this;
    }

    public float length() {
        return MathUtil.sqrt(lengthSquared());
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public float dotProduct(Vector2 other) {
        return x * other.x + y * other.y;
    }

    public Vector2 rotate(float angle) {
        float tmp = x * MathUtil.sin(angle) + y * MathUtil.cos(angle);
        x = x * MathUtil.cos(angle) - y * MathUtil.sin(angle);
        y = tmp;

        return this;
    }

    public float scalarProjection(Vector2 other) {
        return dotProduct(other) / other.length();
    }

    public Vector2 vectorProjection(Vector2 other) {
        return other.mul(dotProduct(other) / other.lengthSquared());
    }

    public Vector2Int toVector2Int() {
        return new Vector2Int((int) x, (int) y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Vector2 casted) {
            return x == casted.x && y == casted.y;
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("{ x: %f, y: %f }", x, y);
    }
}
