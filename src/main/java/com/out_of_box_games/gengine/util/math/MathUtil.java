package com.out_of_box_games.gengine.util.math;

public class MathUtil {

    public static final float PI = (float) Math.PI;

    public static final float PI2 = 2.0f * PI;

    public static final float HALF_ANGLE = 180.0f;

    public static final float FULL_ANGLE = 360.0f;

    private static final int SIN_BITS = 14;

    private static final int SIN_MASK = ~(-1 << SIN_BITS);

    private static final int SIN_COUNT = SIN_MASK + 1;

    private static final float[] SIN_TABLE = new float[SIN_COUNT];

    static {
        for (int i = 0; i < SIN_COUNT; i++) {
            SIN_TABLE[i] = (float) Math.sin((float) i / SIN_COUNT * PI * 2.0f);
        }
    }

    public static int sign(int value) {
        if (value == 0) {
            return 0;
        }

        return value > 0 ? 1 : -1;
    }

    public static float min(float x, float y) {
        return Math.min(x, y);
    }

    public static float max(float x, float y) {
        return Math.max(x, y);
    }

    public static float clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    public static float floor(float value) {
        return (float) Math.floor(value);
    }

    public static float ceil(float value) {
        return (float) Math.ceil(value);
    }

    public static float round(float value) {
        return (float) Math.round(value);
    }

    public static float pow(float value, float exp) {
        return (float) Math.pow(value, exp);
    }

    public static float interpolate(float begin, float end, float value) {
        return begin + (end - begin) * value;
    }

    public static float toRadians(float degrees) {
        return degrees * PI / 180.0f;
    }

    public static float toDegrees(float radians) {
        return radians * 180.0f / PI;
    }

    public static float sin(float angle) {
        return SIN_TABLE[(int) (angle / 360.0f * SIN_COUNT) & SIN_MASK];
    }

    public static float cos(float angle) {
        return sin(angle + 90.0f);
    }

    public static float sqrt(float value) {
        return (float) Math.sqrt(value);
    }
}
