package com.out_of_box_games.firewall.data.enemy;

import com.out_of_box_games.firewall.data.EnemyType;
import com.out_of_box_games.gengine.data.TypeData;

import java.util.List;

public class EnemyData extends TypeData<EnemyData, EnemyType> {

    private int end;

    private float maxHealth;

    private float health;

    private float progress;

    private List<EnemyData> children;

    public int getEnd() {
        return end;
    }

    public EnemyData setEnd(int end) {
        this.end = end;
        return this;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public EnemyData setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
        return this;
    }

    public float getHealth() {
        return health;
    }

    public EnemyData setHealth(float health) {
        this.health = health;
        return this;
    }

    public float getProgress() {
        return progress;
    }

    public EnemyData setProgress(float progress) {
        this.progress = progress;
        return this;
    }

    public List<EnemyData> getChildren() {
        return children;
    }

    public EnemyData setChildren(List<EnemyData> children) {
        this.children = children;
        return this;
    }
}
