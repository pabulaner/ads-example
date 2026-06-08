package com.out_of_box_games.firewall.wave;

import com.out_of_box_games.firewall.data.EnemyType;

import java.util.List;
import java.util.function.Function;

public class WaveContext {

    private int begins;

    private int ends;

    private int budget;

    private Function<EnemyType, Float> health;

    private List<EnemyType> enemies;

    public int getBegins() {
        return begins;
    }

    public WaveContext setBegins(int begins) {
        this.begins = begins;
        return this;
    }

    public int getEnds() {
        return ends;
    }

    public WaveContext setEnds(int ends) {
        this.ends = ends;
        return this;
    }

    public int getBudget() {
        return budget;
    }

    public WaveContext setBudget(int budget) {
        this.budget = budget;
        return this;
    }

    public float getHealth(EnemyType type) {
        return health.apply(type);
    }

    public WaveContext setHealth(Function<EnemyType, Float> health) {
        this.health = health;
        return this;
    }

    public List<EnemyType> getEnemies() {
        return enemies;
    }

    public WaveContext setEnemies(List<EnemyType> enemies) {
        this.enemies = enemies;
        return this;
    }
}
