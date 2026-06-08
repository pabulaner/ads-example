package com.out_of_box_games.firewall.wave;

import com.out_of_box_games.gengine.util.math.RandomUtil;

import java.util.List;

public class WavePart {

    private static final List<WavePart> PARTS = List.of(
            new WavePart()
                    .setBudget(1)
                    .setCount(4)
                    .setHealth(1.0f)
                    .setOffset(1.5f),
            new WavePart()
                    .setBudget(2)
                    .setCount(8)
                    .setHealth(1.0f)
                    .setOffset(1.3f),
            new WavePart()
                    .setBudget(2)
                    .setCount(8)
                    .setHealth(1.0f)
                    .setOffset(0.5f),
            new WavePart()
                    .setBudget(5)
                    .setCount(6)
                    .setHealth(3.3f)
                    .setOffset(3.0f),
            new WavePart()
                    .setBudget(8)
                    .setCount(1)
                    .setHealth(8.0f)
                    .setOffset(4.0f));

    private int budget;

    private int count;

    private float health;

    private float offset;

    public static WavePart getRandom(int budget) {
        while (true) {
            WavePart part = RandomUtil.getRandom(PARTS);

            if (part.budget <= budget) {
                return part;
            }
        }
    }

    public float getOffset() {
        return offset;
    }

    public WavePart setOffset(float offset) {
        this.offset = offset;
        return this;
    }

    public float getHealth() {
        return health;
    }

    public WavePart setHealth(float health) {
        this.health = health;
        return this;
    }

    public int getCount() {
        return count;
    }

    public WavePart setCount(int count) {
        this.count = count;
        return this;
    }

    public int getBudget() {
        return budget;
    }

    public WavePart setBudget(int budget) {
        this.budget = budget;
        return this;
    }
}
