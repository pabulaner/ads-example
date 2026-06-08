package com.out_of_box_games.firewall.util;

public class BinaryUtil {

    private static final int LENGTH = 4;

    public static String toString(int value) {
        String binary = Integer.toBinaryString(value);
        return String.format("%" + LENGTH + "s", binary).replace(' ', '0');
    }
}
