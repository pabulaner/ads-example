package com.out_of_box_games.gengine.util;

public class Color {

    public static final Color TRANSPARENT = Color.fromRgba(0x00000000);

    public static final Color BLACK = Color.fromRgba(0x000000FF);

    public static final Color WHITE = Color.fromRgba(0xFFFFFFFF);

    public static final Color RED = Color.fromRgba(0xFF0000FF);

    private final int rgba;

    private Color(int rgba) {
        this.rgba = rgba;
    }

    public static Color fromRgba(int rgba) {
        return new Color(rgba);
    }

    public static Color fromRgba(int r, int g, int b, int a) {
        return new Color((r << 24) | (g << 16) | (b << 8) | a);
    }

    public int getRgba() {
        return rgba;
    }

    public int getRed() {
        return rgba >>> 24;
    }

    public int getGreen() {
        return (rgba >> 16) & 0xFF;
    }

    public int getBlue() {
        return (rgba >> 8) & 0xFF;
    }

    public int getAlpha() {
        return rgba & 0xFF;
    }

    @Override
    public String toString() {
        return String.format("%08X", rgba);
    }
}
