package com.out_of_box_games.firewall.util;

public class MemoryUtil {

    private static final int BASE = 1000;

    private static final String[] UNITS = {
            "B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"
    };

    public static String toString(float value) {
        if (value < BASE) {
            value = (int) value;
        }

        for (String unit : UNITS) {
            if (value >= BASE) {
                value /= BASE;
            } else {
                return String.format("%.1f%s", value, unit);
            }
        }

        return (int) value + UNITS[UNITS.length - 1];
    }
}
