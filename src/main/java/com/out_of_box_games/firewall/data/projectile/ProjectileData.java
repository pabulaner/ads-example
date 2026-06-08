package com.out_of_box_games.firewall.data.projectile;

import com.out_of_box_games.firewall.data.TowerType;
import com.out_of_box_games.gengine.data.TypeData;
import com.out_of_box_games.gengine.util.math.Vector2;

public class ProjectileData extends TypeData<ProjectileData, TowerType> {

    private int level;

    private float usage;

    private float distance;

    private Vector2 translation;

    private Vector2 direction;

    public int getLevel() {
        return level;
    }

    public ProjectileData setLevel(int level) {
        this.level = level;
        return this;
    }

    public float getUsage() {
        return usage;
    }

    public ProjectileData setUsage(float usage) {
        this.usage = usage;
        return this;
    }

    public float getDistance() {
        return distance;
    }

    public ProjectileData setDistance(float distance) {
        this.distance = distance;
        return this;
    }

    public Vector2 getTranslation() {
        return translation;
    }

    public ProjectileData setTranslation(Vector2 translation) {
        this.translation = translation;
        return this;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public ProjectileData setDirection(Vector2 direction) {
        this.direction = direction;
        return this;
    }
}
