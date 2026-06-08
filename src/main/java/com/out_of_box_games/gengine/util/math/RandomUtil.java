package com.out_of_box_games.gengine.util.math;

import java.util.List;

public class RandomUtil {

    public static int randomInt(int begin, int end) {
        return begin + (int) (Math.random() * (end - begin));
    }

    public static float randomFloat(float begin, float end) {
        return begin + (float) (Math.random() * (end - begin));
    }

    public static <TValue> TValue getRandom(List<TValue> values) {
        int index = (int) (Math.random() * values.size());
        return values.get(index);
    }
}
